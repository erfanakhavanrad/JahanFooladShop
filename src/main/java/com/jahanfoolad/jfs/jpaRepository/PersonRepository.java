package com.jahanfoolad.jfs.jpaRepository;

import com.jahanfoolad.jfs.domain.Person;
import com.jahanfoolad.jfs.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByUserNameIgnoreCase(String userName);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE public.users set img_url=:img_url where id =:uid", nativeQuery = true)
    int updateImageUrl(@Param("uid") long uid, @Param("img_url") String img_url);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE public.person set is_active=:active where id =:uid", nativeQuery = true)
    int updateActivation(@Param("uid") long uid, @Param("active") boolean active);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE public.person set is_authorization_changed=:auth where id in :uid", nativeQuery = true)
    int updateAuthorization(@Param("uid") List<Long> uid, @Param("auth") boolean auth);

    List<Person> findAllByRole(Role role);
    List<Person> findAllByIsActive(boolean isActive);
}
