package com.sdy.modular.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.sdy.modular.system.model.ExchangeOrder;
import com.sdy.modular.system.dao.ExchangeOrderMapper;
import com.sdy.modular.system.service.IExchangeOrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 兑换订单表 服务实现类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-06-27
 */
@Service
public class ExchangeOrderServiceImpl extends ServiceImpl<ExchangeOrderMapper, ExchangeOrder> implements IExchangeOrderService {

    @Autowired
    ExchangeOrderMapper exchangeOrderMapper;

    @Override
    public List<Map<String, Object>> queryPageData(Page<Map<String, Object>> page, int goodsType,String number, String exchangeCode, String orderByField, boolean isAsc) {
        return this.exchangeOrderMapper.queryPageData(page,goodsType,number,exchangeCode,orderByField,isAsc);
    }

    @Override
    public List<Map<String, Object>> queryCountGroupByTime(String beginTime, String endTime) {
        return this.exchangeOrderMapper.queryCountGroupByTime(beginTime,endTime);
    }

    @Override
    public List<Map<String, Object>> queryOrdersByOpenId(String openId) {
        return this.exchangeOrderMapper.queryOrdersByOpenId(openId);
    }
}
