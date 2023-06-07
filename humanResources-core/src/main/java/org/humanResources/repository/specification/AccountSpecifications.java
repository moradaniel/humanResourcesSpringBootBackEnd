package org.humanResources.repository.specification;

import org.humanResources.security.model.AccountImpl;
import org.humanResources.security.model.AccountImpl_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import java.util.Set;

public final class AccountSpecifications {

    public static Specification<AccountImpl> idIn(Set<Long> accountIds) {
        if (CollectionUtils.isEmpty(accountIds)) {
            return null;
        }
        return (root, query, builder) -> root.get(AccountImpl_.id).in(accountIds);
    }

    public static Specification<AccountImpl> idEquals(Long id) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get(AccountImpl_.ID), id);
    }
}