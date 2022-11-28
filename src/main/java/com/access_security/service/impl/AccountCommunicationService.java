package com.access_security.service.impl;

import com.access_security.entity.account.Account;
import com.access_security.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class AccountCommunicationService implements AccountService {
    private OkHttpClient okHttpClient;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @Value("${http-communication.server-response-time-await}")
    private Long responseTimeAwait;
    @Value("${http-communication.users-api-url}")
    private String usersApiUrl;

    @PostConstruct
    private void init() {
        okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(responseTimeAwait, TimeUnit.SECONDS)
                .readTimeout(responseTimeAwait, TimeUnit.SECONDS)
                .writeTimeout(responseTimeAwait, TimeUnit.SECONDS)
                .build();
    }

    @Override
    public User create(Account account) {
        return null;
    }

    @Override
    public boolean update(Account account) {
        return false;
    }

    @Override
    public Optional<User> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Account> getByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public List<Account> getAll() {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }
}
