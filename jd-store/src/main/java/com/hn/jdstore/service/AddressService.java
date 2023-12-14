package com.hn.jdstore.service;

import com.hn.jdstore.domain.entity.AddressDo;
import com.hn.jdstore.domain.vo.AddressVo;
import com.hn.jdstore.domain.vo.IPLocation;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface AddressService {


    void delete(AddressDo addressDo);

    Long update(AddressDo addressDo);

    AddressVo findById(Long id);

    List<AddressVo> getAddressList();

    IPLocation getIPLocation(HttpServletRequest request);

}
