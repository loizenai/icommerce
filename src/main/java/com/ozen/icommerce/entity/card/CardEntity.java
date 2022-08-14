package com.ozen.icommerce.entity.card;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ozen.icommerce.entity.AuditModel;
import com.ozen.icommerce.enums.Color;
import com.ozen.icommerce.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "card")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardEntity extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ic_card_id_generator")
    @SequenceGenerator(
        name = "ic_card_id_generator",
        sequenceName = "ic_card_id_seq",
        allocationSize = 1)
    private Long id;
    private String sessionId;
    
    private Long productId;
    private Color color;
    private Integer quantity;
    
    @Enumerated(EnumType.ORDINAL)
    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.NO;
}
