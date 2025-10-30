package com.mthree.tobiidowu.flooringMastery.dao.impl;
import com.mthree.tobiidowu.flooringMastery.model.Product;
import java.util.List;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductDaoFileImplTest {

    @Test
    void getAllProducts_returnsList() throws Exception {
        ProductDaoFileImpl dao = new ProductDaoFileImpl();

        List<Product> products = dao.getAllProducts();

        assertNotNull(products);
    }

    // test that calling getAllProducts() returns a list that matches up with the products file
    // use mock object and do multiple tests like checking the size of the list and its contents
    @Test
    void getAllProducts_matchesFileSizeAndContainsTypes() throws Exception {
        ProductDaoFileImpl dao = new ProductDaoFileImpl();

        List<Product> products = dao.getAllProducts();

        // verify contains expected product types
        assertTrue(products.stream().anyMatch(p -> "Carpet".equalsIgnoreCase(p.getProductType())));
        assertTrue(products.stream().anyMatch(p -> "Laminate".equalsIgnoreCase(p.getProductType())));
        assertTrue(products.stream().anyMatch(p -> "Tile".equalsIgnoreCase(p.getProductType())));
        assertTrue(products.stream().anyMatch(p -> "Wood".equalsIgnoreCase(p.getProductType())));
    }

    @Test
    void getAllProducts_returnsSameListTwice() throws Exception {
        ProductDaoFileImpl dao = new ProductDaoFileImpl();

        List<Product> products1 = dao.getAllProducts();
        List<Product> products2 = dao.getAllProducts();

        // test that calling getAllProducts() twice returns the same list instance
        assertSame(products1, products2);
    }

    @Test
    void getAllProducts_hasExpectedCostsForKnownTypes() throws Exception {
        ProductDaoFileImpl dao = new ProductDaoFileImpl();

        List<Product> products = dao.getAllProducts();

        Product carpet = products.stream().filter(p -> "Carpet".equalsIgnoreCase(p.getProductType())).findFirst().orElseThrow();
        Product tile = products.stream().filter(p -> "Tile".equalsIgnoreCase(p.getProductType())).findFirst().orElseThrow();
        Product laminate = products.stream().filter(p -> "Laminate".equalsIgnoreCase(p.getProductType())).findFirst().orElseThrow();
        Product wood = products.stream().filter(p -> "Wood".equalsIgnoreCase(p.getProductType())).findFirst().orElseThrow();

        // values taken from Products.txt
        assertEquals(new BigDecimal("2.25"), carpet.getCostPerSquareFoot());
        assertEquals(new BigDecimal("2.10"), carpet.getLaborCostPerSquareFoot());

        assertEquals(new BigDecimal("3.50"), tile.getCostPerSquareFoot());
        assertEquals(new BigDecimal("4.15"), tile.getLaborCostPerSquareFoot());

        assertEquals(new BigDecimal("1.75"), laminate.getCostPerSquareFoot());
        assertEquals(new BigDecimal("2.10"), laminate.getLaborCostPerSquareFoot());

        assertEquals(new BigDecimal("5.15"), wood.getCostPerSquareFoot());
        assertEquals(new BigDecimal("4.75"), wood.getLaborCostPerSquareFoot());
    }
}
