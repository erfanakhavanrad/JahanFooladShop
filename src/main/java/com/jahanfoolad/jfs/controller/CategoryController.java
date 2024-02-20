package com.jahanfoolad.jfs.controller;

import com.jahanfoolad.jfs.JfsApplication;
import com.jahanfoolad.jfs.domain.Category;
import com.jahanfoolad.jfs.domain.ResponseModel;
import com.jahanfoolad.jfs.domain.dto.CategoryDto;
import com.jahanfoolad.jfs.service.CategoryService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Locale;

@Slf4j
@RequestMapping("/category")
@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ResponseModel responseModel;

    @Resource(name = "faMessageSource")
    private MessageSource faMessageSource;

    @Value("${SUCCESS_RESULT}")
    int success;

    @Value("${FAIL_RESULT}")
    int fail;

    @GetMapping("/getAll")
    public ResponseModel getAll(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            responseModel.clear();
            log.info("get all category");
            List<Category> categories = categoryService.getCategories();
            responseModel.setContents(categories);
            responseModel.setResult(success);
            responseModel.setRecordCount(categories.size());
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (AccessDeniedException accessDeniedException) {
            responseModel.setError(faMessageSource.getMessage("ACCESS_DENIED", null, Locale.ENGLISH));
            responseModel.setResult(fail);
            responseModel.setSystemError(accessDeniedException.getMessage());
            responseModel.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
            responseModel.setResult(fail);
            responseModel.setStatus(httpServletResponse.getStatus());
        }
        return responseModel;
    }


    @GetMapping(path = "/getById")
    public ResponseModel getById(@RequestParam Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            log.info("get category ById");
            responseModel.clear();
            responseModel.setContent(categoryService.getCategoryById(id));
            responseModel.setRecordCount(1);
            responseModel.setResult(success);
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (AccessDeniedException accessDeniedException) {
            responseModel.setError(faMessageSource.getMessage("ACCESS_DENIED", null, Locale.ENGLISH));
            responseModel.setResult(fail);
            responseModel.setSystemError(accessDeniedException.getMessage());
            responseModel.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return responseModel;
    }

    @PostMapping(path = "/save")
    public ResponseModel save(@RequestBody CategoryDto categoryDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            log.info("create category");
            responseModel.clear();
            responseModel.setContent(categoryService.createCategory(categoryDto, httpServletRequest));
            responseModel.setResult(success);
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (AccessDeniedException accessDeniedException) {
            responseModel.setError(faMessageSource.getMessage("ACCESS_DENIED", null, Locale.ENGLISH));
            responseModel.setResult(fail);
            responseModel.setSystemError(accessDeniedException.getMessage());
            responseModel.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setSystemError(dataIntegrityViolationException.getMessage());
            responseModel.setError(dataIntegrityViolationException.getMessage());
            responseModel.setResult(fail);
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
            responseModel.setResult(fail);
            responseModel.setStatus(httpServletResponse.getStatus());
        }
        return responseModel;
    }

    @PutMapping(path = "/update")
    public ResponseModel update(@RequestBody CategoryDto categoryDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            log.info("update category");
            responseModel.clear();
            responseModel.setContent(categoryService.updateCategory(categoryDto, httpServletRequest));
            responseModel.setResult(success);
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (AccessDeniedException accessDeniedException) {
            responseModel.setError(faMessageSource.getMessage("ACCESS_DENIED", null, Locale.ENGLISH));
            responseModel.setResult(fail);
            responseModel.setSystemError(accessDeniedException.getMessage());
            responseModel.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setSystemError(dataIntegrityViolationException.getMessage());
            responseModel.setError(dataIntegrityViolationException.getMessage());
            responseModel.setResult(fail);
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
            responseModel.setResult(fail);
            responseModel.setStatus(httpServletResponse.getStatus());
        }
        return responseModel;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseModel delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            log.info("delete category");
            responseModel.clear();
            categoryService.deleteCategory(id);
            responseModel.clear();
            responseModel.setResult(success);
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (AccessDeniedException accessDeniedException) {
            responseModel.setError(faMessageSource.getMessage("ACCESS_DENIED", null, Locale.ENGLISH));
            responseModel.setResult(fail);
            responseModel.setSystemError(accessDeniedException.getMessage());
            responseModel.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return responseModel;
    }

    @GetMapping(path = "/getByParentId")
    public ResponseModel getByParentId(@RequestParam Long id, @RequestParam Integer pageNo, Integer perPage, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            log.info("get by Parent ById");
            responseModel.clear();
            Page<Category> categoryByParentId = categoryService.getCategoriesByParentId(id, pageNo, perPage);
            responseModel.setContent(categoryByParentId);
            responseModel.setRecordCount((int) categoryByParentId.getTotalElements());
            responseModel.setResult(success);
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (AccessDeniedException accessDeniedException) {
            responseModel.setError(faMessageSource.getMessage("ACCESS_DENIED", null, Locale.ENGLISH));
            responseModel.setResult(fail);
            responseModel.setSystemError(accessDeniedException.getMessage());
            responseModel.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return responseModel;
    }


}
