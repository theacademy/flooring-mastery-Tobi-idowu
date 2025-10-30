package com.mthree.tobiidowu.flooringMastery.dao.impl;

import com.mthree.tobiidowu.flooringMastery.dao.ProductDao;
import com.mthree.tobiidowu.flooringMastery.model.Product;
import com.mthree.tobiidowu.flooringMastery.exception.PersistenceException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.LinkedList;
import org.springframework.stereotype.Component;

@Component
public class ProductDaoFileImpl implements ProductDao {
    protected String productFile;
    protected List<Product> products;

    // constructor
    public ProductDaoFileImpl() {
        this.productFile = "Data/Lookup/Products.txt";
        this.products = null;
    }

    public List<Product> getAllProducts() throws PersistenceException {
        if (products != null) {
            return products;
        }

        products = new LinkedList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(productFile))) {
            // Skip header line
            reader.readLine();

            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] fields = line.split(",");

                if (fields.length == 3) {
                    Product product = new Product();

                    product.setProductType(fields[0].trim());
                    product.setCostPerSquareFoot(new BigDecimal(fields[1].trim()));
                    product.setLaborCostPerSquareFoot(new BigDecimal(fields[2].trim()));

                    products.add(product);
                }
            }
        } catch (IOException e) {
            throw new PersistenceException("Could not load product data: " + e.getMessage());
        } catch (NumberFormatException e) {
            throw new PersistenceException("Error parsing product cost: " + e.getMessage());
        }

        return products;
    }
}