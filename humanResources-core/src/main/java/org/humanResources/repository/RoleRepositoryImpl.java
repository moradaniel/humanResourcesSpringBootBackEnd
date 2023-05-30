package org.humanResources.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.humanResources.security.model.AccountImpl;
import org.humanResources.security.model.RoleImpl;
import org.humanResources.security.repository.AccountQueryFilter;
import org.humanResources.security.repository.RepositoryHelper;
import org.humanResources.security.repository.RoleQueryFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class RoleRepositoryImpl /*extends QueryDslRepositorySupport*/ implements RoleRepositoryCustom {

    /*public AccountRepositoryImpl() {
        super(AccountImpl.class);
    }*/

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<RoleImpl> searchByFilter(RoleQueryFilter roleQueryFilter, Pageable pageable) {
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

        String select = " Select distinct role ";
        String countSelect = " Select count(distinct role) ";

        StringBuffer queryBuilder = new StringBuffer();
        //queryBuilder.append(" Select account ");
        queryBuilder.append(" from RoleImpl role ");
        //queryBuilder.append(" left join fetch account.roles roles ");
        //queryBuilder.append(" left join fetch roles.role ");


        buildWhereClause(roleQueryFilter,wheres,paramNames,values);


        String queryWithoutOrdering = wheres.isEmpty() ? queryBuilder.toString() : queryBuilder.append(" WHERE ").append(
                org.humanResources.util.StringUtils.getStringsSeparatedBy(" AND ", wheres)).toString();


        String queryWithOrdering = queryWithoutOrdering;
        /*if (employmentQueryFilter != null && employmentQueryFilter.getOrderBy() != null) {
            queryWithOrdering += " ORDER BY LOWER(" + employmentQueryFilter.getOrderBy() + ")"
                    + (employmentQueryFilter.getOrderDirection().equals(OrderDirection.DESCENDING) ? " DESC " : " ASC ");
        } else {
            queryWithOrdering += " ORDER BY LOWER(person.apellidoNombre) ASC ";
        }*/



        return new PageImpl<RoleImpl>(
                RepositoryHelper.findByNamedParam(em,select+queryWithOrdering, paramNames, values, pageable != null ? Long.valueOf(pageable.getOffset()).intValue() : null, pageable != null ? pageable.getPageSize() : null),
                pageable,
                RepositoryHelper.countByNamedParam(em,countSelect+queryWithoutOrdering, paramNames, values)
                );

    }


    @Override
    public Optional<RoleImpl> loadByName(String name) {
        int page = 0;
        int size = 1;

        RoleQueryFilter roleFilter = new RoleQueryFilter();
        roleFilter.addNames(name);
        //productFilter.setFacets(Arrays.asList(ProductFilter.ProductFacet.values()));

        Page<RoleImpl> result = searchByFilter(roleFilter,PageRequest.of(page,size));

        return result.getContent().stream().findFirst();
    }

    @Override
    public Optional<RoleImpl> loadById(Long id) {
        int page = 0;
        int size = 1;

        RoleQueryFilter roleQueryFilter = new RoleQueryFilter();
        roleQueryFilter.addIds(id);
        //productFilter.setFacets(Arrays.asList(ProductFilter.ProductFacet.values()));

        Page<RoleImpl> result = searchByFilter(roleQueryFilter,PageRequest.of(page,size));

        return result.getContent().stream().findFirst();
    }



    private void buildWhereClause(RoleQueryFilter accountQueryFilter,
                                  List<String> wheres, List<String> paramNames, List<Object> values) {
        if (accountQueryFilter != null) {

          /*  List<String> names = accountQueryFilter.getNames();

            if(!CollectionUtils.isEmpty(names)){
                wheres.add("  account.name IN ( :names )  ");
                paramNames.add("names");
                values.add(names);
            }

            if (accountQueryFilter.getEnabled()!=null) {
                wheres.add("  account.enabled = :enabled ");
                paramNames.add("enabled");
                values.add(accountQueryFilter.getEnabled());
            }*/
        }
    }

}