package com.hn.common.util;

import com.hn.common.api.ResponseCode;
import com.hn.common.enums.ResponseEnum;
import com.hn.common.api.CommonResponse;

public class ResponseTool {


    public static <T> CommonResponse<T> getSuccessResponse() {
        return getSuccessResponse(ResponseEnum.SUCCESS_200, null);
    }

    public static <T> CommonResponse<T> getSuccessResponse(T data) {
        return getSuccessResponse(ResponseEnum.SUCCESS_200, data);
    }

    public static <T> CommonResponse<T> getSuccessResponse(ResponseCode responseCode, T data) {
        return new CommonResponse<T>(responseCode.getCode(), responseCode.getSuccess(), data,
                responseCode.getMsg());
    }

    public static <T> CommonResponse<T> getErrorResponse() {
        return getErrorResponse(ResponseEnum.FAIL_404);
    }

    public static <T> CommonResponse<T> getErrorResponse(ResponseCode responseCode) {
        return getErrorResponse(responseCode, null);
    }

    public static <T> CommonResponse<T> getErrorResponse(ResponseCode responseCode, T data) {
        return new CommonResponse<T>(responseCode.getCode(), responseCode.getSuccess(), data, responseCode.getMsg());
    }

    public static <T> CommonResponse<T> getResponseEnum(Integer code, Boolean success, String msg, T data) {
        return new CommonResponse<T>(code, success, data, msg);
    }

    public static <T> CommonResponse<T> getResponseEnum(ResponseCode responseCode, T data) {
        return new CommonResponse<T>(responseCode.getCode(), responseCode.getSuccess(), data, responseCode.getMsg());
    }
}
