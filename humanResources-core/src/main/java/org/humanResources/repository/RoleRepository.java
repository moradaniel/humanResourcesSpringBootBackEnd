package org.humanResources.repository;

import org.humanResources.security.model.RoleImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository("roleRepository")
public interface RoleRepository extends RoleRepositoryCustom,JpaRepository<RoleImpl, Long> {
    Optional<RoleImpl> findByName(String roleName);

    Optional<RoleImpl> findById(Long id);
}