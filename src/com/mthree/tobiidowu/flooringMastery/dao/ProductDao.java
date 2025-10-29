package com.mthree.tobiidowu.flooringMastery.dao;

import com.mthree.tobiidowu.flooringMastery.model.Product;
import com.mthree.tobiidowu.flooringMastery.exception.PersistenceException;
import java.util.List;

public interface ProductDao {
    public List<Product> getAllProducts() throws PersistenceException;
}