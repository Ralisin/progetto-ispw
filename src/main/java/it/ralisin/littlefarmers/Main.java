package it.ralisin.littlefarmers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Main extends Application {
    static Properties properties;
    static String propertiesPath = "src/main/resources/it/ralisin/littlefarmers/app.properties";
    public void start(Stage stage) throws IOException {
        String firstPage = "HomeScreen.fxml";

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(firstPage));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle(properties.getProperty("APP_NAME"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        properties = new Properties();
        properties.load(new FileInputStream(propertiesPath));

        launch();
    }
}
