package com.ozen.icommerce.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ozen.icommerce.entity.product.CategoryEntity;

public interface ProductCategoryRepository extends JpaRepository<CategoryEntity, Long> {
}

