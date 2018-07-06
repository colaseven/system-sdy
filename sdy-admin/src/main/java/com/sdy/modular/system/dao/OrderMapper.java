package com.sdy.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sdy.modular.system.model.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 保存所有数据
     *
     * @param list
     * @return
     */
    int saveAllOrders(List<Order> list);

    List<Map<String, Object>> getOrders(@Param("page") Page<Order> page, @Param("phoneNumber") String phoneNumber, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);

    List<Map<String, Object>> selectOrders(@Param("phoneNumber") String phoneNumber, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

    List<Order> selectUnBindOrders(@Param("phoneNumber") String phoneNumber);

    List<String> selectBoundAccounts(@Param("openId") String openId);

    String selectCreditsByOpenId(@Param("openId") String openId);

    int getCountByOpenId(@Param("openId") String openId);

    Order selectOrderByBindAccount(@Param("number") String number);

    List<Map<String, String>> selectGroupOrder(@Param("openId") String openId);

    Map<String, Object> getDetail(@Param("orderId") String orderId);

    List<Order> selectOrderByOpenId(@Param("openId") String openId);

}
