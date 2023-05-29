package org.humanResources.web;

import java.util.List;

public class SearchResponse<T> {

    private List<String> errors;
    private List<T> search;
    private SearchMeta meta;


    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
    public List<T> getSearch() {
        return search;
    }

    public void setSearch(List<T> search) {
        this.search = search;
    }

    public SearchMeta getMeta() {
        return meta;
    }

    public void setMeta(SearchMeta meta) {
        this.meta = meta;
    }

}
