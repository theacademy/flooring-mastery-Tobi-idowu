package com.mthree.tobiidowu.flooringMastery.dao;

import com.mthree.tobiidowu.flooringMastery.model.Product;
import java.util.List;

public interface ProductDao {
    public List<Product> getAllProducts() throws PersistenceException;
}