package it.ralisin.littlefarmers.controller.graphic_controller_cli;

import it.ralisin.littlefarmers.beans.LoginCredentialsBean;
import it.ralisin.littlefarmers.controller.app_controller.LoginSignUpController;
import it.ralisin.littlefarmers.enums.UserRole;
import it.ralisin.littlefarmers.exeptions.DAOException;
import it.ralisin.littlefarmers.exeptions.InvalidFormatException;
import it.ralisin.littlefarmers.model.User;
import it.ralisin.littlefarmers.utils.CLIPrinter;
import it.ralisin.littlefarmers.utils.SessionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbsGraphicControllerCLI implements GraphicControllerCLIInterface {
    protected int getMenuChoice(int min, int max) {
        Scanner input = new Scanner(System.in);
        int choice;

        while (true) {
            CLIPrinter.print("Please enter your choice: ");

            choice = input.nextInt();
            if (choice >= min && choice <= max) break;

            CLIPrinter.printf("Invalid option\n");
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

            if(result) {
                User user = SessionManager.getInstance().getUser();
                if(user.getRole() == UserRole.CUSTOMER) new RegionListGraphicControllerCLI().start();
                else if(user.getRole() == UserRole.COMPANY) CLIPrinter.printf("Logged as COMPANY"); // TODO
            }
            else throw new DAOException("Invalid login credentials");
        } catch (InvalidFormatException e) {
            CLIPrinter.printf("Email or password format is not correct");
        } catch (DAOException | SQLException e) {
            CLIPrinter.printf(String.format("Database reading error: %s", e));
        } catch (IOException e) {
            CLIPrinter.printf(String.format("BufferReading error: %s", e));
        }
    }

    protected void signUp() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            CLIPrinter.printf("*** Select role ***");
            CLIPrinter.printf("1) Customer");
            CLIPrinter.printf("2) Company");
            CLIPrinter.print("Select role: ");
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

            if(result) {
                User user = SessionManager.getInstance().getUser();
                if(user.getRole() == UserRole.CUSTOMER) new RegionListGraphicControllerCLI().start();
                else if(user.getRole() == UserRole.COMPANY) CLIPrinter.printf("Registered as COMPANY"); // TODO
            }
            else throw new DAOException("User already exist");
        } catch (InvalidFormatException e) {
            CLIPrinter.printf("Email or password format is not correct");
        } catch (DAOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, String.format("Database writing error: %s", e));
        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, String.format("BufferReading error: %s", e));
        }
    }
}
