package com.jahanfoolad.jfs.service;

import com.jahanfoolad.jfs.domain.Company;
import com.jahanfoolad.jfs.domain.dto.CompanyDto;
import com.jahanfoolad.jfs.domain.dto.ContactDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CompanyService {
    Page<Company> getCompany(Integer pageNo, Integer perPage) throws Exception;
    Company getCompanyById(Long id) throws Exception;
    Company getCompanyByUserId(Long id) throws Exception;
    Company createCompany(CompanyDto companyDto, HttpServletRequest httpServletRequest) throws Exception;
    void deleteCompany(Long id);
    Company updateCompany(CompanyDto companyDto, HttpServletRequest httpServletRequest) throws Exception;
//    Page<Company> findByCategory(CategoryDto categoryId, Integer pageNo, Integer perPage) throws Exception;
    Page<Company> findByProvince(ContactDto contactDto, Integer pageNo, Integer perPage) throws Exception;
    Page<Company> findByCity(ContactDto contactDto, Integer pageNo, Integer perPage);

}
