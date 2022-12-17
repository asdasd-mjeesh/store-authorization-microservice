package com.access_security.rest_controller;

import com.access_security.model.request.filter.ItemFilter;
import com.access_security.model.request.item.ItemRequest;
import com.access_security.model.response.item.ItemResponse;
import com.access_security.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
public class ItemControllerV1 {
    private final ItemService itemService;

    public ItemControllerV1(ItemService itemService) {
        this.itemService = itemService;
    }

    @PreAuthorize("hasAuthority('item:save')")
    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody ItemRequest item) {
        var savedItem = itemService.createItem(item);
        if (savedItem.isPresent()) {
            return new ResponseEntity<>(savedItem.get(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Creation error", HttpStatus.CONFLICT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") Long id) {
        var responseItem = itemService.getById(id);
        if (responseItem.isPresent()) {
            return ResponseEntity.ok(responseItem.get());
        }
        return new ResponseEntity<>("Error", HttpStatus.CONFLICT);
    }

    @PutMapping("/filter")
    public ResponseEntity<List<ItemResponse>> getByFilter(@RequestBody ItemFilter filter) {
        var itemsResponse = itemService.getByFilter(filter);
        return ResponseEntity.ok(itemsResponse);
    }

    @PreAuthorize("hasAuthority('item:save')")
    @PatchMapping("/")
    public ResponseEntity<?> update(@RequestBody ItemRequest item) {
        var updatedItem = itemService.update(item);
        if (updatedItem.isPresent()) {
            return ResponseEntity.ok(updatedItem.get());
        }
        return new ResponseEntity<>("Error", HttpStatus.CONFLICT);
    }

    @PreAuthorize("hasAuthority('item:delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable(name = "id") Long id) {
        boolean isDeleted = itemService.deleteById(id);
        if (isDeleted) {
            return ResponseEntity.ok("Deleted successfully");
        }
        return new ResponseEntity<>("Deleting failed", HttpStatus.CONFLICT);
    }
}
