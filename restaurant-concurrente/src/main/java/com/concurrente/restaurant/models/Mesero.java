package com.example.simulator.models;

import com.example.simulator.HelloController;
import com.example.simulator.Restaurante;
import com.example.simulator.Comida;

public class Mesero implements Runnable {
    public Restaurante restaurante;
    private HelloController controller;
    private int sum = 0;
    private int firstNumber = 1;

    public Mesero(Restaurante restaurante, HelloController controller) {
        this.restaurante = restaurante;
        this.controller = controller;
    }

    public synchronized void verificarOrdenLista() throws InterruptedException {
        restaurante.verificarOrdenLista();
    }

    public synchronized void servirComida() throws InterruptedException {
        synchronized (restaurante) {
            while (restaurante.comensalesEnRestaurante <= 0) {
                if (firstNumber == 1) {
                    controller.updateMeseroStatus("clear");
                    firstNumber--;
                }
                controller.updateMeseroStatus("Mesero descansando");
                System.out.println("Mesero descansando...");
                restaurante.wait();
            }

            if (!restaurante.bufferComidas.isEmpty()) {
                sum = sum + 1;
                controller.updateStatusPanelPane(sum, "ok");
                Thread.sleep(2000);

                Comida comida = restaurante.bufferComidas.poll();
                controller.updateMeseroStatus("Mesero lleva la comida a un comensal");
                System.out.println("Mesero lleva la comida al comensal. Comida en el buffer: " + restaurante.bufferComidas.size());
                Thread.sleep(3000);
                System.out.println("Comensal ha terminado de comer.");
                controller.updateStatusPanelPane(sum, "orden");
                restaurante.mesasOcupadas--;
                restaurante.comensalesEnRestaurante--;
                controller.updateStatusPanelPane(sum, "comiendo");
                Thread.sleep(3000);
                controller.updateStatusPanelPane(sum, "salir");
                System.out.println("Comensal sale del restaurante. Comensales en el restaurante: " +
                        restaurante.comensalesEnRestaurante + ". Mesas ocupadas en el restaurante: " +
                        restaurante.mesasOcupadas);
                controller.updateComensalStatus("clear");

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
