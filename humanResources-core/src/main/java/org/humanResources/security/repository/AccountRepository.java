package org.humanResources.security.repository;

import org.humanResources.security.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository("accountRepository")
public interface AccountRepository extends AccountRepositoryCustom,JpaRepository<Account, Long> {

    //	Returns	unique	user	with	given	user-name
    Optional<Account> findByName(String username);

    Boolean existsByName(String username);



    //Boolean existsByEmail(String email);

}