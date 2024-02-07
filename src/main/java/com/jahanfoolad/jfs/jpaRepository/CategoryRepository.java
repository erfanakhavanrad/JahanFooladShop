package com.jahanfoolad.jfs.jpaRepository;

import com.jahanfoolad.jfs.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
