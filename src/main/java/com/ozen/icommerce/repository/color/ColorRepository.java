package com.ozen.icommerce.repository.color;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ozen.icommerce.entity.product.ColorEntity;

public interface ColorRepository extends JpaRepository<ColorEntity, Long> {
}
