package com.app.repository.impl;

import com.app.exceptions.MyException;
import com.app.model.entities.Producer;
import com.app.repository.ProducerRepository;
import com.app.repository.generic.AbstractGenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class ProducerRepositoryImpl extends AbstractGenericRepository<Producer> implements ProducerRepository {
    @Override
    public Optional<Producer> findByName(String name) {
        Optional<Producer> op = Optional.empty();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = entityManager.getTransaction();
            tx.begin();
            List<Producer> elements = entityManager
                    .createQuery("select p from Producer p where p.name = :name", Producer.class)
                    .setParameter("name", name)
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
            throw new MyException("PRODUCER FIND BY NAME EXCEPTION");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return op;
    }
}
