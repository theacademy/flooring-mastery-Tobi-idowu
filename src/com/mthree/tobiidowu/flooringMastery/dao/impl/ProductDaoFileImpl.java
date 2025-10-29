package com.mthree.tobiidowu.flooringMastery.dao.impl;

import com.mthree.tobiidowu.flooringMastery.dao.ProductDao;
import com.mthree.tobiidowu.flooringmastery.model.Product;
import java.util.List;
import java.util.LinkedList;

@Component
public class ProductDaoFileImpl implements ProductDao {
    private String productFile;
    private List<Product> products;

    // constructor
    public ProductDaoFileImpl() {
        this.productFile = "/../../../../../../../Data/Lookup/Products.txt"
        this.products = null;
    }

    public List<Product> getAllProducts() {
        return new LinkedList<>();
    }
}