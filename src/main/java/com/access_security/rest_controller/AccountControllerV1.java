package com.access_security.rest_controller;

import com.access_security.model.common.PermissionEnum;
import com.access_security.model.common.RoleName;
import com.access_security.model.request.account.AccountRequest;
import com.access_security.model.request.filter.AccountFilter;
import com.access_security.model.response.account.AccountResponse;
import com.access_security.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountControllerV1 {
    private final AccountService accountService;

    public AccountControllerV1(AccountService accountService) {
        this.accountService = accountService;
    }

    @PatchMapping("/confirm")
    public ResponseEntity<String> confirmAccount(@RequestParam(name = "accountId") Long accountId) {
        boolean isConfirmed = accountService.confirm(accountId);
        if (isConfirmed) {
            return ResponseEntity.ok("Confirmation send");
        }
        return new ResponseEntity<>("Error", HttpStatus.CONFLICT);
    }

    @PatchMapping("/addPermissions")
    public ResponseEntity<?> addPermissions(@RequestParam(name = "accountId") Long accountId,
                                            @RequestBody List<PermissionEnum> permissions) {
        var accountResponse = accountService.addPermissions(accountId, permissions);
        if (accountResponse.isPresent()) {
            return ResponseEntity.ok(accountResponse.get());
        }
        return new ResponseEntity<>("Error", HttpStatus.CONFLICT);
    }

    @DeleteMapping("/deletePermissions")
    public ResponseEntity<?> deletePermissions(@RequestParam(name = "accountId") Long accountId,
                                               @RequestBody List<PermissionEnum> permissions) {
        var accountResponse = accountService.deletePermissions(accountId, permissions);
        if (accountResponse.isPresent()) {
            return ResponseEntity.ok(accountResponse.get());
        }
        return new ResponseEntity<>("Error", HttpStatus.CONFLICT);
    }

    @PatchMapping("/changeRole")
    public ResponseEntity<?> changeRole(@RequestParam(name = "accountId") Long accountId,
                                        @RequestParam(name = "roleName") RoleName role) {
        var accountResponse = accountService.changeRole(accountId, role);
        if (accountResponse.isPresent()) {
            return ResponseEntity.ok(accountResponse.get());
        }
        return new ResponseEntity<>("Error", HttpStatus.CONFLICT);
    }

    @PostMapping("/new")
    public ResponseEntity<?> createAccount(@RequestBody AccountRequest account) {
        var savedAccount = accountService.create(account);
        if (savedAccount.isPresent()) {
            return new ResponseEntity<>(savedAccount.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("creation error", HttpStatus.CONFLICT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") Long id) {
        var account = accountService.getById(id);
        if (account.isPresent()) {
            return ResponseEntity.ok(account.get());
        }
        return new ResponseEntity<>(String.format("Account with id=%s not found", id), HttpStatus.NOT_FOUND);
    }

    @PutMapping("/filter")
    public ResponseEntity<List<AccountResponse>> getByFilter(@RequestBody AccountFilter filter) {
        var accountsResponse = accountService.getByFilter(filter);
        return ResponseEntity.ok(accountsResponse);
    }

    @GetMapping("/")
    public ResponseEntity<?> getByEmail(@RequestParam(name = "email") String email) {
        var accountResponse = accountService.getByEmail(email);
        if (accountResponse.isPresent()) {
            return ResponseEntity.ok(accountResponse.get());
        }
        return new ResponseEntity<>("Error", HttpStatus.CONFLICT);
    }

    @PatchMapping("/")
    public ResponseEntity<?> update(@RequestBody AccountRequest account) {
        var updatedAccount = accountService.update(account);
        if (updatedAccount.isPresent()) {
            return ResponseEntity.ok(updatedAccount.get());
        }
        return new ResponseEntity<>("Updating failed", HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "id") Long id) {
        boolean isDeleted = accountService.deleteById(id);
        if (isDeleted) {
            return ResponseEntity.ok("Deleted successfully");
        }
        return new ResponseEntity<>("Deleting was failed", HttpStatus.CONFLICT);
    }
}
