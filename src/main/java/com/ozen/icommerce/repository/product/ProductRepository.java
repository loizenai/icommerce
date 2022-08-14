package com.ozen.icommerce.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ozen.icommerce.entity.product.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
