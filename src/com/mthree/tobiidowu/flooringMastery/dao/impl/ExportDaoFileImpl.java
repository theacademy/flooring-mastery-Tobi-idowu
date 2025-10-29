package com.mthree.tobiidowu.flooringMastery.dao.impl;

import com.mthree.tobiidowu.flooringMastery.dao.ExportDao;
import com.mthree.tobiidowu.flooringMastery.dao.OrderDao;
import com.mthree.tobiidowu.flooringMastery.exception.PersistenceException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class ExportDaoFileImpl implements ExportDao {
    private OrderDao orderDao;
    private String exportFile;

    @Autowired
    public ExportDaoFileImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
        this.exportFile = "/../../../../../../../Data/Backup/DataExport.txt";
    }

    public void exportData() throws PersistenceException {
        // find the name of the folder holding all the order files
        String orderFolder = orderDao.getOrderFolder();
        String orderHeader = orderDao.getOrderHeader();

        // extend header for the export file
        String exportHeader = orderHeader + ",Date";

        // find the orders folder
        File ordersDir = new File(orderFolder);
        if (!ordersDir.exists() || !ordersDir.isDirectory()) {
            throw new PersistenceException("Orders directory not found: " + orderFolder);
        }

        // create Backup directory if it doesn't exist
        File backupDir = new File(exportFile);
        if (!backupDir.exists()) {
            backupDir.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(exportFile))) {
            // write header with date column
            writer.write(exportHeader);
            writer.newLine();

            // get all order files
            File[] orderFiles = ordersDir.listFiles((dir, name) -> name.startsWith("Orders_") && name.endsWith(".txt"));

            if (orderFiles != null) {
                // process each order file
                for (File orderFile : orderFiles) {
                    // extract date from filename
                    String filename = orderFile.getName();
                    String dateStr = filename.substring(7, 15);

                    // convert date string to MM-DD-YYYY
                    String month = dateStr.substring(0, 2);
                    String day = dateStr.substring(2, 4);
                    String year = dateStr.substring(4, 8);

                    String formattedDate = month + "-" + day + "-" + year;

                    // open read connection for this order file
                    try (BufferedReader reader = new BufferedReader(new FileReader(orderFile))) {
                        // skip header line
                        reader.readLine();

                        String line;
                        // read and write each order line with date appended
                        while ((line = reader.readLine()) != null) {
                            if (line.trim().isEmpty()) {
                                continue;
                            }

                            // write current line
                            writer.write(line + "," + formattedDate);
                            writer.newLine();
                        }
                    } catch (IOException e) {
                        throw new PersistenceException(
                                "Error reading order file: " + filename + ", " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            throw new PersistenceException("Error writing export file: " + e.getMessage());
        }
    }
}