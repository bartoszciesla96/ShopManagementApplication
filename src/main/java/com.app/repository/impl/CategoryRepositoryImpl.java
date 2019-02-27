package com.app.repository.impl;

import com.app.exceptions.MyException;
import com.app.model.entities.Category;
import com.app.repository.CategoryRepository;
import com.app.repository.generic.AbstractGenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class CategoryRepositoryImpl extends AbstractGenericRepository<Category> implements CategoryRepository {
    @Override
    public Optional<Category> findByName(String categoryName) {
        Optional<Category> op = Optional.empty();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = entityManager.getTransaction();
            tx.begin();
            List<Category> elements = entityManager
                    .createQuery("select c from Category c where c.name = :name", Category.class)
                    .setParameter("name", categoryName)
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
            throw new MyException("CATEGORY FIND BY NAME EXCEPTION");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return op;
    }
}
