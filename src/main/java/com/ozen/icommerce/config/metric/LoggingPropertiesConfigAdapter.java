package com.ozen.icommerce.config.metric;

import java.time.Duration;

import org.springframework.boot.actuate.autoconfigure.metrics.export.properties.PropertiesConfigAdapter;

import io.micrometer.core.instrument.logging.LoggingRegistryConfig;

public class LoggingPropertiesConfigAdapter extends PropertiesConfigAdapter<LoggingProperties>
    implements LoggingRegistryConfig {

  public LoggingPropertiesConfigAdapter(LoggingProperties properties) {
    super(properties);
  }

  @Override
  public boolean enabled() {
    return get(LoggingProperties::isEnabled, LoggingRegistryConfig.super::enabled);
  }

  @Override
  public String prefix() {
    return "management.metrics.export.logging";
  }

  @Override
  public String get(String key) {
    return null;
  }

  @Override
  public Duration step() {
    return get(LoggingProperties::getStep, LoggingRegistryConfig.super::step);
  }
}
