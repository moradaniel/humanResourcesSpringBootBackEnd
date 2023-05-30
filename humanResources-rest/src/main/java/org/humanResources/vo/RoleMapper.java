package org.humanResources.vo;

//import com.mobilenoc.api.domain.Role;
//import com.mobilenoc.api.entity.RoleJpaEntity;
//import com.mobilenoc.api.entity.UserRoleJpaEntity;
//import com.mobilenoc.api.port.in.CreateRoleCommand;
//import com.mobilenoc.api.port.out.ExposedRoleDto;
import org.humanResources.dto.RoleDetailsResponseDTO;
import org.humanResources.security.model.AccountRoleAssociation;
import org.humanResources.security.model.Role;
import org.mapstruct.*;

import java.util.Collection;


@Mapper(uses = {UserRoleMapper.class}, componentModel = "spring"/*, builder = @Builder(disableBuilder = true)*/)
public abstract class RoleMapper /*extends LazyLoadingAwareMapper*/ {
/*
    @Mappings({})
    public abstract Role mapToDomainEntity(RoleJpaEntity roleJpaEntity);

    @Mappings({@Mapping(target = "id", ignore = true),
            @Mapping(target = "userRoles", ignore = true)
    })
    public abstract Role mapToDomainEntity(CreateRoleCommand source);
*/
    /*@Mappings({})
    public abstract RoleJpaEntity mapToJpaEntity(Role role);*/

    @Mappings({
            @Mapping(target = "id", source = "source.id"),
            @Mapping(target = "name", source = "source.name"),
    })
    public abstract RoleDetailsResponseDTO mapToRoleDetailsResponseDTO(Role source);

    @Mappings({
            @Mapping(target = "id", source = "accountRoleAssociation.role.id"),
            @Mapping(target = "name", source = "accountRoleAssociation.role.name"),
    })
    public abstract RoleDetailsResponseDTO mapAccountRoleAssociationToRoleDetailsResponseDTO(AccountRoleAssociation accountRoleAssociation);



    /*
    public Role.RoleId toRoleId(final Long roleId) {
        return new Role.RoleId(roleId);
    }

    public Long toRoleId(final Role.RoleId roleId) {
        return roleId != null ? roleId.getValue() : null;
    }

    public Role.RoleName toRoleName(final String roleName) {
        return new Role.RoleName(roleName);
    }

    public String toRoleName(final Role.RoleName roleName) {
        return roleName.getValue();
    }

    @Condition
    public boolean isNotLazyLoadedUserRoles(final Collection<UserRoleJpaEntity> sourceCollection) {
        return isNotLazyLoaded(sourceCollection);
    }*/
}