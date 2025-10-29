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

        while (programRunning) {
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
        LocalDate date = view.getDisplayOrdersInput();
        try {
            List<Order> orders = service.getOrdersForDate(date);

            view.displayOrdersList(orders);
        } catch (PersistenceException e) {
            view.displayErrorMessage("Error getting orders for date: " + e.getMessage());
        }
    }

    private void addOrder() {
        view.displayAddOrderBanner();

        LocalDate date = view.getOrderDateInput();

        try {
            List<Tax> taxes = service.getTaxes();
            List<Product> products = service.getProducts();

            Order newOrder = view.getAddOrderInput(taxes, products);
            newOrder.setDate(date);

            service.calculateOrderAttributes(newOrder);

            if (!view.confirmAddOrder(newOrder)) {
                return;
            }

            service.addOrder(newOrder);

            view.displayAddOrderSuccess();
        } catch (PersistenceException e) {
            view.displayErrorMessage("Error adding order: " + e.getMessage());
        }
    }

    private void editOrder(Order old, Order new_) {
        view.displayEditOrderBanner();

        Order potentialOrder = view.getEditOrderInput();

        try {
            Order existingOrder = service.getOrder(potentialOrder.getDate(), potentialOrder.getOrderNumber());

            List<Tax> taxes = service.getTaxes();
            List<Product> products = service.getProducts();

            Order editedOrder = view.getEditOrderInput(existingOrder, taxes, products);

            service.calculateOrderAttributes(editedOrder);

            if (!view.confirmEditOrder(existingOrder, editedOrder)) {
                return;
            }

            service.editOrder(editedOrder);

            view.displayEditOrderSuccess();
        } catch (PersistenceException e) {
            view.displayErrorMessage("Error editing order: " + e.getMessage());
        } catch (NoSuchOrderException e) {
            view.displayErrorMessage("Error removing order: " + e.getMessage());
        }
    }

    private void removeOrder(Order order) {
        view.displayRemoveOrderBanner();

        Order potentialOrder = view.getRemoveOrderInput();

        try {
            Order existingOrder = service.getOrder(potentialOrder.getDate(), potentialOrder.getOrderNumber());

            if (!view.getRemoveOrderConfirmation(potentialOrder)) {
                return;
            }
            service.removeOrder(potentialOrder);

            view.displayRemoveOrderSuccess();
        } catch (PersistenceException e) {
            view.displayErrorMessage("Error removing order: " + e.getMessage());
        } catch (NoSuchOrderException e) {
            view.displayErrorMessage("Error removing order: " + e.getMessage());
        }
    }

    private void exportOrder() {
        if (!view.getExportDataConfirmation()) {
            return;
        }

        try {
            service.exportData();

            view.exportDataSuccessMessage();
        } catch (PersistenceException e) {
            view.displayErrorMessage("Error exporting data: " + e.getMessage());
        }
    }

    private void exitMessage() {
        view.displayQuitMessage();
    }
}
