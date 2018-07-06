package com.sdy.modular.system.service.impl;

import com.sdy.modular.system.service.IBannerService;
import com.sdy.modular.system.model.Banner;
import com.sdy.modular.system.dao.BannerMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 图片轮播表 服务实现类
 * </p>
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements IBannerService {

    @Autowired
    BannerMapper bannerMapper;

    @Override
    public List<Map<String, String>> getList() {
        return this.bannerMapper.getList();
    }
}
