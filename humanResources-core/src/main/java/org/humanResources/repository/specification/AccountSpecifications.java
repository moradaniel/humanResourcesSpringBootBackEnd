package org.humanResources.repository.specification;

import org.humanResources.security.model.AccountImpl;
import org.humanResources.security.model.AccountImpl_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import java.util.Set;

public final class AccountSpecifications {

    public static Specification<AccountImpl> idIn(Set<Long> filmIds) {
        if (CollectionUtils.isEmpty(filmIds)) {
            return null;
        }
        return (root, query, builder) -> root.get(AccountImpl_.id).in(filmIds);
    }
}