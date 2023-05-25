package com.fuegos1981.hospitalSpring.service;

import com.fuegos1981.hospitalSpring.exception.DBException;
import com.fuegos1981.hospitalSpring.repository.QueryRedactor;
import java.util.List;

public interface GlobalService<T> {

    T readById(Integer id);
    T create(T t);
    T update(T t);
    void delete(T t) throws DBException;
    List<T> getAll(QueryRedactor qr);
    int getSize(QueryRedactor qr);
    List<T> getAll();
    int getSize();

}
