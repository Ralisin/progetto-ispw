package it.ralisin.littlefarmers.controller.graphic_controller_cli;

import it.ralisin.littlefarmers.beans.CartBean;
import it.ralisin.littlefarmers.controller.app_controller.CartController;
import it.ralisin.littlefarmers.exeptions.InvalidFormatException;
import it.ralisin.littlefarmers.model.Product;
import it.ralisin.littlefarmers.utils.CLIPrinter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CartGraphicControllerCLI extends AbsGraphicControllerCLI {
    private List<Product> productList;

    @Override
    public void start() {
        productList = CartController.getInstance().getCart().getProductList();
        final String invalidChoiceString = "Invalid choice";

        int choice = -1;
        while(choice == -1) {
            try {
                printProductList();
                choice = showMenu();

                switch(choice) {
                    case 1 -> {
                        CartController.getInstance().buyCart();
                        productList = new ArrayList<>();
                    }
                    case 2 -> {
                        Scanner input = new Scanner(System.in);
                        CLIPrinter.print("ProductId to edit quantity: ");
                        int productId = input.nextInt();
                        CLIPrinter.print("New quantity: ");
                        int quantity = input.nextInt();

                        Product newP = findProductInCartById(productId);
                        if(newP != null) newP.setQuantity(quantity);
                        else throw new InvalidFormatException(invalidChoiceString);

                        CartBean cartBean = new CartBean();
                        cartBean.setProduct(newP);

                        CartController.getInstance().updateProduct(cartBean);
                    }
                    case 3 -> {
                        Scanner input = new Scanner(System.in);
                        CLIPrinter.print("ProductId to edit quantity: ");
                        int productId = input.nextInt();

                        Product productToRemove = findProductInCartById(productId);
                        if(productToRemove == null) throw new InvalidFormatException(invalidChoiceString);

                        CartBean cartBean = new CartBean();
                        cartBean.setProduct(productToRemove);

                        CartController.getInstance().removeProduct(cartBean);
                    }
                    case 0 -> new RegionListGraphicControllerCLI().start();
                    default -> {
                        choice = -1;
                        throw new InvalidFormatException(invalidChoiceString);
                    }
                }
            } catch (IOException | InvalidFormatException e) {
                Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
            }
        }
    }

    @Override
    public int showMenu() throws IOException {
        CLIPrinter.printf("1- Buy cart");
        CLIPrinter.printf("2- Edit product quantity");
        CLIPrinter.printf("3- Remove product");
        CLIPrinter.printf("0- Back");

        return getMenuChoice(0, 3);
    }

    private void printProductList() {
        for(Product p : productList) {
            String productString = String.format("productId: %d, name: %s, productPrice: %f, quantity: %d", p.getProductId(), p.getName(), p.getPrice(), p.getQuantity());

            CLIPrinter.printf(productString);
        }
    }

    private Product findProductInCartById(int id) {
        for(Product p : productList)
            if(p.getProductId() == id) return p;

        return null;
    }
}
