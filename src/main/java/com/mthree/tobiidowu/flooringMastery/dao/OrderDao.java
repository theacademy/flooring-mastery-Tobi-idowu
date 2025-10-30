package com.mthree.tobiidowu.flooringMastery.dao;

import com.mthree.tobiidowu.flooringMastery.model.Order;
import com.mthree.tobiidowu.flooringMastery.exception.PersistenceException;
import java.time.LocalDate;
import java.util.List;

public interface OrderDao {
    public void addOrder(Order order) throws PersistenceException;

    public Order getOrder(LocalDate date, int orderNumber) throws PersistenceException;

    public void editOrder(Order new_) throws PersistenceException;

    public List<Order> getOrdersForDate(LocalDate date) throws PersistenceException;

    public void removeOrder(LocalDate date, int orderNumber) throws PersistenceException;

    public String getOrderFolder();

    public String getOrderHeader();
}