package com.mthree.tobiidowu.flooringMastery.service;

import com.mthree.tobiidowu.flooringMastery.dao.OrderDao;
import com.mthree.tobiidowu.flooringMastery.dao.ProductDao;
import com.mthree.tobiidowu.flooringMastery.dao.TaxDao;
import com.mthree.tobiidowu.flooringMastery.dao.ExportDao;
import com.mthree.tobiidowu.flooringMastery.model.Order;
import com.mthree.tobiidowu.flooringMastery.service.impl.ServiceLayerImpl;
import java.math.BigDecimal;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ServiceLayerImplTest {

    @Mock
    OrderDao orderDao;
    @Mock
    ProductDao productDao;
    @Mock
    TaxDao taxDao;
    @Mock
    ExportDao exportDao;

    @InjectMocks
    ServiceLayerImpl service;

    @ParameterizedTest
    @CsvSource({
            "250.00, 3.50, 4.15, 25.00, 875.00, 1037.50, 478.13, 2390.63",
            "243.00, 5.15, 4.75, 9.25, 1251.45, 1154.25, 222.53, 2628.23",
            "217.00, 2.25, 2.10, 6.00, 488.25, 455.70, 56.64, 1000.59"
    })
    void calculateOrderAttributes_computesMaterialLaborTaxTotal(
            String area, String costPerSqFt, String laborCostPerSqFt, String taxRate,
            String expectedMaterial, String expectedLabor, String expectedTax, String expectedTotal) throws Exception {

        // create a new order
        Order order = new Order();
        order.setArea(new BigDecimal(area));
        order.setCostPerSquareFoot(new BigDecimal(costPerSqFt));
        order.setLaborCostPerSquareFoot(new BigDecimal(laborCostPerSqFt));
        order.setTaxRate(new BigDecimal(taxRate));

        // call the service to calculate the order attributes
        service.calculateOrderAttributes(order);

        // verify the order attributes
        assertEquals(new BigDecimal(expectedMaterial), order.getMaterialCost());
        assertEquals(new BigDecimal(expectedLabor), order.getLaborCost());
        assertEquals(new BigDecimal(expectedTax), order.getTax());
        assertEquals(new BigDecimal(expectedTotal), order.getTotal());
    }
}
