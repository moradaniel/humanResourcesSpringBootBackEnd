package org.humanResources.repository;

import org.humanResources.security.model.Role;
import org.humanResources.security.model.RoleName;

import java.util.Optional;


//@Repository
public interface RoleRepository /*extends JpaRepository<Role, Long> */{
    Optional<Role> findByName(RoleName roleName);
}