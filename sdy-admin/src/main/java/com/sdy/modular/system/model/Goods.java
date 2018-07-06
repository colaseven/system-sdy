package com.sdy.modular.system.model;

import java.beans.Transient;
import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.sdy.core.util.ToolUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author stylefeng123
 * @since 2018-06-20
 */
@TableName("sys_goods")
public class Goods extends Model<Goods> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value="id", type= IdType.AUTO)
    private Integer id;
    /**
     * 商品标题
     */
    private String goodsTitle;
    /**
     * 详情说明
     */
    private String goodsExplain;
    /**
     * 待发货文案
     */
    private String pendingDeliveryPaper;
    /**
     * 商品图片
     */
    private String logoPic;

    /**
     * 商品Banner
     */
    private String bannerPic;
    /**
     * 市面价值
     */
    private String marketValue;
    /**
     * 兑换价格
     */
    private String exchangePrice;
    /**
     * 剩余库存
     */
    private String surplusStock;
    /**
     * 自动下架时间
     */
    private String autoDownTime;

    /**
     * 商品类别
     */
    private int goodsType;

    /**
     * 商品状态
     */
    private int goodsStatus;

    /**
     * 首页Banner图
     */
    private String indexBannerPic;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getGoodsExplain() {
        return goodsExplain;
    }

    public void setGoodsExplain(String goodsExplain) {
        this.goodsExplain = goodsExplain;
    }

    public String getPendingDeliveryPaper() {
        return pendingDeliveryPaper;
    }

    public void setPendingDeliveryPaper(String pendingDeliveryPaper) {
        this.pendingDeliveryPaper = pendingDeliveryPaper;
    }

    public String getLogoPic() {
        return logoPic;
    }

    public void setLogoPic(String logoPic) {
        this.logoPic = logoPic;
    }

    public String getBannerPic() {
        return bannerPic;
    }

    public void setBannerPic(String bannerPic) {
        this.bannerPic = bannerPic;
    }

    public String getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(String marketValue) {
        this.marketValue = marketValue;
    }

    public String getExchangePrice() {
        return exchangePrice;
    }

    public void setExchangePrice(String exchangePrice) {
        this.exchangePrice = exchangePrice;
    }

    public String getSurplusStock() {
        return surplusStock;
    }

    public void setSurplusStock(String surplusStock) {
        this.surplusStock = surplusStock;
    }

    public String getAutoDownTime() {
        return autoDownTime;
    }

    public void setAutoDownTime(String autoDownTime) {
        this.autoDownTime = autoDownTime;
    }

    public int getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(int goodsType) {
        this.goodsType = goodsType;
    }

    public int getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(int goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public String getIndexBannerPic() {
        return indexBannerPic;
    }

    public void setIndexBannerPic(String indexBannerPic) {
        this.indexBannerPic = indexBannerPic;
    }

    public List<String> getBannerPicList() {
        List<String> list = new ArrayList<>();
        if (ToolUtil.isEmpty(bannerPic)) return list;
        for (String str : bannerPic.split(",")){
            if (ToolUtil.isEmpty(str))continue;
            list.add("https://www.sdyong.com/kaptcha/" + str);
        }
        return list;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", goodsTitle=" + goodsTitle +
                ", goodsExplain=" + goodsExplain +
                ", pendingDeliveryPaper=" + pendingDeliveryPaper +
                ", logoPic=" + logoPic +
                ", bannerPic=" + bannerPic +
                ", marketValue=" + marketValue +
                ", exchangePrice=" + exchangePrice +
                ", surplusStock=" + surplusStock +
                ", autoDownTime=" + autoDownTime +
                ", goodsType=" + goodsType +
                "}";
    }
}
