package com.sdy.modular.system.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.sdy.modular.system.model.ExchangeOrder;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 兑换订单表 Mapper 接口
 * </p>
 *
 * @author stylefeng123
 * @since 2018-06-27
 */
public interface ExchangeOrderMapper extends BaseMapper<ExchangeOrder> {

    List<Map<String, Object>> queryPageData(@Param("page") Page<Map<String, Object>> page, @Param("goodsType") int goodsType,@Param("number") String number,@Param("exchangeCode") String exchangeCdode, @Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);

    List<Map<String,Object>> queryCountGroupByTime(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

    List<Map<String,Object>> queryOrdersByOpenId(@Param("openId") String openId);
}
