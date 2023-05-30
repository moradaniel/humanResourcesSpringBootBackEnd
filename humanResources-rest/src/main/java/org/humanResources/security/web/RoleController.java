package org.humanResources.security.web;


//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiImplicitParams;

import jakarta.persistence.EntityNotFoundException;
import org.humanResources.dto.RoleDetailsResponseDTO;
import org.humanResources.security.model.RoleImpl;
import org.humanResources.security.repository.RoleQueryFilter;
import org.humanResources.security.service.RoleService;
import org.humanResources.vo.RoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RoleService roleService;


    @Autowired
    RoleMapper roleMapper;

  /*  @RequestMapping(value="/findByNameStartsWith",
                    method = RequestMethod.GET,
                    produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody
    Page<AccountImpl> findByNameStartsWith(@RequestParam("name") String name){
        return accountService.findByNameStartsWith(name);
    }
*/

/*    @RequestMapping(value="/findByFilter",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody
    Page<AccountImpl> findByFilter(@RequestParam("name") String name){

          *//*      Pageable pageRequest = new PageRequest(0, 1000);
        Page<Account> accounts = accountRepository.findByNameStartsWith(name,pageRequest);
*//*
        final PageRequest page = PageRequest.of(0, 20);

        AccountQueryFilter accountQueryFilter = new AccountQueryFilter();
        accountQueryFilter.addNames(name);

        return accountService.findByFilter(accountQueryFilter,page);
    }*/


    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody Page<RoleDetailsResponseDTO> findRoles() {

          /*      Pageable pageRequest = new PageRequest(0, 1000);
        Page<Account> accounts = accountRepository.findByNameStartsWith(name,pageRequest);
*/
        final PageRequest pageRequest = PageRequest.of(0, 20);

        RoleQueryFilter roleQueryFilter = new RoleQueryFilter();

        //return accountService.findByFilter(accountQueryFilter,page);

        Page<RoleImpl> roleSearchResult = roleService.findByFilter(roleQueryFilter, pageRequest);

        List<RoleDetailsResponseDTO> foundRolesDTOs = new ArrayList<>();

        for (RoleImpl role : roleSearchResult) {

            foundRolesDTOs.add(roleMapper.mapToRoleDetailsResponseDTO(role));
        }

        Page<RoleDetailsResponseDTO> result = new PageImpl<>(foundRolesDTOs, pageRequest,
                foundRolesDTOs.size());

        return result;

    }


    @GetMapping("/{id}")
    public ResponseEntity<RoleDetailsResponseDTO> getRole(final @PathVariable Long id) {
        logger.info("Attempting to get role with id:{}", id);

        RoleImpl role = roleService.loadById(id).orElseThrow(() -> new EntityNotFoundException("Not found role with id " + id));

        return ResponseEntity.ok(roleMapper.mapToRoleDetailsResponseDTO(role));
    }


//
//    @RequestMapping(value="/{id}",
//            method = RequestMethod.PUT,
//            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
//    @ResponseBody
//    public ResponseEntity<ApiResponse<AccountDTO>> updateAccount(/*@Valid*/ @RequestBody AccountDTO accountUpdateDTO, @PathVariable long id, BindingResult bindingResult){
//
//        ApiResponse<AccountDTO> apiResponse = new ApiResponse();
//
//        HttpStatus httpStatus = null;
//
//
//        if (bindingResult.hasErrors()) {
//            List<String> errors = new ArrayList<>();
//            for (ObjectError error : bindingResult.getAllErrors()) {
//                errors.add(error.getCode()+"-"+error.getDefaultMessage());
//            }
//
//            apiResponse.setErrors(errors);
//            httpStatus = HttpStatus.BAD_REQUEST;
//
//        }else{
//
//
//
//            //AccountImpl product = accountMapper.accountDTOToAccount(accountUpdateDTO);
//
//            //attempt to save the product
//            Result<AccountImpl> result = accountService.update(accountUpdateDTO);
//
//            if(result.isOk()){
///*                ProductFilter productFilter = new ProductFilter();
//                productFilter.addProductIds(result.getTarget().getId());
//
//                Long productId = result.getTarget().getId();
//                ProductDTO theUpdatedProduct = productService.loadProductDTOById(productId).orElseThrow(() -> new EntityNotFoundException("Not found product with id " + productId));
//*/
//
//
//                apiResponse.setResponse(roleMapper.accountToAccountDTO(result.getTarget()));
//                httpStatus = HttpStatus.OK;
//            }else {
//                apiResponse.setErrors(result.getErrors());
//                httpStatus = HttpStatus.BAD_REQUEST;
//            }
//
//        }
//
//
//        return new ResponseEntity<>(apiResponse, httpStatus);
//    }


    @RequestMapping(value = "",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody RoleDetailsResponseDTO saveAccount(@RequestBody RoleImpl role) {
        return roleMapper.mapToRoleDetailsResponseDTO(roleService.save(role));
    }


}