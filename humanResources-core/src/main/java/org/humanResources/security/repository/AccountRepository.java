package org.humanResources.security.repository;

import org.humanResources.security.model.Account;
import org.humanResources.security.model.AccountImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository("accountRepository")
public interface AccountRepository extends AccountRepositoryCustom,JpaRepository<AccountImpl, Long> {

    //	Returns	unique	user	with	given	user-name
    Optional<AccountImpl> findByName(String username);

    Boolean existsByName(String username);



    //Boolean existsByEmail(String email);

}