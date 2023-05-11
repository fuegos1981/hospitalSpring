package com.fuegos1981.hospitalSpring.repository;

import com.fuegos1981.hospitalSpring.model.Patient;
import jakarta.persistence.EntityManager;

import java.util.List;

public interface CustomRepository<T> {
    List<T> getAll(QueryRedactor qr);
}
