package it.ralisin.littlefarmers.controller.graphic_controller_cli;

import it.ralisin.littlefarmers.beans.OrderBean;
import it.ralisin.littlefarmers.beans.ProductsListBean;
import it.ralisin.littlefarmers.controller.app_controller.OrderController;
import it.ralisin.littlefarmers.controller.app_controller.SessionController;
import it.ralisin.littlefarmers.enums.OrderStatus;
import it.ralisin.littlefarmers.exeptions.InvalidFormatException;
import it.ralisin.littlefarmers.model.Order;
import it.ralisin.littlefarmers.model.Product;
import it.ralisin.littlefarmers.model.User;
import it.ralisin.littlefarmers.utils.CLIPrinter;
import it.ralisin.littlefarmers.utils.SessionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CompanyHomeGraphicControllerCLI extends AbsGraphicControllerCLI {
    List<Order> orderList = new ArrayList<>();
    OrderController orderController;

    @Override
    public void start() {
        orderController = new OrderController();
        User user = SessionManager.getInstance().getUser();

        orderList = getOrderList(user);

        int choice = -1;
        while(choice == -1) {
            try {
                choice = showMenu();
                switch(choice) {
                    case 0 -> {
                        new SessionController().logOut();
                        new HomeGraphicControllerCLI().start();
                    }
                    case 1 -> {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

                        CLIPrinter.print("Id dell'ordine da modificare: ");
                        int orderId = Integer.parseInt(reader.readLine());
                        CLIPrinter.print("Nuovo stato (waiting, accepted, denied, shipped, delivered): ");
                        String orderStatus = reader.readLine();

                        Order order = getOrderInOrderListById(orderId);

                        order.setStatus(OrderStatus.getByString(orderStatus));

                        OrderBean updateOrderStatusBean = new OrderBean(order);
                        orderController.updateOrderStatus(updateOrderStatusBean);

                        choice = -1;
                    }
                    case 2 -> {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

                        CLIPrinter.print("Id dell'ordine di cui si vogliono vedere i prodotti: ");
                        int orderId = Integer.parseInt(reader.readLine());

                        Order order = getOrderInOrderListById(orderId);

                        OrderBean orderBean = new OrderBean(order);
                        ProductsListBean productsListBean = orderController.getOrderProducts(orderBean);
                        List<Product> productList = productsListBean.getProductList();

                        CLIPrinter.printf(String.format("Prodotti dell'ordine %d", orderId));
                        for(Product p : productList) {
                            String productString = String.format("productId: %d, name: %s, quantity: %d", p.getProductId(), p.getName(), p.getQuantity());

                            CLIPrinter.printf(productString);
                        }

                        choice = -1;
                    }
                    case 3 -> {
                        CLIPrinter.printf("CompanyHomeGraphicControllerCLI case 3: dummy");

                        choice = -1;
                    }
                    default -> {
                        choice = -1;
                        throw new InvalidFormatException("Invalid choice");
                    }
                }
            } catch (IOException | InvalidFormatException e) {
                CLIPrinter.printf(e.getMessage());
            }
        }
    }

    @Override
    public int showMenu() throws IOException {
        CLIPrinter.printf("*** Ordini ***");
        for(Order o : orderList) {
            String orderString = String.format("orderId: %d, customerEmail: %s, orderStatus: %s", o.getId(), o.getCustomerEmail(), o.getStatus());

            CLIPrinter.printf(orderString);
        }
        CLIPrinter.printf("1- Modifica lo stato di un ordine");
        CLIPrinter.printf("2- Vedi i prodotti di un ordine");
        CLIPrinter.printf("3- I tuoi prodotti");
        CLIPrinter.printf("0- LogOut");

        return getMenuChoice(0, 3);
    }

    private Order getOrderInOrderListById(int id) throws IOException {
        Order order = null;
        for(Order o : orderList) if(o.getId() == id) order = o;
        if(order == null) throw new IOException("Invalid order id");

        return order;
    }
}
