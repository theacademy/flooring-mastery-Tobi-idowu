package com.mthree.tobiidowu.flooringMastery.dao.impl;

import com.mthree.tobiidowu.flooringMastery.dao.ExportDao;
import com.mthree.tobiidowu.flooringMastery.dao.OrderDao;
import com.mthree.tobiidowu.flooringMastery.exception.PersistenceException;

@Component
public class ExportDaoFileImpl implements ExportDao {
    private OrderDao orderDao;

    @Autowire
    public ExportDaoFileImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public void exportData() throws PersistenceException {
        return;
    }
}