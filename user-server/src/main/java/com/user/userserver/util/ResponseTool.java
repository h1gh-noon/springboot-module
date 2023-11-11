package com.user.userserver.util;

import com.user.userserver.enums.ResponseEnum;
import com.user.userserver.model.CommonResponse;

public class ResponseTool {


    public static CommonResponse getSuccessResponse() {
        return new CommonResponse(ResponseEnum.SUCCESS_200.getCode(), ResponseEnum.SUCCESS_200.getSuccess(), null,
                ResponseEnum.SUCCESS_200.getMsg());
    }

    public static CommonResponse getSuccessResponse(Object data) {
        return new CommonResponse(ResponseEnum.SUCCESS_200.getCode(), ResponseEnum.SUCCESS_200.getSuccess(), data,
                ResponseEnum.SUCCESS_200.getMsg());
    }

    public static CommonResponse getErrorResponse() {
        return new CommonResponse(404, false, null, null);
    }

    public static CommonResponse getErrorResponse(Integer code) {
        return new CommonResponse(code, false, null, null);
    }

    public static CommonResponse getErrorResponse(Integer code, String msg) {
        return new CommonResponse(code, false, null, msg);
    }

    public static CommonResponse getErrorResponse(Object data, String msg) {
        return new CommonResponse(404, false, data, msg);
    }

    public static CommonResponse getErrorResponse(Integer code, Object data, String msg) {
        return new CommonResponse(code, false, data, msg);
    }

    public static CommonResponse getResponseEnum(ResponseEnum responseEnum, Object data) {
        return new CommonResponse(responseEnum.getCode(), responseEnum.getSuccess(), data, responseEnum.getMsg());
    }
}
