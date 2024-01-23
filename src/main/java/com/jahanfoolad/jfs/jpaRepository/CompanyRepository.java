package com.jahanfoolad.jfs.jpaRepository;

import com.jahanfoolad.jfs.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company , Long >{
}
