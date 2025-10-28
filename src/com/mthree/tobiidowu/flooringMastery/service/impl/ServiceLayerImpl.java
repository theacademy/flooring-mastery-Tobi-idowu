package com.mthree.tobiidowu.flooringMastery.service.impl;

import com.mthree.tobiidowu.flooringMastery.service.ServiceLayer;
import com.mthree.tobiidowu.flooringmastery.model.Order;
import com.mthree.tobiidowu.flooringmastery.model.Product;
import com.mthree.tobiidowu.flooringmastery.model.Tax;
import com.mthree.tobiidowu.flooringMastery.exception.PersistenceException;
import java.util.List;
import java.util.LinkedList;

@Component
public class ServiceLayerImpl implements ServiceLayer {
    public List<Order> getOrdersForDate(LocalDate date) throws PersistanceException {
        return new LinkedList<>();
    }

    public void addOrder(Order order) throws PersistanceException {
        return;
    }

    public Order getOrder(LocalDate date, int orderNumber)  throws PersistanceException {
        return null;
    }

    public void editOrder(Order old, Order new_) throws PersistanceException {
        return;
    }

    public void removeOrder(Order order) throws PersistanceException {
        return;
    }

    public void exportData() throws PersistanceException {
        return;
    }

    public Tax getTax(String state) throws PersistanceException {
        return null;
    }

    public List<Products> getProducts() throws PersistanceException {
        return new LinkedList<>();
    }
}
