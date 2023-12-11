package com.hn.jdstore.service;

import com.hn.jdstore.model.ShopModel;

import java.util.List;

public interface SearchService {

    List<ShopModel> searchAll(String content);

}
