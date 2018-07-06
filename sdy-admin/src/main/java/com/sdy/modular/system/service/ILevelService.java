package com.sdy.modular.system.service;

import com.sdy.modular.system.model.Level;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 积分等级表 服务类
 * </p>
 */
public interface ILevelService extends IService<Level> {

    List<Map<String,Object>> list();

    List<Level> levelList();


    Level getByIntegral(int integral);

    Level getByCredit(int integral);

    void deleteByIntegral(int integral,int id,int level);

    Level getMaxLevel();

    List<Level> selectByLevel(int level);

}
