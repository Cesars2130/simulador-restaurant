package com.concurrente.restaurant;

import com.almasb.fxgl.entity.Entity;
import javafx.application.Platform;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

public class FXGLController implements Observer {

    private final Map<String, Entity> dynamicEntities = new HashMap<>();
    private final List<String> mesasLibres = new ArrayList<>();
    private final List<String> mesasOcupadas = new ArrayList<>();

    public FXGLController(EventBus eventBus) {
        eventBus.subscribe(this);

        // Agrega el fondo al inicio
        Platform.runLater(this::addBackground);

        // Inicializa mesas estáticas con IDs
        Platform.runLater(() -> {
            for (int i = 1; i <= 5; i++) { // Ejemplo de 5 mesas
                String mesaId = "mesa_" + i;
                Entity mesa = entityBuilder()
                        .at(200 + (i - 1) * 120, 200) // Posiciones de las mesas
                        .view(scaledTexture("mesa.png", 100, 50)) // Mesa con tamaño ajustado
                        .buildAndAttach();
                dynamicEntities.put(mesaId, mesa);

                // Todas las mesas están inicialmente libres
                mesasLibres.add(mesaId);
            }

            // Inicializa la representación del chef
            Entity chef = entityBuilder()
                    .at(100, 50) // Posición inicial del chef
                    .view(scaledTexture("chef.png", 60, 80)) // Tamaño ajustado del chef
                    .buildAndAttach();
            dynamicEntities.put("chef", chef);
        });
    }

    // Agrega un fondo a la escena
    private void addBackground() {
        Entity background = entityBuilder()
                .at(0, 0)
                .view(scaledTexture("bg.png", getAppWidth(), getAppHeight())) // Fondo ajustado al tamaño de la ventana
                .buildAndAttach();
        dynamicEntities.put("background", background);
    }

    // Crear un comensal en la interfaz
    public void addComensal(String id, double x, double y) {
        Platform.runLater(() -> {
            Entity comensal = entityBuilder()
                    .at(x, y)
                    .view(scaledTexture("comensal_cola.png", 40, 60)) // Tamaño ajustado del comensal
                    .buildAndAttach();
            dynamicEntities.put(id, comensal);

            // Asignar una mesa libre
            if (!mesasLibres.isEmpty()) {
                String mesaId = mesasLibres.remove(0); // Toma la primera mesa libre
                mesasOcupadas.add(mesaId); // Marca la mesa como ocupada

                // Actualiza la vista de la mesa para indicar que está ocupada
                Entity mesa = dynamicEntities.get(mesaId);
                if (mesa != null) {
                    // Mueve al comensal a la mesa
                    comensal.setX(mesa.getX() + 20); // Ajusta la posición para que se vea sobre la mesa
                    comensal.setY(mesa.getY() - 30); // Ajusta la posición vertical
                    comensal.getViewComponent().clearChildren();
                    comensal.getViewComponent().addChild(scaledTexture("comensal_sentado.png", 40, 60)); // Cambia el asset a "sentado"

                    System.out.println("Comensal " + id + " asignado a " + mesaId);
                }
            } else {
                System.out.println("No hay mesas disponibles para el comensal " + id);
            }
        });
    }

    // Liberar una mesa cuando un comensal se va
    public void liberarMesa(String mesaId) {
        Platform.runLater(() -> {
            if (mesasOcupadas.contains(mesaId)) {
                mesasOcupadas.remove(mesaId);
                mesasLibres.add(mesaId); // Marca la mesa como libre

                // Actualiza la vista de la mesa para indicar que está libre
                Entity mesa = dynamicEntities.get(mesaId);
                if (mesa != null) {
                    mesa.getViewComponent().clearChildren();
                    mesa.getViewComponent().addChild(scaledTexture("mesa.png", 100, 50)); // Textura original para mesa libre

                    // Encuentra y elimina al comensal asociado
                    Entity comensal = dynamicEntities.values().stream()
                            .filter(e -> e.getViewComponent().getChildren().contains(scaledTexture("comensal_sentado.png", 40, 60)))
                            .findFirst().orElse(null);
                    if (comensal != null) {
                        comensal.removeFromWorld(); // Remueve al comensal del mundo
                        dynamicEntities.remove(comensal);
                    }

                    System.out.println(mesaId + " está ahora libre.");
                }
            }
        });
    }

    @Override
    public void update(String event, Object data) {
        switch (event) {
            case "NEW_COMENSAL":
                String id = (String) data;
                addComensal(id, 50, Math.random() * 700); // Posición inicial
                break;
            case "COMENSAL_SALE":
                liberarMesa((String) data); // Libera la mesa asociada al comensal
                break;
            case "UPDATE_COMENSAL":
                Map<String, String> stateChange = (Map<String, String>) data;
                updateComensalState(stateChange.get("id"), stateChange.get("state"));
                break;
            case "SERVE_PLATO":
                servePlato(); // Sirve en la primera mesa ocupada
                break;
            case "CHEF_COOKING":
                setChefAsset("chef_cooking.png");
                break;
            case "CHEF_COOKED":
                setChefAsset("chef.png");
                break;
            default:
                System.out.println("Evento no reconocido: " + event);
        }
    }

    // Cambiar el asset del chef dependiendo del evento
    private void setChefAsset(String assetName) {
        Platform.runLater(() -> {
            Entity chef = dynamicEntities.get("chef");
            if (chef != null) {
                chef.getViewComponent().clearChildren();
                chef.getViewComponent().addChild(scaledTexture(assetName, 60, 80));
                System.out.println("Chef ahora usa el asset: " + assetName);
            }
        });
    }

    // Cambiar estado de un comensal
    public void updateComensalState(String id, String newState) {
        Platform.runLater(() -> {
            Entity comensal = dynamicEntities.get(id);
            if (comensal != null) {
                comensal.getViewComponent().clearChildren();
                if (newState.equals("sentado")) {
                    comensal.getViewComponent().addChild(scaledTexture("comensal_sentado.png", 40, 60));
                } else if (newState.equals("esperando")) {
                    comensal.getViewComponent().addChild(scaledTexture("comensal_cola.png", 40, 60));
                }
            }
        });
    }

    public void servePlato() {
        Platform.runLater(() -> {
            if (!mesasOcupadas.isEmpty()) {
                // Obtiene la primera mesa ocupada
                String mesaId = mesasOcupadas.get(0); // No la remueve, solo sirve
                Entity mesa = dynamicEntities.get(mesaId);

                if (mesa != null) {
                    double mesaX = mesa.getX();
                    double mesaY = mesa.getY();

                    // Crea un mesero y lo anima hacia la mesa
                    Entity mesero = entityBuilder()
                            .at(150, 50)
                            .view(scaledTexture("mesero.png", 40, 80)) // Tamaño ajustado del mesero
                            .buildAndAttach();

                    mesero.translateX(mesaX - mesero.getX());
                    mesero.translateY(mesaY - mesero.getY());
                    spawnPlato(mesaX, mesaY);
                    mesero.removeFromWorld();
                }
            } else {
                System.out.println("No hay mesas ocupadas para servir.");
            }
        });
    }

    private void spawnPlato(double x, double y) {
        Platform.runLater(() -> {
            entityBuilder()
                    .at(x + 10, y - 10)
                    .view(scaledTexture("plato.png", 20, 20)) // Tamaño ajustado del plato
                    .buildAndAttach();
        });
    }

    // Método utilitario para escalar texturas
    private ImageView scaledTexture(String textureName, double width, double height) {
        ImageView imageView = texture(textureName);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        return imageView;
    }
}