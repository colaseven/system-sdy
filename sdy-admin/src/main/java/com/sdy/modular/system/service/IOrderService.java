package com.sdy.modular.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.sdy.modular.system.model.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IOrderService extends IService<Order> {

    boolean saveAll(List<Order> list);

    /**
     * 获取列表
     */
    List<Map<String, Object>> list(String phoneNumber,String beginTime,String endTime);

    List<Map<String, Object>> pageList(Page<Order> page, String phoneNumber, String beginTime, String endTime,String orderByField,boolean isAsc);

    List<Order> selectUnbindOrders(String phoneNumber);

    List<String> selectBoundAccounts(String openId);

    String selectCreditsByOpenId(String openId);

    int getCountByOpenId(String openId);

    Order selectOrderByBindAccount(String number);

    List<Map<String, String>> selectGroupOrder(String openId);

    Map<String,Object> getDetail(String orderId);

    List<Order> selectOrderByOpenId(String openId);

}
