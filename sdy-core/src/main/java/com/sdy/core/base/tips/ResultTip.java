package com.sdy.core.base.tips;

import io.swagger.annotations.ApiModelProperty;

public class ResultTip<T> {

    @ApiModelProperty(value = "状态码", name = "state", dataType = "int", required = true)
    private int state;
    @ApiModelProperty(value = "返回信息", name = "message", dataType = "String")
    private String message;
    @ApiModelProperty(value = "返回数据", name = "data", dataType = "Object")
    private T data;

    public ResultTip() {
    }

    public ResultTip(int state, String message, T data) {
        this.state = state;
        this.message = message;
        this.data = data;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
