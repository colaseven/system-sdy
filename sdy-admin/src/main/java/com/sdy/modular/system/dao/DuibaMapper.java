package com.sdy.modular.system.dao;

import com.sdy.modular.system.model.Duiba;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 兑吧表 Mapper 接口
 * </p>
 */
public interface DuibaMapper extends BaseMapper<Duiba> {

    Duiba selectByOrderNum(@Param("orderNumber") String orderNumber);

}
