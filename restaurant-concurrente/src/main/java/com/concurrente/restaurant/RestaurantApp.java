package com.concurrente.restaurant;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.concurrente.restaurant.models.Chef;
import com.concurrente.restaurant.models.Mesero;
import com.concurrente.restaurant.models.Recepcionista;

public class RestaurantApp extends GameApplication {

    private Restaurante restaurante;
    private EventBus eventBus;
    private FXGLController controller;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("Simulador de Restaurante");
        settings.setVersion("1.0");
    }

    @Override
    protected void initGame() {
        // Inicialización del backend y el controlador
        restaurante = new Restaurante();
        eventBus = new EventBus();
        controller = new FXGLController(eventBus);

        // Creación de hilos para los modelos
        Thread chefThread = new Thread(new Chef(restaurante, eventBus));
        Thread recepcionistaThread = new Thread(new Recepcionista(restaurante, eventBus));
        Thread meseroThread = new Thread(new Mesero(restaurante, eventBus));

        int numMeseros = (int) (Restaurante.CAPACIDAD_MAXIMA * 0.3);
        Thread[] meserosThreads = new Thread[numMeseros];
        for (int i = 0; i < meserosThreads.length; i++) {
            meserosThreads[i] = new Thread(new Mesero(restaurante, eventBus));
        }

        // Iniciar los hilos
        chefThread.start();
        recepcionistaThread.start();
        meseroThread.start();
        for (Thread meseroIndividualThread : meserosThreads) {
            meseroIndividualThread.start();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
