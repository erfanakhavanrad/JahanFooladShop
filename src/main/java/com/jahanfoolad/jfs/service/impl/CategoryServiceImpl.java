package com.jahanfoolad.jfs.service.impl;

import com.jahanfoolad.jfs.JfsApplication;
import com.jahanfoolad.jfs.domain.Category;
import com.jahanfoolad.jfs.domain.Person;
import com.jahanfoolad.jfs.domain.ResponseModel;
import com.jahanfoolad.jfs.domain.dto.CategoryDto;
import com.jahanfoolad.jfs.jpaRepository.CategoryRepository;
import com.jahanfoolad.jfs.security.SecurityService;
import com.jahanfoolad.jfs.service.CategoryService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Resource(name = "faMessageSource")
    private MessageSource faMessageSource;

    @Autowired
    ResponseModel responseModel;

    @Autowired
    private SecurityService securityService;

    @Override
    public Page<Category> getCategories(Integer pageNo, Integer perPage) {
        return categoryRepository.findAll(JfsApplication.createPagination(pageNo, perPage));
    }

    @Override
    public Category getCategoryById(Long id) throws Exception {
        return categoryRepository.findById(id).orElseThrow(() -> new Exception(faMessageSource.getMessage("NOT_FOUND", null, Locale.ENGLISH)));
    }

    @Override
    public Category createCategory(CategoryDto categoryDto, HttpServletRequest httpServletRequest) {
        ModelMapper modelMapper = new ModelMapper();
        Category category = modelMapper.map(categoryDto, Category.class);
        category.setCreatedBy((((Person) securityService.getUserByToken(httpServletRequest).getContent()).getId()));
        return modelMapper.map(categoryRepository.save(category), Category.class);
    }

    @Override
    public Category updateCategory(CategoryDto categoryDto, HttpServletRequest httpServletRequest) throws Exception {
        ModelMapper modelMapper = new ModelMapper();

        Category oldCategory = getCategoryById(categoryDto.getId());
        Category newCategory = modelMapper.map(categoryDto, Category.class);

        responseModel.clear();
        Category updated = (Category) responseModel.merge(oldCategory, newCategory);
        return categoryRepository.save(updated);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Page<Category> getCategoriesByParentId(Long id, Integer pageNo, Integer perPage) throws Exception {
        return categoryRepository.findAllByParentId(id, JfsApplication.createPagination(pageNo,perPage));
    }
}
