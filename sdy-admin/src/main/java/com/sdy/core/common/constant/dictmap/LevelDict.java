package com.sdy.core.common.constant.dictmap;

import com.sdy.core.common.constant.dictmap.base.AbstractDictMap;

public class LevelDict extends AbstractDictMap {
    @Override
    public void init() {
        put("levelNum","积分等级");
        put("startIntegral","开始积分");
        put("endIntegral","结束积分");
    }

    @Override
    protected void initBeWrapped() {

    }
}
