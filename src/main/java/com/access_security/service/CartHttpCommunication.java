package com.access_security.service;

import com.access_security.model.request.cart.BuyItemProperties;
import com.access_security.model.response.cart.CartResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class CartHttpCommunication implements CartService {
    private final OkHttpClient okHttpClient;
    private final MediaType jsonMediaType;
    private final ObjectMapper objectMapper;
    private final EntityResponseExposeService entityResponseExposeService;

    @Value("${http.communication.carts.api.url.root}")
    private String rootItemApiUrl;

    public CartHttpCommunication(OkHttpClient okHttpClient,
                                 MediaType jsonMediaType,
                                 ObjectMapper objectMapper,
                                 EntityResponseExposeService entityResponseExposeService) {
        this.okHttpClient = okHttpClient;
        this.jsonMediaType = jsonMediaType;
        this.objectMapper = objectMapper;
        this.entityResponseExposeService = entityResponseExposeService;
    }

    @Override
    public Optional<CartResponse> getByAccountId(Long accountId) {
        try {
            var request = new Request.Builder()
                    .get()
                    .url(rootItemApiUrl + "/" + accountId)
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            return entityResponseExposeService.getEntityFromHttpResponse(response, HttpStatus.OK, CartResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<CartResponse> addItem(Long accountId, BuyItemProperties buyItemProperties) {
        try {
            var urlBuilder = Objects.requireNonNull(HttpUrl.parse(rootItemApiUrl + "/addItem")).newBuilder();
            urlBuilder.addQueryParameter("accountId", String.valueOf(accountId));
            String url = urlBuilder.build().toString();

            String buyItemPropertiesJson = objectMapper.writeValueAsString(buyItemProperties);
            var body = RequestBody.create(buyItemPropertiesJson, jsonMediaType);
            var request = new Request.Builder()
                    .post(body)
                    .url(url)
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            return entityResponseExposeService.getEntityFromHttpResponse(response, HttpStatus.OK, CartResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<CartResponse> editItem(Long accountId, Long cartItemId, BuyItemProperties buyItemProperties) {
        try {
            var urlBuilder = Objects.requireNonNull(HttpUrl.parse(rootItemApiUrl + "/editItem")).newBuilder();
            urlBuilder.addQueryParameter("accountId", String.valueOf(accountId));
            urlBuilder.addQueryParameter("cartItemId", String.valueOf(cartItemId));
            String url = urlBuilder.build().toString();

            String buyItemPropertiesJson = objectMapper.writeValueAsString(buyItemProperties);
            var body = RequestBody.create(buyItemPropertiesJson, jsonMediaType);
            var request = new Request.Builder()
                    .patch(body)
                    .url(url)
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            return entityResponseExposeService.getEntityFromHttpResponse(response, HttpStatus.OK, CartResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<CartResponse> deleteItem(Long accountId, Long cartItemId) {
        try {
            var urlBuilder = HttpUrl.parse(rootItemApiUrl + "/deleteItem").newBuilder();
            urlBuilder.addQueryParameter("accountId", String.valueOf(accountId));
            urlBuilder.addQueryParameter("cartItemId", String.valueOf(cartItemId));
            String url = urlBuilder.build().toString();

            var request = new Request.Builder()
                    .delete()
                    .url(url)
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            return entityResponseExposeService.getEntityFromHttpResponse(response, HttpStatus.OK, CartResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<CartResponse> deleteAllItems(Long accountId) {
        try {
            var urlBuilder = Objects.requireNonNull(HttpUrl.parse(rootItemApiUrl + "/deleteAllItems")).newBuilder();
            urlBuilder.addQueryParameter("accountId", String.valueOf(accountId));
            String url = urlBuilder.build().toString();

            var request = new Request.Builder()
                    .delete()
                    .url(url)
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            return entityResponseExposeService.getEntityFromHttpResponse(response, HttpStatus.OK, CartResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
