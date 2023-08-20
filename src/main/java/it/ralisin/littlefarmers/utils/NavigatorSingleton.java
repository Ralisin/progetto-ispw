package it.ralisin.littlefarmers.utils;

import it.ralisin.littlefarmers.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class NavigatorSingleton {
    private static NavigatorSingleton instance = null;
    private static final String BORDER_PANE = "#borderPane";
    private final Stage mainStg;
    private Stage loginStg = null;
    private Stage cartStg = null;
    private Stage ordersStg = null;

    public Stage getMainStg() {
        return mainStg;
    }

    public Stage getLoginStg() {
        return loginStg;
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
        BorderPane bP = (BorderPane) mainStg.getScene().lookup(BORDER_PANE);

        Parent newCenter = null;
        if(fxml != null)
            newCenter = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxml)));

        bP.setTop(newCenter);
    }

    public void gotoCenterPageMain(String fxml) throws IOException {
        // lookup is a CSS selector, '#' is used to target elements by their id
        BorderPane bP = (BorderPane) mainStg.getScene().lookup(BORDER_PANE);

        Parent newCenter = null;
        if(fxml != null)
            newCenter = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxml)));

        bP.setCenter(newCenter);
    }

    public void gotoLeftPageMain(String fxml) throws IOException {
        // lookup is a CSS selector, '#' is used to target elements by their id
        BorderPane bP = (BorderPane) mainStg.getScene().lookup(BORDER_PANE);

        Parent newCenter = null;
        if(fxml != null)
            newCenter = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxml)));

        bP.setLeft(newCenter);
    }

    public void gotoLoginSignUpPage(String fxml, boolean signUp) throws IOException {
        if(loginStg != null) loginStg.close();

        TabPane tabPane = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxml)));
        if(signUp) tabPane.getSelectionModel().selectLast();

        Scene scene = new Scene(tabPane, 233, 327);

        loginStg = new Stage();
        loginStg.setResizable(false);
        loginStg.setScene(scene);

        loginStg.setOnCloseRequest(event -> {
            event.consume();

            loginStg.close();
            loginStg = null;

            mainStg.getScene().getRoot().setDisable(false);
        });

        mainStg.getScene().getRoot().setDisable(true);
        loginStg.show();
    }
}
