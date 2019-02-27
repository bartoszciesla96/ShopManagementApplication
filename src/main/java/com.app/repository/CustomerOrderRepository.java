package com.app.repository;

import com.app.model.entities.CustomerOrder;
import com.app.repository.generic.GenericRepository;

import java.util.Optional;

public interface CustomerOrderRepository extends GenericRepository<CustomerOrder> {
    Optional<CustomerOrder> findByCountry(String name);
}
