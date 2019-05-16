package org.humanResources.security.service;


//import com.querydsl.core.types.Predicate;
//import org.humanResources.security.entity.*;
import org.humanResources.security.model.Account;
import org.humanResources.security.repository.AccountRepository;
import org.humanResources.security.repository.AccountQueryFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

//@Service
public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    AccountRepository accountRepository;

    PasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder){
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @Transactional
    public Page<Account> findByNameStartsWith(String name){

  /*      Pageable pageRequest = new PageRequest(0, 1000);
        Page<Account> accounts = accountRepository.findByNameStartsWith(name,pageRequest);
*/


        final PageRequest pageRequest = PageRequest.of(0, 20);

        Page<Account> accounts = accountRepository.findAll(pageRequest);

/*        Predicate accountPredicate = AccountPredicates.firstOrLastNameStartsWith(name);
        Page<Account> accounts = accountRepository.findAll(accountPredicate,page1);
*/
        return accounts;
    }



    @Transactional
    public Account findById(Long id){

        //Account account = accountRepository.findOne(id);
        Account account = accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found account with id "+id));

        return account;
    }

    @Transactional
    public Account findByName(String name){

        Account account = accountRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Not found account with name "+name));

        return account;
    }

    @Transactional
    public Page<Account> findByFilter(AccountQueryFilter accountQueryFilter, Pageable pageable){
        return accountRepository.searchByFilter(accountQueryFilter, pageable);
    }

    /*
    @Transactional
    public Page<Account> findAll(){*/

        /*
        final PageRequest page1 = new PageRequest(
                0, 20, Direction.ASC, "lastName", "salary"
        );

        final PageRequest page2 = new PageRequest(
                0, 20, new Sort(
                new Order(Direction.ASC, "lastName"),
                new Order(Direction.DESC, "salary")
        )
        );

        */

     /*   Pageable pageRequest = new PageRequest(0, 1000);
        Page<Account> accounts = accountRepository.findAll();

        return accounts;
    }*/


    @Transactional
    public Account save(Account account){
        if(account.getId()==null){
            account.setPassword(passwordEncoder.encode(account.getPassword()));
        }
        account = this.accountRepository.save(account);
        return account;
    }
}