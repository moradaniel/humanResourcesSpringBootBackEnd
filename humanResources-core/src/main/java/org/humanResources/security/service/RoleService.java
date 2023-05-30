package org.humanResources.security.service;


import jakarta.persistence.EntityNotFoundException;
import org.humanResources.dto.AccountDTO;
import org.humanResources.repository.RoleRepository;
import org.humanResources.security.model.AccountImpl;
import org.humanResources.security.model.AccountRoleAssociation;
import org.humanResources.security.model.Role;
import org.humanResources.security.model.RoleImpl;
import org.humanResources.security.repository.AccountQueryFilter;
import org.humanResources.security.repository.RoleQueryFilter;
import org.humanResources.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

//@Service
public class RoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);


    //AccountRepository accountRepository;
    RoleRepository roleRepository;

    //PasswordEncoder passwordEncoder;


    public RoleService(RoleRepository roleRepository) {

        this.roleRepository = roleRepository;

    }


//    @Transactional
//    public Page<AccountImpl> findByNameStartsWith(String name){
//
//  /*      Pageable pageRequest = new PageRequest(0, 1000);
//        Page<Account> accounts = accountRepository.findByNameStartsWith(name,pageRequest);
//*/
//
//
//        final PageRequest pageRequest = PageRequest.of(0, 20);
//
//        Page<AccountImpl> accounts = accountRepository.findAll(pageRequest);
//
///*        Predicate accountPredicate = AccountPredicates.firstOrLastNameStartsWith(name);
//        Page<Account> accounts = accountRepository.findAll(accountPredicate,page1);
//*/
//        return accounts;
//    }


    @Transactional
    public Role findById(Long id) {

        //Account account = accountRepository.findOne(id);
        Role role = roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found account with id " + id));

        return role;
    }

    @Transactional
    public RoleImpl findByName(String name) {

        RoleImpl role = roleRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Not found account with name " + name));

        return role;
    }

    @Transactional
    public Page<RoleImpl> findByFilter(RoleQueryFilter roleQueryFilter, Pageable pageable) {
        return roleRepository.searchByFilter(roleQueryFilter, pageable);
    }

    @Transactional
    public Page<RoleImpl> findByCriteria(RoleQueryFilter roleQueryFilter, Pageable pageable) {

        logger.info("attempting to find accounts by criteria");

        //  accountQueryFilter.setCurrentUserId(userService.getCurrentUser().getUserId());

        Page<RoleImpl> foundRoles = roleRepository.searchByFilter(roleQueryFilter, pageable);

        /*List<AccountSearchResponseDTO> foundProductsDTOs = new ArrayList<>();

        for(AccountImpl product:foundProducts){


           // if(Boolean.TRUE == product.getHidden() && !isProductVisibleByUser(product,userService.getCurrentUser())){
           //     continue;
            //}

            foundProductsDTOs.add(productMapper.productToProductSearchResponseDTO(product));
        }

        Page<ProductSearchResponseDTO> result =  new PageImpl<>(foundProductsDTOs, pageable,
                foundProducts.getTotalElements());*/
        return foundRoles;

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
    public RoleImpl save(RoleImpl role) {
        role = this.roleRepository.save(role);
        return role;
    }

    /*
    @Transactional
    public Result<AccountImpl> update(final AccountDTO accountUpdateDTO) {
        logger.info("Attempting to update role with name: {}", accountUpdateDTO.getName());


        //validate product creation request
        DataBinder dataBinder = new DataBinder(accountUpdateDTO);
        // TODO dataBinder.addValidators(new ProductUpdateOperationValidator(productRepository));
        dataBinder.validate();

        Result<AccountImpl> result;

        if (dataBinder.getBindingResult().hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (ObjectError error : dataBinder.getBindingResult().getAllErrors()) {
                errors.add(error.getCode() + "-" + error.getDefaultMessage());
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
            RoleImpl role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new EntityNotFoundException(String.format("Role with id %s does not exist", roleId)));
            existingAccount.getRoles().add(new AccountRoleAssociation(existingAccount, role));
        }

        //delete removed roles from the user
        existingAccount.getRoles().removeIf(userRole -> rolesToBeDeletedSet.contains(userRole.getRole().getId()));


        AccountImpl updatedProduct = this.accountRepository.save(existingAccount);

        result = new Result<>(updatedProduct);
        return result;
    }*/


    //@Override
    public Optional<RoleImpl> loadById(Long id) {
        return roleRepository.loadById(id);
    }
}