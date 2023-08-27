package it.ralisin.littlefarmers;

import it.ralisin.littlefarmers.controller.graphic_controller_CLI.HomeGraphicControllerCLI;

public class CLIMain {
    public static void main(String[] args) {
        HomeGraphicControllerCLI controllerCLI = new HomeGraphicControllerCLI();
        controllerCLI.start();
    }
}
