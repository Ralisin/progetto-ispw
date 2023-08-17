package it.ralisin.littlefarmers.utils;

import it.ralisin.littlefarmers.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class NavigatorSingleton {
    private static NavigatorSingleton instance = null;
    protected Stage stg;

    public Stage getStg() {return stg;}

    protected NavigatorSingleton(Stage stg) {this.stg = stg;}

    public static synchronized NavigatorSingleton getInstance() {return instance;}

    public static synchronized NavigatorSingleton getInstance(Stage stg) {
        if(instance == null) instance = new NavigatorSingleton(stg);
        return instance;
    }

    public void gotoPage(String fxml) throws IOException {
        Parent newRoot = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxml)));
        stg.getScene().setRoot(newRoot);
    }
}
