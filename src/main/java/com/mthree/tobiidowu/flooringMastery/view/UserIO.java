package com.mthree.tobiidowu.flooringMastery.view;

import com.mthree.tobiidowu.flooringMastery.model.Order;

public interface UserIO {
    public void print(String message);

    public String readString(String prompt);

    public int readInt(String prompt);

    public int readInt(String prompt, int min, int max);

    public void displayOrder(Order order);
}
