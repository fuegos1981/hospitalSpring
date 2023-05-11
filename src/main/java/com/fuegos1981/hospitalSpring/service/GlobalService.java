package com.fuegos1981.hospitalSpring.service;

import com.fuegos1981.hospitalSpring.exception.DBException;
import com.fuegos1981.hospitalSpring.repository.QueryRedactor;

import java.sql.SQLException;
import java.util.List;

public interface GlobalService<T> {

    T readById(Integer id) throws DBException, SQLException;
    T create(T t) throws DBException, SQLException;
    T update(T t) throws DBException, SQLException;
    void delete(T t) throws DBException, SQLException;
    List<T> getAll(QueryRedactor qr) throws DBException, SQLException;
    int getSize(QueryRedactor qr) throws DBException;
    List<T> getAll() throws DBException, SQLException;
    int getSize() throws DBException;
}
