package com.jahanfoolad.jfs.service;

import com.jahanfoolad.jfs.domain.Company;
import com.jahanfoolad.jfs.domain.dto.CompanyDto;
import com.jahanfoolad.jfs.domain.dto.ContactDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CompanyService {
    List<Company> getCompanyPersons();
    Company getCompanyById(Long id) throws Exception;
    Company createCompany(CompanyDto companyDto);
    void deleteCompany(Long id);
    Company updateCompany(CompanyDto companyDto) throws Exception;
    List<Company> findByCategory(Long categoryId);
    Page<Company> findByProvince(ContactDto name, Integer pageNo, Integer perPage);
    List<Company> findByCity(Long cityId);

}
