package com.access_security.service;

import com.access_security.model.request.account.AccountRequest;
import com.access_security.model.response.account.AccountResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountCommunicationService implements AccountService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private OkHttpClient okHttpClient;
    private final Long responseTimeAwait = 10L;

    @Value("${http.communication.account.api.url}")
    private String accountApiUrl;

    @PostConstruct
    private void setup() {
        okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(responseTimeAwait, TimeUnit.SECONDS)
                .readTimeout(responseTimeAwait, TimeUnit.SECONDS)
                .writeTimeout(responseTimeAwait, TimeUnit.SECONDS)
                .build();
    }
    
    @Override
    public Optional<AccountResponse> create(AccountRequest account) {
        try {
            var accountJsonString = OBJECT_MAPPER.writeValueAsString(account);
            var body = RequestBody.create(accountJsonString, JSON);
            var request = new Request.Builder()
                    .url(accountApiUrl + "/")
                    .post(body)
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            if (response.code() == HttpStatus.CREATED.value()) {
                AccountResponse accountResponse =
                        OBJECT_MAPPER.readValue(Objects.requireNonNull(response.body()).string(), AccountResponse.class);
                return Optional.ofNullable(accountResponse);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<AccountResponse> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<AccountResponse> getByEmail(String email) {
        return Optional.empty();
    }
}
