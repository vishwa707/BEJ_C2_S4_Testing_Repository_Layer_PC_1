package com.example.jap;

import java.util.List;

public interface ICustomerService {
    Customer saveCustomer(Customer customer);

    boolean deleteCustomer(String customerId);

    List<Customer> getAllCustomers();

    List<Customer> getCustomersByProductSamsung();
}

