package com.app.repository.impl;

import com.app.exceptions.MyException;
import com.app.model.entities.Product;
import com.app.repository.ProductRepository;
import com.app.repository.generic.AbstractGenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryImpl extends AbstractGenericRepository<Product> implements ProductRepository {
    @Override
    public Optional<Product> findByName(String productName) {
        Optional<Product> op = Optional.empty();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = entityManager.getTransaction();
            tx.begin();
            List<Product> elements = entityManager
                    .createQuery("select p from Product p where p.name = :name", Product.class)
                    .setParameter("name", productName)
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
            throw new MyException("PRODUCT FIND BY NAME EXCEPTION");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return op;
    }
}
