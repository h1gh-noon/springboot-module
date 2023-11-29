package com.hn.jdstore.service;

import com.hn.jdstore.entity.HanmaAddressEntity;
import com.hn.jdstore.model.AddressModel;
import com.hn.jdstore.model.IPLocation;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface AddressService {


    void delete(HanmaAddressEntity hanmaAddressEntity);

    Long update(HanmaAddressEntity hanmaAddressEntity);

    AddressModel findById(Long id);

    List<AddressModel> getAddressList();

    IPLocation getIPLocation(HttpServletRequest request);

}
