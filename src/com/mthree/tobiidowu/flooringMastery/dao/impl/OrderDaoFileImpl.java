package com.mthree.tobiidowu.flooringMastery.dao.impl;

import com.mthree.tobiidowu.flooringMastery.dao.OrderDao;
import com.mthree.tobiidowu.flooringMastery.model.Order;
import com.mthree.tobiidowu.flooringMastery.exception.PersistenceException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.LinkedList;
import org.springframework.stereotype.Component;

@Component
public class OrderDaoFileImpl implements OrderDao {
    private String orderFolder;
    private String orderHeader;
    private String largestOrderNumberFile;

    // constructor
    public OrderDaoFileImpl() {
        this.orderFolder = "/../../../../../../../Data/Orders";
        this.orderHeader = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot," +
                "LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total";
        this.largestOrderNumberFile = "/../../../../../../../Data/largestOrderNumber.txt";
    }

    public void addOrder(Order order) throws PersistenceException {
        // find id first then add to file
        try (RandomAccessFile file = new RandomAccessFile(largestOrderNumberFile, "rw")) {
            // read the current largest order number (if file doesnt exist set it to 0)
            String line = file.readLine();
            int currentNumber = line != null ? Integer.parseInt(line.trim()) : 0;

            // assign order
            order.setOrderNumber(currentNumber);

            // write order to correct file
            writeOrderToFile(order);

            // increment it
            currentNumber++;

            // seek to beginning and write back
            file.seek(0);
            file.writeBytes(String.valueOf(currentNumber));
            file.setLength(String.valueOf(currentNumber).length());
        } catch (IOException | NumberFormatException e) {
            throw new PersistenceException("Error maintaining order numbers: " + e.getMessage());
        }
    }

    public Order getOrder(LocalDate date, int orderNumber) throws PersistenceException {
        File orderFile = getOrderFileFromDate(date);

        if (!orderFile.exists()) {
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(orderFile))) {
            // skip header line
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                Order order = parseOrderFromCsv(line, date);

                if (order != null && order.getOrderNumber() == orderNumber) {
                    return order;
                }
            }
        } catch (IOException e) {
            throw new PersistenceException("Error reading order file: " + e.getMessage());
        }

        return null;
    }

    public void editOrder(Order newOrder) throws PersistenceException {
        LocalDate date = newOrder.getDate();
        if (date == null) {
            throw new PersistenceException("Order date is required");
        }

        String replacementLine = formatOrderAsCsv(newOrder);

        modifyOrderInFile(date, newOrder.getOrderNumber(), replacementLine, "editing order");
    }

    public List<Order> getOrdersForDate(LocalDate date) throws PersistenceException {
        File orderFile = getOrderFileFromDate(date);

        List<Order> orders = new LinkedList<>();

        // if file doesn't exist, return empty list
        if (!orderFile.exists()) {
            return orders;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(orderFile))) {
            // skip header line
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                Order order = parseOrderFromCsv(line, date);

                if (order != null) {
                    orders.add(order);
                }
            }
        } catch (IOException e) {
            throw new PersistenceException("Error reading orders for date: " + e.getMessage());
        }

        return orders;
    }

    public void removeOrder(LocalDate date, int orderNumber) throws PersistenceException {
        modifyOrderInFile(date, orderNumber, null, "removing order");
    }

    private void writeOrderToFile(Order order) throws PersistenceException {
        LocalDate date = order.getDate();

        if (date == null) {
            throw new PersistenceException("Order date is required");
        }

        File orderFile = getOrderFileFromDate(date);

        boolean fileExists = orderFile.exists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(orderFile, true))) {
            // write header if file is new
            if (!fileExists) {
                writer.write(orderHeader);
                writer.newLine();
            }

            // write order data
            String orderLine = formatOrderAsCsv(order);

            writer.write(orderLine);
            writer.newLine();
        } catch (IOException e) {
            throw new PersistenceException("Error writing order to file: " + e.getMessage());
        }
    }

    private void modifyOrderInFile(LocalDate date, Integer targetOrderNumber, String replacementLine, String operation)
            throws PersistenceException {
        File orderFile = getOrderFileFromDate(date);
        String fileName = orderFile.getPath();

        if (!orderFile.exists()) {
            throw new PersistenceException("Order file does not exist for date: " + date);
        }

        // use temp file to avoid loading entire file into memory
        File tempFile = new File(fileName + ".tmp");
        boolean orderFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(orderFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                // keep header
                if (line.equals(orderHeader)) {
                    writer.write(line);
                    writer.newLine();
                } else {
                    Order order = parseOrderFromCsv(line, date);

                    if (order != null && order.getOrderNumber() != null
                            && order.getOrderNumber().equals(targetOrderNumber)) {
                        // order matches - replace or skip based on replacementLine
                        if (replacementLine != null) {
                            // edit: write replacement line
                            writer.write(replacementLine);
                            writer.newLine();
                        }

                        // remove: skip writing (replacementLine is null)
                        orderFound = true;
                    } else {
                        // keep existing line
                        writer.write(line);
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            if (tempFile.exists()) {
                tempFile.delete();
            }

            throw new PersistenceException("Error " + operation + ": " + e.getMessage());
        }

        if (!orderFound) {
            tempFile.delete();

            throw new PersistenceException("Order not found for " + operation);
        }

        // replace original file with temp file
        if (orderFile.delete() && tempFile.renameTo(orderFile)) {
            // success
        } else {
            tempFile.delete();

            throw new PersistenceException("Error replacing order file after " + operation);
        }
    }

    private File getOrderFileFromDate(LocalDate date) {
        // format date as MMDDYYYY with leading zeros
        String dateStr = String.format("%02d%02d%04d", date.getMonthValue(), date.getDayOfMonth(), date.getYear());
        String fileName = orderFolder + "/Orders_" + dateStr + ".txt";

        return new File(fileName);
    }

    private String formatOrderAsCsv(Order order) {
        return order.getOrderNumber() + "," +
                order.getCustomerName() + "," +
                order.getState() + "," +
                order.getTaxRate() + "," +
                order.getProductType() + "," +
                order.getArea() + "," +
                order.getCostPerSquareFoot() + "," +
                order.getLaborCostPerSquareFoot() + "," +
                order.getMaterialCost() + "," +
                order.getLaborCost() + "," +
                order.getTax() + "," +
                order.getTotal();
    }

    private Order parseOrderFromCsv(String csvLine, LocalDate date) {
        try {
            String[] fields = csvLine.split(",");
            if (fields.length != 12) {
                return null;
            }

            Order order = new Order();
            order.setOrderNumber(Integer.parseInt(fields[0].trim()));
            order.setCustomerName(fields[1].trim());
            order.setState(fields[2].trim());
            order.setTaxRate(new BigDecimal(fields[3].trim()));
            order.setProductType(fields[4].trim());
            order.setArea(new BigDecimal(fields[5].trim()));
            order.setCostPerSquareFoot(new BigDecimal(fields[6].trim()));
            order.setLaborCostPerSquareFoot(new BigDecimal(fields[7].trim()));
            order.setMaterialCost(new BigDecimal(fields[8].trim()));
            order.setLaborCost(new BigDecimal(fields[9].trim()));
            order.setTax(new BigDecimal(fields[10].trim()));
            order.setTotal(new BigDecimal(fields[11].trim()));
            order.setDate(date);

            return order;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // getters
    public String getOrderFolder() {
        return this.orderFolder;
    }

    public String getOrderHeader() {
        return this.orderHeader;
    }
}