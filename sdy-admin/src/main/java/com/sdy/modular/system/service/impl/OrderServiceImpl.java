package com.sdy.modular.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.sdy.modular.system.model.Order;
import com.sdy.modular.system.service.IOrderService;
import com.sdy.modular.system.dao.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("orderService")
public class OrderServiceImpl extends com.baomidou.mybatisplus.service.impl.ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public boolean saveAll(List<Order> list) {
        return this.orderMapper.saveAllOrders(list) > 0;
    }

    @Override
    public List<Map<String, Object>> list(String phoneNumber,String beginTime,String endTime) {
        return this.orderMapper.selectOrders(phoneNumber,beginTime,endTime);
    }

    @Override
    public List<Map<String, Object>> pageList(Page<Order> page, String phoneNumber, String beginTime, String endTime,String orderByField,boolean isAsc) {
        return this.orderMapper.getOrders(page,phoneNumber,beginTime,endTime,orderByField,isAsc);
    }

    @Override
    public List<Order> selectUnbindOrders(String phoneNumber) {
        return this.orderMapper.selectUnBindOrders(phoneNumber);
    }

    @Override
    public List<String> selectBoundAccounts(String openId) {
        return this.orderMapper.selectBoundAccounts(openId );
    }

    @Override
    public String selectCreditsByOpenId(String openId) {
        return orderMapper.selectCreditsByOpenId(openId);
    }

    @Override
    public int getCountByOpenId(String openId) {
        return this.orderMapper.getCountByOpenId(openId);
    }

    @Override
    public Order selectOrderByBindAccount(String number) {
        return this.orderMapper.selectOrderByBindAccount(number);
    }

    @Override
    public List<Map<String, String>> selectGroupOrder(String openId) {
        return this.orderMapper.selectGroupOrder(openId);
    }

    @Override
    public Map<String, Object> getDetail(String orderId) {
        return this.orderMapper.getDetail(orderId);
    }

    @Override
    public List<Order> selectOrderByOpenId(String openId) {
        return this.orderMapper.selectOrderByOpenId(openId);
    }
}
