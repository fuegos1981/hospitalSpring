package com.fuegos1981.hospitalSpring.repository;

import com.fuegos1981.hospitalSpring.controller.MainController;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class CustomRepositoryImpl<T> implements CustomRepository<T>{

    private final EntityManager entityManager;
    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    CustomRepositoryImpl(EntityManager entityManager) {

        this.entityManager = entityManager;
    }

    @Override
    public List<T> findAll(QueryRedactor qr, Class<T> clazz){

        logger.info("!!!!!!!!!!!!"+clazz.getName());
        int[] limit =qr.getLimit();
        TypedQuery<T> query;
        query = entityManager.createQuery(qr.getQuery(),clazz);
        if (limit!=null) {
            query = query.setFirstResult(limit[0]).setMaxResults(limit[1]);
        }
        logger.info(query.toString());
        //addFilters(query, qr.getSelectionValues());
        return query.getResultList();
    }

    @Override
    public int count(QueryRedactor qr, Class<T> clazz) {
        return findAll(qr,clazz).size();
    }

    private void addFilters(TypedQuery<T> query , Object[] filters){
        if (filters!= null) {
            int step = 1;
            for (Object obj : filters) {
                query.setParameter(step,obj);
                step++;
            }
        }
    }

}
