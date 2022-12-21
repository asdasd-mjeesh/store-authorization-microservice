package com.access_security.api.rest_controller;

import com.access_security.model.request.cart.BuyItemProperties;
import com.access_security.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/carts")
public class CartControllerV1 {
    private final CartService cartService;

    public CartControllerV1(CartService cartService) {
        this.cartService = cartService;
    }

    @PreAuthorize("hasAuthority('cart:read')")
    @GetMapping("/{accountId}")
    public ResponseEntity<?> getByAccountId(@PathVariable(name = "accountId") Long accountId) {
        var cartResponse = cartService.getByAccountId(accountId);
        if (cartResponse.isPresent()) {
            return ResponseEntity.ok(cartResponse.get());
        }
        return new ResponseEntity<>("Error", HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasAuthority('cart:edit')")
    @PostMapping("/addItem")
    public ResponseEntity<?> addItem(@RequestParam(name = "accountId") Long accountId,
                                     @RequestBody BuyItemProperties buyItemProperties) {
        var cartResponse = cartService.addItem(accountId, buyItemProperties);
        if (cartResponse.isPresent()) {
            return ResponseEntity.ok(cartResponse.get());
        }
        return new ResponseEntity<>("Error", HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasAuthority('cart:edit')")
    @PatchMapping("/editItem")
    public ResponseEntity<?> editItem(@RequestParam(name = "accountId") Long accountId,
                                      @RequestParam(name = "cartItemId") Long cartItemId,
                                      @RequestBody BuyItemProperties buyItemProperties) {
        var updatedCart = cartService.editItem(accountId, cartItemId, buyItemProperties);
        if (updatedCart.isPresent()) {
            return ResponseEntity.ok(updatedCart.get());
        }
        return new ResponseEntity<>("Error", HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasAuthority('cart:edit')")
    @DeleteMapping("/deleteItem")
    public ResponseEntity<?> deleteItem(@RequestParam(name = "accountId") Long accountId,
                                        @RequestParam(name = "cartItemId") Long cartItemId) {
        var updatedCart = cartService.deleteItem(accountId, cartItemId);
        if (updatedCart.isPresent()) {
            return ResponseEntity.ok(updatedCart.get());
        }
        return new ResponseEntity<>("Error", HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasAuthority('cart:edit')")
    @DeleteMapping("/deleteAllItems")
    public ResponseEntity<?> deleteAllItems(@RequestParam(name = "accountId") Long accountId) {
        var updatedCart = cartService.deleteAllItems(accountId);
        if (updatedCart.isPresent()) {
            return ResponseEntity.ok(updatedCart.get());
        }
        return new ResponseEntity<>("Error", HttpStatus.CONFLICT);
    }
}
