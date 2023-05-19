package org.humanResources.dto;

import java.util.Set;

public class AccountDTO {

    private Long id;
    private String name;

    private Set<Long> roleIds;

    public AccountDTO(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
