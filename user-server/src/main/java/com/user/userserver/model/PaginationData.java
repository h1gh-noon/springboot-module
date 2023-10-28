package com.user.userserver.model;

import org.springframework.boot.context.properties.bind.DefaultValue;

public class PaginationData {


    private Integer currentPage;
    private Integer pageSize;
    private Long total;
    private Object data;

    public PaginationData() {
    }

    public PaginationData(Integer currentPage, Integer pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public PaginationData(Integer currentPage, Integer pageSize, Long total, Object data) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.total = total;
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
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

    @Override
    public String toString() {
        return "Pagination{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", data=" + data +
                '}';
    }
}
