package org.humanResources.web;

public class SearchMeta {

    private int page;
    private int page_count;
    private int per_page;

    public SearchMeta() {
    }

    public SearchMeta(int page, int page_count, int per_page) {
        this.page = page;
        this.page_count = page_count;
        this.per_page = per_page;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }
}
