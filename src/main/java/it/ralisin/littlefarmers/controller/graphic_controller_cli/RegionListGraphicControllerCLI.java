package it.ralisin.littlefarmers.controller.graphic_controller_cli;

import it.ralisin.littlefarmers.controller.app_controller.SessionController;
import it.ralisin.littlefarmers.enums.Regions;
import it.ralisin.littlefarmers.exeptions.InvalidFormatException;
import it.ralisin.littlefarmers.utils.CLIPrinter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegionListGraphicControllerCLI extends AbsGraphicControllerCLI {
    @Override
    public void start() {
        int choice = -1;
        while(choice == -1) {
            try {
                choice = showMenu();
                Regions region = null;
                switch(choice) {
                    case 1 -> region = Regions.ABRUZZO;
                    case 2 -> region = Regions.BASILICATA;
                    case 3 -> region = Regions.CALABRIA;
                    case 4 -> region = Regions.CAMPANIA;
                    case 5 -> region = Regions.EMILIAROMAGNA;
                    case 6 -> region = Regions.FRIULIVENEZIAGIULIA;
                    case 7 -> region = Regions.LAZIO;
                    case 8 -> region = Regions.LIGURIA;
                    case 9 -> region = Regions.LOMBARDIA;
                    case 10 -> region = Regions.MARCHE;
                    case 11 -> region = Regions.MOLISE;
                    case 12 -> region = Regions.PIEMONTE;
                    case 13 -> region = Regions.PUGLIA;
                    case 14 -> region = Regions.SARDEGNA;
                    case 15 -> region = Regions.SICILIA;
                    case 16 -> region = Regions.TOSCANA;
                    case 17 -> region = Regions.TRENTINOALTOADIGE;
                    case 18 -> region = Regions.UMBRIA;
                    case 19 -> region = Regions.VALDAOSTA;
                    case 20 -> region = Regions.VENETO;
                    case 0 -> {
                        new SessionController().logOut();
                        new HomeGraphicControllerCLI().start();
                    }
                    case 21 -> new CartGraphicControllerCLI().start();
                    default -> {
                        choice = -1;
                        throw new InvalidFormatException("Invalid choice");
                    }
                }

                if(region != null) {
                    RegionProductsGraphicControllerCLI regionProductsControllerCLI = new RegionProductsGraphicControllerCLI();
                    regionProductsControllerCLI.setRegion(region);
                    regionProductsControllerCLI.start();
                }
            } catch (IOException | InvalidFormatException e) {
                Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
            }
        }
    }

    @Override
    public int showMenu() throws IOException {
        CLIPrinter.printf("*** Select region you want products ***");
        CLIPrinter.printf("1- Abruzzo          6- Friuli     11- Molise    16- Toscana");
        CLIPrinter.printf("2- Basilicata       7- Lazio      12- Piemonte  17- Trentino Alto Adige");
        CLIPrinter.printf("3- Calabria         8- Liguria    13- Puglia    18- Umbria");
        CLIPrinter.printf("4- Campania         9- Lombardia  14- Sardegna  19- Val d'Aosta");
        CLIPrinter.printf("5- Emilia Romagna  10- Marche     15- Sicilia   20- Veneto");
        CLIPrinter.printf("0- LogOut    21- Cart");

        return getMenuChoice(0, 21);
    }
}
