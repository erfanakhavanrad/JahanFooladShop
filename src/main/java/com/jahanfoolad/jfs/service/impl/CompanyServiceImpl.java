package com.jahanfoolad.jfs.service.impl;

import com.jahanfoolad.jfs.JfsApplication;
import com.jahanfoolad.jfs.domain.Company;
import com.jahanfoolad.jfs.domain.Contact;
import com.jahanfoolad.jfs.domain.ResponseModel;
import com.jahanfoolad.jfs.domain.dto.CompanyDto;
import com.jahanfoolad.jfs.domain.dto.ContactDto;
import com.jahanfoolad.jfs.jpaRepository.CategoryRepository;
import com.jahanfoolad.jfs.jpaRepository.CompanyRepository;
import com.jahanfoolad.jfs.jpaRepository.ContactRepository;
import com.jahanfoolad.jfs.service.CompanyService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Resource(name = "faMessageSource")
    private MessageSource faMessageSource;

    @Autowired
    ResponseModel responseModel;

    @Override
    public Page<Company> getCompany(Integer pageNo, Integer perPage) {
        return companyRepository.findAll(JfsApplication.createPagination(pageNo, perPage));
    }

    @Override
    public Company getCompanyByUserId(Long id) throws Exception {
        return companyRepository.findById(id).orElseThrow(() -> new Exception(faMessageSource.getMessage("NOT_FOUND", null, Locale.ENGLISH)));
    }

    public Company getCompanyById(Long id) throws Exception {
        return companyRepository.findById(id).orElseThrow(() -> new Exception(faMessageSource.getMessage("NOT_FOUND", null, Locale.ENGLISH)));
    }

    @Override
    public Company createCompany(CompanyDto companyDto, HttpServletRequest httpServletRequest) {
        ModelMapper modelMapper = new ModelMapper();
        Company company = modelMapper.map(companyDto, Company.class);
        return companyRepository.save(company);
    }

    @Override
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    @Override
    public Company updateCompany(CompanyDto companyDto, HttpServletRequest httpServletRequest) throws Exception {
        log.info("update company");
        responseModel.clear();

        ModelMapper modelMapper = new ModelMapper();

        Company foundCompany = getCompanyById(companyDto.getId());
        Company newCompany = modelMapper.map(companyDto, Company.class);
        Company updated = (Company) responseModel.merge(foundCompany, newCompany);

        if (newCompany.getContactList() != null && !newCompany.getContactList().isEmpty()) {
            updated.setContactList(newCompany.getContactList());
        }
        return companyRepository.save(updated);
    }

    @Override
    public Page<Company> findByProvince(ContactDto contactDto, Integer pageNo, Integer perPage) {
        List<Contact> contacts = contactRepository.findAllByProvince(contactDto.getProvince());
        return companyRepository.findAllByContactListIn(contacts, JfsApplication.createPagination(pageNo, perPage));
    }

    @Override
    public Page<Company> findByCity(ContactDto contactDto, Integer pageNo, Integer perPage) {
        List<Contact> contacts = contactRepository.findAllByCity(contactDto.getCity());
        return companyRepository.findAllByContactListIn(contacts, JfsApplication.createPagination(pageNo, perPage));
    }

}


