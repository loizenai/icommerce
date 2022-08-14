package com.ozen.icommerce.config.pagination;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "config.pagination")
public class PaginationProperties {
  private int number;
  private int size;
}