package com.example.jap;

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
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;
    private Product product;

    @BeforeEach
    public void setUp() {
        product = new Product("P001", "Samsung Galaxy", "Latest Samsung smartphone");
        customer = new Customer("1", "John Doe", "1234567890", product);
    }

    @Test
    public void testSaveCustomer_Success() {
        when(customerRepository.save(customer)).thenReturn(customer);

        Customer savedCustomer = customerService.saveCustomer(customer);

        assertNotNull(savedCustomer);
        assertEquals("John Doe", savedCustomer.getCustomerName());
        assertEquals("Samsung Galaxy", savedCustomer.getCustomerProduct().getProductName());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void testSaveCustomer_Failure() {
        when(customerRepository.save(customer)).thenReturn(null);

        Customer savedCustomer = customerService.saveCustomer(customer);

        assertNull(savedCustomer);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void testDeleteCustomer_Success() {
        // Simulate successful deletion
        doNothing().when(customerRepository).deleteById("1");

        boolean isDeleted = customerService.deleteCustomer("1");

        assertTrue(isDeleted);
        verify(customerRepository, times(1)).deleteById("1");
    }

    @Test
    public void testDeleteCustomer_Failure() {
        doThrow(new RuntimeException("Customer not found")).when(customerRepository).deleteById("invalid-id");

        boolean isDeleted = customerService.deleteCustomer("invalid-id");

        verify(customerRepository, times(1)).deleteById("invalid-id");
    }

    @Test
    public void testGetAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);

        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = customerService.getAllCustomers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getCustomerName());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void testGetCustomersByProductSamsung() {
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);

        when(customerRepository.findCustomersByProductSamsung()).thenReturn(customers);

        List<Customer> result = customerService.getCustomersByProductSamsung();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Samsung Galaxy", result.get(0).getCustomerProduct().getProductName());
        verify(customerRepository, times(1)).findCustomersByProductSamsung();
    }
}
