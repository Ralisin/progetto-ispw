package it.ralisin.littlefarmers.controller.app_controller;

import it.ralisin.littlefarmers.beans.ProductsListBean;
import it.ralisin.littlefarmers.dao.queries.ProductDAO;
import it.ralisin.littlefarmers.dao.queries.ProductsDAOFactory;
import it.ralisin.littlefarmers.enums.Regions;
import it.ralisin.littlefarmers.model.Product;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegionProductsController {
    /**
     * @param region the region from which to take products
     * @return "List<Product>" if no error occurred,
     *         "null" if some error occurred */
    public ProductsListBean loadRegionProducts(Regions region) {
        List<Product> productList = null;

        try {
            ProductDAO productDAO = new ProductsDAOFactory().createProductsDAO();
            productList = productDAO.getProductsByRegion(region);
        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, String.format("loadRegionProducts failed %s", e));
        }

        return new ProductsListBean(productList);
    }
}
