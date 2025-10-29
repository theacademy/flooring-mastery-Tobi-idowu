package com.mthree.tobiidowu.flooringMastery.service;

import com.mthree.tobiidowu.flooringMastery.model.Order;
import com.mthree.tobiidowu.flooringMastery.model.Product;
import com.mthree.tobiidowu.flooringMastery.model.Tax;
import com.mthree.tobiidowu.flooringMastery.exception.PersistenceException;
import java.time.LocalDate;
import java.util.List;

public interface ServiceLayer {
    public List<Order> getOrdersForDate(LocalDate date) throws PersistenceException;

    public void addOrder(Order order) throws PersistenceException;

    public Order getOrder(LocalDate date, int orderNumber) throws PersistenceException;

    public void editOrder(Order old, Order new_) throws PersistenceException;

    public void removeOrder(Order order) throws PersistenceException;

    public void exportData() throws PersistenceException;

    public Tax getTax(String state) throws PersistenceException;

    public List<Product> getProducts() throws PersistenceException;
}
