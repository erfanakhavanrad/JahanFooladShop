package com.jahanfoolad.jfs.jpaRepository;

import com.jahanfoolad.jfs.domain.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {
}
