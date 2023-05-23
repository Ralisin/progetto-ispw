package it.ralisin.littlefarmers.controller;

import javafx.event.ActionEvent;

public class HomeController {
    public String onClickRegionButton(ActionEvent event) {
        String str = event.getSource().toString();

        String regionName = str.substring(str.indexOf("'")+1, str.lastIndexOf("'"));

        event.getSource().getClass().getResource("RegionScreen.fxml");

        return regionName;
    }
}
