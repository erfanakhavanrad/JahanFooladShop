package com.jahanfoolad.jfs.jpaRepository;

import com.jahanfoolad.jfs.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
