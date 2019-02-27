package com.app.repository;

import com.app.model.entities.Customer;
import com.app.repository.generic.GenericRepository;

import java.util.Optional;

public interface CustomerRepository extends GenericRepository<Customer> {
    Optional<Customer> findByName(String name);
}
