package com.mthree.tobiidowu.flooringMastery.dao;

import com.mthree.tobiidowu.flooringMastery.model.Tax;
import com.mthree.tobiidowu.flooringMastery.exception.PersistenceException;
import java.util.List;

public interface TaxDao {
    public List<Tax> getAllTaxes() throws PersistenceException;
}