package com.jahanfoolad.jfs.service;

import com.jahanfoolad.jfs.domain.Category;
import com.jahanfoolad.jfs.domain.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<Category> getCategories();
    Category getCategoryById(Long id) throws Exception;
    Category createCategory(CategoryDto categoryDto);
    void deleteCategory(Long id);
}
