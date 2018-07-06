package com.sdy.modular.system.dao;

import com.sdy.modular.system.model.Banner;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 图片轮播表 Mapper 接口
 * </p>
 */
public interface BannerMapper extends BaseMapper<Banner> {

    List<Map<String, String>> getList();

}
