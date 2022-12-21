package com.access_security.service;

import com.access_security.model.common.PermissionEnum;
import com.access_security.model.common.RoleName;
import com.access_security.model.request.account.AccountRequest;
import com.access_security.model.request.filter.AccountFilter;
import com.access_security.model.response.account.AccountResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class AccountHttpCommunication implements AccountService {
    private final OkHttpClient okHttpClient;
    private final MediaType jsonMediaType;
    private final ObjectMapper objectMapper;
    private final EntityResponseExposeService entityResponseExposeService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${http.communication.account.api.url.root}")
    private String accountsRootApiUrl;

    public AccountHttpCommunication(OkHttpClient okHttpClient,
                                    MediaType jsonMediaType,
                                    ObjectMapper objectMapper,
                                    EntityResponseExposeService entityResponseExposeService,
                                    BCryptPasswordEncoder passwordEncoder) {
        this.okHttpClient = okHttpClient;
        this.jsonMediaType = jsonMediaType;
        this.objectMapper = objectMapper;
        this.entityResponseExposeService = entityResponseExposeService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean confirm(String confirmationToken) {
        try {
            var urlBuilder = Objects.requireNonNull(HttpUrl.parse(accountsRootApiUrl + "/confirm")).newBuilder();
            urlBuilder.addQueryParameter("token", String.valueOf(confirmationToken));
            String url = urlBuilder.build().toString();

            var body = RequestBody.create(new byte[0]);
            var request = new Request.Builder()
                    .patch(body)
                    .url(url)
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            return response.code() == HttpStatus.OK.value();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<AccountResponse> addPermissions(Long accountId, List<PermissionEnum> permissions) {
        try {
            var urlBuilder = Objects.requireNonNull(HttpUrl.parse(accountsRootApiUrl + "/addPermissions")).newBuilder();
            urlBuilder.addQueryParameter("accountId", String.valueOf(accountId));
            String url = urlBuilder.build().toString();

            String permissionsJsonString = objectMapper.writeValueAsString(permissions);
            var body = RequestBody.create(permissionsJsonString, jsonMediaType);
            var request = new Request.Builder()
                    .patch(body)
                    .url(url)
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            return entityResponseExposeService.getEntityFromHttpResponse(response, HttpStatus.OK, AccountResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<AccountResponse> deletePermissions(Long accountId, List<PermissionEnum> permissions) {
        try {
            var urlBuilder = Objects.requireNonNull(HttpUrl.parse(accountsRootApiUrl + "/deletePermissions")).newBuilder();
            urlBuilder.addQueryParameter("accountId", String.valueOf(accountId));
            String url = urlBuilder.build().toString();

            String permissionsJsonString = objectMapper.writeValueAsString(permissions);
            var body = RequestBody.create(permissionsJsonString, jsonMediaType);
            var request = new Request.Builder()
                    .delete(body)
                    .url(url)
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            return entityResponseExposeService.getEntityFromHttpResponse(response, HttpStatus.OK, AccountResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<AccountResponse> changeRole(Long accountId, RoleName role) {
        try {
            var urlBuilder = Objects.requireNonNull(HttpUrl.parse(accountsRootApiUrl + "/changeRole")).newBuilder();
            urlBuilder.addQueryParameter("accountId", String.valueOf(accountId));
            urlBuilder.addQueryParameter("roleName", role.name());
            String url = urlBuilder.build().toString();

            var body = RequestBody.create(new byte[0]);
            var request = new Request.Builder()
                    .patch(body)
                    .url(url)
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            return entityResponseExposeService.getEntityFromHttpResponse(response, HttpStatus.OK, AccountResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<AccountResponse> create(AccountRequest account) {
        try {
            String encodedPassword = passwordEncoder.encode(account.getPassword());
            account.setPassword(encodedPassword);
            var accountJsonString = objectMapper.writeValueAsString(account);
            var body = RequestBody.create(accountJsonString, jsonMediaType);
            var request = new Request.Builder()
                    .post(body)
                    .url(accountsRootApiUrl + "/")
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            return entityResponseExposeService.getEntityFromHttpResponse(response, HttpStatus.CREATED, AccountResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<AccountResponse> getById(Long id) {
        try {
            var request = new Request.Builder()
                    .get()
                    .url(accountsRootApiUrl + "/" + id)
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            return entityResponseExposeService.getEntityFromHttpResponse(response, HttpStatus.OK, AccountResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<AccountResponse> getByFilter(AccountFilter filter) {
        try {
            String filterJson = objectMapper.writeValueAsString(filter);
            var body = RequestBody.create(filterJson, jsonMediaType);
            var request = new Request.Builder()
                    .put(body)
                    .url(accountsRootApiUrl + "/filter")
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            return entityResponseExposeService
                    .getEntityFromHttpResponse(response, HttpStatus.OK, List.class).orElse(new ArrayList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<AccountResponse> getByEmail(String email) {
        try {
            var urlBuilder = Objects.requireNonNull(HttpUrl.parse(accountsRootApiUrl + "/")).newBuilder();
            urlBuilder.addQueryParameter("email", email);
            String url = urlBuilder.build().toString();

            var request = new Request.Builder()
                    .get()
                    .url(url)
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            return entityResponseExposeService.getEntityFromHttpResponse(response, HttpStatus.OK, AccountResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<AccountResponse> update(AccountRequest account) {
        try {
            String accountJson = objectMapper.writeValueAsString(account);
            var body = RequestBody.create(accountJson, jsonMediaType);

            var request = new Request.Builder()
                    .patch(body)
                    .url(accountsRootApiUrl + "/")
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            return entityResponseExposeService.getEntityFromHttpResponse(response, HttpStatus.OK, AccountResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            var request = new Request.Builder()
                    .delete()
                    .url(accountsRootApiUrl + "/" + id)
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            return response.code() == HttpStatus.OK.value();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
