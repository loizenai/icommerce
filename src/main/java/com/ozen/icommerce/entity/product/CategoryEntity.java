package com.ozen.icommerce.entity.product;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ozen.icommerce.entity.AuditModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "category")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryEntity extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ic_product_category_id_generator")
    @SequenceGenerator(
        name = "ic_product_category_id_generator",
        sequenceName = "ic_product_category_id_seq",
        allocationSize = 1)
    private Long id;
    private String name;
    
    @OneToMany(mappedBy = "category")
    private List<ProductEntity> product;
}
