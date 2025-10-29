package com.mthree.tobiidowu.flooringMastery.view;


import com.mthree.tobiidowu.flooringMastery.model.Order;
import com.mthree.tobiidowu.flooringMastery.model.Product;
import com.mthree.tobiidowu.flooringMastery.model.Tax;
import java.time.LocalDate;
import.java.util.List;
import java.math.BigDecimal;

@Component
public class View {
    private UserIO io;

    // constructor
    @Autowired
    public View(UserIO io) {
        this.io = io;
    }

    public int displayMainMenuAndGetSelection() {
        return 0;
    }

    public LocalDate getDateInput() {
        return null;
    }

    public int getOrderNumberInput() {
        return 0;
    }

    // display order methods
    public void displayOrderInfo(Order order) {
        return;
    }

    public void displayOrderInfo(Order order) {
        return;
    }

    // add order methods
    public void displayAddOrderBanner() {
        return;
    }

    public Order getAddOrderInput(List<Tax> taxes, List<Product> products) {
        return null;
    }

    public void displayAddOrderSuccess() {
        return;
    }

    // edit order methods
    public void displayEditOrderBanner() {
        return;
    }

    public Order getEditOrderInput(List<Tax> taxes, List<Product> products) {
        return null;
    }

    public void displayEditOrderSuccess() {
        return;
    }

    // remove order methods
    public void displayRemoveOrderBanner() {
        return;
    }

    public Order getRemoveOrderInput() {
        return null;
    }

    public boolean getRemoveOrderConfirmation() {
        return false;
    }

    public void displayRemoveOrderSuccess() {
        return;
    }

    // export data methods
    public boolean getExportDataConfirmation() {
        return false;
    }

    public void displayExportDataSuccess() {
        return;
    }

    // quit method
    public void displayQuitMessage() {
        return;
    }

    // error method
    public void ErrorMessage() {
        return;
    }

    // validate methods
    private String validateNameInput(String name) {
        return "";
    }

    private Tax validateStateInput(String state) {
        return null;
    }

    private Product validateProductInput(String productName) {
        return null;
    }

    private BigDecimal validateAreaInput(String area) {
        return null;
    }
}
