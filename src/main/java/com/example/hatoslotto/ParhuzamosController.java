package com.example.hatoslotto;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ParhuzamosController {

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    private Thread thread1;
    private Thread thread2;

    private boolean running = false;

    @FXML
    protected void onStartButtonClick() {
        if (running) {
            return; // Már futnak a szálak
        }
        running = true;

        // Szál 1: 1 másodpercenként frissíti a label1-et
        thread1 = new Thread(() -> {
            int counter = 0;
            while (running) {
                try {
                    Thread.sleep(1000); // 1 másodperc
                } catch (InterruptedException e) {
                    break;
                }
                int finalCounter = counter++;
                Platform.runLater(() -> label1.setText("Label 1: " + finalCounter));
            }
        });
        thread1.setDaemon(true); // A szál leáll az alkalmazással együtt
        thread1.start();

        // Szál 2: 2 másodpercenként frissíti a label2-t
        thread2 = new Thread(() -> {
            int counter = 0;
            while (running) {
                try {
                    Thread.sleep(2000); // 2 másodperc
                } catch (InterruptedException e) {
                    break;
                }
                int finalCounter = counter++;
                Platform.runLater(() -> label2.setText("Label 2: " + finalCounter));
            }
        });
        thread2.setDaemon(true); // A szál leáll az alkalmazással együtt
        thread2.start();
    }

    @FXML
    protected void onStopButtonClick() {
        running = false;
        if (thread1 != null) {
            thread1.interrupt();
        }
        if (thread2 != null) {
            thread2.interrupt();
        }
    }
}
