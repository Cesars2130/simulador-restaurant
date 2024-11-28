package com.concurrente.restaurant.models;

import com.concurrente.restaurant.Restaurante;
import com.concurrente.restaurant.EventBus;
import javafx.application.Platform;
import java.util.Random;

public class Comensal implements Runnable {
    private int id;
    private Restaurante restaurante;
    private Recepcionista recepcionista;
    private EventBus eventBus;

    public Comensal(Restaurante restaurante, Recepcionista recepcionista, EventBus eventBus, int id) {
        this.restaurante = restaurante;
        this.recepcionista = recepcionista;
        this.eventBus = eventBus;
        this.id = id;
    }

    @Override
    public void run() {
        while (true) {
            try {
                entrarRestaurante();

                // Simular tiempo dentro del restaurante
                Thread.sleep(5000);

                // Tiempo aleatorio de comida antes de abandonar el restaurante
                int tiempoComida = new Random().nextInt(5000) + 3000;
                Thread.sleep(tiempoComida);

                eventBus.notifyObservers("COMENSAL_SALE", id);
                // Dejar el restaurante
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Método sincronizado para entrar al restaurante
    private synchronized void entrarRestaurante() throws InterruptedException {
        recepcionista.entrarComensal();
    }
}
