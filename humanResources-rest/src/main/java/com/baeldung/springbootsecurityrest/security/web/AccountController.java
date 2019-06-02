package com.baeldung.springbootsecurityrest.security.web;


import org.humanResources.security.model.AccountImpl;
import org.humanResources.security.repository.AccountQueryFilter;
import org.humanResources.security.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AccountService accountService;


    @RequestMapping(value="/api/account/findByNameStartsWith",
                    method = RequestMethod.GET,
                    produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody
    Page<AccountImpl> findByNameStartsWith(@RequestParam("name") String name){
        return accountService.findByNameStartsWith(name);
    }


    @RequestMapping(value="/api/account/findByFilter",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody
    Page<AccountImpl> findByFilter(@RequestParam("name") String name){

          /*      Pageable pageRequest = new PageRequest(0, 1000);
        Page<Account> accounts = accountRepository.findByNameStartsWith(name,pageRequest);
*/
        final PageRequest page = new PageRequest(0, 20);

        AccountQueryFilter accountQueryFilter = new AccountQueryFilter();
        accountQueryFilter.addNames(name);

        return accountService.findByFilter(accountQueryFilter,page);
    }


    @RequestMapping(value="/api/accounts",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody
    Page<AccountImpl> findAccounts(){

          /*      Pageable pageRequest = new PageRequest(0, 1000);
        Page<Account> accounts = accountRepository.findByNameStartsWith(name,pageRequest);
*/
      //  final PageRequest page = new PageRequest(0, 20);

        AccountQueryFilter accountQueryFilter = new AccountQueryFilter();

        return accountService.findByFilter(accountQueryFilter,PageRequest.of(0, 20));
    }

    @RequestMapping(value="/api/accounts",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody
    AccountImpl saveAccount(@RequestBody AccountImpl account){
        account.setPassword("test");
        return accountService.save(account);
    }

}