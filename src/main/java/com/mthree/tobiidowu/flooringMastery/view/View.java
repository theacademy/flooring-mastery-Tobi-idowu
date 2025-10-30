package com.mthree.tobiidowu.flooringMastery.view;

import com.mthree.tobiidowu.flooringMastery.model.Order;
import com.mthree.tobiidowu.flooringMastery.model.Product;
import com.mthree.tobiidowu.flooringMastery.model.Tax;
import com.mthree.tobiidowu.flooringMastery.view.UserIO;
import java.time.LocalDate;
import java.time.DateTimeException;
import java.util.List;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class View {
    private UserIO io;

    // constructor
    @Autowired
    public View(UserIO io) {
        this.io = io;
    }

    public int displayMainMenuAndGetSelection() {
        String mainMenu = "\n  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n" +
                "  *\n" +
                "  *  <<Flooring Program>>\n" +
                "  *\n" +
                "  *  1. Display Orders\n" +
                "  *  2. Add an Order\n" +
                "  *  3. Edit an Order\n" +
                "  *  4. Remove an Order\n" +
                "  *  5. Export All Data\n" +
                "  *  6. Quit\n" +
                "  *\n" +
                "  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n";

        io.print(mainMenu);

        int choice = io.readInt("What choice would you like to make?: ", 1, 6);

        return choice;
    }

    public LocalDate getDateInput() {
        LocalDate dateObj;

        while (true) {
            // get date from user
            String date = io.readString("Please enter a date in the MM/DD/YYYY format: ");

            // check for proper formatting
            String[] splitDate = date.split("/");

            if (splitDate.length != 3) {
                continue;
            }

            try {
                // confirm date exists
                int month = Integer.parseInt(splitDate[0]);
                int day = Integer.parseInt(splitDate[1]);
                int year = Integer.parseInt(splitDate[2]);

                // create date object
                dateObj = LocalDate.of(year, month, day);

                return dateObj;
            } catch (NumberFormatException e) {
                io.print("Invalid integer input: The month day and year should all be integers.\n");
            } catch (DateTimeException e) {
                io.print("Invalid date.");
            }
        }
    }

    public int getOrderNumberInput() {
        return io.readInt("Enter an order number: ");
    }

    // display order methods
    public void displayOrderInfo(Order order) {
        io.displayOrder(order);
    }

    public LocalDate getDisplayOrdersInput() {
        LocalDate date = getDateInput();

        return date;
    }

    public void displayOrdersList(List<Order> orders) {
        if (orders.isEmpty()) {
            io.print("\nNo orders found for this date.\n");
            return;
        }

        io.print("\n=== Orders ===");

        // display each order
        for (Order order : orders) {
            displayOrderInfo(order);
        }
    }

    // add order methods
    public void displayAddOrderBanner() {
        io.print("\n=== Add Order ===");
    }

    public LocalDate getAddOrderDateInput() {
        while (true) {
            // get date from user
            LocalDate date = getDateInput();

            // get current date
            LocalDate today = LocalDate.now();

            // check if date is in the future
            if (date.isAfter(today)) {
                return date;
            } else {
                io.print("Error: Order date must be in the future. Please try again.\n");
            }
        }
    }

    public Order getAddOrderInput(List<Tax> taxes, List<Product> products) {
        // get customer name
        String customerName = getNameInput("Enter customer name: ", false);

        // get state
        Tax selectedTax = getStateInput("Enter state abbreviation: ", taxes, false);

        // display available products
        io.print("\nAvailable products:");

        for (Product product : products) {
            String info = String.format("- %s (Cost per square feet: %f, Labor per square feet: %f)",
                    product.getProductType(),
                    product.getCostPerSquareFoot(),
                    product.getLaborCostPerSquareFoot());

            io.print(info);
        }

        // get product type
        Product selectedProduct = getProductInput("\nEnter product type: ", products, false);

        // get area
        BigDecimal area = getAreaInput("Enter area in square feet, minimum 100.0 sq ft: ", false);

        // Create Order with all available fields
        Order order = new Order();

        order.setOrderNumber(-1);
        order.setCustomerName(customerName);
        order.setState(selectedTax.getState());
        order.setTaxRate(selectedTax.getTaxRate());
        order.setProductType(selectedProduct.getProductType());
        order.setCostPerSquareFoot(selectedProduct.getCostPerSquareFoot());
        order.setLaborCostPerSquareFoot(selectedProduct.getLaborCostPerSquareFoot());
        order.setArea(area);

        return order;
    }

    public boolean confirmAddOrder(Order order) {
        io.print("\n=== Confirm Add Order ===");

        // display order details
        displayOrderInfo(order);

        // get confirmation from user
        int choice = io.readInt("\nPlace this order?\n1. Yes\n2. No\nChoice: ", 1, 2);

        return choice == 1;
    }

    public void displayAddOrderSuccess() {
        io.print("\nOrder added successfully!\n");
    }

    // edit order methods
    public void displayEditOrderBanner() {
        io.print("\n=== Edit Order ===");
    }

    public Order getEditOrderInput() {
        // get date and order number from user
        LocalDate date = getDateInput();
        int orderNumber = getOrderNumberInput();

        // add details to order object
        Order potentialOrder = new Order();

        potentialOrder.setOrderNumber(orderNumber);
        potentialOrder.setDate(date);

        return potentialOrder;
    }

    public Order getEditOrderInput(Order existingOrder, List<Tax> taxes, List<Product> products) {
        Order edited = new Order();

        // get valid customer name
        String maybeName = getNameInput("\nEnter customer name (" + existingOrder.getCustomerName() + "): ", true);

        // get valid state
        Tax maybeTax = getStateInput("Enter state abbreviation (" + existingOrder.getState() + "): ", taxes, true);

        // get valid product type
        Product maybeProduct = getProductInput("Enter product type (" + existingOrder.getProductType() + "): ",
                products, true);

        // get valid area
        BigDecimal maybeArea = getAreaInput("Enter area (" + existingOrder.getArea() + " sq ft): ", true);

        // set order number
        edited.setOrderNumber(existingOrder.getOrderNumber());

        // set date
        edited.setDate(existingOrder.getDate());

        // set name
        if (maybeName == null) {
            edited.setCustomerName(existingOrder.getCustomerName());
        } else {
            edited.setCustomerName(maybeName);
        }

        // set area
        if (maybeArea == null) {
            edited.setArea(existingOrder.getArea());
        } else {
            edited.setArea(maybeArea);
        }

        // Set tax info
        if (maybeTax != null) {
            edited.setState(maybeTax.getState());
            edited.setTaxRate(maybeTax.getTaxRate());
        } else {
            edited.setState(existingOrder.getState());
            edited.setTaxRate(existingOrder.getTaxRate());
        }

        // Set product info
        if (maybeProduct != null) {
            edited.setProductType(maybeProduct.getProductType());
            edited.setCostPerSquareFoot(maybeProduct.getCostPerSquareFoot());
            edited.setLaborCostPerSquareFoot(maybeProduct.getLaborCostPerSquareFoot());
        } else {
            edited.setProductType(existingOrder.getProductType());
            edited.setCostPerSquareFoot(existingOrder.getCostPerSquareFoot());
            edited.setLaborCostPerSquareFoot(existingOrder.getLaborCostPerSquareFoot());
        }

        return edited;
    }

    public boolean confirmEditOrder(Order old, Order new_) {
        io.print("\n=== Confirm Edit Order ===");

        io.print("\nOld order:");
        displayOrderInfo(old);

        io.print("\nNew order:");
        displayOrderInfo(new_);
        io.print("");

        int choice = io.readInt("Save these changes?\n1. Yes\n2. No\nChoice: ", 1, 2);

        return choice == 1;
    }

    public void displayEditOrderSuccess() {
        io.print("\nOrder edited successfully!\n");
    }

    // remove order methods
    public void displayRemoveOrderBanner() {
        io.print("\n=== Remove Order ===");
    }

    public Order getRemoveOrderInput() {
        // get date and order number from user
        LocalDate date = getDateInput();
        int orderNumber = getOrderNumberInput();

        Order potentialOrder = new Order();

        // add details to order object
        potentialOrder.setOrderNumber(orderNumber);
        potentialOrder.setDate(date);

        return potentialOrder;
    }

    public boolean getRemoveOrderConfirmation(Order order) {
        io.print("\n=== Confirm Remove Order ===");

        // display order details
        displayOrderInfo(order);

        // get confirmation from user
        int choice = io.readInt("\nRemove this order?\n1. Yes\n2. No\nChoice: ", 1, 2);

        return choice == 1;
    }

    public void displayRemoveOrderSuccess() {
        io.print("\nOrder removed successfully!\n");
    }

    // export data methods
    public boolean getExportDataConfirmation() {
        io.print("\n=== Confirm Export Data ===");

        // get confirmation from user
        int choice = io.readInt("Export all order data?\n1. Yes\n2. No\nChoice: ", 1, 2);

        return choice == 1;
    }

    public void exportDataSuccessMessage() {
        io.print("\nData exported successfully!\n");
    }

    // quit method
    public void displayQuitMessage() {
        io.print("\nHappy to help!\n");
    }

    // error method
    public void errorMessage(String message) {
        io.print(message);
    }

    // validate methods
    private String getNameInput(String prompt, boolean allowEmpty) {
        while (true) {
            // get input from user
            String input = io.readString(prompt);
            String trimmed = input.trim();

            if (trimmed.isEmpty()) {
                if (allowEmpty)
                    return null;

                io.print("Customer name can't be blank. Please try again.\n");
                continue;
            }

            // check if input is valid
            if (trimmed.matches("[a-zA-Z0-9., ]+")) {
                return trimmed;
            }

            io.print("Customer name can only contain letters, numbers, spaces, commas, and periods. Please try again.\n");
        }
    }

    private Tax getStateInput(String prompt, List<Tax> taxes, boolean allowEmpty) {
        while (true) {
            // get input from user
            String input = io.readString(prompt);
            String abbr = input.trim();

            if (abbr.isEmpty()) {
                if (allowEmpty)
                    return null;

                io.print("Invalid state. Please try again.\n");
                continue;
            }

            // validate state
            for (Tax tax : taxes) {
                if (tax.getState().equalsIgnoreCase(abbr)) {
                    return tax;
                }
            }

            io.print("Invalid state. Please try again.\n");
        }
    }

    private Product getProductInput(String prompt, List<Product> products, boolean allowEmpty) {
        while (true) {
            // get input from user
            String input = io.readString(prompt);
            String name = input.trim();

            if (name.isEmpty()) {
                if (allowEmpty)
                    return null;

                io.print("Invalid product. Please try again.\n");
                continue;
            }

            // validate product
            for (Product product : products) {
                if (product.getProductType().equalsIgnoreCase(name)) {
                    return product;
                }
            }

            io.print("Invalid product. Please try again.\n");
        }
    }

    private BigDecimal getAreaInput(String prompt, boolean allowEmpty) {
        while (true) {
            // get input from user
            String input = io.readString(prompt);
            String s = input.trim();

            if (s.isEmpty()) {
                if (allowEmpty)
                    return null;

                io.print("Area must be a valid number and at least 100.0 square feet. Please try again.\n");
                continue;
            }
            // validate area
            try {
                BigDecimal area = new BigDecimal(s);

                if (area.compareTo(BigDecimal.valueOf(100)) < 0) {
                    io.print("Area must be a valid number and at least 100.0 square feet. Please try again.\n");
                    continue;
                }

                return area;
            } catch (Exception e) {
                io.print("Area must be a valid number and at least 100.0 square feet. Please try again.\n");
            }
        }
    }
}
