package com.app.repository.impl;

import com.app.exceptions.MyException;
import com.app.model.entities.Country;
import com.app.model.entities.Customer;
import com.app.repository.CountryRepository;
import com.app.repository.connection.DbConnection;
import com.app.repository.generic.AbstractGenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class CountryRepositoryImpl extends AbstractGenericRepository<Country> implements CountryRepository {
    private EntityManagerFactory entityManagerFactory = DbConnection.getInstance().getEntityManagerFactory();

    @Override
    public Optional<Country> findByName(String countryName) {
        Optional<Country> op = Optional.empty();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = entityManager.getTransaction();
            tx.begin();
            List<Country> elements = entityManager
                    .createQuery("select c from Country c where c.name = :name", Country.class)
                    .setParameter("name", countryName)
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
            throw new MyException("COUNTRY FIND BY NAME EXCEPTION");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return op;
    }
}
