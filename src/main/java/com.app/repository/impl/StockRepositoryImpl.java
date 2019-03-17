package com.app.repository.impl;

import com.app.exceptions.MyException;
import com.app.model.entities.Stock;
import com.app.repository.StockRepository;
import com.app.repository.generic.AbstractGenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class StockRepositoryImpl extends AbstractGenericRepository<Stock> implements StockRepository {

    @Override
    public Optional<Stock> findByProductId(Long productId) {
        Optional<Stock> op = Optional.empty();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = entityManager.getTransaction();
            tx.begin();
            List<Stock> elements = entityManager
                    .createQuery("select s from Stock s where s.product.id = :productId", Stock.class)
                    .setParameter("productId", productId)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {

            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            throw new MyException("CUSTOMER FIND BY NAME EXCEPTION");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return op;
    }
}
