package it.ralisin.littlefarmers.controller;

import it.ralisin.littlefarmers.utils.NavigatorSingleton;
import javafx.event.ActionEvent;

import java.io.IOException;

public class HomeScreenController {
    public void onClickCompany(ActionEvent event) {
        System.out.println(event.getSource().toString());
    }

    public void onClickUser(ActionEvent actionEvent) throws IOException {
        NavigatorSingleton.getInstance().gotoPage("RegionListScreen.fxml");
    }

    public void onClickLogin(ActionEvent event) {
        System.out.println(event.getSource().toString());
    }
}
