package it.ralisin.littlefarmers.controller.graphic_controller_cli;

import it.ralisin.littlefarmers.beans.LoginCredentialsBean;
import it.ralisin.littlefarmers.beans.OrderBean;
import it.ralisin.littlefarmers.controller.app_controller.LoginSignUpController;
import it.ralisin.littlefarmers.controller.app_controller.OrderController;
import it.ralisin.littlefarmers.enums.UserRole;
import it.ralisin.littlefarmers.exeptions.DAOException;
import it.ralisin.littlefarmers.exeptions.InvalidFormatException;
import it.ralisin.littlefarmers.model.Order;
import it.ralisin.littlefarmers.model.User;
import it.ralisin.littlefarmers.utils.CLIPrinter;
import it.ralisin.littlefarmers.utils.SessionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbsGraphicControllerCLI implements GraphicControllerCLIInterface {
    protected int getMenuChoice(int min, int max) {
        Scanner input = new Scanner(System.in);
        int choice;

        while (true) {
            CLIPrinter.print("Per favore, inserisci un numero: ");

            choice = input.nextInt();
            if (choice >= min && choice <= max) break;

            CLIPrinter.printf("Opzione non valida\n");
        }

        return choice;
    }

    protected void login() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            CLIPrinter.print("email: ");
            String email = reader.readLine();
            CLIPrinter.print("password: ");
            String psw = reader.readLine();

            LoginCredentialsBean loginCredentialsBean = new LoginCredentialsBean(email, psw);
            boolean result = new LoginSignUpController().login(loginCredentialsBean);

            if(result) nextPage(SessionManager.getInstance().getUser());
            else CLIPrinter.printf("Credenziali login non valide");

            new HomeGraphicControllerCLI().start();
        } catch (InvalidFormatException e) {
            CLIPrinter.printf("Formato email o password invalido");
        } catch (DAOException | SQLException e) {
            CLIPrinter.printf(String.format("Database reading error: %s", e));
        } catch (IOException e) {
            CLIPrinter.printf(String.format("BufferReading error: %s", e));
        }
    }

    protected void signUp() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            CLIPrinter.printf("*** Seleziona il tuo ruolo ***");
            CLIPrinter.printf("1) Customer");
            CLIPrinter.printf("2) Company");
            int choice = getMenuChoice(1, 2);

            UserRole role = UserRole.NONE;
            if(choice == 1) role = UserRole.CUSTOMER;
            else if(choice == 2) role = UserRole.COMPANY;

            CLIPrinter.print("email: ");
            String email = reader.readLine();
            CLIPrinter.print("repeat email: ");
            String emailRep = reader.readLine();
            CLIPrinter.print("password: ");
            String psw = reader.readLine();
            CLIPrinter.print("repeat password: ");
            String pswRep = reader.readLine();

            LoginCredentialsBean loginCredentialsBean = new LoginCredentialsBean(email, emailRep, psw, pswRep, role);
            boolean result = new LoginSignUpController().signUp(loginCredentialsBean);

            if(result) nextPage(SessionManager.getInstance().getUser());
            else CLIPrinter.printf("User already exist");

            new HomeGraphicControllerCLI().start();
        } catch (InvalidFormatException e) {
            CLIPrinter.printf("Email or password format is not correct");
        } catch (DAOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, String.format("Database writing error: %s", e));
        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, String.format("BufferReading error: %s", e));
        }
    }

    private void nextPage(User user) {
        if(user.getRole() == UserRole.CUSTOMER) new CustomerHomeGraphicControllerCLI().start();
        else if(user.getRole() == UserRole.COMPANY) new CompanyHomeGraphicControllerCLI().start();
    }

    protected List<Order> getOrderList(User user) {
        OrderController orderController = new OrderController();

        OrderBean orderBean = null;
        if(user.getRole() == UserRole.CUSTOMER) orderBean = orderController.getCustomerOrdersList();
        else if(user.getRole() == UserRole.COMPANY) orderBean = orderController.getCompanyOrdersList();

        if (orderBean != null) return orderBean.getOrderList();
        return new ArrayList<>();
    }
}
