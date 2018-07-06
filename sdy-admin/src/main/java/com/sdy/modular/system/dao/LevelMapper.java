package com.sdy.modular.system.dao;

import com.sdy.modular.system.model.Level;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 积分等级表 Mapper 接口
 * </p>
 */
public interface LevelMapper extends BaseMapper<Level> {


    /**
     * 查询等级列表
     */
    List<Map<String, Object>> list();

    Level getByIntegral(@Param("integral") int integral);

    Level getByCredit(@Param("integral") int integral);

    List<Level> levelList();

    void deleteByIntegral(@Param("integral") int integral, @Param("id") int id, @Param("level") int level);

    Level getMaxLevel();

    List<Level> selectByLevel(@Param("level") int level);

}
