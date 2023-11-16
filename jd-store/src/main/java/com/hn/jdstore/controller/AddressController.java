package com.hn.jdstore.controller;

import com.hn.common.exceptions.TemplateException;
import com.hn.jdstore.entity.HanmaAddressEntity;
import com.hn.jdstore.enums.ExceptionMsgEnum;
import com.hn.jdstore.exception.SelfException;
import com.hn.jdstore.model.AddressModel;
import com.hn.jdstore.model.CommonResponse;
import com.hn.jdstore.service.AddressService;
import com.hn.jdstore.util.ResponseUtil;
import com.hn.jdstore.util.Utils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
@RequestMapping("/address")
@Slf4j
public class AddressController {

    @Resource
    private AddressService addressService;

    @PostMapping("/addressAdd")
    public CommonResponse addressAdd(@RequestBody HanmaAddressEntity hanmaAddress) {
        hanmaAddress.setId(null);
        String time = Utils.getTimestampStr();
        hanmaAddress.setCreateTime(time);
        hanmaAddress.setUpdateTime(time);
        return ResponseUtil.getSuccessResponse(addressService.update(hanmaAddress));
    }

    @PostMapping("/addressDelete")
    public CommonResponse addressDelete(@RequestBody HanmaAddressEntity hanmaAddress) {
        if (hanmaAddress.getId() == null) {
            return ResponseUtil.getErrorResponse();
        }
        return ResponseUtil.getSuccessResponse(addressService.update(hanmaAddress));
    }

    @PostMapping("/addressUpdate")
    public CommonResponse addressUpdate(@RequestBody HanmaAddressEntity hanmaAddress) {
        hanmaAddress.setCreateTime(null);
        hanmaAddress.setUpdateTime(Utils.getTimestampStr());
        return ResponseUtil.getSuccessResponse(addressService.update(hanmaAddress));
    }

    @PostMapping("/getAddressById")
    public CommonResponse getAddressById(@RequestBody HanmaAddressEntity hanmaAddress) {
        if (hanmaAddress.getId() == null) {
            return ResponseUtil.getErrorResponse();
        }

        AddressModel addressModel = new AddressModel();
        BeanUtils.copyProperties(addressService.findById(hanmaAddress.getId()), addressModel);
        return ResponseUtil.getSuccessResponse(addressModel);
    }

    @RequestMapping("/getAddressList")
    public CommonResponse getAddressInfoProductList() {
        return ResponseUtil.getSuccessResponse(addressService.getAddressList());
    }
}
