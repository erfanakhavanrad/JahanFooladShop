package com.jahanfoolad.jfs.jpaRepository;

import com.jahanfoolad.jfs.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository  extends JpaRepository<Contact , Long> {

    List<Contact> findAllByProvince(String province);
}