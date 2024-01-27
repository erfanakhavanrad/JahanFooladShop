package com.jahanfoolad.jfs.jpaRepository;

import com.jahanfoolad.jfs.domain.Company;
import com.jahanfoolad.jfs.domain.Contact;
import com.jahanfoolad.jfs.domain.dto.ContactDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company , Long> {
//    List<Company> findCompaniesBy(@Param("name") String name);

//    List<Company> findByCategoryId(Long categoryId);
//    List<Company> findByCityId(Long cityId);
      Page<Company> findAllByContactListIn(List<Contact> contactList , Pageable pageable);
}
