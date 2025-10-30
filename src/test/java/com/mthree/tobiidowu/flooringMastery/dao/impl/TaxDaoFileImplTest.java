package com.mthree.tobiidowu.flooringMastery.dao.impl;
import com.mthree.tobiidowu.flooringMastery.model.Tax;
import java.util.List;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaxDaoFileImplTest {

    @Test
    void getAllTaxes_returnsList() throws Exception {
        TaxDaoFileImpl dao = new TaxDaoFileImpl();

        List<Tax> taxes = dao.getAllTaxes();

        assertNotNull(taxes);
    }

    @Test
    void getAllTaxes_returnsSameListTwice() throws Exception {
        TaxDaoFileImpl dao = new TaxDaoFileImpl();

        List<Tax> taxes = dao.getAllTaxes();
        List<Tax> taxes2 = dao.getAllTaxes();

        // test that calling getAllTaxes() twice returns the same list instance
        assertSame(taxes, taxes2);
    }

    @Test
    void getAllTaxes_matchesFileSizeAndContainsStates() throws Exception {
        TaxDaoFileImpl dao = new TaxDaoFileImpl();

        List<Tax> taxes = dao.getAllTaxes();

        // verify contains expected state abbreviations
        assertTrue(taxes.stream().anyMatch(t -> "TX".equalsIgnoreCase(t.getState())));
        assertTrue(taxes.stream().anyMatch(t -> "WA".equalsIgnoreCase(t.getState())));
        assertTrue(taxes.stream().anyMatch(t -> "KY".equalsIgnoreCase(t.getState())));
        assertTrue(taxes.stream().anyMatch(t -> "CA".equalsIgnoreCase(t.getState())));
    }

    @Test
    void getAllTaxes_hasExpectedRatesForKnownStates() throws Exception {
        TaxDaoFileImpl dao = new TaxDaoFileImpl();

        List<Tax> taxes = dao.getAllTaxes();

        Tax ca = taxes.stream().filter(t -> "CA".equalsIgnoreCase(t.getState())).findFirst().orElseThrow();
        Tax tx = taxes.stream().filter(t -> "TX".equalsIgnoreCase(t.getState())).findFirst().orElseThrow();
        Tax wa = taxes.stream().filter(t -> "WA".equalsIgnoreCase(t.getState())).findFirst().orElseThrow();
        Tax ky = taxes.stream().filter(t -> "KY".equalsIgnoreCase(t.getState())).findFirst().orElseThrow();

        // values taken from Taxes.txt
        assertEquals("California", ca.getStateName());
        assertEquals(new BigDecimal("25.00"), ca.getTaxRate());

        assertEquals("Texas", tx.getStateName());
        assertEquals(new BigDecimal("4.45"), tx.getTaxRate());

        assertEquals("Washington", wa.getStateName());
        assertEquals(new BigDecimal("9.25"), wa.getTaxRate());

        assertEquals("Kentucky", ky.getStateName());
        assertEquals(new BigDecimal("6.00"), ky.getTaxRate());
    }
}
