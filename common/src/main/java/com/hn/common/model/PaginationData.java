package com.hn.common.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PaginationData<T> {

    private Integer currentPage = 1;
    private Integer pageSize = 10;
    private Long total;
    private T data;
    private List<T> list;


    public PaginationData(Integer currentPage, Integer pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public PaginationData(Integer currentPage, Integer pageSize, Long total, T data) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.total = total;
        this.data = data;
    }

}
