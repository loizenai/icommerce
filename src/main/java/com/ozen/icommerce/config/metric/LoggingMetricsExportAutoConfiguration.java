package com.ozen.icommerce.config.metric;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.logging.LoggingMeterRegistry;
import io.micrometer.core.instrument.logging.LoggingRegistryConfig;

@ConditionalOnProperty(
    prefix = "management.metrics.export.logging",
    name = "enabled",
    havingValue = "true",
    matchIfMissing = true)
@Configuration
@EnableConfigurationProperties(LoggingProperties.class)
public class LoggingMetricsExportAutoConfiguration {
  @Bean
  @ConditionalOnMissingBean
  public LoggingRegistryConfig loggingRegistryConfig(LoggingProperties loggingProperties) {
    return new LoggingPropertiesConfigAdapter(loggingProperties);
  }

  @Bean
  @ConditionalOnMissingBean
  public LoggingMeterRegistry loggingMeterRegistry(LoggingRegistryConfig config) {
    return new LoggingMeterRegistry(config, Clock.SYSTEM);
  }
}
