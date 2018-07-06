package com.sdy.modular.system.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "AuthLoginForm", description = "授权登录信息")
public class AuthLoginForm {

    @ApiModelProperty(value = "积分", name = "credits", dataType = "String", required = true)
    private String credits;

    @ApiModelProperty(value = "微信openId", name = "openId", dataType = "String", required = true)
    private String openId;

    @ApiModelProperty(value = "接口回调地址", name = "redirect", dataType = "String", required = true)
    private String redirect;

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }
}
