package org.humanResources.security.repository;

import org.humanResources.security.model.AccountImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface AccountRepositoryCustom {

    Page<AccountImpl> searchByFilter(AccountQueryFilter accountQueryFilter, Pageable pageable);

    Optional<AccountImpl> loadByName(String name);

    Optional<AccountImpl> loadById(Long id);

}
