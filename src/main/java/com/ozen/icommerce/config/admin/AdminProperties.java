package com.ozen.icommerce.config.admin;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "config.admin")
public class AdminProperties {
  private Long id;
  private String username;
}