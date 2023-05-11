package it.ralisin.littlefarmers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public void start(Stage stage) throws IOException {
        String firstPage = "HomeScreen.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(firstPage));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Title");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
