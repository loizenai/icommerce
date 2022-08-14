package com.ozen.icommerce.config.metric;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "management.metrics.export.logging")
public class LoggingProperties {

  private String domain = "metrics";

  private boolean enabled;

  private Duration step = Duration.ofMinutes(1);

  public String getDomain() {
    return this.domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public Duration getStep() {
    return this.step;
  }

  public void setStep(Duration step) {
    this.step = step;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
}
