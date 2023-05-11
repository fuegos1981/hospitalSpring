package com.fuegos1981.hospitalSpring.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class CustomRepositoryImp<T> implements CustomRepository{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<T> getAll(QueryRedactor qr) {
        return entityManager.createQuery(qr.getQuery())
                .getResultList();
    }
}
