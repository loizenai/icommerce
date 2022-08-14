
package com.ozen.icommerce.es.entity;

import java.util.List;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

@FieldNameConstants
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(indexName = "ic_products")
@Data
@Builder
public class EsProductEntity { 
  @Id
  String id;
  
  String name;
  String brand;
  String category;
  Double price;
  
  Long referId;
  
  List<ColorImage> colors;
}