package com.sdy.modular.system.model;


public class YearData<T> {

    private String sourceMonth;

    private T monthList;

    public String getSourceMonth() {
        return sourceMonth;
    }

    public void setSourceMonth(String sourceMonth) {
        this.sourceMonth = sourceMonth;
    }

    public T getMonthList() {
        return monthList;
    }

    public void setMonthList(T monthList) {
        this.monthList = monthList;
    }
}
