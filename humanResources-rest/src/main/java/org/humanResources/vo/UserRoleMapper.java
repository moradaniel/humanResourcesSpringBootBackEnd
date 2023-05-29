package org.humanResources.vo;

//import com.mobilenoc.api.domain.UserRole;
//import com.mobilenoc.api.entity.UserRoleJpaEntity;
//import com.mobilenoc.api.port.out.ExposedUserRoleDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(uses = {AccountMapper.class, RoleMapper.class}, componentModel = "spring" /*, builder = @Builder(disableBuilder = true)*/)
public abstract class UserRoleMapper /*extends LazyLoadingAwareMapper*/ {

    /*
    @Mappings({
            @Mapping(target = "user.isAccountNonExpired", ignore = true),
            @Mapping(target = "user.isAccountNonLocked", ignore = true),
            @Mapping(target = "user.isCredentialsNonExpired", ignore = true),
            @Mapping(target = "user.isEnabled", ignore = true),
            @Mapping(target = "user.userRoles", ignore = true),
            @Mapping(target = "user.userGroups", ignore = true),
            @Mapping(target = "role.userRoles", ignore = true)
    })

    public abstract UserRole mapToDomainEntity(UserRoleJpaEntity userRoleJpaEntity);

    @Mappings({
            @Mapping(target = "user.userGroups", ignore = true),
            @Mapping(target = "user.isAdmin", ignore = true),
            @Mapping(target = "user.password", ignore = true),
            @Mapping(target = "user.groups", ignore = true)
    })
    public abstract UserRoleJpaEntity mapToJpaEntity(UserRole userRole);
*/
    /*@Mappings({})
    public abstract ExposedUserRoleDto mapTo(UserRole userRole);*/

    /*
    public UserRole.UserRoleId toUserRoleId(final Long userRoleId) {
        return new UserRole.UserRoleId(userRoleId);
    }

    public Long toUserRoleId(final UserRole.UserRoleId userRoleId) {
        return userRoleId != null ? userRoleId.getValue() : null;
    }*/

}