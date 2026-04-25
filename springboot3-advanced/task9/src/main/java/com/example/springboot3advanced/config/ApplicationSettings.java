package com.example.springboot3advanced.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "app")
public class ApplicationSettings {
    private String message;
    private Map<String, Boolean> featureFlags;
}
