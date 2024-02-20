package com.jahanfoolad.jfs.jpaRepository;

import com.jahanfoolad.jfs.domain.Category;
import com.jahanfoolad.jfs.domain.CorpPerson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findAllByParentId(@Param("parentId") Long parentId, Pageable pageable);
}
