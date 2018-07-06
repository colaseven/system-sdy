package com.sdy.modular.system.service.impl;

import com.sdy.modular.system.dao.DuibaMapper;
import com.sdy.modular.system.model.Duiba;
import com.sdy.modular.system.service.IDuibaService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 兑吧表 服务实现类
 * </p>
 */
@Service
public class DuibaServiceImpl extends ServiceImpl<DuibaMapper, Duiba> implements IDuibaService {

    @Autowired
    DuibaMapper duibaMapper;

    @Override
    public Duiba selectByOrderNum(String orderNumber) {
        return this.duibaMapper.selectByOrderNum(orderNumber);
    }
}
