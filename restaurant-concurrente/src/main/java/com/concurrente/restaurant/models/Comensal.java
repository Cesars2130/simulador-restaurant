package com.example.simulator.models;

import com.example.simulator.HelloController;
import com.example.simulator.Restaurante;
import javafx.application.Platform;
import java.util.Random;

public class Comensal implements Runnable {
    private Restaurante restaurante;
    private HelloController controller;
    private Recepcionista recepcionista;

    public Comensal(Restaurante restaurante, HelloController controller, Recepcionista recepcionista) {
        this.restaurante = restaurante;
        this.controller = controller;
        this.recepcionista = recepcionista;
    }

    @Override
    public void run() {
        while (true) {
            try {
                entrarRestaurante();

                // Intentar contactar al mesero
                if (controller != null) {
                    Platform.runLater(() ->
                            controller.updateMeseroStatus("Llamando al mesero"));
                }

                // Simular tiempo dentro del restaurante
                Thread.sleep(5000);

                // Tiempo aleatorio de comida antes de abandonar el restaurante
                int tiempoComida = new Random().nextInt(5000) + 3000;
                Thread.sleep(tiempoComida);

                // Actualizar el estado del comensal antes de salir
                if (controller != null) {
                    Platform.runLater(() -> controller.updateComensalStatus("Saliendo del restaurante"));
                }

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
