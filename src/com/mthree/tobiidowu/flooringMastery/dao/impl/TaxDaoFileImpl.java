package com.mthree.tobiidowu.flooringMastery.dao.impl;

import com.mthree.tobiidowu.flooringMastery.dao.TaxDao;
import com.mthree.tobiidowu.flooringmastery.model.Tax;
import java.util.List;
import java.util.LinkedList;

@Component
public class TaxDaoFileImpl implements TaxDao {
    private String taxFile;
    private List<Tax> taxes;

    // constructor
    public TaxDaoFileImpl() {
        this.taxFile = "/../../../../../../../Data/Lookup/Taxes.txt"
        this.taxes = null;
    }

    public List<Tax> getAllTaxes() {
        return new LinkedList<>();
    }
}