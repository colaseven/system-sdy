package com.sdy.modular.system.model;


public class GroupOrder {

    private SourceList sourceList;

    public GroupOrder(SourceList sourceList) {
        this.sourceList = sourceList;
    }

    public SourceList getSourceList() {
        return sourceList;
    }

    public void setSourceList(SourceList sourceList) {
        this.sourceList = sourceList;
    }
}
