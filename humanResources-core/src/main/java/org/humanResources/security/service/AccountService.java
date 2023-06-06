package org.humanResources.security.service;


import com.cosium.spring.data.jpa.entity.graph.domain2.DynamicEntityGraph;
import jakarta.persistence.EntityNotFoundException;
import org.humanResources.dto.AccountDTO;
import org.humanResources.repository.RoleRepository;
import org.humanResources.repository.specification.AccountSpecifications;
import org.humanResources.security.model.*;
import org.humanResources.security.repository.AccountQueryFilter;
import org.humanResources.security.repository.AccountRepository;
import org.humanResources.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;

import java.util.*;
import java.util.stream.Collectors;

//@Service
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);


    AccountRepository accountRepository;
    RoleRepository roleRepository;

    PasswordEncoder passwordEncoder;


    public AccountService(AccountRepository accountRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder){
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
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
    public Page<AccountImpl> findByFilter2(AccountQueryFilter accountQueryFilter, Pageable pageable){
       // return accountRepository.searchByFilter(accountQueryFilter, pageable);
        //return accountRepository.findAll(specification, pageable, new NamedEntityGraph(EntityGraphType.FETCH, "graphName"))

        //Accounten
        return accountRepository.findAll(pageable/*PageRequest.of(0,2000)*/, DynamicEntityGraph.loading().addPath(AccountImpl_.ROLES/*, AccountRoleAssociation_.ROLE*/).build());

    }


    @Transactional
    public Page<AccountImpl> findByFilter3(AccountQueryFilter accountQueryFilter, Specification<AccountImpl> accountSpec, Pageable page){
        // Getting film ids and page data to prevent:
        // HHH000104: firstResult/maxResults specified with collection fetch; applying in memory!
        // which affects application's performance
        Page<Long> filmIdsPage = this.accountRepository.findEntityIds(accountSpec, page);

        List<AccountImpl> result;
        List<Long> filmIds = filmIdsPage.getContent();
        if (CollectionUtils.isEmpty(filmIds)) {
            result = List.of();
        } else {
            // Retrieve accounts using IN predicate
            Specification<AccountImpl> fimlIdInSpecification = AccountSpecifications.idIn(Set.copyOf(filmIds));
            result = this.accountRepository.findAll(fimlIdInSpecification,
                    DynamicEntityGraph.loading().addPath(AccountImpl_.ROLES, AccountRoleAssociation_.ROLE).build());
        }
        return PageableExecutionUtils.getPage(result, page, () -> filmIdsPage.getTotalElements());
        //return result;
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
    public Result<AccountImpl> update(final AccountDTO accountUpdateDTO){
        logger.info("Attempting to update account with name: {}",accountUpdateDTO.getName());


        //validate product creation request
        DataBinder dataBinder = new DataBinder(accountUpdateDTO);
       // TODO dataBinder.addValidators(new ProductUpdateOperationValidator(productRepository));
        dataBinder.validate();

        Result<AccountImpl> result;

        if (dataBinder.getBindingResult().hasErrors()) {
            List<String> errors = new ArrayList<>();
            for(ObjectError error : dataBinder.getBindingResult().getAllErrors()){
                errors.add(error.getCode()+"-"+error.getDefaultMessage());
            }


            result = new Result<>(null, errors);
            return result;
        }

        //get existing audit information
        //here we discard any update to audit dates since is managed by JPA
        AccountImpl existingAccount = loadById(accountUpdateDTO.getId()).orElseThrow(() -> new EntityNotFoundException("Not found account with id " + accountUpdateDTO.getId()));
        //product.setAudit(existingProduct.getAudit());

        existingAccount.setName(accountUpdateDTO.getName());


        //update roles
        Set<Long> currentRoleIdsSet = existingAccount.getRoles().stream().map(userRole -> userRole.getRole().getId()).collect(Collectors.toSet());
        Set<Long> newSetOfRoleIdsSet = accountUpdateDTO.getRoleIds();

        Set<Long> rolesToBeDeletedSet = currentRoleIdsSet.stream()
                .filter(val -> !newSetOfRoleIdsSet.contains(val))
                .collect(Collectors.toSet());

        Set<Long> rolesToBeAddedSet = newSetOfRoleIdsSet.stream()
                .filter(val -> !currentRoleIdsSet.contains(val))
                .collect(Collectors.toSet());


        //add the new roles of the user
        for (Long roleId : rolesToBeAddedSet) {
            RoleImpl role = roleRepository.findById(roleId/*, Set.of(RoleFilter.RoleFacet.values())*/)
                    .orElseThrow(() -> new EntityNotFoundException(String.format("Role with id %s does not exist", roleId)));
            existingAccount.getRoles().add(new AccountRoleAssociation(existingAccount, role));
        }

        //delete removed roles from the user
        existingAccount.getRoles().removeIf(userRole -> rolesToBeDeletedSet.contains(userRole.getRole().getId()));


        AccountImpl updatedProduct = this.accountRepository.save(existingAccount);

        result = new Result<>(updatedProduct);
        return result;
    }


   // @Override
    public Optional<AccountImpl> loadById(Long id) {
        return accountRepository.loadById(id);
    }
}