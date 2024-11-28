package com.concurrente.restaurant;

import com.almasb.fxgl.entity.Entity;
import javafx.application.Platform;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

import com.almasb.fxgl.dsl.FXGL;

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
            // Genera 10 mesas en 2 filas de 5 mesas
            for (int i = 1; i <= 10; i++) {
                // Calcular la posición X (en la fila) y la posición Y (en la fila correspondiente)
                int fila = (i - 1) / 5;  // Esto genera 0 para la primera fila y 1 para la segunda fila
                int columna = (i - 1) % 5; // Esto genera las columnas de 0 a 4
                
                // Calcular las posiciones X y Y de las mesas
                double mesaX = 200 + columna * 120; // Espaciado horizontal
                double mesaY = 300 + fila * 130;    // Espaciado vertical entre filas
                
                String mesaId = "mesa_" + i;
                Entity mesa = entityBuilder()
                        .at(mesaX, mesaY)  // Posiciones de las mesas
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


    public void liberarMesa() {
        Platform.runLater(() -> {
            if (!mesasOcupadas.isEmpty()) {
                // Tomar la primera mesa de la lista de mesas ocupadas
                String mesaId = mesasOcupadas.remove(0);
                mesasLibres.add(mesaId);
    
                // Obtener la entidad de la mesa
                Entity mesa = dynamicEntities.get(mesaId);
                if (mesa != null) {
                    // Restaurar la vista original de la mesa
                    mesa.getViewComponent().clearChildren();
                    mesa.getViewComponent().addChild(scaledTexture("mesa.png", 100, 50));
    
                    // Eliminar el comensal asociado a esta mesa
                    dynamicEntities.entrySet().removeIf(entry -> {
                        if (entry.getKey().startsWith("comensal_")) {
                            Entity comensal = entry.getValue();
                            // Verificar si el comensal está en la posición de la mesa
                            if (comensal.getX() == mesa.getX() + 20 && comensal.getY() == mesa.getY() - 30) {
                                comensal.removeFromWorld();
                                return true; // Eliminar de dynamicEntities
                            }
                        }
                        return false;
                    });
    
                    // Eliminar el plato asociado a esta mesa
                    dynamicEntities.entrySet().removeIf(entry -> {
                        if (entry.getKey().startsWith("plato_")) {
                            Entity plato = entry.getValue();
                            // Verificar si el plato está cerca de la mesa
                            if (Math.abs(plato.getX() - mesa.getX() - 10) < 50 &&  // Ajuste en la coordenada X por el desplazamiento
                                Math.abs(plato.getY() - mesa.getY() + 10) < 50) {  // Ajuste en la coordenada Y por el desplazamiento
                                plato.removeFromWorld();
                                return true; // Eliminar de dynamicEntities
                            }
                        }
                        return false;
                    });
    
                    System.out.println("Mesa " + mesaId + " ahora está libre.");
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
                liberarMesa(); // Libera la mesa asociada al comensal
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
                // Crear una copia de la lista para evitar modificaciones concurrentes
                List<String> mesasOcupadasCopy = new ArrayList<>(mesasOcupadas);
    
                for (String mesaId : mesasOcupadasCopy) {
                    Entity mesa = dynamicEntities.get(mesaId);
                    if (mesa != null) {
                        // Verifica la existencia del plato en la mesa
                        boolean platoExiste = mesa.getViewComponent().getChildren().stream()
                            .anyMatch(node -> {
                                if (node instanceof ImageView) {
                                    ImageView imageView = (ImageView) node;
                                    return imageView != null
                                        && imageView.getImage() != null
                                        && imageView.getFitWidth() == 20
                                        && imageView.getFitHeight() == 20;
                                }
                                return false;
                            });
    
                        if (!platoExiste) {
                            // Obtiene la posición de la mesa
                            double mesaX = mesa.getX();
                            double mesaY = mesa.getY();
    
                            // Crea un mesero y lo anima hacia la mesa
                            Entity mesero = entityBuilder()
                                    .at(150, 50)
                                    .view(scaledTexture("mesero.png", 40, 80)) // Tamaño ajustado del mesero
                                    .buildAndAttach();
    
                            FXGL.animationBuilder()
                                    .duration(javafx.util.Duration.seconds(2))
                                    .translate(mesero)
                                    .from(mesero.getPosition())
                                    .to(mesa.getPosition())
                                    .buildAndPlay();
    
                            // Añade el plato tras la animación
                            FXGL.runOnce(() -> {
                                Entity plato = spawnPlato(mesaX, mesaY);
                                mesero.removeFromWorld(); // Elimina al mesero después de servir
    
                                // Después de servir el plato, elimínalo (por ejemplo, después de 5 segundos)
                                FXGL.runOnce(() -> {
                                    plato.removeFromWorld(); // Elimina el plato
                                }, javafx.util.Duration.seconds(5));
                            }, javafx.util.Duration.seconds(2));
    
                            break; // Salir después de servir una mesa
                        }
                    }
                }
            } else {
                System.out.println("No hay mesas ocupadas para servir.");
            }
        });
    }
    
    private Entity spawnPlato(double x, double y) {
        return entityBuilder()
                .at(x + 10, y - 10)
                .view(scaledTexture("plato.png", 20, 20)) // Tamaño ajustado del plato
                .buildAndAttach();
    }
    

    // Método utilitario para escalar texturas
    private ImageView scaledTexture(String textureName, double width, double height) {
        ImageView imageView = texture(textureName);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        return imageView;
    }
}