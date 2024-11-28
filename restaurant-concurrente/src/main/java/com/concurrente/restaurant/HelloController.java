package com.concurrente.restaurant;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.application.Platform;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class HelloController {

    @FXML
    private Label bufferComidaLabel;
    @FXML
    private Label bufferOrdenesLabel;
    @FXML
    private TextArea cocineroTextArea;
    @FXML
    private TextArea comensalTextArea;
    @FXML
    private TextArea consoleTextArea;
    @FXML
    private TextArea meseroTextArea;
    @FXML
    private Label mesa1, mesa2, mesa3, mesa4, mesa5, mesa6, mesa7, mesa8, mesa9, mesa10, mesa11, mesa12, mesa13, mesa14, mesa15, mesa16, mesa17, mesa18, mesa19, mesa20;
    @FXML
    private Pane s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15, s16, s17, s18, s19, s20;
    Pane sMaster = s8;
    Label mesaMaster = mesa8;
    public synchronized void updateStatusPanelPane(int sum, String status) {
        System.out.println("Recibo esto: " + sum);
        System.out.println("Clase s: " +sum);
        if(1 == sum){
            if("ok" == status){
                Platform.runLater(() -> {
                    // Change to yellow color
                    s1.setStyle("-fx-background-color:  #D6F900");
                    mesa1.setText("C1");
                });
            }else if("orden" == status){
                Platform.runLater(() -> {
                    // Change to red color
                    s1.setStyle("-fx-background-color:  red");
                });
            }else if("comiendo" == status){
                Platform.runLater(() -> {
                    // Change to blue color
                    s1.setStyle("-fx-background-color:  #00EAF9");
                });
            }else{
                Platform.runLater(() -> {
                    // Change to red final color
                    s1.setStyle("-fx-background-color:  red");
                    mesa1.setText("EN ESPERA");
                });
            }
        }else if(2 == sum){
            if("ok" == status){
                Platform.runLater(() -> {
                    // Change to yellow color
                    s2.setStyle("-fx-background-color:  #D6F900");
                    mesa2.setText("C2");
                });
            }else if("orden" == status){
                Platform.runLater(() -> {
                    // Change to red color
                    s2.setStyle("-fx-background-color:  red");
                });
            }else if("comiendo" == status){
                Platform.runLater(() -> {
                    // Change to blue color
                    s2.setStyle("-fx-background-color:  #00EAF9");
                });
            }else{
                Platform.runLater(() -> {
                    // Change to yellow color
                    s2.setStyle("-fx-background-color:  red");
                    mesa2.setText("EN ESPERA");
                });
            }
        }else if(3 == sum){
            if("ok" == status){
                Platform.runLater(() -> {
                    // Change to yellow color
                    s3.setStyle("-fx-background-color:  #D6F900");
                    mesa3.setText("C3");
                });
            }else if("orden" == status){
                Platform.runLater(() -> {
                    // Change to red color
                    s3.setStyle("-fx-background-color:  red");
                });
            }else if("comiendo" == status){
                Platform.runLater(() -> {
                    // Change to blue color
                    s3.setStyle("-fx-background-color:  #00EAF9");
                });
            }else{
                Platform.runLater(() -> {
                    // Change to yellow color
                    s3.setStyle("-fx-background-color:  red");
                    mesa3.setText("EN ESPERA");
                });
            }
        }else if(4 == sum){
            if("ok" == status){
                Platform.runLater(() -> {
                    // Change to yellow color
                    s4.setStyle("-fx-background-color:  #D6F900");
                    mesa4.setText("C4");
                });
            }else if("orden" == status){
                Platform.runLater(() -> {
                    // Change to red color
                    s4.setStyle("-fx-background-color:  red");
                });
            }else if("comiendo" == status){
                Platform.runLater(() -> {
                    // Change to blue color
                    s4.setStyle("-fx-background-color:  #00EAF9");
                });
            }else{
                Platform.runLater(() -> {
                    // Change to yellow color
                    s4.setStyle("-fx-background-color:  red");
                    mesa4.setText("EN ESPERA");
                });
            }
        }else if(5 == sum){
            if("ok" == status){
                Platform.runLater(() -> {
                    // Change to yellow color
                    s5.setStyle("-fx-background-color:  #D6F900");
                    mesa5.setText("C5");
                });
            }else if("orden" == status){
                Platform.runLater(() -> {
                    // Change to red color
                    s5.setStyle("-fx-background-color:  red");
                });
            }else if("comiendo" == status){
                Platform.runLater(() -> {
                    // Change to blue color
                    s5.setStyle("-fx-background-color:  #00EAF9");
                });
            }else{
                Platform.runLater(() -> {
                    // Change to yellow color
                    s5.setStyle("-fx-background-color:  red");
                    mesa5.setText("EN ESPERA");
                });
            }
        }else if(6 == sum){
            if("ok" == status){
                Platform.runLater(() -> {
                    // Change to yellow color
                    s6.setStyle("-fx-background-color:  #D6F900");
                    mesa6.setText("C6");
                });
            }else if("orden" == status){
                Platform.runLater(() -> {
                    // Change to red color
                    s6.setStyle("-fx-background-color:  red");
                });
            }else if("comiendo" == status){
                Platform.runLater(() -> {
                    // Change to blue color
                    s6.setStyle("-fx-background-color:  #00EAF9");
                });
            }else{
                Platform.runLater(() -> {
                    // Change to yellow color
                    s6.setStyle("-fx-background-color:  red");
                    mesa6.setText("EN ESPERA");
                });
            }
        }else if(7 == sum){
            if("ok" == status){
                Platform.runLater(() -> {
                    // Change to yellow color
                    s7.setStyle("-fx-background-color:  #D6F900");
                    mesa7.setText("C7");
                });
            }else if("orden" == status){
                Platform.runLater(() -> {
                    // Change to red color
                    s7.setStyle("-fx-background-color:  red");
                });
            }else if("comiendo" == status){
                Platform.runLater(() -> {
                    // Change to blue color
                    s7.setStyle("-fx-background-color:  #00EAF9");
                });
            }else{
                Platform.runLater(() -> {
                    // Change to yellow color
                    s7.setStyle("-fx-background-color:  red");
                    mesa7.setText("EN ESPERA");
                });
            }
        }else if(8 == sum){
            if("ok" == status){
                Platform.runLater(() -> {
                    // Change to yellow color
                    s8.setStyle("-fx-background-color:  #D6F900");
                    mesa8.setText("C8");
                });
            }else if("orden" == status){
                Platform.runLater(() -> {
                    // Change to red color
                    s8.setStyle("-fx-background-color:  red");
                });
            }else if("comiendo" == status){
                Platform.runLater(() -> {
                    // Change to blue color
                    s8.setStyle("-fx-background-color:  #00EAF9");
                });
            }else{
                Platform.runLater(() -> {
                    // Change to yellow color
                    s8.setStyle("-fx-background-color:  red");
                    mesa8.setText("EN ESPERA");
                });
            }
        }else if(9 == sum){
            if("ok" == status){
                Platform.runLater(() -> {
                    // Change to yellow color
                    s9.setStyle("-fx-background-color:  #D6F900");
                    mesa9.setText("C9");
                });
            }else if("orden" == status){
                Platform.runLater(() -> {
                    // Change to red color
                    s9.setStyle("-fx-background-color:  red");
                });
            }else if("comiendo" == status){
                Platform.runLater(() -> {
                    // Change to blue color
                    s9.setStyle("-fx-background-color:  #00EAF9");
                });
            }else{
                Platform.runLater(() -> {
                    // Change to yellow color
                    s9.setStyle("-fx-background-color:  red");
                    mesa9.setText("EN ESPERA");
                });
            }
        }else if(10 == sum){
            if("ok" == status){
                Platform.runLater(() -> {
                    // Change to yellow color
                    s10.setStyle("-fx-background-color:  #D6F900");
                    mesa10.setText("C10");
                });
            }else if("orden" == status){
                Platform.runLater(() -> {
                    // Change to red color
                    s10.setStyle("-fx-background-color:  red");
                });
            }else if("comiendo" == status){
                Platform.runLater(() -> {
                    // Change to blue color
                    s10.setStyle("-fx-background-color:  #00EAF9");
                });
            }else{
                Platform.runLater(() -> {
                    // Change to yellow color
                    s10.setStyle("-fx-background-color:  red");
                    mesa10.setText("EN ESPERA");
                });
            }
        }else if(11 == sum){
            if("ok" == status){
                Platform.runLater(() -> {
                    // Change to yellow color
                    s11.setStyle("-fx-background-color:  #D6F900");
                    mesa11.setText("C11");
                });
            }else if("orden" == status){
                Platform.runLater(() -> {
                    // Change to red color
                    s11.setStyle("-fx-background-color:  red");
                });
            }else if("comiendo" == status){
                Platform.runLater(() -> {
                    // Change to blue color
                    s11.setStyle("-fx-background-color:  #00EAF9");
                });
            }else{
                Platform.runLater(() -> {
                    // Change to yellow color
                    s11.setStyle("-fx-background-color:  red");
                    mesa11.setText("EN ESPERA");
                });
            }
        }else if(12 == sum){
            if("ok" == status){
                Platform.runLater(() -> {
                    // Change to yellow color
                    s12.setStyle("-fx-background-color:  #D6F900");
                    mesa12.setText("C12");
                });
            }else if("orden" == status){
                Platform.runLater(() -> {
                    // Change to red color
                    s12.setStyle("-fx-background-color:  red");
                });
            }else if("comiendo" == status){
                Platform.runLater(() -> {
                    // Change to blue color
                    s12.setStyle("-fx-background-color:  #00EAF9");
                });
            }else{
                Platform.runLater(() -> {
                    // Change to yellow color
                    s12.setStyle("-fx-background-color:  red");
                    mesa12.setText("EN ESPERA");
                });
            }
        }else if(13 == sum){
            if("ok" == status){
                Platform.runLater(() -> {
                    // Change to yellow color
                    s13.setStyle("-fx-background-color:  #D6F900");
                    mesa13.setText("C10");
                });
            }else if("orden" == status){
                Platform.runLater(() -> {
                    // Change to red color
                    s13.setStyle("-fx-background-color:  red");
                });
            }else if("comiendo" == status){
                Platform.runLater(() -> {
                    // Change to blue color
                    s13.setStyle("-fx-background-color:  #00EAF9");
                });
            }else{
                Platform.runLater(() -> {
                    // Change to yellow color
                    s13.setStyle("-fx-background-color:  red");
                    mesa13.setText("EN ESPERA");
                });
            }
        }else if(14 == sum){
            if("ok" == status){
                Platform.runLater(() -> {
                    // Change to yellow color
                    s14.setStyle("-fx-background-color:  #D6F900");
                    mesa14.setText("C14");
                });
            }else if("orden" == status){
                Platform.runLater(() -> {
                    // Change to red color
                    s14.setStyle("-fx-background-color:  red");
                });
            }else if("comiendo" == status){
                Platform.runLater(() -> {
                    // Change to blue color
                    s14.setStyle("-fx-background-color:  #00EAF9");
                });
            }else{
                Platform.runLater(() -> {
                    // Change to yellow color
                    s14.setStyle("-fx-background-color:  red");
                    mesa14.setText("EN ESPERA");
                });
            }
        }else if(15 == sum){
            if("ok" == status){
                Platform.runLater(() -> {
                    // Change to yellow color
                    s15.setStyle("-fx-background-color:  #D6F900");
                    mesa15.setText("C15");
                });
            }else if("orden" == status){
                Platform.runLater(() -> {
                    // Change to red color
                    s15.setStyle("-fx-background-color:  red");
                });
            }else if("comiendo" == status){
                Platform.runLater(() -> {
                    // Change to blue color
                    s15.setStyle("-fx-background-color:  #00EAF9");
                });
            }else{
                Platform.runLater(() -> {
                    // Change to yellow color
                    s15.setStyle("-fx-background-color:  red");
                    mesa15.setText("EN ESPERA");
                });
            }
        }else if(16 == sum){
            if("ok" == status){
                Platform.runLater(() -> {
                    // Change to yellow color
                    s16.setStyle("-fx-background-color:  #D6F900");
                    mesa16.setText("C16");
                });
            }else if("orden" == status){
                Platform.runLater(() -> {
                    // Change to red color
                    s16.setStyle("-fx-background-color:  red");
                });
            }else if("comiendo" == status){
                Platform.runLater(() -> {
                    // Change to blue color
                    s16.setStyle("-fx-background-color:  #00EAF9");
                });
            }else{
                Platform.runLater(() -> {
                    // Change to yellow color
                    s16.setStyle("-fx-background-color:  red");
                    mesa16.setText("EN ESPERA");
                });
            }
        }else if(17 == sum){
            if("ok" == status){
                Platform.runLater(() -> {
                    // Change to yellow color
                    s17.setStyle("-fx-background-color:  #D6F900");
                    mesa17.setText("C17");
                });
            }else if("orden" == status){
                Platform.runLater(() -> {
                    // Change to red color
                    s17.setStyle("-fx-background-color:  red");
                });
            }else if("comiendo" == status){
                Platform.runLater(() -> {
                    // Change to blue color
                    s17.setStyle("-fx-background-color:  #00EAF9");
                });
            }else{
                Platform.runLater(() -> {
                    // Change to yellow color
                    s17.setStyle("-fx-background-color:  red");
                    mesa17.setText("EN ESPERA");
                });
            }
        }else if(18 == sum){
            if("ok" == status){
                Platform.runLater(() -> {
                    // Change to yellow color
                    s18.setStyle("-fx-background-color:  #D6F900");
                    mesa18.setText("C18");
                });
            }else if("orden" == status){
                Platform.runLater(() -> {
                    // Change to red color
                    s18.setStyle("-fx-background-color:  red");
                });
            }else if("comiendo" == status){
                Platform.runLater(() -> {
                    // Change to blue color
                    s18.setStyle("-fx-background-color:  #00EAF9");
                });
            }else{
                Platform.runLater(() -> {
                    // Change to yellow color
                    s18.setStyle("-fx-background-color:  red");
                    mesa18.setText("EN ESPERA");
                });
            }
        }else if(19 == sum){
            if("ok" == status){
                Platform.runLater(() -> {
                    // Change to yellow color
                    s19.setStyle("-fx-background-color:  #D6F900");
                    mesa19.setText("C19");
                });
            }else if("orden" == status){
                Platform.runLater(() -> {
                    // Change to red color
                    s19.setStyle("-fx-background-color:  red");
                });
            }else if("comiendo" == status){
                Platform.runLater(() -> {
                    // Change to blue color
                    s19.setStyle("-fx-background-color:  #00EAF9");
                });
            }else{
                Platform.runLater(() -> {
                    // Change to yellow color
                    s19.setStyle("-fx-background-color:  red");
                    mesa19.setText("EN ESPERA");
                });
            }
        }else if(20 == sum){
            if("ok" == status){
                Platform.runLater(() -> {
                    // Change to yellow color
                    s20.setStyle("-fx-background-color:  #D6F900");
                    mesa20.setText("C20");
                });
            }else if("orden" == status){
                Platform.runLater(() -> {
                    // Change to red color
                    s20.setStyle("-fx-background-color:  red");
                });
            }else if("comiendo" == status){
                Platform.runLater(() -> {
                    // Change to blue color
                    s20.setStyle("-fx-background-color:  #00EAF9");
                });
            }else{
                Platform.runLater(() -> {
                    // Change to red final color
                    s20.setStyle("-fx-background-color:  red");
                    mesa20.setText("EN ESPERA");
                });
            }
        }
    }
    public void updateConsole(String message) {
        Platform.runLater(() -> {
            consoleTextArea.appendText(message + "\n");
        });
    }

    public synchronized void updateBufferComidaTextArea(String message) {
        Platform.runLater(() -> {
            bufferComidaLabel.setText(message);
        });
    }

    public synchronized void updateBufferOrdenesTextArea(String message) {
        Platform.runLater(() -> {
            bufferOrdenesLabel.setText(message);
        });
    }

    public synchronized void updateChefStatus(String status) {
        Platform.runLater(() -> cocineroTextArea.appendText("Chef: " + status + "\n"));
    }

    public synchronized void updateMeseroStatus(String status) {
        if(status!="clear"){
            Platform.runLater(() -> meseroTextArea.appendText("Mesero: " + status + "\n"));
        }else{
            Platform.runLater(() -> meseroTextArea.clear());
        }
    }

    public synchronized void updateComensalStatus(String status) {
        if(status!="clear"){
            Platform.runLater(() -> comensalTextArea.appendText("Comensal: " + status + "\n"));
        }else{
            Platform.runLater(() -> comensalTextArea.deleteText(0, 21));
        }
    }
    public void updateRecepcionistaStatus(String status) {
        // Platform.runLater(() -> recepcionistaLabel.setText("Recepcionista: " + status));
    }

    public void redirectSystemOutput() {
        OutputStream out = new OutputStream() {
            private StringBuilder buffer = new StringBuilder();

            @Override
            public void write(int b) throws IOException {
                if (b == '\n') {
                    updateConsole(buffer.toString());
                    //updateBufferComidaTextArea(bufferComidaLabel.toString());
                    buffer.setLength(0); // Limpia el buffer después de cada nueva línea
                } else {
                    buffer.append((char) b);
                }
            }
        };
        System.setOut(new PrintStream(out, true));
        System.setErr(new PrintStream(out, true));
    }

}

