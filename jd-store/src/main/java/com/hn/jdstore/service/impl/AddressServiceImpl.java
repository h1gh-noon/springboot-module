package com.hn.jdstore.service.impl;

import com.hn.jdstore.entity.HanmaAddressEntity;
import com.hn.jdstore.model.AddressModel;
import com.hn.jdstore.service.AddressService;
import com.hn.jdstore.dao.AddressDao;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {


    @Resource
    private AddressDao addressDao;

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
