package com.example.springboot3advanced.controller;

import com.example.springboot3advanced.config.ApplicationSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/config")
@RequiredArgsConstructor
public class ConfigController {

    private final ApplicationSettings applicationSettings;

    @GetMapping
    public Map<String, Object> getConfig() {
        return Map.of(
            "message", applicationSettings.getMessage(),
            "featureFlags", applicationSettings.getFeatureFlags()
        );
    }
}
