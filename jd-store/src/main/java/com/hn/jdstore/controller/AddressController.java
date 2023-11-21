package com.hn.jdstore.controller;

import com.hn.common.api.CommonResponse;
import com.hn.jdstore.entity.HanmaAddressEntity;
import com.hn.jdstore.model.AddressModel;
import com.hn.jdstore.service.AddressService;
import com.hn.common.util.ResponseTool;
import com.hn.common.util.Util;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Controller
@RequestMapping("/address")
@Slf4j
public class AddressController {

    @Resource
    private AddressService addressService;

    @PostMapping("/addressAdd")
    public CommonResponse<Long> addressAdd(@RequestBody HanmaAddressEntity hanmaAddress) {
        hanmaAddress.setId(null);
        String time = Util.getTimestampStr();
        hanmaAddress.setCreateTime(time);
        hanmaAddress.setUpdateTime(time);
        return ResponseTool.getSuccessResponse(addressService.update(hanmaAddress));
    }

    @PostMapping("/addressDelete")
    public CommonResponse<Long> addressDelete(@RequestBody HanmaAddressEntity hanmaAddress) {
        if (hanmaAddress.getId() == null) {
            return ResponseTool.getErrorResponse();
        }
        return ResponseTool.getSuccessResponse(addressService.update(hanmaAddress));
    }

    @PostMapping("/addressUpdate")
    public CommonResponse<Long> addressUpdate(@RequestBody HanmaAddressEntity hanmaAddress) {
        hanmaAddress.setCreateTime(null);
        hanmaAddress.setUpdateTime(Util.getTimestampStr());
        return ResponseTool.getSuccessResponse(addressService.update(hanmaAddress));
    }

    @PostMapping("/getAddressById")
    public CommonResponse<AddressModel> getAddressById(@RequestBody HanmaAddressEntity hanmaAddress) {
        if (hanmaAddress.getId() == null) {
            return ResponseTool.getErrorResponse();
        }

        AddressModel addressModel = new AddressModel();
        BeanUtils.copyProperties(addressService.findById(hanmaAddress.getId()), addressModel);
        return ResponseTool.getSuccessResponse(addressModel);
    }

    @RequestMapping("/getAddressList")
    public CommonResponse<List<AddressModel>> getAddressInfoProductList() {
        return ResponseTool.getSuccessResponse(addressService.getAddressList());
    }
}
