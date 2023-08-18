package it.ralisin.littlefarmers.utils;

import it.ralisin.littlefarmers.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class NavigatorSingleton {
    private static NavigatorSingleton instance = null;
    private final Stage mainStg;
    private Stage loginStg;
    private Stage cartStg;
    private Stage ordersStg;

    private final String borderPane = "#borderPane";
    public Stage getMainStg() {
        return mainStg;
    }

    protected NavigatorSingleton(Stage stg) {
        this.mainStg = stg;
    }

    public static synchronized NavigatorSingleton getInstance() {
        return instance;
    }

    public static synchronized NavigatorSingleton getInstance(Stage stg) {
        if(instance == null) instance = new NavigatorSingleton(stg);
        return instance;
    }

    public void gotoTopPageMain(String fxml) throws IOException {
        // lookup is a CSS selector, '#' is used to target elements by their id
        BorderPane bP = (BorderPane) mainStg.getScene().lookup(borderPane);

        Parent newCenter = null;
        if(fxml != null)
            newCenter = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxml)));

        bP.setTop(newCenter);
    }

    public void gotoCenterPageMain(String fxml) throws IOException {
        // lookup is a CSS selector, '#' is used to target elements by their id
        BorderPane bP = (BorderPane) mainStg.getScene().lookup(borderPane);

        Parent newCenter = null;
        if(fxml != null)
            newCenter = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxml)));

        bP.setCenter(newCenter);
    }

    public void gotoLeftPageMain(String fxml) throws IOException {
        // lookup is a CSS selector, '#' is used to target elements by their id
        BorderPane bP = (BorderPane) mainStg.getScene().lookup(borderPane);

        Parent newCenter = null;
        if(fxml != null)
            newCenter = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxml)));

        bP.setLeft(newCenter);
    }
}
