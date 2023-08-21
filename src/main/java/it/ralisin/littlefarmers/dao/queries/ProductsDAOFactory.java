package it.ralisin.littlefarmers.dao.queries;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ProductsDAOFactory {

    public ProductDAO createProductsDAO() throws IOException {
        InputStream input = new FileInputStream("src/main/resources/it/ralisin/littlefarmers/conf/db.properties");
        Properties properties = new Properties();
        properties.load(input);

        String categoryDaoType = properties.getProperty("PRODUCTS_DAO_TYPE");

        return switch (categoryDaoType) {
            case "jdbc" -> new ProductsDAOJdbc();
            case "csv" -> new ProductsDAOCsv();
            default -> throw new IOException("Configuration file error");
        };
    }
}
