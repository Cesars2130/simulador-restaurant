package com.concurrente.restaurant;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

import java.util.HashMap;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

public class FXGLController {

    private final Map<String, Entity> dynamicEntities = new HashMap<>();

    // Crear un comensal en la interfaz
    public void addComensal(String id, double x, double y) {
        Entity comensal = entityBuilder()
                .at(x, y)
                .view(texture("comensal_cola.png"))
                .buildAndAttach();
        dynamicEntities.put(id, comensal);
    }

    // Cambiar estado de un comensal (ejemplo: sentado, esperando)
    public void updateComensalState(String id, String newState) {
        Entity comensal = dynamicEntities.get(id);
        if (comensal != null) {
            if (newState.equals("sentado")) {
                comensal.getViewComponent().setView(texture("comensal_sentado.png"));
            } else if (newState.equals("esperando")) {
                comensal.getViewComponent().setView(texture("comensal_cola.png"));
            }
        }
    }

    // Crear y animar un mesero llevando un plato
    public void servePlato(String mesaId) {
        Entity mesa = dynamicEntities.get(mesaId);
        if (mesa != null) {
            double mesaX = mesa.getX();
            double mesaY = mesa.getY();

            Entity mesero = entityBuilder()
                    .at(150, 50) // Posición inicial
                    .view(texture("mesero.png"))
                    .buildAndAttach();

            animateMesero(mesero, mesaX, mesaY, () -> {
                spawnPlato(mesaX, mesaY);
                mesero.removeFromWorld();
            });
        }
    }

    private void animateMesero(Entity mesero, double targetX, double targetY, Runnable onComplete) {
        FXGL.runOnce(() -> {
            mesero.translateX(targetX - mesero.getX());
            mesero.translateY(targetY - mesero.getY());
            onComplete.run();
        }, Duration.seconds(2));
    }

    private void spawnPlato(double x, double y) {
        entityBuilder()
                .at(x + 10, y - 10)
                .view(texture("plato.png"))
                .buildAndAttach();
    }

    public void removeEntity(String id) {
        Entity entity = dynamicEntities.get(id);
        if (entity != null) {
            entity.removeFromWorld();
            dynamicEntities.remove(id);
        }
    }
}
