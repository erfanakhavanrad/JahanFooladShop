package com.jahanfoolad.jfs.controller;

import com.jahanfoolad.jfs.domain.Category;
import com.jahanfoolad.jfs.domain.ResponseModel;
import com.jahanfoolad.jfs.domain.dto.CategoryDto;
import com.jahanfoolad.jfs.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/category")
@RestController
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    ResponseModel responseModel;

    @Value("${SUCCESS_RESULT}")
    int success;

    @Value("${FAIL_RESULT}")
    int fail;

    @GetMapping("/getAll")
    public ResponseModel getAll(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        responseModel.clear();
        try {
            List<Category> categories = categoryService.getCategories();
            responseModel.setContents(categories);
            responseModel.setResult(success);
            responseModel.setRecordCount(categories.size());
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setSystemError(dataIntegrityViolationException.getMessage());
            responseModel.setError(dataIntegrityViolationException.getMessage());
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return responseModel;
    }


    @GetMapping(path = "/getById")
    public ResponseModel getById(@RequestParam Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            responseModel.clear();
            responseModel.setContent(categoryService.getCategoryById(id));
            responseModel.setResult(success);

        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setSystemError(dataIntegrityViolationException.getMessage());
            responseModel.setError(dataIntegrityViolationException.getMessage());
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return responseModel;
    }

    @PostMapping(path = "/save")
    public ResponseModel save(@RequestBody CategoryDto categoryDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        try {
            responseModel.clear();
            responseModel.setContent(categoryService.createCategory(categoryDto));
            responseModel.setResult(success);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setSystemError(dataIntegrityViolationException.getMessage());
            responseModel.setError(dataIntegrityViolationException.getMessage());
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return responseModel;
    }

    @PutMapping(path = "/update")
    public ResponseModel update(@RequestBody CategoryDto categoryDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            responseModel.clear();
            responseModel.setContent(categoryService.updateCategory(categoryDto));
            responseModel.setResult(success);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setSystemError(dataIntegrityViolationException.getMessage());
            responseModel.setError(dataIntegrityViolationException.getMessage());
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return responseModel;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseModel delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        responseModel.clear();
        try {
            categoryService.deleteCategory(id);
            responseModel.clear();
            responseModel.setResult(success);
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return responseModel;
    }


}
