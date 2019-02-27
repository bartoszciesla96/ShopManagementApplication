package com.app.repository.impl;

import com.app.exceptions.MyException;
import com.app.model.entities.Shop;
import com.app.repository.ShopRepository;
import com.app.repository.connection.DbConnection;
import com.app.repository.generic.AbstractGenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class ShopRepositoryImpl extends AbstractGenericRepository<Shop> implements ShopRepository {

    @Override
    public Optional<Shop> findByName(String shopName) {
        Optional<Shop> op = Optional.empty();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = entityManager.getTransaction();
            tx.begin();
            List<Shop> elements = entityManager
                    .createQuery("select s from Shop s where s.name = :name", Shop.class)
                    .setParameter("name", shopName)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {

            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            throw new MyException("SHOP FIND BY NAME EXCEPTION");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return op;
    }
}

