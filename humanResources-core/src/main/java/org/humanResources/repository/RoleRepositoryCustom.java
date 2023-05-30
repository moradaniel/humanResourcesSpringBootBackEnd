package org.humanResources.repository;

import org.humanResources.security.model.RoleImpl;
import org.humanResources.security.repository.RoleQueryFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface RoleRepositoryCustom {

    Page<RoleImpl> searchByFilter(RoleQueryFilter roleQueryFilter, Pageable pageable);

    Optional<RoleImpl> loadByName(String name);

    Optional<RoleImpl> loadById(Long id);

}
