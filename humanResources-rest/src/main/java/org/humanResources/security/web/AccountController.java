package org.humanResources.security.web;


//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiImplicitParams;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.humanResources.dto.AccountDTO;
import org.humanResources.dto.AccountDetailsResponseDTO;
import org.humanResources.dto.AccountSearchResponseDTO;
import org.humanResources.security.model.AccountImpl;
import org.humanResources.security.repository.AccountQueryFilter;
import org.humanResources.security.service.AccountService;
import org.humanResources.util.Result;
import org.humanResources.vo.AccountMapper;
import org.humanResources.vo.ApiResponse;
import org.humanResources.web.SearchMeta;
import org.humanResources.web.SearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AccountService accountService;


    @Autowired
    AccountMapper accountMapper;

    @RequestMapping(value="/findByNameStartsWith",
                    method = RequestMethod.GET,
                    produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody
    Page<AccountImpl> findByNameStartsWith(@RequestParam("name") String name){
        return accountService.findByNameStartsWith(name);
    }


    @RequestMapping(value="/findByFilter",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody
    Page<AccountImpl> findByFilter(@RequestParam("name") String name){

          /*      Pageable pageRequest = new PageRequest(0, 1000);
        Page<Account> accounts = accountRepository.findByNameStartsWith(name,pageRequest);
*/
        final PageRequest page = PageRequest.of(0, 20);

        AccountQueryFilter accountQueryFilter = new AccountQueryFilter();
        accountQueryFilter.addNames(name);

        return accountService.findByFilter(accountQueryFilter,page);
    }

    @GetMapping(value = "", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody Page<AccountDTO> findAccounts(
            @RequestParam(value="pageNumber", required=false, defaultValue = "1") Integer pageNumber,//pageNumber requested
            //@RequestParam(value="count", required=false) Integer pageSize,//number of rows requested (pagesize)
            @RequestParam(value="pageSize", required=false, defaultValue = "10") Integer pageSize//,number of rows requested (pagesize)
    ){

        if(pageNumber > 0){
            pageNumber = pageNumber - 1;  //convert 1 index based to 0 based by decrementing 1
        }else{
            pageNumber = 0;
        }

        if(pageSize > 100){
            pageSize = 100;
        }


          /*      Pageable pageRequest = new PageRequest(0, 1000);
        Page<Account> accounts = accountRepository.findByNameStartsWith(name,pageRequest);
*/
        final PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        AccountQueryFilter accountQueryFilter = new AccountQueryFilter();

        //return accountService.findByFilter(accountQueryFilter,page);

        Page<AccountImpl> accountSearchResult = accountService.findByFilter3(accountQueryFilter, pageRequest);

        List<AccountDTO> foundAccountsDTOs = new ArrayList<>();

        for(AccountImpl account:accountSearchResult){

            foundAccountsDTOs.add(accountMapper.accountToAccountDTO(account));
        }

        Page<AccountDTO> result =  new PageImpl<>(foundAccountsDTOs, pageRequest,
                accountSearchResult.getTotalElements());

        return result;

    }


    @GetMapping("/{id}")
    public ResponseEntity<AccountDetailsResponseDTO> getAccount(final @PathVariable Long id) {
        logger.info("Attempting to get account with id:{}", id);

        AccountImpl account = accountService.loadById(id).orElseThrow(() -> new EntityNotFoundException("Not found account with id " + id));

        return ResponseEntity.ok(accountMapper.accountToAccountDetailsResponseDTO(account));
    }



    @RequestMapping(value="/{id}",
            method = RequestMethod.PUT,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public ResponseEntity<ApiResponse<AccountDTO>> updateAccount(/*@Valid*/ @RequestBody AccountDTO accountUpdateDTO, @PathVariable long id, BindingResult bindingResult){

        ApiResponse<AccountDTO> apiResponse = new ApiResponse();

        HttpStatus httpStatus = null;


        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errors.add(error.getCode()+"-"+error.getDefaultMessage());
            }

            apiResponse.setErrors(errors);
            httpStatus = HttpStatus.BAD_REQUEST;

        }else{



            //AccountImpl product = accountMapper.accountDTOToAccount(accountUpdateDTO);

            //attempt to save the product
            Result<AccountImpl> result = accountService.update(accountUpdateDTO);

            if(result.isOk()){
/*                ProductFilter productFilter = new ProductFilter();
                productFilter.addProductIds(result.getTarget().getId());

                Long productId = result.getTarget().getId();
                ProductDTO theUpdatedProduct = productService.loadProductDTOById(productId).orElseThrow(() -> new EntityNotFoundException("Not found product with id " + productId));
*/


                apiResponse.setResponse(accountMapper.accountToAccountDTO(result.getTarget()));
                httpStatus = HttpStatus.OK;
            }else {
                apiResponse.setErrors(result.getErrors());
                httpStatus = HttpStatus.BAD_REQUEST;
            }

        }


        return new ResponseEntity<>(apiResponse, httpStatus);
    }



    /**
     *   /api/search/accounts.json?category_ids%5B%5D=11&category_ids=15&category_ids=8size=2&page=0&sort=name,asc
     *
     * https://stackoverflow.com/questions/35404329/swagger-documentation-for-spring-pageable-interface
     */
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "page", dataType = "string", paramType = "query",
//                    value = "Results page index you want to retrieve (1..N)"),
//            @ApiImplicitParam(name = "per_page", dataType = "string", paramType = "query",
//                    value = "Number of records per page. 50 maximum."),
//            @ApiImplicitParam(name = "order_by", dataType = "string", paramType = "query",
//                    value = ""),
//            @ApiImplicitParam(name = "sort_by", dataType = "string", paramType = "query",
//                    value = ""),
//            /*,
//            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
//                    value = "Sorting criteria in the format: property(,asc|desc). " +
//                            "Default sort order is ascending. " +
//                            "Multiple sort criteria are supported.")*/
//            //https://localhost:8443/api/search/products.json?order_by=products.created_at&per_page=4&sort_by=desc
//    })
    @RequestMapping(value="/api/accounts2", ///search/accounts
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<SearchResponse<AccountSearchResponseDTO>> findAccountsByCriteriaPaginated(
            AccountQueryFilter accountQueryFilterDTO,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer webPageIndex,
            @RequestParam(value = "per_page", required = false, defaultValue = "9") Integer pageSize,
            @RequestParam(value = "order_by", required = false, defaultValue = "") String order_by,
            @RequestParam(value = "sort_by", required = false, defaultValue = "") String sort_by
            /*,
            @RequestParam(value = "sort", required = false, defaultValue = "name,asc") String[] sort*/){

        //TODO move this to AppConfig
        //the web page index is 1 based, but the repository layer is 0 based
        int webPageIndexBased = 1;

/*
        if(productFilterDTO == null ||
                (
                        StringUtils.isEmpty(productFilterDTO.getQuery()) &&
                                StringUtils.isEmpty(productFilterDTO.getProduct_name()) &&
                                CollectionUtils.isEmpty(productFilterDTO.getCategory_ids()) &&
                                CollectionUtils.isEmpty(productFilterDTO.getProduct_ids()) &&
                                CollectionUtils.isEmpty(productFilterDTO.getProducer_ids()) &&
                                CollectionUtils.isEmpty(productFilterDTO.getCertification_ids())
                )
        ) {
            //build default search result by ordering descending by create date
            order_by = "products.created_at";
            sort_by = "desc";
        }*/

        if(webPageIndex < webPageIndexBased){
            webPageIndex = webPageIndexBased;
        }
        if(pageSize > 50){
            pageSize = 50;
        }
        //convert 1 based to 0 based by decrementing 1
        int repositoryPageIndex = webPageIndex - 1;

        List<Sort.Order> orders = new ArrayList<>();

        //TODO implement sorting
        /*for (String propOrder: sort) {
            String[] propOrderSplit = propOrder.split(",");
            String property = propOrderSplit[0];
            if (propOrderSplit.length == 1) {
                orders.add(new Sort.Order(property));
            } else {
                Sort.Direction direction
                        = Sort.Direction.fromStringOrNull(propOrderSplit[1]);
                orders.add(new Sort.Order(direction, property));
            }
        }*/

        Pageable pageRequest = PageRequest.of(repositoryPageIndex, pageSize,
                orders.isEmpty() ? null : Sort.by(orders));


 //       ProductFilter productFilter = productFilterMapper.productFilterDTOToProductFilter(productFilterDTO);
 //       productFilter.setOrder_by(order_by);
 //       productFilter.setSort_by(sort_by);


        //productFilter.addFacets(getSearchProductsFacets());

        Page<AccountImpl> productSearchResult = accountService.findByFilter(accountQueryFilterDTO, pageRequest);

        List<AccountDTO> productSearchResponseDTOs = new ArrayList<>();


        //--------------------------------------------
       // Page<AccountImpl> foundProducts = accountRepository.searchByFilter(accountQueryFilter, pageable);

        List<AccountDTO> foundProductsDTOs = new ArrayList<>();

        for(AccountImpl product:productSearchResult){


           // if(Boolean.TRUE == product.getHidden() && !isProductVisibleByUser(product,userService.getCurrentUser())){
           //     continue;
            //}

            foundProductsDTOs.add(accountMapper.accountToAccountDTO(product));
        }

        Page<AccountDTO> result =  new PageImpl<>(foundProductsDTOs, pageRequest,
                foundProductsDTOs.size());


        //--------------------------------------------


        HttpStatus httpStatus = null;

        SearchResponse searchResponse = new SearchResponse();


        //if(!CollectionUtils.isEmpty(productSearchResult.getContent())){
            SearchMeta meta = new SearchMeta(webPageIndex,productSearchResult.getTotalPages(),pageSize);
            // get the business roles and address
            /*for(ProductSearchResponseDTO productSearchResponseDTO: productSearchResult) {

                productSearchResponseDTOs.add(productSearchResponseDTO);
            }*/
            searchResponse.setSearch(result.getContent());
            searchResponse.setMeta(meta);
        /*}else {

            //TODO implement this
            List<String> errors = new ArrayList<>();
            errors.add("The search did not give any results, returning recently added products.");
            searchResponse.setErrors(errors);
        }*/


        if(searchResponse == null){
            httpStatus = HttpStatus.BAD_REQUEST;
        }else{
            httpStatus = HttpStatus.OK;
        }
        return new ResponseEntity<>(searchResponse, httpStatus);

    }






        @RequestMapping(value="",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public @ResponseBody
    AccountImpl saveAccount(@RequestBody AccountImpl account){
        account.setPassword("test");
        return accountService.save(account);
    }




}