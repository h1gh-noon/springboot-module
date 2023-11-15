package com.hn.jdstore.service;

import com.hn.jdstore.entity.HanmaAddressEntity;
import com.hn.jdstore.model.AddressModel;

import java.util.List;

public interface AddressService {


    void delete(HanmaAddressEntity hanmaAddressEntity);

    Long update(HanmaAddressEntity hanmaAddressEntity);

    AddressModel findById(Long id);

    List<AddressModel> getAddressList();

}
