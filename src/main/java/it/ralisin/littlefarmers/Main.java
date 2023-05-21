package it.ralisin.littlefarmers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class Main extends Application {
    public void start(Stage stage) throws IOException {
        String firstPage = "HomeScreen.fxml";

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(firstPage));
        Scene scene = new Scene(fxmlLoader.load(), 720, 480);
        stage.setTitle("LittleFarmers");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        System.out.println("Seleziona la view:\n 1- Interfaccia grafica\n 2- Linea di comando");

        while(true) {
            choice = scanner.nextInt();

            if(choice == 1) {
                launch();
                break;
            }
            else if(choice == 2) {
                // TODO: Second view
                break;
            }
            else System.out.println("Valore inserito non valido");
        }
    }
}
