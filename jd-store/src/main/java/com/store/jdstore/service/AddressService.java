package com.store.jdstore.service;

import com.store.jdstore.entity.HanmaAddressEntity;
import com.store.jdstore.model.AddressModel;

import java.util.List;

public interface AddressService {


    void delete(HanmaAddressEntity hanmaAddressEntity);

    Long update(HanmaAddressEntity hanmaAddressEntity);

    AddressModel findById(Long id);

    List<AddressModel> getAddressList();

}
