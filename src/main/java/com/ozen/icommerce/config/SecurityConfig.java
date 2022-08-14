package com.ozen.icommerce.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "config.jwt")
public class SecurityConfig {
  private String jwtSecret;
  private String jwtExpiration;
}