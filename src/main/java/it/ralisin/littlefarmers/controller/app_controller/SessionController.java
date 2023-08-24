package it.ralisin.littlefarmers.controller.app_controller;

import it.ralisin.littlefarmers.utils.SessionManager;

public class SessionController {
    public void logOut() {
        SessionManager.getInstance().clear();
        CartController.getInstance().deleteCart();
    }
}
