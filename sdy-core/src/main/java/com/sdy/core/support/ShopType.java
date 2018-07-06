package com.sdy.core.support;

public enum ShopType {
    TaoBao(0, "淘宝"),//
    TianMao(1, "天猫"),
    SuNing(2, "苏宁"),//
    JingDong(3, "京东"),//
    DianZan(4, "点赞"),//
    Other(5, "其他");//0 淘宝  1 天猫  2 苏宁  3 京东 4 点赞  5 其他

    private int code;
    private String text;

    ShopType(int code, String text) {
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

    public static ShopType getCodeByText(String text) {
        String str = text.substring(text.indexOf("[") + 1, text.indexOf("]"));
        ShopType[] shopTypes = ShopType.values();
        for (ShopType shopType : shopTypes) {
            if (shopType.getText().equals(str)) {
                return shopType;
            }
        }
        return Other;
    }
}
