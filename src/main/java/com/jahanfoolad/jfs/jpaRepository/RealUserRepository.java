package com.jahanfoolad.jfs.jpaRepository;

import com.jahanfoolad.jfs.domain.RealPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
//public interface RealUserRepository extends CrudRepository<RealPerson, Long> {
//public interface RealUserRepository extends PagingAndSortingRepository<RealPerson, Long> {
public interface RealUserRepository extends JpaRepository<RealPerson, Long> {

}
