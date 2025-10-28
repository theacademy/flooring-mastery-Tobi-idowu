package com.mthree.tobiidowu.flooringMastery.model;

import java.math.BigDecimal;


public class Tax {
    private String state;
    private String stateName;
    private BigDecimal taxRate;

    // getters
    public String getState() {
        return state;
    }

    public String getStateName() {
        return stateName;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }
}
