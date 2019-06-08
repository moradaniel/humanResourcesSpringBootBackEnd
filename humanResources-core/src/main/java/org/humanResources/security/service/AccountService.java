package org.humanResources.security.service;



import org.humanResources.dto.AccountDTO;
import org.humanResources.dto.AccountSearchResponseDTO;
import org.humanResources.security.model.AccountImpl;
import org.humanResources.security.repository.AccountRepository;
import org.humanResources.security.repository.AccountQueryFilter;
import org.humanResources.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Service
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);


    AccountRepository accountRepository;

    PasswordEncoder passwordEncoder;


    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder){
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @Transactional
    public Page<AccountImpl> findByNameStartsWith(String name){

  /*      Pageable pageRequest = new PageRequest(0, 1000);
        Page<Account> accounts = accountRepository.findByNameStartsWith(name,pageRequest);
*/


        final PageRequest pageRequest = PageRequest.of(0, 20);

        Page<AccountImpl> accounts = accountRepository.findAll(pageRequest);

/*        Predicate accountPredicate = AccountPredicates.firstOrLastNameStartsWith(name);
        Page<Account> accounts = accountRepository.findAll(accountPredicate,page1);
*/
        return accounts;
    }



    @Transactional
    public AccountImpl findById(Long id){

        //Account account = accountRepository.findOne(id);
        AccountImpl account = accountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found account with id "+id));

        return account;
    }

    @Transactional
    public AccountImpl findByName(String name){

        AccountImpl account = accountRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Not found account with name "+name));

        return account;
    }

    @Transactional
    public Page<AccountImpl> findByFilter(AccountQueryFilter accountQueryFilter, Pageable pageable){
        return accountRepository.searchByFilter(accountQueryFilter, pageable);
    }

    @Transactional
    public Page<AccountImpl> findByCriteria(AccountQueryFilter accountQueryFilter, Pageable pageable) {

        logger.info("attempting to find accounts by criteria");

      //  accountQueryFilter.setCurrentUserId(userService.getCurrentUser().getUserId());

        Page<AccountImpl> foundProducts = accountRepository.searchByFilter(accountQueryFilter, pageable);

        /*List<AccountSearchResponseDTO> foundProductsDTOs = new ArrayList<>();

        for(AccountImpl product:foundProducts){


           // if(Boolean.TRUE == product.getHidden() && !isProductVisibleByUser(product,userService.getCurrentUser())){
           //     continue;
            //}

            foundProductsDTOs.add(productMapper.productToProductSearchResponseDTO(product));
        }

        Page<ProductSearchResponseDTO> result =  new PageImpl<>(foundProductsDTOs, pageable,
                foundProducts.getTotalElements());*/
        return foundProducts;

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
    public AccountImpl save(AccountImpl account){
        if(account.getId()==null){
            account.setPassword(passwordEncoder.encode(account.getPassword()));
        }
        account = this.accountRepository.save(account);
        return account;
    }

    @Transactional
    public Result<AccountImpl> update(final AccountImpl account){
        logger.info("Attempting to update account with name: {}",account.getName());


        //validate product creation request
        DataBinder dataBinder = new DataBinder(account);
       // TODO dataBinder.addValidators(new ProductUpdateOperationValidator(productRepository));
        dataBinder.validate();

        Result<AccountImpl> result;

        if (dataBinder.getBindingResult().hasErrors()) {
            List<String> errors = new ArrayList<>();
            for(ObjectError error : dataBinder.getBindingResult().getAllErrors()){
                errors.add(error.getCode()+"-"+error.getDefaultMessage());
            }


            result = new Result<>(account, errors);
            return result;
        }

        //get existing audit information
        //here we discard any update to audit dates since is managed by JPA
        AccountImpl existingProduct = loadById(account.getId()).orElseThrow(() -> new EntityNotFoundException("Not found account with id " + account.getId()));
        //product.setAudit(existingProduct.getAudit());

        existingProduct.setName(account.getName());

        AccountImpl updatedProduct = this.accountRepository.save(existingProduct);

        result = new Result<>(updatedProduct);
        return result;
    }


   // @Override
    public Optional<AccountImpl> loadById(Long id) {
        return accountRepository.loadById(id);
    }
}