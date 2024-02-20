package com.jahanfoolad.jfs.jpaRepository;

import com.jahanfoolad.jfs.domain.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

}
