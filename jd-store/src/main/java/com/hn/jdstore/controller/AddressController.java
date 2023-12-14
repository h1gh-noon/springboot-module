package com.hn.jdstore.controller;

import com.hn.common.api.CommonResponse;
import com.hn.common.dto.Validation;
import com.hn.common.util.ResponseTool;
import com.hn.common.util.Util;
import com.hn.jdstore.domain.entity.HanmaAddressDo;
import com.hn.jdstore.domain.vo.AddressVo;
import com.hn.jdstore.domain.vo.IPLocation;
import com.hn.jdstore.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
@RequestMapping("/address")
@Slf4j
@Tag(name = "地址接口")
public class AddressController {

    @Resource
    private AddressService addressService;

    @PostMapping("/addressAdd")
    @Operation(summary = "新增地址")
    public CommonResponse<Long> addressAdd(@RequestBody HanmaAddressDo hanmaAddress) {
        hanmaAddress.setId(null);
        String time = Util.getTimestampStr();
        hanmaAddress.setCreateTime(time);
        hanmaAddress.setUpdateTime(time);
        return ResponseTool.getSuccessResponse(addressService.update(hanmaAddress));
    }

    @RequestMapping("/addressDelete")
    @Operation(summary = "删除地址")
    public CommonResponse<Long> addressDelete(@RequestParam Long id) {
        HanmaAddressDo hanmaAddressDo = new HanmaAddressDo();
        hanmaAddressDo.setId(id);
        addressService.delete(hanmaAddressDo);
        return ResponseTool.getSuccessResponse();
    }

    @PostMapping("/addressUpdate")
    @Operation(summary = "修改地址")
    public CommonResponse<Long> addressUpdate(@RequestBody @Validated(Validation.Update.class) AddressVo addressVo) {
        HanmaAddressDo hanmaAddress = new HanmaAddressDo();
        BeanUtils.copyProperties(addressVo, hanmaAddress);

        hanmaAddress.setUpdateTime(Util.getTimestampStr());
        return ResponseTool.getSuccessResponse(addressService.update(hanmaAddress));
    }

    @RequestMapping("/getAddressById")
    @Operation(summary = "根据id查询地址")
    public CommonResponse<AddressVo> getAddressById(@RequestParam Long id) {
        AddressVo addressVo = new AddressVo();
        BeanUtils.copyProperties(addressService.findById(id), addressVo);
        return ResponseTool.getSuccessResponse(addressVo);
    }

    @RequestMapping("/getAddressList")
    @Operation(summary = "获取地址列表")
    public CommonResponse<List<AddressVo>> getAddressInfoProductList() {
        return ResponseTool.getSuccessResponse(addressService.getAddressList());
    }

    @RequestMapping("/getIPLocation")
    @Operation(summary = "根据ip地址获取位置")
    public CommonResponse<IPLocation> getIPLocation(HttpServletRequest request) {
        return ResponseTool.getSuccessResponse(addressService.getIPLocation(request));
    }
}
