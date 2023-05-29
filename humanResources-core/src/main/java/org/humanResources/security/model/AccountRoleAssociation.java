package org.humanResources.security.model;

import org.humanResources.persistence.PersistentAbstract;

import jakarta.persistence.*;

@Entity
@Table(name = "SEC_ACCOUNT_ROLE")
@SequenceGenerator(name="SEQ_GEN", sequenceName="SEC_ACCOUNT_ROLE_SEQ", allocationSize=1)
public class AccountRoleAssociation extends PersistentAbstract implements java.io.Serializable {

    @ManyToOne()
    @JoinColumn(name="ACCOUNTID", nullable=false, updatable=false)
    private AccountImpl account;


    @ManyToOne()
    @JoinColumn(name="ROLEID", nullable=false, updatable=false)
    private RoleImpl role;



    @Column(name = "SORTORDER", nullable = false)
    private Integer sortOrder = 1;

    public AccountRoleAssociation() {
    }

    public AccountRoleAssociation(AccountImpl account, RoleImpl role) {
        this.account = account;
        this.role = role;
    }


    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }



    public AccountImpl getAccount() {
        return account;
    }



    public void setAccount(AccountImpl account) {
        this.account = account;
    }

    public RoleImpl getRole() {
        return role;
    }

    public void setRole(RoleImpl role) {
        this.role = role;
    }



}