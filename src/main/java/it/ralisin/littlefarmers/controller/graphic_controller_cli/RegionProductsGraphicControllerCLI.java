package it.ralisin.littlefarmers.controller.graphic_controller_cli;

import it.ralisin.littlefarmers.beans.CartBean;
import it.ralisin.littlefarmers.beans.ProductsListBean;
import it.ralisin.littlefarmers.controller.app_controller.CartController;
import it.ralisin.littlefarmers.controller.app_controller.RegionProductsController;
import it.ralisin.littlefarmers.enums.Regions;
import it.ralisin.littlefarmers.exeptions.InvalidFormatException;
import it.ralisin.littlefarmers.model.Product;
import it.ralisin.littlefarmers.utils.CLIPrinter;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegionProductsGraphicControllerCLI extends AbsGraphicControllerCLI {
    private Regions region;
    private List<Product> productList;

    @Override
    public void start() {
        ProductsListBean productsListBean = new RegionProductsController().loadRegionProducts(region);
        productList = productsListBean.getProductList();

        while(true) {
            int choice;
            try {
                choice = showMenu();

                if (choice == 0) new RegionListGraphicControllerCLI().start();
                Product p = null;
                for(Product p1 : productList)
                    if(p1.getProductId() == choice) p = p1;

                if(p != null) {
                    p.setQuantity(p.getQuantity()+1);

                    CartBean cartBean = new CartBean();
                    cartBean.setProduct(p);

                    CartController.getInstance().addProduct(cartBean);
                }
                else throw new InvalidFormatException("Invalid choice");
            } catch (IOException | InvalidFormatException e) {
                Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
            }
        }
    }

    @Override
    public int showMenu() throws IOException {
        CLIPrinter.printf(String.format("*** Regione:%s ***", region.getRegionString()));
        for(Product p : productList) {
            String productString = String.format("productId: %d, name: %s, price: %f", p.getProductId(), p.getName(), p.getPrice());

            CLIPrinter.printf(productString);
        }
        CLIPrinter.printf("0- Back");

        Scanner input = new Scanner(System.in);
        CLIPrinter.print("Please enter your choice: ");

        return input.nextInt();
    }

    public void setRegion(Regions region) {
        this.region = region;
    }
}
