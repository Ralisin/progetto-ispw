package it.ralisin.littlefarmers.controller.graphic_controller_cli;

import java.io.IOException;

public interface GraphicControllerCLIInterface {
    void start();

    int showMenu() throws IOException;
}
