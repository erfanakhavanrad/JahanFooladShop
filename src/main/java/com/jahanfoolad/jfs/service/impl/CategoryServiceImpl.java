package com.jahanfoolad.jfs.service.impl;

import com.jahanfoolad.jfs.domain.Category;
import com.jahanfoolad.jfs.domain.dto.CategoryDto;
import com.jahanfoolad.jfs.jpaRepository.CategoryRepository;
import com.jahanfoolad.jfs.service.CategoryService;
import jakarta.annotation.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Locale;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Resource(name = "faMessageSource")
    private MessageSource faMessageSource;

    @Resource(name = "enMessageSource")
    private MessageSource enMessageSource;

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) throws Exception {
        return categoryRepository.findById(id).orElseThrow(() -> new Exception(enMessageSource.getMessage("item_not_found_message", null, Locale.ENGLISH)));
    }

    @Override
    public Category createCategory(CategoryDto categoryDto) {
        ModelMapper modelMapper = new ModelMapper();
        Category category = modelMapper.map(categoryDto, Category.class);
        return modelMapper.map(categoryRepository.save(category), Category.class);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
