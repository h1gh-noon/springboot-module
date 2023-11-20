package com.hn.common.util;

import com.hn.common.api.ResponseCode;
import com.hn.common.enums.ResponseEnum;
import com.hn.common.api.CommonResponse;

public class ResponseTool {


    public static CommonResponse getSuccessResponse() {
        return getSuccessResponse(ResponseEnum.SUCCESS_200, null);
    }

    public static CommonResponse getSuccessResponse(Object data) {
        return getSuccessResponse(ResponseEnum.SUCCESS_200, data);
    }

    public static CommonResponse getSuccessResponse(ResponseCode responseCode, Object data) {
        return new CommonResponse(responseCode.getCode(), responseCode.getSuccess(), data,
                responseCode.getMsg());
    }

    public static CommonResponse getErrorResponse() {
        return getErrorResponse(ResponseEnum.FAIL_404);
    }

    public static CommonResponse getErrorResponse(ResponseCode responseCode) {
        return getErrorResponse(responseCode, null);
    }
    public static CommonResponse getErrorResponse(ResponseCode responseCode, Object data) {
        return new CommonResponse(responseCode.getCode(), responseCode.getSuccess(), data, responseCode.getMsg());
    }

    public static CommonResponse getResponseEnum(Integer code, Boolean success, String msg, Object data) {
        return new CommonResponse(code, success, data, msg);
    }

    public static CommonResponse getResponseEnum(ResponseCode responseCode, Object data) {
        return new CommonResponse(responseCode.getCode(), responseCode.getSuccess(), data, responseCode.getMsg());
    }
}
