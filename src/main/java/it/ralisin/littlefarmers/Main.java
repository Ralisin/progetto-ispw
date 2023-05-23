package it.ralisin.littlefarmers;

import it.ralisin.littlefarmers.utils.NavigatorSingleton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public void start(Stage stage) throws IOException {
        String firstPage = "HomeScreen.fxml";

        Parent root = FXMLLoader.load(getClass().getResource(firstPage));
        Scene scene = new Scene(root, 720, 480);

        NavigatorSingleton n = NavigatorSingleton.getInstance(stage);

        n.getStg().setTitle("LittleFarmers");
        n.getStg().setScene(scene);
        n.getStg().show();
    }

    public static void main(String[] args) {
        launch();
        /*
        Sample code to have basic two views
        Scanner scanner = new Scanner(System.in);
        int choice;
        System.out.println("Seleziona la view:\n 1- Interfaccia grafica\n 2- Linea di comando");

        while(true) {
            choice = scanner.nextInt();

            if(choice == 1) {
                launch();
                break;
            }
            else if(choice == 2) { // TODO: Second view
            }
            else System.out.println("Valore inserito non valido");
        }
         */
    }
}
