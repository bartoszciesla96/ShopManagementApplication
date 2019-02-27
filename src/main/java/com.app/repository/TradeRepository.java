package com.app.repository;

import com.app.model.entities.Trade;
import com.app.repository.generic.GenericRepository;

import java.util.Optional;

public interface TradeRepository extends GenericRepository<Trade> {
    Optional<Trade> findByName(String name);
}
