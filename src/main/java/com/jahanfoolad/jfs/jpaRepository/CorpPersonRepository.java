package com.jahanfoolad.jfs.jpaRepository;

import com.jahanfoolad.jfs.domain.Contact;
import com.jahanfoolad.jfs.domain.CorpPerson;
import com.jahanfoolad.jfs.domain.RealPerson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CorpPersonRepository extends JpaRepository<CorpPerson, Long> {
    CorpPerson findByCellPhone(@Param("cellPhone") String cellPhone);

    Page<CorpPerson> findAllByContactListIn(List<Contact> contactList, PageRequest of);

//    Page<CorpPerson> findAllByContactListContaining(List<Contact> contactList, PageRequest of);
//
//    Page<CorpPerson> findAllByContactListContains(List<Contact> contactList, PageRequest of);
//
//    Page<CorpPerson> findAllByContactListIsContaining(List<Contact> contactList, PageRequest of);
//
//    Page<CorpPerson> findAllByContactListLike(List<Contact> contactList, PageRequest of);
//
//    Page<CorpPerson> findAllByContactListIsLike(List<Contact> contactList, PageRequest of);
//
//    Page<CorpPerson> findAllByContactListIsLikeIgnoreCase(List<Contact> contactList, PageRequest of);

//    Page<CorpPerson> findAllBy(List<>, String, PageRequest of);


//        @Query("SELECT rp FROM RealPerson AS rp WHERE rp.cellPhone = :cellPhone")
//        RealPerson findCorpPersonBy(@Param("cellPhone") String cellPhone);


}
