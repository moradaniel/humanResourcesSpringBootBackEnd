package org.humanResources.security.repository;




import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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

    private List<String> names = new ArrayList<>();

    private String name;


    private Boolean enabled;


    /*
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }*/

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }


    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        if(CollectionUtils.isNotEmpty(names)) {
            this.names.clear();
            this.names.addAll(names);
        }
    }

    public void addNames(String ... names) {
        this.names.addAll(Arrays.asList(names));
    }

}
