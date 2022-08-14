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
@Table(name = "brand")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandEntity extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ic_brand_id_generator")
    @SequenceGenerator(
        name = "ic_brand_id_generator",
        sequenceName = "ic_brand_id_seq",
        allocationSize = 1)
    private Long id;
    private String name;
    private String description;
    
    @OneToMany(mappedBy = "brand")
    private List<ProductEntity> product;
}