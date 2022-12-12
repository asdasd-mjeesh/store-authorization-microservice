package com.access_security.service;

import com.access_security.model.request.account.AccountRequest;
import com.access_security.model.response.account.AccountResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class AccountCommunicationService implements AccountService {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public final OkHttpClient okHttpClient;
    public final ObjectMapper objectMapper;

    @Value("${http.communication.account.api.url}")
    private String accountApiUrl;

    public AccountCommunicationService(OkHttpClient okHttpClient, ObjectMapper objectMapper) {
        this.okHttpClient = okHttpClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public Optional<AccountResponse> create(AccountRequest account) {
        try {
            var accountJsonString = objectMapper.writeValueAsString(account);
            var body = RequestBody.create(accountJsonString, JSON);
            var request = new Request.Builder()
                    .url(accountApiUrl + "/")
                    .post(body)
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            if (response.code() == HttpStatus.CREATED.value()) {
                AccountResponse accountResponse =
                        objectMapper.readValue(Objects.requireNonNull(response.body()).string(), AccountResponse.class);
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
