package it.ralisin.littlefarmers.dao.queries;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ProductsDAOFactory {

    public ProductDAO createProductsDAO() throws IOException {
        InputStream input = null;
        Properties properties = new Properties();

        try {
            input = new FileInputStream("src/main/resources/it/ralisin/littlefarmers/conf/db.properties");

            properties.load(input);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
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
