package com.app.repository;

import com.app.model.entities.Producer;
import com.app.repository.generic.GenericRepository;

import java.util.Optional;

public interface ProducerRepository extends GenericRepository<Producer> {
    Optional<Producer> findByName(String name);
}
