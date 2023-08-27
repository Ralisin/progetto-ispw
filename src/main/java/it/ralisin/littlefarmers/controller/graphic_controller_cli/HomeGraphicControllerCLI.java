package it.ralisin.littlefarmers.controller.graphic_controller_cli;

import it.ralisin.littlefarmers.exeptions.InvalidFormatException;
import it.ralisin.littlefarmers.utils.CLIPrinter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeGraphicControllerCLI extends AbsGraphicControllerCLI {
    @Override
    public void start() {
        int choice = -1;
        while(choice == -1) {
            try {
                choice = showMenu();
                switch(choice) {
                    case 1 -> login();
                    case 2 -> signUp();
                    case 3 -> System.exit(0);
                    default -> {
                        choice = -1;
                        throw new InvalidFormatException("Invalid choice");
                    }
                }
            } catch (IOException | InvalidFormatException e) {
                Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
            }
        }
    }

    @Override
    public int showMenu() throws IOException {
        CLIPrinter.printf("*** LittleFarmers ***");
        CLIPrinter.printf("1- Login");
        CLIPrinter.printf("2- Sign Up");
        CLIPrinter.printf("3- Quit");

        return getMenuChoice(1, 3);
    }
}
