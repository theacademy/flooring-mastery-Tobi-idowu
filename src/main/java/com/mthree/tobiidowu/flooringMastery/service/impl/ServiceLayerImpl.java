package com.mthree.tobiidowu.flooringMastery.service.impl;

import com.mthree.tobiidowu.flooringMastery.dao.OrderDao;
import com.mthree.tobiidowu.flooringMastery.dao.ProductDao;
import com.mthree.tobiidowu.flooringMastery.dao.TaxDao;
import com.mthree.tobiidowu.flooringMastery.dao.ExportDao;
import com.mthree.tobiidowu.flooringMastery.service.ServiceLayer;
import com.mthree.tobiidowu.flooringMastery.model.Order;
import com.mthree.tobiidowu.flooringMastery.model.Product;
import com.mthree.tobiidowu.flooringMastery.model.Tax;
import com.mthree.tobiidowu.flooringMastery.exception.PersistenceException;
import com.mthree.tobiidowu.flooringMastery.exception.NoSuchOrderException;
import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class ServiceLayerImpl implements ServiceLayer {
    private OrderDao orderDao;
    private ProductDao productDao;
    private TaxDao taxDao;
    private ExportDao exportDao;

    @Autowired
    public ServiceLayerImpl(OrderDao orderDao, ProductDao productDao, TaxDao taxDao, ExportDao exportDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.taxDao = taxDao;
        this.exportDao = exportDao;
    }

    public List<Order> getOrdersForDate(LocalDate date) throws PersistenceException {
        return orderDao.getOrdersForDate(date);
    }

    public void addOrder(Order order) throws PersistenceException {
        orderDao.addOrder(order);
    }

    public Order getOrder(LocalDate date, int orderNumber) throws PersistenceException, NoSuchOrderException {
        Order order = orderDao.getOrder(date, orderNumber);

        if (order == null) {
            throw new NoSuchOrderException("Order not found.");
        }

        return order;
    }

    public void calculateOrderAttributes(Order order) throws PersistenceException {
        // Store all intermediate results in BigDecimal variables for clarity
        BigDecimal area = order.getArea();
        BigDecimal costPerSquareFoot = order.getCostPerSquareFoot();
        BigDecimal laborCostPerSquareFoot = order.getLaborCostPerSquareFoot();
        BigDecimal taxRate = order.getTaxRate();

        // Calculate material cost
        BigDecimal materialCost = area.multiply(costPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
        order.setMaterialCost(materialCost);

        // Calculate labor cost
        BigDecimal laborCost = area.multiply(laborCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
        order.setLaborCost(laborCost);

        // Calculate subtotal (material + labor)
        BigDecimal subTotal = materialCost.add(laborCost);

        // Calculate tax (taxRate is stored as percentage, divide by 100)
        BigDecimal taxPercent = taxRate.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
        BigDecimal tax = subTotal.multiply(taxPercent).setScale(2, RoundingMode.HALF_UP);
        order.setTax(tax);

        // Calculate total
        BigDecimal total = subTotal.add(tax);
        order.setTotal(total);
    }

    public void editOrder(Order newOrder) throws PersistenceException {
        orderDao.editOrder(newOrder);
    }

    public void removeOrder(Order order) throws PersistenceException {
        orderDao.removeOrder(order.getDate(), order.getOrderNumber());
    }

    public void exportData() throws PersistenceException {
        exportDao.exportData();
    }

    public List<Product> getProducts() throws PersistenceException {
        return productDao.getAllProducts();
    }

    public List<Tax> getTaxes() throws PersistenceException {
        return taxDao.getAllTaxes();
    }
}
