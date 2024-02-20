package com.jahanfoolad.jfs.service;

import com.jahanfoolad.jfs.domain.Category;
import com.jahanfoolad.jfs.domain.dto.CategoryDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    List<Category> getCategories() throws Exception;

    Category getCategoryById(Long id) throws Exception;

    Category createCategory(CategoryDto categoryDto, HttpServletRequest httpServletRequest) throws Exception;

    Category updateCategory(CategoryDto categoryDto, HttpServletRequest httpServletRequest) throws Exception;

    void deleteCategory(Long id) throws Exception;

    Page<Category> getCategoriesByParentId(Long id, Integer pageNo, Integer perPage) throws Exception;
}
