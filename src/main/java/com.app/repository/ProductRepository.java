package com.app.repository;

import com.app.model.entities.Product;
import com.app.repository.generic.GenericRepository;

import java.util.Optional;

public interface ProductRepository extends GenericRepository<Product> {
    Optional<Product> findByName(String name);
}
