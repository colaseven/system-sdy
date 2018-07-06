package com.sdy.modular.system.util;

import com.sdy.core.util.SpringUtil;
import com.sdy.modular.system.model.Goods;
import com.sdy.modular.system.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.TimerTask;

/**
 * Created by zhangzheng on 2018-6-29.
 */
public class MyTimerTask extends TimerTask {

    private Goods goods;

    public MyTimerTask(Goods goods){
        this.goods = goods;
    }

    @Autowired
    IGoodsService goodsService;

    @Override
    public void run() {
        doTask();
    }

    private void doTask(){
        if (goodsService == null) {
            goodsService = (IGoodsService) SpringUtil.getBean("goodsService");
        }
        Goods goods1 = goodsService.selectById(1);
        System.out.println(goods1.getId());
    }
}
