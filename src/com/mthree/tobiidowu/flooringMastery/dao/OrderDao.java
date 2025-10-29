package com.mthree.tobiidowu.flooringMastery.dao;

import com.mthree.tobiidowu.flooringmastery.model.Order;
import java.time.LocalDate;
import java.util.List;

public interface OrderDao {
    public void addOrder(Order order);

    public Order getOrder(LocalDate date, int orderNumber);

    public void editOrder(Order new_);

    public List<Order> getOrdersForDate(LocalDate date);

    public void removeOrder(LocalDate date, int orderNumber);
}