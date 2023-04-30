package com.nick.bean;

public class Page {
    private Integer page;
    private Integer totalPage;
    private Integer pageSize;

    public Page(Integer page, Integer totalPage, Integer pageSize) {
        this.page = page;
        this.totalPage = totalPage;
        this.pageSize = pageSize;
    }

    public Page() {
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "Page{" +
                "page=" + page +
                ", totalPage=" + totalPage +
                ", pageSize=" + pageSize +
                '}';
    }
}
