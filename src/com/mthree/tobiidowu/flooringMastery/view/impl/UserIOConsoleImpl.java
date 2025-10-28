package com.mthree.tobiidowu.flooringMastery.view.impl;

import com.mthree.tobiidowu.flooringMastery.view.UserIO;
import java.util.Scanner;

@Component
public class UserIOConsoleImpl implements UserIO {
    private Scanner console;

    public UserIOConsoleImpl() {
        this.console = new Scanner(System.in);
    }

    public void print(String message) {
        return;
    }

    public String readString(String prompt) {
        return "";
    }

    public int readInt(String prompt) {
        return 0;
    }

    public int readInt(String prompt, int min, int max) {
        return 0;
    }
}
