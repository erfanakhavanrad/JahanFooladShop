package com.jahanfoolad.jfs.service;

import com.jahanfoolad.jfs.domain.Company;
import com.jahanfoolad.jfs.domain.dto.CompanyDto;
import com.jahanfoolad.jfs.domain.dto.ContactDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CompanyService {
    List<Company> getCompanyPersons() throws Exception;
    Company getCompanyById(Long id) throws Exception;
    Company getCompanyByUserId(Long id) throws Exception;
    Company createCompany(CompanyDto companyDto);
    void deleteCompany(Long id);
    Company updateCompany(CompanyDto companyDto) throws Exception;
    List<Company> findByCategory(Long categoryId);
    Page<Company> findByProvince(ContactDto contactDto, Integer pageNo, Integer perPage);
    Page<Company> findByCity(ContactDto contactDto, Integer pageNo, Integer perPage);

}
