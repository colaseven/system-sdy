package com.sdy.core.support;

/**
 * Created by zhangzheng on 2018-6-25.
 */
public enum GoodsType {
    Coupon(1,"coupon"),//优惠券
    Kind(2,"kind");//实物

    private int code;
    private String text;

    GoodsType(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static GoodsType getTextByCode(int code){
        GoodsType[] goodsTypes = GoodsType.values();
        for (GoodsType goodsType : goodsTypes){
            if (goodsType.getCode() == code) return goodsType;
        }
        return null;
    }

    public static GoodsType getCodeByText(String text){
        GoodsType[] goodsTypes = GoodsType.values();
        for (GoodsType goodsType : goodsTypes){
            if (goodsType.getText().equals(text)) return goodsType;
        }
        return null;
    }
}
