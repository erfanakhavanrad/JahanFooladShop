package com.jahanfoolad.jfs.service.impl;

import com.jahanfoolad.jfs.domain.Category;
import com.jahanfoolad.jfs.domain.dto.CategoryDto;
import com.jahanfoolad.jfs.jpaRepository.CategoryRepository;
import com.jahanfoolad.jfs.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) throws Exception {
        return categoryRepository.findById(id).orElseThrow(() -> new Exception("Category not found"));
    }

    @Override
    public Category createCategory(CategoryDto categoryDto) {
        return null;
    }

    @Override
    public void deleteCategory(Long id) {

    }
}
