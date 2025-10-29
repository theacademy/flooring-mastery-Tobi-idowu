package com.mthree.tobiidowu.flooringMastery.dao.impl;

import com.mthree.tobiidowu.flooringMastery.dao.ExportDao;
import com.mthree.tobiidowu.flooringMastery.dao.OrderDao;
import com.mthree.tobiidowu.flooringMastery.exception.PersistenceException;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class ExportDaoFileImpl implements ExportDao {
    private OrderDao orderDao;

    @Autowired
    public ExportDaoFileImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public void exportData() throws PersistenceException {
        return;
    }
}