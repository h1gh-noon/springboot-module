package com.store.jdstore.model;

import lombok.Data;

import java.util.List;

@Data
public class Pagination<T> {

    private Integer currentPage = 1;
    private Integer pageSize = 10;

    private Long total;

    private List<T> list;

    public Pagination() {
    }

    public Pagination(Integer currentPage, Integer pageSize, Long total, List<T> list) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.total = total;
        this.list = list;
    }
}
