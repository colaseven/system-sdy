package com.sdy.core.support;

/**
 * Created by zhangzheng on 2018-7-1.
 */
public enum ExchangeStatus {

    Exchanged(1,"已兑换"),
    UnExchanged(2,"未兑换");

    private int code;

    private String text;

    ExchangeStatus(int code,String text){
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

    public static ExchangeStatus getTextByCode(int code){
        ExchangeStatus[] exchangeStatuses = ExchangeStatus.values();
        for (ExchangeStatus exchangeStatus : exchangeStatuses){
            if (exchangeStatus.getCode() == code) return exchangeStatus;
        }
        return null;
    }

    public static ExchangeStatus getCodeByText(String text){
        ExchangeStatus[] exchangeStatuses = ExchangeStatus.values();
        for (ExchangeStatus exchangeStatus : exchangeStatuses){
            if (exchangeStatus.getText().equals(text)) return exchangeStatus;
        }
        return null;
    }
}
