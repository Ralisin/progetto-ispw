package it.ralisin.littlefarmers;

import it.ralisin.littlefarmers.utils.NavigatorSingleton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    public void start(Stage stage) throws IOException {
        String firstPage = "Home.fxml";

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(firstPage)));
        Scene scene = new Scene(root, 900, 650);

        NavigatorSingleton n = NavigatorSingleton.getInstance(stage);

        n.getStg().setTitle("LittleFarmers");
        n.getStg().setResizable(false);
        n.getStg().setScene(scene);
        n.getStg().show();
    }

    public static void main(String[] args) {
        launch(); // TODO: implement second view
    }
}
