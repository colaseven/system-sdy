package com.sdy.modular.system.service;

import com.sdy.modular.system.model.Banner;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 图片轮播表 服务类
 * </p>
 */
public interface IBannerService extends IService<Banner> {

    List<Map<String,String>> getList();
}
