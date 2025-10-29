package com.mthree.tobiidowu.flooringMastery.controller;

import com.mthree.tobiidowu.flooringMastery.model.Order;
import com.mthree.tobiidowu.flooringMastery.model.Product;
import com.mthree.tobiidowu.flooringMastery.model.Tax;
import com.mthree.tobiidowu.flooringMastery.service.ServiceLayer;
import com.mthree.tobiidowu.flooringMastery.view.View;
import java.time.LocalDate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class Controller {
    private ServiceLayer service;
    private View view;

    // constructor
    @Autowired
    public Controller(ServiceLayer service, View view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        boolean programRunning = true;

        // main loop
        while (programRunning) {
            // get menu selection
            int menuSelection = getMenuSelection();

            switch (menuSelection) {
                case 1:
                    displayOrders();
                    break;
                case 2:
                    addOrder();
                    break;
                case 3:
                    editOrder();
                    break;
                case 4:
                    removeOrder();
                    break;
                case 5:
                    exportOrder();
                    break;
                case 6:
                    programRunning = false;
                    exitMessage();
                    break;
            }
        }
    }

    private int getMenuSelection() {
        return view.displayMainMenuAndGetSelection();
    }

    private void displayOrders() {
        // get date from user
        LocalDate date = view.getDisplayOrdersInput();

        try {
            // get all orders for given date
            List<Order> orders = service.getOrdersForDate(date);

            // display orders
            view.displayOrdersList(orders);
        } catch (PersistenceException e) {
            view.displayErrorMessage("Error getting orders for date: " + e.getMessage());
        }
    }

    private void addOrder() {
        // display ui banner
        view.displayAddOrderBanner();

        // get order date from user
        LocalDate date = view.getAddOrderDateInput();

        try {
            // get all taxes and products
            List<Tax> taxes = service.getTaxes();
            List<Product> products = service.getProducts();

            // get new order input from user
            Order newOrder = view.getAddOrderInput(taxes, products);
            newOrder.setDate(date);

            // calculate order attributes
            service.calculateOrderAttributes(newOrder);

            // confirm add order
            if (!view.confirmAddOrder(newOrder)) {
                return;
            }

            // persist the new order
            service.addOrder(newOrder);

            // display success message
            view.displayAddOrderSuccess();
        } catch (PersistenceException e) {
            view.displayErrorMessage("Error adding order: " + e.getMessage());
        }
    }

    private void editOrder(Order old, Order new_) {
        // display ui banner
        view.displayEditOrderBanner();

        // get order to edit from user
        Order potentialOrder = view.getEditOrderInput();

        try {
            // get full order details
            Order existingOrder = service.getOrder(potentialOrder.getDate(), potentialOrder.getOrderNumber());

            if (existingOrder == null) {
                view.displayErrorMessage("Order not found.");
                return;
            }

            // get all taxes and products
            List<Tax> taxes = service.getTaxes();
            List<Product> products = service.getProducts();

            // get edited order input from user
            Order editedOrder = view.getEditOrderInput(existingOrder, taxes, products);

            // calculate order attributes
            service.calculateOrderAttributes(editedOrder);

            // confirm edit order
            if (!view.confirmEditOrder(existingOrder, editedOrder)) {
                return;
            }

            // persist the edited order
            service.editOrder(editedOrder);

            // display success message
            view.displayEditOrderSuccess();

        } catch (PersistenceException e) {
            view.displayErrorMessage("Error editing order: " + e.getMessage());
        } catch (NoSuchOrderException e) {
            view.displayErrorMessage("Error removing order: " + e.getMessage());
        }
    }

    private void removeOrder(Order order) {
        // display ui banner
        view.displayRemoveOrderBanner();

        // get order to remove from user
        Order potentialOrder = view.getRemoveOrderInput();

        try {
            // get full order details
            Order existingOrder = service.getOrder(potentialOrder.getDate(), potentialOrder.getOrderNumber());

            // get confirmation from user
            if (!view.getRemoveOrderConfirmation(potentialOrder)) {
                return;
            }
            
            // remove the order
            service.removeOrder(potentialOrder);

            // display success message
            view.displayRemoveOrderSuccess();

        } catch (PersistenceException e) {
            view.displayErrorMessage("Error removing order: " + e.getMessage());
        } catch (NoSuchOrderException e) {
            view.displayErrorMessage("Error removing order: " + e.getMessage());
        }
    }

    private void exportOrder() {
        // get confirmation from user
        if (!view.getExportDataConfirmation()) {
            return;
        }

        try {
            // export data
            service.exportData();

            // display success message
            view.exportDataSuccessMessage();
            
        } catch (PersistenceException e) {
            view.displayErrorMessage("Error exporting data: " + e.getMessage());
        }
    }

    private void exitMessage() {
        view.displayQuitMessage();
    }
}
