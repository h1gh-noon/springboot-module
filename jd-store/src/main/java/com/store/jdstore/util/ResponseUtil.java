package com.store.jdstore.util;

import com.store.jdstore.model.CommonResponse;

public class ResponseUtil {


    public static CommonResponse getSuccessResponse() {
        return new CommonResponse(200, true, null, null);
    }

    public static CommonResponse getSuccessResponse(Object data) {
        return new CommonResponse(200, true, data, null);
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

}
