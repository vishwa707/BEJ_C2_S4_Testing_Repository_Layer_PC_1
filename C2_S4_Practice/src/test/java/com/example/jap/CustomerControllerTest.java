package com.example.jap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @Mock
    private ICustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private Customer customer;
    private Product product;

    @BeforeEach
    public void setUp() {
        // Initialize Product and Customer objects
        product = new Product("P001", "Samsung Galaxy", "Latest Samsung smartphone");
        customer = new Customer("1", "John Doe", "1234567890", product);
    }

    @Test
    public void testSaveCustomer_Success() {
        when(customerService.saveCustomer(customer)).thenReturn(customer);

        Customer savedCustomer = customerController.saveCustomer(customer);

        assertNotNull(savedCustomer);
        assertEquals("John Doe", savedCustomer.getCustomerName());
        assertEquals("Samsung Galaxy", savedCustomer.getCustomerProduct().getProductName());
        verify(customerService, times(1)).saveCustomer(customer);
    }

    @Test
    public void testSaveCustomer_Failure() {
        when(customerService.saveCustomer(customer)).thenReturn(null);

        Customer savedCustomer = customerController.saveCustomer(customer);

        assertNull(savedCustomer);
        verify(customerService, times(1)).saveCustomer(customer);
    }

    @Test
    public void testDeleteCustomer_Success() {
        when(customerService.deleteCustomer("1")).thenReturn(true);

        boolean isDeleted = customerController.deleteCustomer("1");

        assertTrue(isDeleted);
        verify(customerService, times(1)).deleteCustomer("1");
    }

    @Test
    public void testDeleteCustomer_Failure() {
        when(customerService.deleteCustomer("1")).thenReturn(false);

        boolean isDeleted = customerController.deleteCustomer("1");

        assertFalse(isDeleted);
        verify(customerService, times(1)).deleteCustomer("1");
    }

    @Test
    public void testGetAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);

        when(customerService.getAllCustomers()).thenReturn(customers);

        List<Customer> result = customerController.getAllCustomers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getCustomerName());
        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    public void testGetCustomersByProductSamsung() {
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);

        when(customerService.getCustomersByProductSamsung()).thenReturn(customers);

        List<Customer> result = customerController.getCustomersByProductSamsung();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Samsung Galaxy", result.get(0).getCustomerProduct().getProductName());
        verify(customerService, times(1)).getCustomersByProductSamsung();
    }

    @AfterEach
    public void tearDown() {
        customer = null;
        product = null;
    }
}
