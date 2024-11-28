package com.concurrente.restaurant.models;

import com.concurrente.restaurant.Restaurante;
import com.concurrente.restaurant.Comida;
import com.concurrente.restaurant.EventBus  ;

public class Mesero implements Runnable {
    public Restaurante restaurante;
    private int sum = 0;
    private int firstNumber = 1;
    private EventBus eventBus;

    public Mesero(Restaurante restaurante, EventBus eventBus) {
        this.restaurante = restaurante;
        this.eventBus = eventBus;
    }
    public synchronized void verificarOrdenLista() throws InterruptedException {
        restaurante.verificarOrdenLista();
    }

    public synchronized void servirComida() throws InterruptedException {
        synchronized (restaurante) {
            while (restaurante.comensalesEnRestaurante <= 0) {
                if (firstNumber == 1) {
                    firstNumber--;
                }
                System.out.println("Mesero descansando...");
                restaurante.wait();
            }

            if (!restaurante.bufferComidas.isEmpty()) {
                sum = sum + 1;
                //controller.updateStatusPanelPane(sum, "ok");
                Thread.sleep(2000);

                Comida comida = restaurante.bufferComidas.poll();

                restaurante.verificarOrdenLista();
                eventBus.notifyObservers("SERVE_PLATO", null);

                //controller.updateMeseroStatus("Mesero lleva la comida a un comensal");
                System.out.println("Mesero lleva la comida al comensal. Comida en el buffer: " + restaurante.bufferComidas.size());
                Thread.sleep(3000);
                System.out.println("Comensal ha terminado de comer.");
                //controller.updateStatusPanelPane(sum, "orden");
                restaurante.mesasOcupadas--;
                restaurante.comensalesEnRestaurante--;
                //controller.updateStatusPanelPane(sum, "comiendo");
                Thread.sleep(3000);
                //controller.updateStatusPanelPane(sum, "salir");
                System.out.println("Comensal sale del restaurante. Comensales en el restaurante: " +
                        restaurante.comensalesEnRestaurante + ". Mesas ocupadas en el restaurante: " +
                        restaurante.mesasOcupadas);
                
                System.out.print("notificar que el comensal esta saliendo");
                eventBus.notifyObservers("COMENSAL_SALE", null);

                restaurante.notify();
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                servirComida();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
