package com.hn.jdstore.service.impl;

import com.hn.jdstore.dao.AddressDao;
import com.hn.jdstore.domain.entity.AddressDo;
import com.hn.jdstore.domain.vo.AddressVo;
import com.hn.jdstore.domain.vo.IPLocation;
import com.hn.jdstore.service.AddressService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AddressServiceImpl implements AddressService {

    @Value("${baidu_api.map.access_token}")
    private String accToken;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private AddressDao addressDao;

    @Override
    public void delete(AddressDo addressDo) {
        addressDao.delete(addressDo);
    }

    @Override
    public Long update(AddressDo addressDo) {
        return addressDao.save(addressDo).getId();
    }

    @Override
    public AddressVo findById(Long id) {
        AddressDo addressDo = addressDao.findById(id).orElse(null);
        if (addressDo == null) {
            return null;
        }
        AddressVo addressDoVo = new AddressVo();
        BeanUtils.copyProperties(addressDo, addressDoVo);
        return addressDoVo;
    }

    @Override
    public List<AddressVo> getAddressList() {
        return addressDao.findAll().stream().map(e -> {
            AddressVo h = new AddressVo();
            BeanUtils.copyProperties(e, h);
            return h;
        }).collect(Collectors.toList());
    }

    @Override
    public IPLocation getIPLocation(HttpServletRequest request) {
        // String ip = IPUtil.getRequestIp(request);
        String ip = "60.216.71.178";
        String url = "https://api.map.baidu.com/location/ip?ip=" + ip + "&coor=bd09ll&ak=" + accToken;
        IPLocation ipLocation = restTemplate.getForObject(url, IPLocation.class);
        log.info("百度ip定位信息={}", ipLocation);
        return ipLocation;
    }

}
