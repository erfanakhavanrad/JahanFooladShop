package com.jahanfoolad.jfs.service.impl;

import com.jahanfoolad.jfs.domain.Company;
import com.jahanfoolad.jfs.domain.dto.CompanyDto;
import com.jahanfoolad.jfs.jpaRepository.CompanyRepository;
import com.jahanfoolad.jfs.service.CompanyService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl  implements CompanyService {

    @Autowired
    CompanyRepository  companyRepository;

    @Override
    public List<Company> getCompanyPersons() {
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyByUserId(Long id) throws Exception {
        return companyRepository.findById(id).orElseThrow ( () -> new Exception("company not found"));
    }

    @Override
    public Company createCompany(CompanyDto companyDto) {
        ModelMapper  modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Company company = modelMapper.map(companyDto,Company.class);
        return modelMapper.map(companyRepository.save(company) , Company.class);
    }

    @Override
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
}


