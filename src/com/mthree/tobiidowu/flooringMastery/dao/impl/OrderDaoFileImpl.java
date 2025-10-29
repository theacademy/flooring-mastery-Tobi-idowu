package com.mthree.tobiidowu.flooringMastery.dao.impl;

import com.mthree.tobiidowu.flooringMastery.dao.OrderDao;
import com.mthree.tobiidowu.flooringMastery.model.Order;
import java.time.LocalDate;
import java.util.List;
import java.util.LinkedList;
import org.springframework.stereotype.Component;

@Component
public class OrderDaoFileImpl implements OrderDao {
    private String orderFolder;
    private String orderHeader;

    // constructor
    public OrderDaoFileImpl() {
        this.orderFolder = "/../../../../../../../Data/Orders";
        this.orderHeader = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot," +
                "LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total";
    }

    public void addOrder(Order order) throws PersistenceException {
        // find id first then add to file
        return;
    }

    public Order getOrder(LocalDate date, int orderNumber) throws PersistenceException {
        return null;
    }

    public void editOrder(Order new_) throws PersistenceException {
        return;
    }

    public List<Order> getOrdersForDate(LocalDate date) throws PersistenceException {
        return new LinkedList<>();
    }

    public void removeOrder(LocalDate date, int orderNumber) throws PersistenceException {
        return;
    }

    // getters
    public String getOrderFolder() {
        return this.orderFolder;
    }

    public String getOrderHeader() {
        return this.orderHeader;
    }
}