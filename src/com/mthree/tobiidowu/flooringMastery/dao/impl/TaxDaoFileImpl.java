package com.mthree.tobiidowu.flooringMastery.dao.impl;

import com.mthree.tobiidowu.flooringMastery.dao.TaxDao;
import com.mthree.tobiidowu.flooringMastery.model.Tax;
import java.util.List;
import java.util.LinkedList;
import org.springframework.stereotype.Component;

@Component
public class TaxDaoFileImpl implements TaxDao {
    private String taxFile;
    private List<Tax> taxes;

    // constructor
    public TaxDaoFileImpl() {
        this.taxFile = "/../../../../../../../Data/Lookup/Taxes.txt";
        this.taxes = null;
    }

    public List<Tax> getAllTaxes() throws PersistenceException {
        return new LinkedList<>();
    }
}