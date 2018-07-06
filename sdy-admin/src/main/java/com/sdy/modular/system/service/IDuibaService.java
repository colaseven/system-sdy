package com.sdy.modular.system.service;

import com.sdy.modular.system.model.Duiba;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 兑吧表 服务类
 * </p>
 */
public interface IDuibaService extends IService<Duiba> {

    Duiba selectByOrderNum(String orderNumber);
}
