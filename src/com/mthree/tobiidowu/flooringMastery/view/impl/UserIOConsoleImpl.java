package com.mthree.tobiidowu.flooringMastery.view.impl;

import com.mthree.tobiidowu.flooringMastery.view.UserIO;
import com.mthree.tobiidowu.flooringMastery.model.Order;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class UserIOConsoleImpl implements UserIO {
    private Scanner console;

    // constructor
    public UserIOConsoleImpl() {
        this.console = new Scanner(System.in);
    }

    public void print(String message) {
        System.out.println(message);

        return;
    }

    public String readString(String prompt) {
        System.out.print(prompt);

        return console.nextLine();
    }

    public int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);

            String input = console.nextLine();

            try {
                int number = Integer.parseInt(input);

                return number;
            } catch (Exception e) {
                System.out.println("Please enter an integer.\n");
            }
        }
    }

    public int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);

            String input = console.nextLine();

            try {
                int number = Integer.parseInt(input);

                if (number >= min && number <= max) {
                    return number;
                }

                System.out.printf("\nPlease enter an integer between %d and %d inclusive.\n", min, max);
            } catch (Exception e) {
                System.out.println("Please enter an integer.\n");
            }
        }
    }

    public void displayOrder(Order order) {
        String output = order.getOrderNumber() + "," + order.getCustomerName() + "," + order.getState() + "," +
                order.getTaxRate() + "," + order.getProductType() + "," + order.getArea() + "," +
                order.getCostPerSquareFoot() + "," + order.getLaborCostPerSquareFoot() + "," +
                order.getMaterialCost() + "," + order.getLaborCost() + "," + order.getTax() + "," +
                order.getTotal();

        System.out.println(output);
    }
}
