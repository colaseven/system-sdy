package com.sdy.core.support;

/**
 * Created by zhangzheng on 2018-6-29.
 */
public enum GoodsStatus {

    Grounding(1,"已上架"),
    Undercarriage(2,"已下架");

    private int code;

    private String text;

    GoodsStatus(int code,String text){
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

    public static GoodsStatus getTextByCode(int code){
        GoodsStatus[] goodsStatuses = GoodsStatus.values();
        for (GoodsStatus goodsStatus : goodsStatuses){
            if (goodsStatus.getCode() == code) return goodsStatus;
        }
        return null;
    }

    public static GoodsStatus getCodeByText(String text){
        GoodsStatus[] goodsStatuses = GoodsStatus.values();
        for (GoodsStatus goodsStatus : goodsStatuses){
            if (goodsStatus.getText().equals(text)) return goodsStatus;
        }
        return null;
    }
}
