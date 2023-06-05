package org.humanResources.repository.asimio;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;


import java.io.Serializable;

@NoRepositoryBean
public interface AsimioJpaSpecificationExecutor<E, ID extends Serializable>
        extends JpaSpecificationExecutor<E> {

    Page<ID> findEntityIds(Pageable pageable);

    //Page<ID> findEntityIds(Specification<E> spec, Pageable pageable);

    //Page<ID> findEntityIds(NamedQueryParametersSpecification<E> parametrizedSpec, Pageable pageable);
}