package com.hn.jdstore.controller;

import com.hn.common.api.CommonResponse;
import com.hn.common.util.ResponseTool;
import com.hn.jdstore.model.SearchModel;
import com.hn.jdstore.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@Tag(name = "搜索接口")
public class SearchController {

    @Resource
    private SearchService searchService;

    @PostMapping("/searchAll")
    @Operation(summary = "添加商品分类")
    public CommonResponse<SearchModel> searchAll(@RequestParam @NotBlank String content) {
        return ResponseTool.getSuccessResponse(searchService.searchAll(content));
    }
}
