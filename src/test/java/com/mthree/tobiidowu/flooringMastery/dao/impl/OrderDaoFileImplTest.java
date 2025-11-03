package com.mthree.tobiidowu.flooringMastery.dao.impl;

import com.mthree.tobiidowu.flooringMastery.dao.OrderDao;
import com.mthree.tobiidowu.flooringMastery.exception.PersistenceException;
import com.mthree.tobiidowu.flooringMastery.model.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class OrderDaoFileImplTest {

    private OrderDao dao;
    private String testOrderFolder;
    private String testLargestOrderNumberFile;

    @BeforeEach
    void setUp() throws IOException {
        testOrderFolder = "TestData/Orders";
        testLargestOrderNumberFile = "TestData/largestOrderNumber.txt";

        new File(testOrderFolder).mkdirs();

        dao = new OrderDaoFileImpl();

        ((OrderDaoFileImpl) dao).orderFolder = testOrderFolder;
        ((OrderDaoFileImpl) dao).largestOrderNumberFile = testLargestOrderNumberFile;

        try (FileWriter writer = new FileWriter(testLargestOrderNumberFile)) {
            writer.write("0");
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        deleteDirectory(new File("TestData"));
    }

    @Test
    void testAddAndGetOrder() throws PersistenceException {
        // create multiple orders
        Order order1 = createOrder("Doctor Who", "WA", "Wood", 243.00, LocalDate.of(2024, 1, 15));
        Order order2 = createOrder("Albert Einstein", "KY", "Carpet", 217.00, LocalDate.of(2024, 1, 15));

        // add orders
        dao.addOrder(order1);
        dao.addOrder(order2);

        // verify each order retrieved
        assertEquals(0, dao.getOrder(order1.getDate(), order1.getOrderNumber()).getOrderNumber());
        assertEquals(1, dao.getOrder(order2.getDate(), order2.getOrderNumber()).getOrderNumber());
        assertEquals("Doctor Who", dao.getOrder(order1.getDate(), 0).getCustomerName());
        assertEquals("Albert Einstein", dao.getOrder(order2.getDate(), 1).getCustomerName());
    }

    @Test
    void testGetOrdersForDate() throws PersistenceException {
        // add orders
        dao.addOrder(createOrder("Doctor Who", "WA", "Wood", 243.00, LocalDate.of(2024, 2, 20)));
        dao.addOrder(createOrder("Albert Einstein", "KY", "Carpet", 217.00, LocalDate.of(2024, 2, 20)));

        // get orders for date
        List<Order> orders = dao.getOrdersForDate(LocalDate.of(2024, 2, 20));

        // should have two orders
        assertEquals(2, orders.size());
    }

    @Test
    void testEditOrder() throws PersistenceException {
        // add order
        Order original = createOrder("Ada Lovelace", "CA", "Tile", 250, LocalDate.of(2024, 1, 15));
        dao.addOrder(original);

        // edit order
        Order modified = createOrder("Modified Name", "TX", "Carpet", 100, LocalDate.of(2024, 1, 15));
        modified.setOrderNumber(original.getOrderNumber());
        dao.editOrder(modified);

        // verify updated
        Order retrieved = dao.getOrder(original.getDate(), original.getOrderNumber());
        assertEquals("Modified Name", retrieved.getCustomerName());
        assertEquals("TX", retrieved.getState());
    }

    @Test
    void testRemoveOrder() throws PersistenceException {
        // add orders
        Order order1 = createOrder("Doctor Who", "WA", "Wood", 243.00, LocalDate.of(2024, 3, 10));
        Order order2 = createOrder("Albert Einstein", "KY", "Carpet", 217.00, LocalDate.of(2024, 3, 10));
        dao.addOrder(order1);
        dao.addOrder(order2);

        // remove first order
        dao.removeOrder(order1.getDate(), order1.getOrderNumber());

        // first removed, second exists
        assertNull(dao.getOrder(order1.getDate(), order1.getOrderNumber()));
        assertNotNull(dao.getOrder(order2.getDate(), order2.getOrderNumber()));
        assertEquals(1, dao.getOrdersForDate(order1.getDate()).size());
    }

    // helper method to create orders from Data folder examples
    private Order createOrder(String customerName, String state, String productType, double area, LocalDate date) {
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setState(state);
        order.setProductType(productType);
        order.setArea(new BigDecimal(String.valueOf(area)));

        // set product costs
        if ("Wood".equals(productType)) {
            order.setCostPerSquareFoot(new BigDecimal("5.15"));
            order.setLaborCostPerSquareFoot(new BigDecimal("4.75"));
        } else if ("Carpet".equals(productType)) {
            order.setCostPerSquareFoot(new BigDecimal("2.25"));
            order.setLaborCostPerSquareFoot(new BigDecimal("2.10"));
        } else if ("Tile".equals(productType)) {
            order.setCostPerSquareFoot(new BigDecimal("3.50"));
            order.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        }

        // set tax rates
        if ("WA".equals(state)) {
            order.setTaxRate(new BigDecimal("9.25"));
        } else if ("KY".equals(state)) {
            order.setTaxRate(new BigDecimal("6.00"));
        } else if ("CA".equals(state)) {
            order.setTaxRate(new BigDecimal("25.00"));
        } else if ("TX".equals(state)) {
            order.setTaxRate(new BigDecimal("4.45"));
        }

        // calculate costs
        BigDecimal materialCost = order.getArea().multiply(order.getCostPerSquareFoot());
        BigDecimal laborCost = order.getArea().multiply(order.getLaborCostPerSquareFoot());
        order.setMaterialCost(materialCost);
        order.setLaborCost(laborCost);
        BigDecimal subtotal = materialCost.add(laborCost);
        BigDecimal tax = subtotal.multiply(order.getTaxRate().divide(new BigDecimal("100")));
        order.setTax(tax);
        order.setTotal(subtotal.add(tax));
        order.setDate(date);

        return order;
    }

    // helper method to delete directory
    private void deleteDirectory(File directory) throws IOException {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
            directory.delete();
        }
    }
}
