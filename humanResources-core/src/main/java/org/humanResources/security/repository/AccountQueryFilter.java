package org.humanResources.security.repository;


import java.io.Serializable;


/**
 * DAO implementation agnostic class that can hold commons constraints to be used
 * by query DAO query methods. The class allows to keep informations useful to do
 * common things on object retrieval methods like multiple field results ordering,
 * paging, and very common by date filtering.
 *
 *
 */
public class AccountQueryFilter /*extends AbstractQueryFilter*/ implements Serializable {


    private static final long serialVersionUID = 1L;

    private String name;


    private Boolean enabled;


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

}
