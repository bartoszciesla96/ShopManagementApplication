package com.app.repository.impl;

import com.app.exceptions.MyException;
import com.app.model.entities.Customer;
import com.app.repository.CustomerRepository;
import com.app.repository.generic.AbstractGenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class CustomerRepositoryImpl extends AbstractGenericRepository<Customer> implements CustomerRepository {

    @Override
    public Optional<Customer> findByName(String customerName) {
        Optional<Customer> op = Optional.empty();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = entityManager.getTransaction();
            tx.begin();
            List<Customer> elements = entityManager
                    .createQuery("select c from Customer c where c.name = :name", Customer.class)
                    .setParameter("name", customerName)
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
