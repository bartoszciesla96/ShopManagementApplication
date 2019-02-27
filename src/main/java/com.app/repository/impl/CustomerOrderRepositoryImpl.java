package com.app.repository.impl;

import com.app.exceptions.MyException;
import com.app.model.entities.Country;
import com.app.model.entities.CustomerOrder;
import com.app.repository.CustomerOrderRepository;
import com.app.repository.generic.AbstractGenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class CustomerOrderRepositoryImpl extends AbstractGenericRepository<CustomerOrder> implements CustomerOrderRepository {

    @Override
    public Optional<CustomerOrder> findByCountry(String countryName) {
        Optional<CustomerOrder> op = Optional.empty();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = entityManager.getTransaction();
            tx.begin();
            List<Country> elements = entityManager
                    .createQuery("select distinct c from CustomerOrder c inner join c.customer cu where cu.country.name = :name", Country.class)
                    .setParameter("name", countryName)
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
