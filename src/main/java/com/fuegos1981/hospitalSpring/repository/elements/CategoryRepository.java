package com.fuegos1981.hospitalSpring.repository.elements;

import com.fuegos1981.hospitalSpring.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
