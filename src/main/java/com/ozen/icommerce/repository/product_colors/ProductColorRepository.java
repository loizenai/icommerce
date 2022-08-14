package com.ozen.icommerce.repository.product_colors;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ozen.icommerce.entity.product.ProductColorsEntity;

public interface ProductColorRepository extends JpaRepository<ProductColorsEntity, Long> {
}
