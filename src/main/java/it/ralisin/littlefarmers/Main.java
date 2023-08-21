package it.ralisin.littlefarmers;

import it.ralisin.littlefarmers.controller.graphic_controller.AbsCustomerGraphicController;
import it.ralisin.littlefarmers.utils.NavigatorSingleton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    public void start(Stage stage) throws IOException {
        String defaultTop = "HomeTop.fxml";
        String defaultCenter = "HomeCenter.fxml";

        // Get graphic components
        Parent top = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(defaultTop)));
        Parent center = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(defaultCenter)));

        BorderPane borderPane = new BorderPane();
        borderPane.setId("borderPane");

        // Attach graphic components to the border pane
        borderPane.setTop(top);
        borderPane.setCenter(center);

        Scene scene = new Scene(borderPane, 900, 650);

        // Create the navigator singleton
        NavigatorSingleton n = NavigatorSingleton.getInstance(stage);

        n.getMainStg().setTitle("LittleFarmers");
        n.getMainStg().setResizable(false);
        n.getMainStg().setScene(scene);
        n.getMainStg().show();
    }

    public static void main(String[] args) {
        launch(); // TODO: implement second view
    }
}
