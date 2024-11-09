package com.example.jap;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    @Query("{'customerProduct.productName': 'Samsung'}")
    List<Customer> findCustomersByProductSamsung();
}
