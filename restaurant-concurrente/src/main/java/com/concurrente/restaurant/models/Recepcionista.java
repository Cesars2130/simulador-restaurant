package com.concurrente.restaurant.models;

import com.concurrente.restaurant.Restaurante;
import com.concurrente.restaurant.Orden;
import com.concurrente.restaurant.EventBus;

public class Recepcionista implements Runnable {
    private Restaurante restaurante;
    private EventBus eventBus;
    private int sum = 0;

    public Recepcionista(Restaurante restaurante, EventBus eventBus) {
        this.restaurante = restaurante;
        this.eventBus = eventBus;
    }
    
    synchronized void entrarComensal() throws InterruptedException {
        synchronized (restaurante) {
            Orden orden = new Orden(restaurante.comensalesEnRestaurante);

            restaurante.bufferOrdenes.offer(orden);

            restaurante.colaEspera.offer(new Comensal(restaurante, this, eventBus, (sum + 1)));

            while (restaurante.comensalesEnRestaurante >= Restaurante.CAPACIDAD_MAXIMA ||
                    restaurante.mesasOcupadas >= Restaurante.CAPACIDAD_MAXIMA) {
                System.out.println("Restaurante lleno. Comensal esperando afuera.");
                restaurante.wait();
            }

            restaurante.comensalesEnRestaurante++;
            sum = sum + 1;
            restaurante.mesasOcupadas++;
            String id = "comensal_" + sum;

            eventBus.notifyObservers("NEW_COMENSAL", id);

            System.out.println("Comensal entra al restaurante. Comensales en el restaurante: " + restaurante.comensalesEnRestaurante +
                    ". Mesas ocupadas: " + restaurante.mesasOcupadas);

            if (restaurante.comensalesEnRestaurante + 1 < Restaurante.CAPACIDAD_MAXIMA && restaurante.mesasOcupadas + 1 < Restaurante.CAPACIDAD_MAXIMA) {
                restaurante.bufferDeComidaListo = true;
                restaurante.notify();
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Simular tiempo de espera del recepcionista
                Thread.sleep(1000);
                System.out.println("Recepcionista desbloquea la entrada.");
                entrarComensal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}



