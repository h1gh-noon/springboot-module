package com.hn.jdstore.service.impl;

import com.hn.common.util.IPUtil;
import com.hn.jdstore.dao.AddressDao;
import com.hn.jdstore.entity.HanmaAddressEntity;
import com.hn.jdstore.model.AddressModel;
import com.hn.jdstore.model.IPLocation;
import com.hn.jdstore.service.AddressService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    @Value("${baidu_api.map.access_token}")
    private String accToken;

    @Resource
    private RestTemplate restTemplate;

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

    @Override
    public IPLocation getIPLocation(HttpServletRequest request) {
        String ip = IPUtil.getRequestIp(request);
        String url = "https://api.map.baidu.com/location/ip?ip=" + ip + "&coor=bd09ll&ak=" + accToken;
        return restTemplate.getForObject(url, IPLocation.class);
    }

}
