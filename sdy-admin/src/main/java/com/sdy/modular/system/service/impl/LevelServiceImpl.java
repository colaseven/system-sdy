package com.sdy.modular.system.service.impl;

import com.sdy.modular.system.dao.LevelMapper;
import com.sdy.modular.system.model.Level;
import com.sdy.modular.system.service.ILevelService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 积分等级表 服务实现类
 * </p>
 */
@Service
public class LevelServiceImpl extends ServiceImpl<LevelMapper, Level> implements ILevelService {

    @Autowired
    LevelMapper levelMapper;

    @Override
    public List<Map<String, Object>> list() {
        return this.levelMapper.list();
    }

    @Override
    public List<Level> levelList() {
        return this.levelMapper.levelList();
    }

    @Override
    public Level getByIntegral(int integral) {
        return this.levelMapper.getByIntegral(integral);
    }

    @Override
    public Level getByCredit(int integral) {
        return this.levelMapper.getByCredit(integral);
    }

    @Override
    public void deleteByIntegral(int integral, int id,int level) {
        this.levelMapper.deleteByIntegral(integral,id,level);
    }

    @Override
    public Level getMaxLevel() {
        return this.levelMapper.getMaxLevel();
    }

    @Override
    public List<Level> selectByLevel(int level) {
        return this.levelMapper.selectByLevel(level);
    }
}
