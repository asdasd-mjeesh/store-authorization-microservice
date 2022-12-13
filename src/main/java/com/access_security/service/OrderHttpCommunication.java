package com.access_security.service;

import com.access_security.model.common.OrderStatus;
import com.access_security.model.request.filter.OrderFilter;
import com.access_security.model.response.order.OrderResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderHttpCommunication implements OrderService {
    private final OkHttpClient okHttpClient;
    private final MediaType jsonMediaType;
    private final ObjectMapper objectMapper;
    private final EntityResponseExposeService entityResponseExposeService;

    @Value("${http.communication.orders.api.url.root}")
    private String rootOrdersApiUrl;

    public OrderHttpCommunication(OkHttpClient okHttpClient,
                                  MediaType jsonMediaType,
                                  ObjectMapper objectMapper,
                                  EntityResponseExposeService entityResponseExposeService) {
        this.okHttpClient = okHttpClient;
        this.jsonMediaType = jsonMediaType;
        this.objectMapper = objectMapper;
        this.entityResponseExposeService = entityResponseExposeService;
    }

    @Override
    public Optional<OrderResponse> createOrder(Long accountId) {
        try {
            var urlBuilder = Objects.requireNonNull(HttpUrl.parse(rootOrdersApiUrl + "/")).newBuilder();
            urlBuilder.addQueryParameter("accountId", String.valueOf(accountId));
            String url = urlBuilder.build().toString();

            var body = RequestBody.create(new byte[0]);
            var request = new Request.Builder()
                    .post(body)
                    .url(url)
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            return entityResponseExposeService.getEntityFromHttpResponse(response, HttpStatus.CREATED, OrderResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<OrderResponse> getById(Long id) {
        try {
            var request = new Request.Builder()
                    .get()
                    .url(rootOrdersApiUrl + "/" + id)
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            return entityResponseExposeService.getEntityFromHttpResponse(response, HttpStatus.OK, OrderResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OrderResponse> getByFilter(OrderFilter filter) {
        try {
            String filterJson = objectMapper.writeValueAsString(filter);
            var body = RequestBody.create(filterJson, jsonMediaType);
            var request = new Request.Builder()
                    .put(body)
                    .url(rootOrdersApiUrl + "/filter")
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
    public Optional<OrderResponse> changeStatus(Long orderId, OrderStatus status) {
        try {
            var urlBuilder = Objects.requireNonNull(HttpUrl.parse(rootOrdersApiUrl + "/changeStatus")).newBuilder();
            urlBuilder.addQueryParameter("orderId", String.valueOf(orderId));
            urlBuilder.addQueryParameter("status", String.valueOf(status));
            String url = urlBuilder.build().toString();

            var body = RequestBody.create(new byte[0]);
            var request = new Request.Builder()
                    .patch(body)
                    .url(url)
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            return entityResponseExposeService.getEntityFromHttpResponse(response, HttpStatus.OK, OrderResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
