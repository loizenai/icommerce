package com.ozen.icommerce.entity.product;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ozen.icommerce.entity.AuditModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_colors")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductColorsEntity extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ic_product_colors_id_generator")
    @SequenceGenerator(
        name = "ic_product_colors_id_generator",
        sequenceName = "ic_product_colors_id_seq",
        allocationSize = 1)
    private Long id;
    
    private String imageUrl;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private ProductEntity product;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "color_id")
    private ColorEntity color;
}