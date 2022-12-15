package com.access_security.service;

import com.access_security.model.request.filter.ProducerFilter;
import com.access_security.model.request.producer.ProducerRequest;
import com.access_security.model.response.producer.ProducerResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProducerHttpCommunication implements ProducerService {
    private final OkHttpClient okHttpClient;
    private final MediaType jsonMediaType;
    private final ObjectMapper objectMapper;
    private final EntityResponseExposeService entityResponseExposeService;

    @Value("${http.communication.producers.api.url.root}")
    private String producersRootApiUrl;

    public ProducerHttpCommunication(OkHttpClient okHttpClient,
                                     MediaType jsonMediaType,
                                     ObjectMapper objectMapper,
                                     EntityResponseExposeService entityResponseExposeService) {
        this.okHttpClient = okHttpClient;
        this.jsonMediaType = jsonMediaType;
        this.objectMapper = objectMapper;
        this.entityResponseExposeService = entityResponseExposeService;
    }

    @Override
    public Optional<ProducerResponse> create(ProducerRequest producer) {
        try {
            String producerJson = objectMapper.writeValueAsString(producer);
            var body = RequestBody.create(producerJson, jsonMediaType);
            var request = new Request.Builder()
                    .post(body)
                    .url(producersRootApiUrl + "/")
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            return entityResponseExposeService.getEntityFromHttpResponse(response, HttpStatus.CREATED, ProducerResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<ProducerResponse> getById(Long id) {
        try {
            var request = new Request.Builder()
                    .get()
                    .url(producersRootApiUrl + "/" + id)
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            return entityResponseExposeService.getEntityFromHttpResponse(response, HttpStatus.OK, ProducerResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ProducerResponse> getByFilter(ProducerFilter filter) {
        try {
            String filterJson = objectMapper.writeValueAsString(filter);
            var body = RequestBody.create(filterJson, jsonMediaType);
            var request = new Request.Builder()
                    .put(body)
                    .url(producersRootApiUrl + "/filter")
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
    public Optional<ProducerResponse> update(ProducerRequest producer) {
        try {
            String producerJson = objectMapper.writeValueAsString(producer);
            var body = RequestBody.create(producerJson, jsonMediaType);
            var request = new Request.Builder()
                    .patch(body)
                    .url(producersRootApiUrl + "/")
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            return entityResponseExposeService.getEntityFromHttpResponse(response, HttpStatus.OK, ProducerResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            var request = new Request.Builder()
                    .delete()
                    .url(producersRootApiUrl + "/" + id)
                    .build();
            var call = okHttpClient.newCall(request);
            var response = call.execute();

            return response.code() == HttpStatus.OK.value();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
