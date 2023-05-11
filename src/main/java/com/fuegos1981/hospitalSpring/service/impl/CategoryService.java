package com.fuegos1981.hospitalSpring.service.impl;

import com.fuegos1981.hospitalSpring.model.Category;
import com.fuegos1981.hospitalSpring.model.Diagnosis;
import com.fuegos1981.hospitalSpring.repository.MainQuery;
import com.fuegos1981.hospitalSpring.repository.QueryRedactor;
import com.fuegos1981.hospitalSpring.repository.elements.CategoryRepository;

import com.fuegos1981.hospitalSpring.service.GlobalService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService implements GlobalService<Category> {

    private CategoryRepository categoryRepository;
    private DoctorService doctorService;

    public CategoryService(CategoryRepository categoryRepository, DoctorService doctorService) {
        this.categoryRepository = categoryRepository;
        this.doctorService = doctorService;
    }

    @Override
    public Category create(Category category){
        return categoryRepository.save(category);
    }

    @Override
    public Category readById(Integer id) throws SQLException{
        if (id == null)
            return null;
        return categoryRepository.findById(id).get();
    }

    @Override
    public Category update(Category category){
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Category category) throws SQLException {
        Map<String,Object> selection = new HashMap<>();

            selection.put("category.name", category.getName());
            if (doctorService.getAll(QueryRedactor.getRedactor(MainQuery.GET_ALL_DOCTORS,selection)).size()>0){
                throw new SQLException("Doctor use this category!");
            }

        categoryRepository.delete(category);
    }

    @Override
    public List<Category> getAll(QueryRedactor qr) throws SQLException {
        return null;//simpleRepository.getAll(qr);
    }

    public int getSize(QueryRedactor qr){
        return 0;//simpleRepository.getSize(qr);
    }

    @Override
    public List<Category> getAll() throws SQLException {
        return categoryRepository.findAll();
    }

    public int getSize(){
        return (int)categoryRepository.count();
    }


}
