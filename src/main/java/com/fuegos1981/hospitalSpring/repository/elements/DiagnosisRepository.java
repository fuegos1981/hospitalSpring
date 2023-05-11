package com.fuegos1981.hospitalSpring.repository.elements;

import com.fuegos1981.hospitalSpring.model.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagnosisRepository extends JpaRepository<Diagnosis, Integer> {
}
