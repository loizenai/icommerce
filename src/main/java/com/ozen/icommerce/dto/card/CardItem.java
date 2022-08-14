package com.ozen.icommerce.dto.card;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.ozen.icommerce.enums.Color;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CardItem {
    private Long id;
    
    @NotNull(message="Product_Id must NOT NULL")
    private Long productId;
    
    @NotNull(message="Color must NOT NULL")
    private Color color;
    
    @NotNull(message="Quantity must NOT NULL")
    @Positive
    private Integer quantity;
    
    private String sessionId;
}
