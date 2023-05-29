package org.humanResources.security.repository;

import org.humanResources.security.model.Account;
import org.humanResources.security.model.AccountImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class AccountRepositoryImpl /*extends QueryDslRepositorySupport*/ implements AccountRepositoryCustom {

    /*public AccountRepositoryImpl() {
        super(AccountImpl.class);
    }*/

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<AccountImpl> searchByFilter(AccountQueryFilter accountQueryFilter, Pageable pageable) {
  /*
        org.humanResources.security.entity.QAccountImpl account = org.humanResources.security.entity.QAccountImpl.accountImpl;
        QAccountRoleAssociation accountRoleAssociation = QAccountRoleAssociation.accountRoleAssociation;
        QRoleImpl role = QRoleImpl.roleImpl;

        JPQLQuery query = from(account)
                .leftJoin(account.roles,accountRoleAssociation)
                .leftJoin(account.roles.raccountRoleAssociation.role,role)
                .fetchJoin()
                //.where(QAccountImpl.accountImpl.officeCode.eq(officeCode));
                ;
        query = super.getQuerydsl().applyPagination(pageable, query);
        //QueryResults<AccountImpl> entities = query.fetchResults();
        List<AccountImpl> entities = query.fetch();
        return new PageImpl<AccountImpl>(entities, pageable, entities.size());
*/

        List<String> wheres = new ArrayList<>();
        List<String> paramNames = new ArrayList<>();
        List<Object> values = new ArrayList<>();

        String select = " Select distinct account ";
        String countSelect = " Select count(distinct account) ";

        StringBuffer queryBuilder = new StringBuffer();
        //queryBuilder.append(" Select account ");
        queryBuilder.append(" from AccountImpl account ");
        queryBuilder.append(" left join fetch account.roles roles ");
        queryBuilder.append(" left join fetch roles.role ");


        buildWhereClause(accountQueryFilter,wheres,paramNames,values);


        String queryWithoutOrdering = wheres.isEmpty() ? queryBuilder.toString() : queryBuilder.append(" WHERE ").append(
                org.humanResources.util.StringUtils.getStringsSeparatedBy(" AND ", wheres)).toString();


        String queryWithOrdering = queryWithoutOrdering;
        /*if (employmentQueryFilter != null && employmentQueryFilter.getOrderBy() != null) {
            queryWithOrdering += " ORDER BY LOWER(" + employmentQueryFilter.getOrderBy() + ")"
                    + (employmentQueryFilter.getOrderDirection().equals(OrderDirection.DESCENDING) ? " DESC " : " ASC ");
        } else {
            queryWithOrdering += " ORDER BY LOWER(person.apellidoNombre) ASC ";
        }*/



        return new PageImpl<AccountImpl>(
                RepositoryHelper.findByNamedParam(em,select+queryWithOrdering, paramNames, values, pageable != null ? new Long(pageable.getOffset()).intValue() : null, pageable != null ? pageable.getPageSize() : null),
                pageable,
                RepositoryHelper.countByNamedParam(em,countSelect+queryWithoutOrdering, paramNames, values)
                );

    }


    @Override
    public Optional<AccountImpl> loadByName(String name) {
        int page = 0;
        int size = 1;

        AccountQueryFilter productFilter = new AccountQueryFilter();
        productFilter.addNames(name);
        //productFilter.setFacets(Arrays.asList(ProductFilter.ProductFacet.values()));

        Page<AccountImpl> result = searchByFilter(productFilter,PageRequest.of(page,size));

        return result.getContent().stream().findFirst();
    }

    @Override
    public Optional<AccountImpl> loadById(Long id) {
        int page = 0;
        int size = 1;

        AccountQueryFilter productFilter = new AccountQueryFilter();
        productFilter.addIds(id);
        //productFilter.setFacets(Arrays.asList(ProductFilter.ProductFacet.values()));

        Page<AccountImpl> result = searchByFilter(productFilter,PageRequest.of(page,size));

        return result.getContent().stream().findFirst();
    }



    private void buildWhereClause(AccountQueryFilter accountQueryFilter,
                                  List<String> wheres, List<String> paramNames, List<Object> values) {
        if (accountQueryFilter != null) {

            List<String> names = accountQueryFilter.getNames();

            if(!CollectionUtils.isEmpty(names)){
                wheres.add("  account.name IN ( :names )  ");
                paramNames.add("names");
                values.add(names);
            }

            if (accountQueryFilter.getEnabled()!=null) {
                wheres.add("  account.enabled = :enabled ");
                paramNames.add("enabled");
                values.add(accountQueryFilter.getEnabled());
            }
        }
    }

}