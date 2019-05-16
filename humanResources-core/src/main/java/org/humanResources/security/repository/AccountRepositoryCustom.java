package org.humanResources.security.repository;

import org.humanResources.security.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface AccountRepositoryCustom {

    Page<Account> searchByFilter(AccountQueryFilter accountQueryFilter, Pageable pageable);
}
