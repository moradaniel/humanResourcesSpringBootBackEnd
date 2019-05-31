package org.humanResources.security.model;


import org.humanResources.persistence.Persistent;

import java.util.List;

public interface Account extends Persistent {
    public void setName(String name);

    public String getName();

    public String getUsername();

    public void setUsername(String name);

    public String getPassword();

    public void setPassword(String password);

    public List<AccountRoleAssociation> getRoles();

    public void setRoles(List<AccountRoleAssociation> roles);

    public void addRole(AccountRoleAssociation accountRoleAssociation);

}
