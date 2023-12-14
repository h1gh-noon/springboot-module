package com.hn.jdstore.service.impl;

import com.alibaba.fastjson2.JSON;
import com.hn.jdstore.dao.SearchDao;
import com.hn.jdstore.domain.dto.ProductDto;
import com.hn.jdstore.domain.entity.ShopDo;
import com.hn.jdstore.domain.vo.ProductVo;
import com.hn.jdstore.domain.vo.ShopVo;
import com.hn.jdstore.service.ProductService;
import com.hn.jdstore.service.SearchService;
import com.hn.jdstore.service.ShopService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SearchServiceImpl implements SearchService {

    @Resource
    private ShopService shopService;

    @Resource
    private ProductService productService;

    @Resource
    private SearchDao searchDao;


    @Override
    public List<ShopVo> searchAll(String content) {

        Page<ShopDo> shopPageList = shopService.searchShopByNamePageList(1, 30, content);
        List<ShopVo> shopVos = shopPageList.getContent().stream().map(e -> {
            ShopVo sm = new ShopVo();
            BeanUtils.copyProperties(e, sm);
            return sm;
        }).toList();
        Set<ShopVo> resultShopVo = new HashSet<>(shopVos);

        List<Map<String, Object>> list = searchDao.searchAll("%" + content + "%");
        List<ProductDto> productDtos = JSON.parseArray(JSON.toJSONString(list), ProductDto.class);
        productDtos.forEach(e -> {
            ShopVo shopVo =
                    resultShopVo.stream().filter(s -> Objects.equals(s.getId(), e.getShopId())).findFirst().orElse(null);
            ProductVo pm = new ProductVo();
            BeanUtils.copyProperties(e, pm);
            if (shopVo == null) {
                ShopVo sm = new ShopVo();
                sm.setId(e.getShopId());
                sm.setName(e.getShopName());
                sm.setState(e.getShopState());
                sm.setDescDetail(e.getShopDescDetail());
                sm.setExpressLimit(e.getShopExpressLimit());
                sm.setExpressPrice(e.getShopExpressPrice());
                sm.setImgUrl(e.getShopImg());
                List<ProductVo> pList = new ArrayList<>();
                pList.add(pm);
                sm.setProductList(pList);
                resultShopVo.add(sm);
            } else if (shopVo.getProductList() == null) {
                List<ProductVo> pList = new ArrayList<>();
                pList.add(pm);
                shopVo.setProductList(pList);
            } else {
                shopVo.getProductList().add(pm);
            }
        });

        return resultShopVo.stream().toList();
    }
}
