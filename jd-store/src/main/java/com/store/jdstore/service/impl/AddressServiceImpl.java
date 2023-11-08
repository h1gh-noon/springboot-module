package com.store.jdstore.service.impl;

import com.store.jdstore.dao.AddressDao;
import com.store.jdstore.entity.HanmaAddressEntity;
import com.store.jdstore.model.AddressModel;
import com.store.jdstore.service.AddressService;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
public class AddressServiceImpl implements AddressService {


    private AddressDao addressDao;

    public AddressServiceImpl(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    @Override
    public void delete(HanmaAddressEntity hanmaAddress) {
        addressDao.delete(hanmaAddress);
    }

    @Override
    public Long update(HanmaAddressEntity hanmaAddress) {
        return addressDao.save(hanmaAddress).getId();
    }

    @Override
    public AddressModel findById(Long id) {
        HanmaAddressEntity hanmaAddressEntity = addressDao.findById(id).orElse(null);
        if (hanmaAddressEntity == null) {
            return null;
        }
        AddressModel hanmaAddressModel = new AddressModel();
        BeanUtils.copyProperties(hanmaAddressEntity, hanmaAddressModel);
        return hanmaAddressModel;
    }

    @Override
    public List<AddressModel> getAddressList() {
        return addressDao.findAll().stream().map(e -> {
            AddressModel h = new AddressModel();
            BeanUtils.copyProperties(e, h);
            return h;
        }).collect(Collectors.toList());
    }

}
