package com.access_security.rest_controller;

import com.access_security.model.account.Account;
import com.access_security.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountControllerV1 {
    private final AccountService accountService;

    @Autowired
    public AccountControllerV1(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/new")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        var savedAccount = accountService.create(account);
        return new ResponseEntity<>(savedAccount, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('account:read')")
    public ResponseEntity<Account> findById(@PathVariable("id") Long id) {
        var account = accountService.getById(id).orElse(new Account());
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}
