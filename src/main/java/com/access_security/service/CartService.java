package com.access_security.service;

import com.access_security.model.request.cart.BuyItemProperties;
import com.access_security.model.response.cart.CartResponse;

import java.util.Optional;

public interface CartService {
    Optional<CartResponse> getByAccountId(Long accountId);

    Optional<CartResponse> addItem(Long accountId, BuyItemProperties buyItemProperties);

    Optional<CartResponse> editItem(Long accountId, Long cartItemId, BuyItemProperties buyItemProperties);

    Optional<CartResponse> deleteItem(Long accountId, Long cartItemId);

    Optional<CartResponse> deleteAllItems(Long accountId);
}
