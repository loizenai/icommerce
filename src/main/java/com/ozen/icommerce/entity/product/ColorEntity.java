package com.ozen.icommerce.entity.product;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ozen.icommerce.entity.AuditModel;
import com.ozen.icommerce.enums.Color;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "color")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColorEntity extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ic_color_id_generator")
    @SequenceGenerator(
        name = "ic_color_id_generator",
        sequenceName = "ic_color_id_seq",
        allocationSize = 1)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private Color color;
    
    @OneToMany(mappedBy = "color")
    private List<ProductColorsEntity> productColors;
}
