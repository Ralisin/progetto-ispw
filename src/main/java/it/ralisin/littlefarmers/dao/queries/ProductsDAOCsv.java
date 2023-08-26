package it.ralisin.littlefarmers.dao.queries;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import it.ralisin.littlefarmers.enums.Regions;
import it.ralisin.littlefarmers.model.Product;
import it.ralisin.littlefarmers.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductsDAOCsv implements ProductDAO {
    private final File fd;
    static final String CSV_FILE_NAME = "localDBFile.csv";
    static final int INDEX_COMPANY_EMAIL = 0;
    static final int INDEX_PRODUCT_ID = 1;
    static final int INDEX_PRODUCT_NAME = 2;
    static final int INDEX_PRODUCT_DESCRIPTION = 3;
    static final int INDEX_PRODUCT_PRICE = 4;
    static final int INDEX_PRODUCT_REGION = 5;
    static final int INDEX_PRODUCT_CATEGORY = 6;
    static final int INDEX_PRODUCT_IMAGE_LINK = 7;

    public ProductsDAOCsv() throws IOException {
        this.fd = new File(CSV_FILE_NAME);

        if (!fd.exists()) {
            throw new IOException(CSV_FILE_NAME + " file does not exist");
        }
    }

    @Override
    public List<Product> getProducts() {
        try {
            return getProductsFromCSVFile();
        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, String.format("Error on getting products in ProductsDAOCsv, %s", e));
        }

        return new ArrayList<>();
    }

    @Override
    public List<Product> getProductsByRegion(Regions region) {
        List<Product> productList = getProducts();

        return productList.stream()
                .filter(product -> product.getRegion() == region)
                .toList();
    }

    @Override
    public List<Product> getProductByCompany(User company) {
        List<Product> productList = getProducts();

        return productList.stream()
                .filter(product -> Objects.equals(product.getCompanyEmail(), company.getEmail()))
                .toList();
    }

    private List<Product> getProductsFromCSVFile() throws IOException {
        List<Product> productList = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fd)))) {
            String[] productRecord;
            productList = new ArrayList<>();

            while ((productRecord = csvReader.readNext()) != null) {
                String companyEmail = productRecord[INDEX_COMPANY_EMAIL];
                int productId = Integer.parseInt(productRecord[INDEX_PRODUCT_ID]);
                String name = productRecord[INDEX_PRODUCT_NAME];
                String description = productRecord[INDEX_PRODUCT_DESCRIPTION];
                float price = Float.parseFloat(productRecord[INDEX_PRODUCT_PRICE]);
                Regions region = Regions.getByRegionString(productRecord[INDEX_PRODUCT_REGION]);
                String category = productRecord[INDEX_PRODUCT_CATEGORY];
                String imageLink = productRecord[INDEX_PRODUCT_IMAGE_LINK];

                Product product = new Product(companyEmail, productId, name, description, region, category, imageLink);
                product.setPrice(price);

                productList.add(product);
            }
        } catch (FileNotFoundException e) {
            Logger.getAnonymousLogger().log(Level.INFO, String.format("Invalid file descriptor %s", e));
        } catch (IOException | CsvValidationException e) {
            throw new IOException(e);
        }

        return productList;
    }
}
