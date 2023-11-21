package com.hn.common.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaginationData<T> {

    @Schema(description = "当前页码")
    private Integer currentPage;

    @Schema(description = "每页条数")
    private Integer pageSize;

    @Schema(description = "总条数")
    private Long total;

    @Schema(description = "响应的数据", accessMode = Schema.AccessMode.READ_ONLY)
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
