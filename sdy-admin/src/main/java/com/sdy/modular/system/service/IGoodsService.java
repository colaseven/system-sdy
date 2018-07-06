package com.sdy.modular.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.sdy.modular.system.model.Goods;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-06-20
 */
public interface IGoodsService extends IService<Goods> {

    List<Map<String,Object>> queryGoods(String goodsTitle);

    List<Map<String,Object>> getGoodsBanner();

    List<Map<String, Object>> queryPageData(Page<Goods> page, String goodsTitle, String goodsStatus, String orderByField, boolean isAsc);

}
