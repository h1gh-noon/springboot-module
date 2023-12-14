package com.hn.jdstore.service;

import com.hn.jdstore.domain.entity.HanmaAddressDo;
import com.hn.jdstore.domain.vo.AddressVo;
import com.hn.jdstore.domain.vo.IPLocation;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface AddressService {


    void delete(HanmaAddressDo hanmaAddressDo);

    Long update(HanmaAddressDo hanmaAddressDo);

    AddressVo findById(Long id);

    List<AddressVo> getAddressList();

    IPLocation getIPLocation(HttpServletRequest request);

}
