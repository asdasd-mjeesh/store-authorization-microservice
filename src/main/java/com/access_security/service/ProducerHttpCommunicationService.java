package com.access_security.service;

import com.access_security.model.request.filter.ProducerFilter;
import com.access_security.model.request.producer.ProducerRequest;
import com.access_security.model.response.producer.ProducerResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProducerHttpCommunicationService implements ProducerService {
    public final OkHttpClient okHttpClient;
    public final MediaType jsonMediaType;
    public final ObjectMapper objectMapper;

    @Value("${http.communication.producers.api.url.root}")
    private String producersRootApiUrl;

    public ProducerHttpCommunicationService(OkHttpClient okHttpClient, MediaType jsonMediaType, ObjectMapper objectMapper) {
        this.okHttpClient = okHttpClient;
        this.jsonMediaType = jsonMediaType;
        this.objectMapper = objectMapper;
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

            return this.getProducerFromHttpResponse(response, HttpStatus.CREATED);
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

            return this.getProducerFromHttpResponse(response, HttpStatus.OK);
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

            return objectMapper.readValue(Objects.requireNonNull(response.body()).string(), List.class);
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

            return this.getProducerFromHttpResponse(response, HttpStatus.OK);
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

    private Optional<ProducerResponse> getProducerFromHttpResponse(Response response, HttpStatus expectedResponseCode)
            throws IOException {
        if (response.code() == expectedResponseCode.value()) {
            ProducerResponse producerResponse =
                    objectMapper.readValue(Objects.requireNonNull(response.body()).string(), ProducerResponse.class);
            return Optional.ofNullable(producerResponse);
        }
        return Optional.empty();
    }
}
