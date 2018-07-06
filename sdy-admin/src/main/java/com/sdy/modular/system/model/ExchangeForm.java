package com.sdy.modular.system.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by zhangzheng on 2018-6-27.
 */

@ApiModel(value = "ExchangeForm", description = "兑换信息")
public class ExchangeForm {

    @ApiModelProperty(value = "兑换商品Id", name = "goodsId", dataType = "int", required = true)
    private int goodsId;

    @ApiModelProperty(value = "兑换数量", name = "count", dataType = "int", required = true)
    private int count;

    @ApiModelProperty(value = "openId", name = "openId", dataType = "String", required = true)
    private String openId;

    @ApiModelProperty(value = "联系人", name = "linkMan", dataType = "String", required = false)
    private String linkMan;

    @ApiModelProperty(value = "详细地址", name = "address", dataType = "String", required = false)
    private String address;

    @ApiModelProperty(value = "联系方式", name = "phoneNumber", dataType = "String", required = false)
    private String phoneNumber;

    @ApiModelProperty(value = "备注", name = "remark", dataType = "String", required = false)
    private String remark;

    @ApiModelProperty(value = "邮政编号", name = "code", dataType = "String", required = false)
    private String code;

    @ApiModelProperty(value = "省", name = "province", dataType = "String", required = false)
    private String province;

    @ApiModelProperty(value = "市", name = "city", dataType = "String", required = false)
    private String city;

    @ApiModelProperty(value = "区", name = "district", dataType = "String", required = false)
    private String district;

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
