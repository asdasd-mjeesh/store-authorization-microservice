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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class AccountHttpCommunicationService implements AccountService {
    public final OkHttpClient okHttpClient;
    public final MediaType jsonMediaType;
    public final ObjectMapper objectMapper;

    @Value("${http.communication.account.api.url}")
    private String accountApiUrl;

    public AccountHttpCommunicationService(OkHttpClient okHttpClient, MediaType jsonMediaType, ObjectMapper objectMapper) {
        this.okHttpClient = okHttpClient;
        this.jsonMediaType = jsonMediaType;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean confirm(Long accountId) {
        try {
            var urlBuilder = Objects.requireNonNull(HttpUrl.parse(accountApiUrl + "/confirm")).newBuilder();
            urlBuilder.addQueryParameter("accountId", String.valueOf(accountId));
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
            var urlBuilder = Objects.requireNonNull(HttpUrl.parse(accountApiUrl + "/addPermissions")).newBuilder();
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

            return this.getAccountFromHttpResponse(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<AccountResponse> deletePermissions(Long accountId, List<PermissionEnum> permissions) {
        try {
            var urlBuilder = Objects.requireNonNull(HttpUrl.parse(accountApiUrl + "/deletePermissions")).newBuilder();
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

            return this.getAccountFromHttpResponse(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<AccountResponse> changeRole(Long accountId, RoleName role) {
        try {
            var urlBuilder = Objects.requireNonNull(HttpUrl.parse(accountApiUrl + "/changeRole")).newBuilder();
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

            return this.getAccountFromHttpResponse(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<AccountResponse> create(AccountRequest account) {
        try {
            var accountJsonString = objectMapper.writeValueAsString(account);
            var body = RequestBody.create(accountJsonString, jsonMediaType);
            var request = new Request.Builder()
                    .post(body)
                    .url(accountApiUrl + "/")
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            return this.getAccountFromHttpResponse(response, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<AccountResponse> getById(Long id) {
        try {
            var request = new Request.Builder()
                    .get()
                    .url(accountApiUrl + "/" + id)
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            if (response.code() == HttpStatus.OK.value()) {
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
    public List<AccountResponse> getByFilter(AccountFilter filter) {
        try {
            String filterJson = objectMapper.writeValueAsString(filter);
            var body = RequestBody.create(filterJson, jsonMediaType);
            var request = new Request.Builder()
                    .put(body)
                    .url(accountApiUrl + "/filter")
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            if (response.code() == HttpStatus.OK.value()) {
                return objectMapper.readValue(Objects.requireNonNull(response.body()).string(), List.class);
            }
            return new ArrayList<>();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<AccountResponse> getByEmail(String email) {
        try {
            var urlBuilder = Objects.requireNonNull(HttpUrl.parse(accountApiUrl + "/")).newBuilder();
            urlBuilder.addQueryParameter("email", email);
            String url = urlBuilder.build().toString();

            var request = new Request.Builder()
                    .get()
                    .url(url)
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            return this.getAccountFromHttpResponse(response, HttpStatus.OK);
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
                    .url(accountApiUrl + "/")
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            return this.getAccountFromHttpResponse(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            var request = new Request.Builder()
                    .delete()
                    .url(accountApiUrl + "/" + id)
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            return response.code() == HttpStatus.OK.value();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<AccountResponse> getAccountFromHttpResponse(Response response, HttpStatus expectedResponseCode)
            throws IOException {
        if (response.code() == expectedResponseCode.value()) {
            AccountResponse accountResponse =
                    objectMapper.readValue(Objects.requireNonNull(response.body()).string(), AccountResponse.class);
            return Optional.ofNullable(accountResponse);
        }
        return Optional.empty();
    }
}
