package com.access_security.service;

import com.access_security.model.request.filter.ItemFilter;
import com.access_security.model.request.item.ItemRequest;
import com.access_security.model.response.item.ItemResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemHttpCommunication implements ItemService {
    private final OkHttpClient okHttpClient;
    private final MediaType jsonMediaType;
    private final ObjectMapper objectMapper;
    private final EntityResponseExposeService entityResponseExposeService;

    @Value("${http.communication.items.api.url.root}")
    private String rootItemApiUrl;

    public ItemHttpCommunication(OkHttpClient okHttpClient,
                                 MediaType jsonMediaType,
                                 ObjectMapper objectMapper,
                                 EntityResponseExposeService entityResponseExposeService) {
        this.okHttpClient = okHttpClient;
        this.jsonMediaType = jsonMediaType;
        this.objectMapper = objectMapper;
        this.entityResponseExposeService = entityResponseExposeService;
    }

    @Override
    public Optional<ItemResponse> createItem(ItemRequest item) {
        try {
            String itemJson = objectMapper.writeValueAsString(item);
            var body = RequestBody.create(itemJson, jsonMediaType);
            var request = new Request.Builder()
                    .post(body)
                    .url(rootItemApiUrl + "/")
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            return entityResponseExposeService.getEntityFromHttpResponse(response, HttpStatus.CREATED, ItemResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<ItemResponse> getById(Long id) {
        try {
            var request = new Request.Builder()
                    .get()
                    .url(rootItemApiUrl + "/" + id)
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            return entityResponseExposeService.getEntityFromHttpResponse(response, HttpStatus.OK, ItemResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ItemResponse> getByFilter(ItemFilter filter) {
        try {
            String filterJson = objectMapper.writeValueAsString(filter);
            var body = RequestBody.create(filterJson, jsonMediaType);
            var request = new Request.Builder()
                    .put(body)
                    .url(rootItemApiUrl + "/filter")
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
    public Optional<ItemResponse> update(ItemRequest item) {
        try {
            String itemJson = objectMapper.writeValueAsString(item);
            var body = RequestBody.create(itemJson, jsonMediaType);
            var request = new Request.Builder()
                    .patch(body)
                    .url(rootItemApiUrl + "/")
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            return entityResponseExposeService.getEntityFromHttpResponse(response, HttpStatus.OK, ItemResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            var request = new Request.Builder()
                    .delete()
                    .url(rootItemApiUrl + "/" + id)
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            return response.code() == HttpStatus.OK.value();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
