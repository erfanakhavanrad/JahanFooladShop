package com.jahanfoolad.jfs.controller;

import com.jahanfoolad.jfs.domain.Product;
import com.jahanfoolad.jfs.domain.ResponseModel;
import com.jahanfoolad.jfs.domain.dto.ProductDto;
import com.jahanfoolad.jfs.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/product")
@RestController
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    ResponseModel responseModel;

    @GetMapping("/getAll")
    public ResponseModel getProducts(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

        responseModel.clear();
        try {
            log.info("get product");
            List<Product> products = productService.getProducts();
            responseModel.setContent(products);
            responseModel.setResult(1);
            responseModel.setRecordCount(products.size());
            responseModel.setStatus(httpServletResponse.getStatus());
        }catch(DataIntegrityViolationException dataIntegrityViolationException){
            responseModel.setError(dataIntegrityViolationException.getMessage());
        }catch(Exception e){
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(0);
        }
        return responseModel;
    }

    @GetMapping("/getbyid")
    public ResponseModel getProductByUserId(@RequestBody Long id, HttpServletRequest httpServletRequest , HttpServletResponse httpServletResponse){

        responseModel.clear();

        try {
            log.info("get product by user id");
            responseModel.setContent(productService.getProductByUserId(id));
            responseModel.setResult(1);
        }catch(DataIntegrityViolationException dataIntegrityViolationException){
            responseModel.setError(dataIntegrityViolationException.getMessage());
        }catch(Exception e){
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(0);
        }
        return responseModel;
    }

    @PostMapping("/save")
    public ResponseModel createProduct(@RequestBody ProductDto productDto , HttpServletRequest httpServletRequest , HttpServletResponse httpServletResponse ){
        responseModel.clear();

        try {
            log.info("create product");
            responseModel.setContent(productService.createProduct(productDto));
            responseModel.setResult(1);
        }catch(DataIntegrityViolationException dataIntegrityViolationException){
            responseModel.setError(dataIntegrityViolationException.getMessage());
        }catch(Exception e){
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(0);
        }
        return responseModel;

    }

    @DeleteMapping("/delete/{id}")
    public ResponseModel deleteProduct(@PathVariable("id") Long id, HttpServletRequest httpServletRequest , HttpServletResponse httpServletResponse){
        responseModel.clear();

        try {
            log.info("delete product");
            productService.deleteProduct(id);
            responseModel.setResult(1);
        }catch(DataIntegrityViolationException dataIntegrityViolationException){
            responseModel.setError(dataIntegrityViolationException.getMessage());
        }catch(Exception e){
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(0);
        }
        return responseModel;
    }
}
