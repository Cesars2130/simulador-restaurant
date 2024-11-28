package com.concurrente.restaurant;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import com.example.simulator.models.Chef;
import com.example.simulator.models.Mesero;
import com.example.simulator.models.Comensal;
import com.example.simulator.models.Recepcionista;

public class HelloApplication extends Application {
    private HelloController controller;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 850, 750);

        this.controller = fxmlLoader.getController();
        controller.redirectSystemOutput(); // Llama al método para redirigir la salida de la consola

        Restaurante restaurante = new Restaurante();

        HelloApplication app = new HelloApplication();

        Thread chefThread = new Thread(new Chef(restaurante, this.controller));
        Thread meseroThread = new Thread(new Mesero(restaurante, this.controller));
        Thread recepcionistaThread = new Thread(new Recepcionista(restaurante, this.controller));

        int numMeseros = (int) (Restaurante.CAPACIDAD_MAXIMA * 0.3);
        Thread[] meserosThreads = new Thread[numMeseros];
        for (int i = 0; i < meserosThreads.length; i++) {
            meserosThreads[i] = new Thread(new Mesero(restaurante, this.controller));
        }

        chefThread.start();
        meseroThread.start();
        recepcionistaThread.start();

        for (Thread meseroIndividualThread : meserosThreads) {
            meseroIndividualThread.start();
        }

        stage.setTitle("DORIAN INDUSTRIES");
        stage.setScene(scene);
        stage.show();

    }


    public static void main(String[] args) {
        launch();
    }
}