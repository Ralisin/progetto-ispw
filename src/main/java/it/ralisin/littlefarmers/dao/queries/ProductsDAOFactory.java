package it.ralisin.littlefarmers.dao.queries;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductsDAOFactory {

    public ProductDAO createProductsDAO() throws IOException {
        InputStream input = null;
        Properties properties = new Properties();

        try {
            input = new FileInputStream("src/main/resources/it/ralisin/littlefarmers/conf/db.properties");

            properties.load(input);
        } catch (FileNotFoundException e) {
            Logger.getAnonymousLogger().log(Level.INFO, String.format("File db.properties not found, %s", e));
        } finally {
            if (input != null) input.close();
        }

        String categoryDaoType = properties.getProperty("PRODUCTS_DAO_TYPE");

        return switch (categoryDaoType) {
            case "jdbc" -> new ProductsDAOJdbc();
            case "csv" -> new ProductsDAOCsv();
            default -> throw new IOException("Configuration file error");
        };
    }
}
