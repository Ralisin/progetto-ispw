package it.ralisin.littlefarmers.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class MainController {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox vBox;
    private int currButton = 0;

    @FXML
    protected void onBasicButtonClick() {
        System.out.println("Prova");
        vBox.getChildren().add(new Button("Bottone " + currButton));
        currButton++;
    }
}
