package com.jahanfoolad.jfs.service;

import com.jahanfoolad.jfs.domain.Company;
import com.jahanfoolad.jfs.domain.dto.CompanyDto;

import java.util.List;

public interface CompanyService {
    List<Company> getCompanyPersons();
    Company getCompanyByUserId(Long id) throws Exception;
    Company createCompany(CompanyDto companyDto);
    void deleteCompany(Long id);
}
