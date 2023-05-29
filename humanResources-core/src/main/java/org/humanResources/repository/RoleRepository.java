package org.humanResources.repository;

import org.humanResources.security.model.RoleImpl;
import org.humanResources.security.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<RoleImpl, Long> {
    Optional<RoleImpl> findByName(RoleName roleName);

    Optional<RoleImpl> findById(Long id);
}