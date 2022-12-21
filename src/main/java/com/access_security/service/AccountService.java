package com.access_security.service;

import com.access_security.model.common.PermissionEnum;
import com.access_security.model.common.RoleName;
import com.access_security.model.request.account.AccountRequest;
import com.access_security.model.request.filter.AccountFilter;
import com.access_security.model.response.account.AccountResponse;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    boolean confirm(String confirmationToken);

    Optional<AccountResponse> addPermissions(Long accountId, List<PermissionEnum> permissions);

    Optional<AccountResponse> deletePermissions(Long accountId, List<PermissionEnum> permissions);

    Optional<AccountResponse> changeRole(Long accountId, RoleName role);

    Optional<AccountResponse> create(AccountRequest account);

    Optional<AccountResponse> getById(Long id);

    List<AccountResponse> getByFilter(AccountFilter filter);

    Optional<AccountResponse> getByEmail(String email);

    Optional<AccountResponse> update(AccountRequest account);

    boolean deleteById(Long id);
}
