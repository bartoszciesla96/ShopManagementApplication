package com.app.repository;

import com.app.model.entities.Shop;
import com.app.repository.generic.GenericRepository;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends GenericRepository<Shop> {
    Optional<Shop> findByName(String name);
    List<Shop> findAllByName(String name);
}
