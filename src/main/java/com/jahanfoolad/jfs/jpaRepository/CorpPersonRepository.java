package com.jahanfoolad.jfs.jpaRepository;

import com.jahanfoolad.jfs.domain.CorpPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorpPersonRepository extends JpaRepository<CorpPerson, Long> {
}