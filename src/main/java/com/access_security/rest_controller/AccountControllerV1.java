package com.access_security.rest_controller;

import com.access_security.model.response.account.AccountResponse;
import com.access_security.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountControllerV1 {
    private final AccountService accountService;

    public AccountControllerV1(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/new")
    public ResponseEntity<?> createAccount(@RequestBody AccountResponse account) {
        var savedAccount = accountService.create(account);
        if (savedAccount.isPresent()) {
            return new ResponseEntity<>(savedAccount.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("creation error", HttpStatus.CONFLICT);
    }
}
