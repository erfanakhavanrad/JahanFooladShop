package com.jahanfoolad.jfs.controller;

import com.jahanfoolad.jfs.domain.Product;
import com.jahanfoolad.jfs.domain.ResponseModel;
import com.jahanfoolad.jfs.domain.dto.FileDto;
import com.jahanfoolad.jfs.domain.dto.ProductDto;
import com.jahanfoolad.jfs.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
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

    @Value("${SUCCESS_RESULT}")
    int success;

    @Value("${FAIL_RESULT}")
    int fail;

    @GetMapping("/getAll")
    public ResponseModel getProducts(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        responseModel.clear();
        try {
            log.info("get product");
            List<Product> products = productService.getProducts();
            responseModel.setContent(products);
            responseModel.setResult(1);
            responseModel.setRecordCount(products.size());
            responseModel.setStatus(httpServletResponse.getStatus());
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setError(dataIntegrityViolationException.getMessage());
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(0);
        }
        return responseModel;
    }

    @GetMapping("/getbyid")
    public ResponseModel getProductByUserId(@RequestParam Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        responseModel.clear();

        try {
            log.info("get product by user id");
            responseModel.setContent(productService.getProductById(id));
            responseModel.setResult(1);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setError(dataIntegrityViolationException.getMessage());
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(0);
        }
        return responseModel;
    }

    @PostMapping("/save")
    public ResponseModel createProduct(@RequestBody ProductDto productDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        try {
            log.info("create product");
            responseModel.clear();
            responseModel.setContent(productService.createProduct(productDto));
            responseModel.setResult(1);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            responseModel.setError(dataIntegrityViolationException.getMessage());
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(0);
        }
        return responseModel;

    }

    @DeleteMapping("/delete/{id}")
    public ResponseModel deleteProduct(@PathVariable("id") Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        responseModel.clear();

        try {
            log.info("delete product");
            productService.deleteProduct(id);
            responseModel.setResult(1);
        } catch (Exception e) {
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(0);
        }
        return responseModel;
    }

    @PostMapping("/update")
    public ResponseModel updateCompany(@RequestBody ProductDto productDto, HttpServletRequest httpServletRequest , HttpServletResponse httpServletResponse){

        try{
            log.info("update file");
            responseModel.setContent(productService.updateProduct(productDto));
            responseModel.setResult(success);
        }catch (DataIntegrityViolationException dataIntegrityViolationException){
            responseModel.setError(dataIntegrityViolationException.getMessage());
            responseModel.setResult(fail);
        }catch (Exception e){
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(0);
        }
        return  responseModel;
    }

    @GetMapping("/findByFile")
    public ResponseModel findByFile(FileDto fileDto , @RequestParam Integer pageNo , Integer perPage , HttpServletResponse httpServletResponse , HttpServletRequest httpServletRequest){
        responseModel.clear();
        try{
            log.info("find by file");
            Page<Product> products = productService.findByFile(fileDto,pageNo,perPage);
            responseModel.setContent(products);
            responseModel.setResult(1);
            responseModel.setRecordCount((int) products.getTotalElements());
            responseModel.setStatus(httpServletResponse.getStatus());
        }catch (DataIntegrityViolationException dataIntegrityViolationException){
            responseModel.setError(dataIntegrityViolationException.getMessage());
        }catch (Exception e){
            responseModel.setError(e.getMessage());
        } finally {
            responseModel.setStatus(httpServletResponse.getStatus());
            responseModel.setResult(0);
        }
        return  responseModel;
    }
}
