package com.access_security.service;

import com.access_security.model.response.item.ItemResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
public class EntityResponseExposeService {
    private final ObjectMapper objectMapper;

    public EntityResponseExposeService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <E> Optional<E> getEntityFromHttpResponse(Response response, HttpStatus expectedResponseCode, Class<E> clazz)
            throws IOException {
        if (response.code() == expectedResponseCode.value()) {
            E entityResponse =
                    objectMapper.readValue(Objects.requireNonNull(response.body()).string(), clazz);
            return Optional.ofNullable(entityResponse);
        }
        return Optional.empty();
    }
}
