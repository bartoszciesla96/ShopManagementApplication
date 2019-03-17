package com.app.repository;

import com.app.model.entities.Stock;
import com.app.repository.generic.GenericRepository;

import java.util.Optional;

public interface StockRepository extends GenericRepository<Stock> {
    Optional<Stock> findByProductId(Long productId);
}
