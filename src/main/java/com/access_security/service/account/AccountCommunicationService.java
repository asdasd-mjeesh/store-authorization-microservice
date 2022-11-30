package com.access_security.service.account;

import com.access_security.model.account.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class AccountCommunicationService implements AccountService {
    private OkHttpClient okHttpClient;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

//    @Value("${http-communication.server-response-time-await}")
//    private Long responseTimeAwait;
//    @Value("${http-communication.users-api-url}")
//    private String usersApiUrl;

    private final PasswordEncoder passwordEncoder;

    public AccountCommunicationService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    private List<Account> accounts = new ArrayList<>();
    public static Long count = 1L;

    @PostConstruct
    private void init() {
//        okHttpClient = new OkHttpClient().newBuilder()
//                .connectTimeout(responseTimeAwait, TimeUnit.SECONDS)
//                .readTimeout(responseTimeAwait, TimeUnit.SECONDS)
//                .writeTimeout(responseTimeAwait, TimeUnit.SECONDS)
//                .build();
    }

    @Override
    public Account create(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setId(count++);
        accounts.add(account);
        return account;
    }

    @Override
    public boolean update(Account account) {
        return false;
    }

    @Override
    public Optional<Account> getById(Long id) {
        return accounts.stream().
                filter(account -> account.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Account> getByEmail(String email) {
        return accounts.stream()
                .filter(account -> account.getEmail().equals(email))
                .findFirst();
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
