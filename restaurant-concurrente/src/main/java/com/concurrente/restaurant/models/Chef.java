package com.concurrente.restaurant.models;

import com.concurrente.restaurant.Restaurante;
import com.concurrente.restaurant.Orden;
import com.concurrente.restaurant.Comida;
import com.concurrente.restaurant.HelloController;


public class Chef implements Runnable {
    private Restaurante restaurante;
    private HelloController controller;
    private volatile boolean running = true;

    public Chef(Restaurante restaurante, HelloController controller) {
        this.restaurante = restaurante;
        this.controller = controller;
    }

    public void cocinar() throws InterruptedException {
        synchronized (restaurante) {
            while (restaurante.bufferOrdenes.isEmpty()) {
                restaurante.wait();
            }

            if (restaurante.bufferComidas.size() == 5) {
                restaurante.notify(); // Agrega esta línea para notificar al mesero
            } else if (restaurante.bufferComidas.isEmpty()) {
                Thread.sleep(4000);

                for (int i = 0; i < Restaurante.TAMANO_BUFFER_COMIDA; i++) {
                    Orden orden = restaurante.bufferOrdenes.poll();
                    Comida comida = new Comida(orden);
                    restaurante.bufferComidas.offer(comida);
                }
                System.out.println("Chef ha cocinado la orden. Comida en el buffer: " + restaurante.bufferComidas.size());
            }
        }
    }

    @Override
    public void run() {
        while (running) {
            try {
                cocinar();
                if (this.controller != null) {
                    this.controller.updateBufferOrdenesTextArea(restaurante.bufferOrdenes.size() + " TOTAL");
                    this.controller.updateBufferComidaTextArea(restaurante.bufferComidas.size() + " TOTAL");
                    this.controller.updateChefStatus("Cocinando");
                    this.controller.updateChefStatus("Chef ha cocinado la orden. Comida en el buffer: " + restaurante.bufferComidas.size());
                }
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
