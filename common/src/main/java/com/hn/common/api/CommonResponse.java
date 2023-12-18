package com.hn.common.api;


import com.hn.common.enums.ResponseEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "响应数据对象")
public class CommonResponse<T> {

    @Schema(description = "响应码", example = "200")
    private Integer code;

    @Schema(description = "操作是否成功", example = "true")
    private Boolean success;

    @Schema(description = "响应信息", example = "操作成功")
    private String msg;

    @Schema(description = "响应数据")
    private T data;

    public CommonResponse(Integer code) {
        this.code = code;
    }

    public CommonResponse(Integer code, Boolean success) {
        this.code = code;
        this.success = success;
    }

    public CommonResponse(Integer code, Boolean success, T data) {
        this.code = code;
        this.success = success;
        this.data = data;
    }

    public CommonResponse(Integer code, Boolean success, T data, String msg) {
        this.code = code;
        this.success = success;
        this.data = data;
        this.msg = msg;
    }

    public CommonResponse(ResponseEnum responseEnum) {
        this.code = responseEnum.getCode();
        this.success = responseEnum.getSuccess();
        this.msg = responseEnum.getMsg();
    }

    public CommonResponse(ResponseEnum responseEnum, T data) {
        this.code = responseEnum.getCode();
        this.success = responseEnum.getSuccess();
        this.msg = responseEnum.getMsg();
        this.data = data;
    }

}
