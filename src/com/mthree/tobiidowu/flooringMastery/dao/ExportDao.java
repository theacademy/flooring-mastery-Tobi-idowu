package com.mthree.tobiidowu.flooringMastery.dao;

import com.mthree.tobiidowu.flooringMastery.exception.PersistenceException;

public interface ExportDao {
    public void exportData() throws PersistenceException;
}