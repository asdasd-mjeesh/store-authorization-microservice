package com.access_security.service;

import com.access_security.model.request.filter.ItemFilter;
import com.access_security.model.request.item.ItemRequest;
import com.access_security.model.response.item.ItemResponse;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    Optional<ItemResponse> createItem(ItemRequest item);

    Optional<ItemResponse> getById(Long id);

    List<ItemResponse> getByFilter(ItemFilter filter);

    Optional<ItemResponse> update(ItemRequest item);

    boolean deleteById(Long id);
}
