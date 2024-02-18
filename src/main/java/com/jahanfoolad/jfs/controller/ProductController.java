package com.jahanfoolad.jfs.controller;

import com.jahanfoolad.jfs.domain.Product;
import com.jahanfoolad.jfs.domain.ResponseModel;
import com.jahanfoolad.jfs.domain.dto.FileDto;
import com.jahanfoolad.jfs.domain.dto.ProductDto;
import com.jahanfoolad.jfs.service.ProductService;
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

import java.util.List;
import java.util.Locale;

@Slf4j
@RequestMapping("/product")
@RestController
public class ProductController {
    @Autowired
    ProductService productService;

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

        responseModel.clear();
        try {
            log.info("get product");
            List<Product> products = productService.getProducts();
            responseModel.setContent(products);
            responseModel.setResult(success);
            responseModel.setRecordCount(products.size());
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setSystemError(dataIntegrityViolationException.getMessage());
            responseModel.setError(faMessageSource.getMessage("already_not_exists",null, Locale.ENGLISH));
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return responseModel;
    }

    @GetMapping("/getbyid")
    public ResponseModel getProductById(@RequestParam Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        responseModel.clear();

        try {
            log.info("get product by user id");
            responseModel.setContent(productService.getProductById(id));
            responseModel.setResult(success);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setSystemError(dataIntegrityViolationException.getMessage());
            responseModel.setError(faMessageSource.getMessage("already_not_exists",null, Locale.ENGLISH));
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return responseModel;
    }

    @PostMapping("/save")
    public ResponseModel createProduct(@RequestBody ProductDto productDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        try {
            log.info("create product");
            responseModel.clear();
            responseModel.setContent(productService.createProduct(productDto));
            responseModel.setResult(success);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setSystemError(dataIntegrityViolationException.getMessage());
            responseModel.setError(faMessageSource.getMessage("already_exists",null, Locale.ENGLISH));
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return responseModel;

    }

    @DeleteMapping("/delete/{id}")
    public ResponseModel deleteProduct(@PathVariable("id") Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        responseModel.clear();

        try {
            log.info("delete product");
            productService.deleteProduct(id);
            responseModel.setResult(success);
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return responseModel;
    }

    @PutMapping("/update")
    public ResponseModel updateProduct(@RequestBody ProductDto productDto, HttpServletRequest httpServletRequest , HttpServletResponse httpServletResponse){

        try{
            log.info("update file");
            responseModel.setContent(productService.updateProduct(productDto));
            responseModel.setResult(success);
        }catch (DataIntegrityViolationException dataIntegrityViolationException){
            responseModel.setSystemError(dataIntegrityViolationException.getMessage());
            responseModel.setError(faMessageSource.getMessage("already_exists",null, Locale.ENGLISH));
            responseModel.setResult(fail);
        }catch (Exception e){
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(fail);
        }
        return  responseModel;
    }
}
