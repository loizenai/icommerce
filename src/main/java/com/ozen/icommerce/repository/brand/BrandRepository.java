package com.ozen.icommerce.repository.brand;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ozen.icommerce.entity.product.BrandEntity;

public interface BrandRepository extends JpaRepository<BrandEntity, Long> {
}
