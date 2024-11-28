package com.concurrente.restaurant.models;

import com.concurrente.restaurant.Restaurante;
import com.concurrente.restaurant.Orden;
import com.concurrente.restaurant.Comida;
import com.concurrente.restaurant.EventBus;

public class Chef implements Runnable {
    private final Restaurante restaurante;
    private final EventBus eventBus;
    private volatile boolean running = true;

    public Chef(Restaurante restaurante, EventBus eventBus) {
        this.restaurante = restaurante;
        this.eventBus = eventBus;
    }

    public void cocinar() throws InterruptedException {
        synchronized (restaurante) {
            while (restaurante.bufferOrdenes.isEmpty()) {
                restaurante.wait();
            }

            eventBus.notifyObservers("CHEF_COOKING", null);

            if (restaurante.bufferComidas.size() == 5) {
                restaurante.notify(); // Agrega esta línea para notificar al mesero
            } else if (restaurante.bufferComidas.isEmpty()) {
                Thread.sleep(4000);

                for (int i = 0; i < Restaurante.TAMANO_BUFFER_COMIDA; i++) {
                    Orden orden = restaurante.bufferOrdenes.poll();
                    Comida comida = new Comida(orden);
                    restaurante.bufferComidas.offer(comida);
                }
                eventBus.notifyObservers("CHEF_COOKED", restaurante.bufferComidas.size());
                System.out.println("Chef ha cocinado la orden. Comida en el buffer: " + restaurante.bufferComidas.size());
            }
        }
    }

    @Override
    public void run() {
        while (running) {
            try {
                cocinar();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }

    public void stopRunning() {
        running = false;
    }
}
