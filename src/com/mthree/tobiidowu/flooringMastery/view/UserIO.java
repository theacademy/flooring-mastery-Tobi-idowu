package com.mthree.tobiidowu.flooringMastery.view;

public interface UserIO {
    public void print(String message);

    public String readString(String prompt);

    public int readInt(String prompt);

    public int readInt(String prompt, int min, int max);
}
