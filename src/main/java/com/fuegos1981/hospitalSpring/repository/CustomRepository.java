package com.fuegos1981.hospitalSpring.repository;

import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

public interface CustomRepository<T>{
    List<T> findAll(QueryRedactor qr, Class<T> clazz);
    int count(QueryRedactor qr, Class<T> clazz);
}
