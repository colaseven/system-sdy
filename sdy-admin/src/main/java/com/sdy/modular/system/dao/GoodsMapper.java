package com.sdy.modular.system.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.sdy.modular.system.model.Goods;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author stylefeng123
 * @since 2018-06-20
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    List<Map<String,Object>> queryGoods(@Param("goodsTitle") String goodsTitle);

    List<Map<String,Object>> getGoodsBanner();

    List<Map<String, Object>> queyPageData(@Param("page") Page<Goods> page, @Param("goodsTitle") String goodsTitle, @Param("goodsStatus") String goodsStatus, @Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);

}
