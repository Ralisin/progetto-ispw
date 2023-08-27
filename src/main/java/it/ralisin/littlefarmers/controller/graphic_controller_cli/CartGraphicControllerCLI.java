package it.ralisin.littlefarmers.controller.graphic_controller_cli;

import it.ralisin.littlefarmers.beans.CartBean;
import it.ralisin.littlefarmers.controller.app_controller.CartController;
import it.ralisin.littlefarmers.exeptions.InvalidFormatException;
import it.ralisin.littlefarmers.model.Product;
import it.ralisin.littlefarmers.utils.CLIPrinter;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class CartGraphicControllerCLI extends AbsGraphicControllerCLI {
    private List<Product> productList;

    @Override
    public void start() {
        productList = CartController.getInstance().getCart().getProductList();
        final String invalidChoiceString = "Scelta non valida";

        int choice = -1;
        while(choice == -1) {
            try {
                printProductList();
                choice = showMenu();

                switch(choice) {
                    case 1 -> {
                        CartController.getInstance().buyCart();
                        new CustomerHomeGraphicControllerCLI().start();
                    }
                    case 2 -> {
                        Scanner input = new Scanner(System.in);
                        CLIPrinter.print("Id del prodotto di cui modificare la quantità: ");
                        int productId = input.nextInt();
                        CLIPrinter.print("Nuova quantità: ");
                        int quantity = input.nextInt();

                        Product newP = findProductInCartById(productId);
                        if(newP != null) newP.setQuantity(quantity);
                        else throw new InvalidFormatException(invalidChoiceString);

                        CartBean cartBean = new CartBean();
                        cartBean.setProduct(newP);

                        CartController.getInstance().updateProduct(cartBean);

                        choice = -1;
                    }
                    case 3 -> {
                        Scanner input = new Scanner(System.in);
                        CLIPrinter.print("Id del prodotto da rimuovere dal carrello: ");
                        int productId = input.nextInt();

                        Product productToRemove = findProductInCartById(productId);
                        if(productToRemove == null) throw new InvalidFormatException(invalidChoiceString);

                        CartBean cartBean = new CartBean();
                        cartBean.setProduct(productToRemove);

                        productList.remove(productToRemove);
                        CartController.getInstance().removeProduct(cartBean);

                        choice = -1;
                    }
                    case 0 -> new CustomerHomeGraphicControllerCLI().start();
                    default -> throw new InvalidFormatException(invalidChoiceString);
                }
            } catch (IOException | InvalidFormatException e) {
                CLIPrinter.printf(e.getMessage());

                choice = -1;
            }
        }
    }

    @Override
    public int showMenu() throws IOException {
        CLIPrinter.printf("1- Acquista carrello");
        CLIPrinter.printf("2- Modifica la quantità di prodotto nel carrello");
        CLIPrinter.printf("3- Rimuovi un prodotto");
        CLIPrinter.printf("0- Indietro");

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
