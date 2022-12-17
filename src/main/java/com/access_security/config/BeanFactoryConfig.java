package com.access_security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.concurrent.TimeUnit;

@Configuration
public class BeanFactoryConfig {
    @Value("${http.communication.await.time}")
    private final Long responseTimeAwait = 10L;

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient().newBuilder()
                .connectTimeout(responseTimeAwait, TimeUnit.SECONDS)
                .readTimeout(responseTimeAwait, TimeUnit.SECONDS)
                .writeTimeout(responseTimeAwait, TimeUnit.SECONDS)
                .build();
    }

    @Bean
    public MediaType jsonMediaType() {
        return MediaType.get("application/json; charset=utf-8");
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
