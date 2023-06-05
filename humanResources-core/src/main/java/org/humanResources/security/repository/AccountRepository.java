package org.humanResources.security.repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;
import org.humanResources.repository.asimio.AsimioJpaSpecificationExecutor;
import org.humanResources.security.model.Account;
import org.humanResources.security.model.AccountImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository("accountRepository")
public interface AccountRepository extends AccountRepositoryCustom,
        JpaRepository<AccountImpl, Long>,
        AsimioJpaSpecificationExecutor<AccountImpl, Long>,
        EntityGraphJpaRepository<AccountImpl, Long>,
        EntityGraphJpaSpecificationExecutor<AccountImpl>{

    //	Returns	unique	user	with	given	user-name
    Optional<AccountImpl> findByName(String username);

    Boolean existsByName(String username);


    //Boolean existsByEmail(String email);

}