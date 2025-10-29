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
import java.time.LocalDate;
import java.util.List;
import java.util.LinkedList;

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

    public List<Product> getProducts() throws PersistanceException {
        return new LinkedList<>();
    }
}
