package com.jahanfoolad.jfs.service.impl;

import com.jahanfoolad.jfs.domain.Company;
import com.jahanfoolad.jfs.domain.dto.CompanyDto;
import com.jahanfoolad.jfs.jpaRepository.CompanyRepository;
import com.jahanfoolad.jfs.service.CompanyService;
import jakarta.annotation.Resource;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class CompanyServiceImpl  implements CompanyService {

    @Autowired
    CompanyRepository  companyRepository;

    @Resource(name = "faMessageSource")
    private MessageSource faMessageSource;
    @Resource(name = "enMessageSource")
    private MessageSource enMessageSource;

    @Override
    public List<Company> getCompanyPersons() {
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyByUserId(Long id) throws Exception {
        return companyRepository.findById(id).orElseThrow ( () -> new Exception(enMessageSource.getMessage("failed_message",null,Locale.ENGLISH)));
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


