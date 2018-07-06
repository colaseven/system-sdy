package com.sdy.modular.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.sdy.modular.system.model.Goods;
import com.sdy.modular.system.dao.GoodsMapper;
import com.sdy.modular.system.service.IGoodsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-06-20
 */
@Service("goodsService")
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Autowired
    GoodsMapper goodsMapper;


    @Override
    public List<Map<String, Object>> queryGoods(String goodsTitle) {
        return this.goodsMapper.queryGoods(goodsTitle);
    }

    @Override
    public List<Map<String, Object>> getGoodsBanner() {
        return this.goodsMapper.getGoodsBanner();
    }

    @Override
    public List<Map<String, Object>> queryPageData(Page<Goods> page, String goodsTitle, String goodsStatus, String orderByField, boolean isAsc) {
        return this.goodsMapper.queyPageData(page,goodsTitle,goodsStatus,orderByField,isAsc);
    }
}
