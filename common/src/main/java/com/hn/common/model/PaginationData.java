package com.hn.common.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaginationData<T> {

    private Integer currentPage;
    private Integer pageSize;
    private Long total;
    private T data;


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
