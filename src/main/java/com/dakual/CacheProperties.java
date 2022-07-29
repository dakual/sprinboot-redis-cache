package com.dakual;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties("cache")
public class CacheProperties {
    private Map<String, Long> expirations = new HashMap<>();
    private int expiration;

    public Map<String, Long> getExpirations() {
        return expirations;
    }

    public void setExpirations(Map<String, Long> expirations) {
        this.expirations = expirations;
    }

    public int getTimeout() {
        return expiration;
    }

    public void setTimeout(int expiration) {
        this.expiration = expiration;
    }
}
