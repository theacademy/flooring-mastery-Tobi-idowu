package com.mthree.tobiidowu.flooringMastery.controller;

import com.mthree.tobiidowu.flooringMastery.model.Order;
import com.mthree.tobiidowu.flooringMastery.model.Product;
import com.mthree.tobiidowu.flooringMastery.model.Tax;
import com.mthree.tobiidowu.flooringMastery.service.ServiceLayer;
import com.mthree.tobiidowu.flooringMastery.view.View;
import java.time.LocalDate;

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
        return;
    }

    private int getMenuSelection() {
        return 0;
    }

    private void displayOrders(LocalDate date) {
        return;
    }

    private void addOrder(Order order) {
        return;
    }

    private void editOrder(Order old, Order new_) {
        return;
    }

    private void removeOrder(Order order) {
        return;
    }

    private void exportOrder() {
        return;
    }

    private void exitMessage() {
        return;
    }
}
