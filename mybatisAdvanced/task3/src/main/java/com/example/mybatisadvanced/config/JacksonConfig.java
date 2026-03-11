package com.example.mybatisadvanced.config;

import org.apache.ibatis.javassist.util.proxy.Proxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.example.mybatisadvanced.mixin.MyBatisJavassistProxyMixIn;

@Configuration
public class JacksonConfig {
    @Bean
    Jackson2ObjectMapperBuilder objectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.mixIn(Proxy.class, MyBatisJavassistProxyMixIn.class);
        return builder;
    }
}
