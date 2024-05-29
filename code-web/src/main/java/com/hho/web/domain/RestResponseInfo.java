package com.hho.web.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/** * @author shun */
@Data
@ToString
@ApiModel(value = "请求响应结果集")
public class RestResponseInfo<T> implements Serializable {

    @ApiModelProperty(value = "是否成功")
    private Boolean success = true;

    @ApiModelProperty(value = "响应编码")
    private Integer code = 200;

    @ApiModelProperty(value = "操作信息")
    private String msg = "operate successfully";

    @ApiModelProperty(value = "返回对象")
    private T data;

    public RestResponseInfo() {

    }

    public RestResponseInfo(Boolean success, Integer code, String msg, T data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public RestResponseInfo(T data) {
        this.data = data;
    }

    public RestResponseInfo(String msg) {
        this.msg = msg;
    }

    public static <T> RestResponseInfo ok(T data) {
        return new RestResponseInfo(data);
    }

    public static RestResponseInfo ok(String msg) {
        return new RestResponseInfo(msg);
    }

    public static RestResponseInfo ok() {
        return new RestResponseInfo();
    }

    public static RestResponseInfo error(String msg) {
        return new RestResponseInfo(false, 500, msg, null);
    }

    public static RestResponseInfo error(Object msg) {
        return new RestResponseInfo(false, 500, msg.toString(), null);
    }

    public static RestResponseInfo error() {
        return new RestResponseInfo(false, 500, "操作失败", null);
    }

    public void setResponse(boolean success) {
        if (success == false) {
            this.success = false;
            this.code = 500;
            this.msg = "操作失败";
        }
    }

    public void setResponse(boolean success, String msg) {
        if (success == false) {
            this.success = false;
            this.code = 500;
        }
        this.msg = msg;
    }

}
