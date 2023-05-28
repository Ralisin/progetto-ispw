package it.ralisin.littlefarmers.controller;

import it.ralisin.littlefarmers.Main;
import it.ralisin.littlefarmers.utils.NavigatorSingleton;
import javafx.event.ActionEvent;

import java.io.IOException;

public class HomeScreenController {
    public void onClickLogin(ActionEvent event) {
        System.out.println("Logged In");
    }

    public void onClickSignUp(ActionEvent event) {
        System.out.println("Signed Up");
    }

    public void onClickCart(ActionEvent event) {
        System.out.println("Cart");
    }

    public String onClickRegionButton(ActionEvent event) throws IOException {
        String str = event.getSource().toString();
        String regionName = str.substring(str.indexOf("'")+1, str.lastIndexOf("'"));
        System.out.println(regionName);


        NavigatorSingleton.getInstance().gotoPage("HomeScreen.fxml");

        System.out.println(Main.class.getResource("RegionProductsScreen.fxml"));

        return regionName;
    }
}
