package com.app.repository.impl;


import com.app.exceptions.MyException;
import com.app.model.entities.Trade;
import com.app.repository.TradeRepository;
import com.app.repository.generic.AbstractGenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class TradeRepositoryImpl extends AbstractGenericRepository<Trade> implements TradeRepository {
    @Override
    public Optional<Trade> findByName(String tradeName) {
        Optional<Trade> op = Optional.empty();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = entityManager.getTransaction();
            tx.begin();
            List<Trade> elements = entityManager
                    .createQuery("select t from Trade t where t.name = :name", Trade.class)
                    .setParameter("name", tradeName)
                    .getResultList();
            if (!elements.isEmpty()) {
                op = Optional.of(elements.get(0));
            }
            tx.commit();
        } catch (Exception e) {

            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            throw new MyException("TRADE FIND BY NAME EXCEPTION");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return op;
    }
}
