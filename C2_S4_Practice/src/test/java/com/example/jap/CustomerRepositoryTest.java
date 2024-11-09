package com.example.jap;

import com.example.jap.Customer;
import com.example.jap.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@DataMongoTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    // Set up test data
    @BeforeEach
    void setUp() {
        Product product = new Product("P001", "Laptop", "Gaming Laptop");
        Customer customer = new Customer("C001", "John Doe", "1234567890", product);
        customerRepository.save(customer);
    }

    // Clean up after tests
    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    // Test case to check successful insertion
    @Test
    void testSaveCustomerSuccess() {
        Product product = new Product("P002", "Phone", "Smartphone");
        Customer customer = new Customer("C002", "Jane Doe", "0987654321", product);
        Customer savedCustomer = customerRepository.save(customer);

        assertNotNull(savedCustomer);
        assertEquals("Jane Doe", savedCustomer.getCustomerName());
        assertEquals("Phone", savedCustomer.getCustomerProduct().getProductName());
    }

    // Test case to check retrieving a customer by ID
    @Test
    void testFindByIdSuccess() {
        Optional<Customer> foundCustomer = customerRepository.findById("C001");
        assertTrue(foundCustomer.isPresent());
        assertEquals("John Doe", foundCustomer.get().getCustomerName());
    }

    // Test case to check customer not found scenario
    @Test
    void testFindByIdFailure() {
        Optional<Customer> foundCustomer = customerRepository.findById("InvalidId");
        assertFalse(foundCustomer.isPresent());
    }

    // Test case to check if customer data is deleted successfully
    @Test
    void testDeleteCustomer() {
        customerRepository.deleteById("C001");
        Optional<Customer> deletedCustomer = customerRepository.findById("C001");
        assertFalse(deletedCustomer.isPresent());
    }

    // Test case to check if all customers are retrieved
    @Test
    void testFindAllCustomers() {
        Iterable<Customer> customers = customerRepository.findAll();
        assertNotNull(customers);
        assertTrue(customers.iterator().hasNext());
    }
}
