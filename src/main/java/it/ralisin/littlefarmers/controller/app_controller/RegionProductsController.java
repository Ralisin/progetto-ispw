package it.ralisin.littlefarmers.controller.app_controller;

import it.ralisin.littlefarmers.beans.ProductBean;
import it.ralisin.littlefarmers.dao.queries.ProductsDAO;
import it.ralisin.littlefarmers.enums.Regions;
import it.ralisin.littlefarmers.exeptions.DAOException;
import it.ralisin.littlefarmers.model.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegionProductsController {
    /**
     * @param region the region from which to take products
     * @return "List<Product>" if no error occurred,
     *         "null" if some error occurred */
    public ProductBean loadRegionProducts(Regions region) {
        List<Product> productList = null;

        try {
            productList = ProductsDAO.getProductsByRegion(region);
        } catch (SQLException | DAOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, String.format("loadRegionProducts failed %s", e));
        }

        return new ProductBean(productList);
    }
}
