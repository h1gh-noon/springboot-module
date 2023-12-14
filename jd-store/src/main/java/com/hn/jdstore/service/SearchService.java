package com.hn.jdstore.service;

import com.hn.jdstore.domain.vo.ShopVo;

import java.util.List;

public interface SearchService {

    List<ShopVo> searchAll(String content);

}
