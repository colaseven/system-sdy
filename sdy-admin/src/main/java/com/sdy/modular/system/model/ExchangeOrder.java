package com.sdy.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 兑换订单表
 * </p>
 *
 * @author stylefeng123
 * @since 2018-06-27
 */
@TableName("sys_exchange_order")
public class ExchangeOrder extends Model<ExchangeOrder> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 订单号
     */
    private String number;
    /**
     * 兑换积分
     */
    private Integer credits;
    /**
     * 兑换时间
     */
    private String exchangeTime;
    /**
     * 兑换卷码
     */
    private String exchangeCode;

    /**
     * 收货人
     */
    private String linkMan;

    /**
     * 联系方式
     */
    private String phoneNumber;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String district;

    /**
     * 地址
     */
    private String address;
    /**
     * 邮政编号
     */
    private String code;
    /**
     * 备注
     */
    private String remark;
    /**
     * 商品Id
     */
    private Integer goodsId;

    /**
     * 商品类别
     */
    private Integer goodsType;

    /**
     * openId
     */
    private String openId;

    /**
     * 兑换年份
     */
    private String exchangeYear;

    /**
     * 兑换月份
     */
    private String exchangeMonth;

    /**
     * 快递单号
     */
    private String expressNumber;

    /**
     * 快递公司
     */
    private String express;

    /**
     * 兑换状态
     */
    private int exchangeStatus;

    public ExchangeOrder(){}

    public ExchangeOrder(Integer id, String number, Integer credits, String exchangeTime, String exchangeCode,String linkMan,String phoneNumber, String province, String city, String district, String address, String code, String remark, Integer goodsId,Integer goodsType,String openId,String exchangeYear,String exchangeMonth,String expressNumber,String express,int exchangeStatus) {
        this.id = id;
        this.number = number;
        this.credits = credits;
        this.exchangeTime = exchangeTime;
        this.exchangeCode = exchangeCode;
        this.linkMan = linkMan;
        this.phoneNumber = phoneNumber;
        this.province = province;
        this.city = city;
        this.district = district;
        this.address = address;
        this.code = code;
        this.remark = remark;
        this.goodsId = goodsId;
        this.goodsType = goodsType;
        this.openId = openId;
        this.exchangeYear = exchangeYear;
        this.exchangeMonth = exchangeMonth;
        this.expressNumber = expressNumber;
        this.express = express;
        this.exchangeStatus = exchangeStatus;
    }

    public ExchangeOrder(String number, Integer credits, String exchangeTime, String exchangeCode,String linkMan,String phoneNumber, String province, String city, String district, String address, String code, String remark, Integer goodsId,Integer goodsType,String openId,String exchangeYear,String exchangeMonth,int exchangeStatus) {
        this.number = number;
        this.credits = credits;
        this.exchangeTime = exchangeTime;
        this.exchangeCode = exchangeCode;
        this.linkMan = linkMan;
        this.phoneNumber = phoneNumber;
        this.province = province;
        this.city = city;
        this.district = district;
        this.address = address;
        this.code = code;
        this.remark = remark;
        this.goodsId = goodsId;
        this.goodsType = goodsType;
        this.openId = openId;
        this.exchangeYear = exchangeYear;
        this.exchangeMonth = exchangeMonth;
        this.exchangeStatus = exchangeStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public String getExchangeTime() {
        return exchangeTime;
    }

    public void setExchangeTime(String exchangeTime) {
        this.exchangeTime = exchangeTime;
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getExchangeYear() {
        return exchangeYear;
    }

    public void setExchangeYear(String exchangeYear) {
        this.exchangeYear = exchangeYear;
    }

    public String getExchangeMonth() {
        return exchangeMonth;
    }

    public void setExchangeMonth(String exchangeMonth) {
        this.exchangeMonth = exchangeMonth;
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public int getExchangeStatus() {
        return exchangeStatus;
    }

    public void setExchangeStatus(int exchangeStatus) {
        this.exchangeStatus = exchangeStatus;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ExchangeOrder{" +
        "id=" + id +
        ", number=" + number +
        ", credits=" + credits +
        ", exchangeTime=" + exchangeTime +
        ", exchangeCode=" + exchangeCode +
        ", province=" + province +
        ", city=" + city +
        ", district=" + district +
        ", code=" + code +
        ", remark=" + remark +
        ", goodsId=" + goodsId +
        "}";
    }
}
