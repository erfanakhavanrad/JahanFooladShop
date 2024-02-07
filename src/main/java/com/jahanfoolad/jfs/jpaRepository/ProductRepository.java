package com.jahanfoolad.jfs.jpaRepository;

import com.jahanfoolad.jfs.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findAllByFileListIn(List<Product> productList, PageRequest of);
}
