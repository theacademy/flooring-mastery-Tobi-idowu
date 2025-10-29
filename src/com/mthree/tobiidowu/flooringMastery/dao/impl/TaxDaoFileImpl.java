package com.mthree.tobiidowu.flooringMastery.dao.impl;

import com.mthree.tobiidowu.flooringMastery.dao.TaxDao;
import com.mthree.tobiidowu.flooringMastery.model.Tax;
import com.mthree.tobiidowu.flooringMastery.exception.PersistenceException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
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
        if (taxes != null) {
            return taxes;
        }

        taxes = new LinkedList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(taxFile))) {
            // Skip header line
            reader.readLine();

            String line;
            
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] fields = line.split(",");

                if (fields.length == 3) {
                    Tax tax = new Tax();

                    tax.setState(fields[0].trim());
                    tax.setStateName(fields[1].trim());
                    tax.setTaxRate(new BigDecimal(fields[2].trim()));

                    taxes.add(tax);
                }
            }
        } catch (IOException e) {
            throw new PersistenceException("Could not load tax data: " + e.getMessage());
        } catch (NumberFormatException e) {
            throw new PersistenceException("Error parsing tax rate: " + e.getMessage());
        }

        return taxes;
    }
}