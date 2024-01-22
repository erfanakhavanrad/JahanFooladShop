package com.jahanfoolad.jfs.jpaRepository;

import com.jahanfoolad.jfs.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
}
