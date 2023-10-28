package com.user.userserver.model;

public class Pagination {

    private Integer currentPage;
    private Integer pageSize;
    private Object data;

    public Pagination() {
    }

    public Pagination(Integer currentPage, Integer pageSize, Object data) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.data = data;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
