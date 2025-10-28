package com.mthree.tobiidowu.flooringMastery.service;

import com.mthree.tobiidowu.flooringmastery.model.Order;
import com.mthree.tobiidowu.flooringmastery.model.Product;
import com.mthree.tobiidowu.flooringmastery.model.Tax;
import com.mthree.tobiidowu.flooringMastery.exception.PersistenceException;
import java.util.List;

public interface ServiceLayer {
    public List<Order> getOrdersForDate(LocalDate date) throws PersistanceException;

    public void addOrder(Order order) throws PersistanceException;

    public Order getOrder(LocalDate date, int orderNumber)  throws PersistanceException;

    public void editOrder(Order old, Order new_) throws PersistanceException;

    public void removeOrder(Order order) throws PersistanceException;

    public void exportData() throws PersistanceException;

    public Tax getTax(String state) throws PersistanceException;

    public List<Products> getProducts() throws PersistanceException;
}
