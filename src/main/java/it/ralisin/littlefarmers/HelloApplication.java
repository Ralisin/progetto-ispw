package it.ralisin.littlefarmers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloApplication extends Application {
    static Logger logger = Logger.getLogger(HelloApplication.class.getName());
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        logger.log(Level.INFO, "Try 1");
        launch();
        logger.log(Level.INFO, "Try 2");
    }
}