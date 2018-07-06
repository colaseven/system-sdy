package com.sdy.modular.system.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "BindForm", description = "绑定信息")
public class BindForm {

    @ApiModelProperty(value = "绑定手机号", name = "phoneNumber", dataType = "String", required = true)
    private String phoneNumber;

    @ApiModelProperty(value = "微信openId", name = "openId", dataType = "String", required = true)
    private String openId;

    private String verifyCode;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
