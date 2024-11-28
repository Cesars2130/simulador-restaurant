package com.concurrente.restaurant;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

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

        // Inicializa mesas estáticas con IDs
        for (int i = 1; i <= 5; i++) { // Ejemplo de 5 mesas
            String mesaId = "mesa_" + i;
            Entity mesa = entityBuilder()
                    .at(200 + (i - 1) * 120, 200) // Posiciones de las mesas
                    .view(texture("mesa.png"))
                    .buildAndAttach();
            dynamicEntities.put(mesaId, mesa);

            // Todas las mesas están inicialmente libres
            mesasLibres.add(mesaId);
        }

        // Inicializa la representación del chef
        Entity chef = entityBuilder()
                .at(100, 50) // Posición inicial del chef
                .view(texture("chef.png"))
                .buildAndAttach();
        dynamicEntities.put("chef", chef);
    }

    // Crear un comensal en la interfaz
    public void addComensal(String id, double x, double y) {
        Entity comensal = entityBuilder()
                .at(x, y)
                .view(texture("comensal_cola.png"))
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
                comensal.getViewComponent().addChild(texture("comensal_sentado.png")); // Cambia el asset a "sentado"

                System.out.println("Comensal " + id + " asignado a " + mesaId);
            }
        } else {
            System.out.println("No hay mesas disponibles para el comensal " + id);
        }
    }

    // Liberar una mesa cuando un comensal se va
    public void liberarMesa(String mesaId) {
        if (mesasOcupadas.contains(mesaId)) {
            mesasOcupadas.remove(mesaId);
            mesasLibres.add(mesaId); // Marca la mesa como libre

            // Actualiza la vista de la mesa para indicar que está libre
            Entity mesa = dynamicEntities.get(mesaId);
            if (mesa != null) {
                mesa.getViewComponent().clearChildren();
                mesa.getViewComponent().addChild(texture("mesa.png")); // Textura original para mesa libre

                // Encuentra y elimina al comensal asociado
                Entity comensal = dynamicEntities.values().stream()
                        .filter(e -> e.getViewComponent().getChildren().contains(texture("comensal_sentado.png")))
                        .findFirst().orElse(null);
                if (comensal != null) {
                    comensal.removeFromWorld(); // Remueve al comensal del mundo
                    dynamicEntities.remove(comensal);
                }

                System.out.println(mesaId + " está ahora libre.");
            }
        }
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
        Entity chef = dynamicEntities.get("chef");
        if (chef != null) {
            chef.getViewComponent().clearChildren();
            chef.getViewComponent().addChild(texture(assetName));
            System.out.println("Chef ahora usa el asset: " + assetName);
        }
    }

    // Cambiar estado de un comensal
    public void updateComensalState(String id, String newState) {
        Entity comensal = dynamicEntities.get(id);
        if (comensal != null) {
            comensal.getViewComponent().clearChildren();
            if (newState.equals("sentado")) {
                comensal.getViewComponent().addChild(texture("comensal_sentado.png"));
            } else if (newState.equals("esperando")) {
                comensal.getViewComponent().addChild(texture("comensal_cola.png"));
            }
        }
    }

    public void servePlato() {
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
                        .view(texture("mesero.png"))
                        .buildAndAttach();

                FXGL.runOnce(() -> {
                    mesero.translateX(mesaX - mesero.getX());
                    mesero.translateY(mesaY - mesero.getY());
                    spawnPlato(mesaX, mesaY);
                    mesero.removeFromWorld();
                }, javafx.util.Duration.seconds(2));
            }
        } else {
            System.out.println("No hay mesas ocupadas para servir.");
        }
    }

    private void spawnPlato(double x, double y) {
        entityBuilder()
                .at(x + 10, y - 10)
                .view(texture("plato.png"))
                .buildAndAttach();
    }
}
