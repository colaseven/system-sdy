package com.sdy.modular.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.sdy.modular.system.model.ExchangeOrder;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 兑换订单表 服务类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-06-27
 */
public interface IExchangeOrderService extends IService<ExchangeOrder> {

    List<Map<String, Object>> queryPageData(Page<Map<String, Object>> page, int goodsType,String number,String exchangeCode, String orderByField, boolean isAsc);

    List<Map<String,Object>> queryCountGroupByTime(String beginTime, String endTime);

    List<Map<String,Object>> queryOrdersByOpenId(String openId);

}
