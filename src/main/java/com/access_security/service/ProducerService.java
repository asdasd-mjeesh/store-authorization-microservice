package com.access_security.service;

import com.access_security.model.request.filter.ProducerFilter;
import com.access_security.model.request.producer.ProducerRequest;
import com.access_security.model.response.producer.ProducerResponse;

import java.util.List;
import java.util.Optional;

public interface ProducerService {
    Optional<ProducerResponse> create(ProducerRequest producer);

    Optional<ProducerResponse> getById(Long id);

    List<ProducerResponse> getByFilter(ProducerFilter filter);

    Optional<ProducerResponse> update(ProducerRequest producer);

    boolean deleteById(Long id);
}
