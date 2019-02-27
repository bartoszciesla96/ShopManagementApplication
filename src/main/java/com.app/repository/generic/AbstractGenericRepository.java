package com.app.repository.generic;

import com.app.exceptions.MyException;
import com.app.repository.connection.DbConnection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractGenericRepository<T> implements GenericRepository<T> {


    @SuppressWarnings("unchecked")
    private Class<T> entityClass = (Class<T>) ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    protected EntityManagerFactory entityManagerFactory = DbConnection.getInstance().getEntityManagerFactory();

    @Override
    public Optional<T> addOrUpdate(T item) {
        Optional<T> op = Optional.empty();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = entityManager.getTransaction();
            tx.begin();
            T t = (T)entityManager.merge(item);
            op = Optional.of(t);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new MyException("ADD OR UPDATE EXCEPTION");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return op;
    }

    @Override
    public Optional<T> delete(Long id) {
        Optional<T> op = Optional.empty();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = entityManager.getTransaction();
            tx.begin();
            op = Optional.of(entityManager.find(entityClass, id));
            entityManager.remove(op.orElseThrow(() -> new MyException("ID IS NOT CORRECT")));
            tx.commit();
        } catch (Exception e) {

            if (tx != null) {
                tx.rollback();
            }

            throw new MyException("DELETE EXCEPTION");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return op;
    }

    @Override
    public void deleteAll() {
        EntityTransaction tx = null;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            tx = entityManager.getTransaction();
            tx.begin();
            List<T> elements = entityManager
                    .createQuery("select t from " + entityClass.getCanonicalName() + " t", entityClass)
                    .getResultList();
            elements.forEach(entityManager::remove);
            tx.commit();
        } catch (Exception e) {

            if (tx != null) {
                tx.rollback();
            }

            throw new MyException("DELETE ALL EXCEPTION");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public Optional<T> findById(Long id) {
        Optional<T> op = Optional.empty();
        EntityTransaction tx = null;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            tx = entityManager.getTransaction();
            tx.begin();
            op = Optional.of(entityManager.find(entityClass, id));
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return op;
    }

    @Override
    public List<T> findAll() {
        List<T> elements;
        EntityTransaction tx = null;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            tx = entityManager.getTransaction();
            tx.begin();
            elements = entityManager
                    .createQuery("select t from " + entityClass.getCanonicalName() + " t", entityClass)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {

            if (tx != null) {
                tx.rollback();
            }

            throw new MyException("FIND ALL EXCEPTION");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return elements;
    }
}
