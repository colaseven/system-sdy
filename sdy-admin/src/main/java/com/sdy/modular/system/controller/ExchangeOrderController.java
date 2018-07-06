package com.sdy.modular.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.sdy.core.base.controller.BaseController;
import com.sdy.core.common.constant.factory.PageFactory;
import com.sdy.core.common.exception.BizExceptionEnum;
import com.sdy.core.support.ExchangeStatus;
import com.sdy.core.util.ToolUtil;
import com.sdy.modular.system.warpper.ExchangeOrderWarpper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.sdy.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.sdy.modular.system.model.ExchangeOrder;
import com.sdy.modular.system.service.IExchangeOrderService;

import java.util.List;
import java.util.Map;

/**
 * 控制器
 *
 * @author fengshuonan
 * @Date 2018-06-27 11:41:31
 */
@Controller
@RequestMapping("/exchangeOrder")
public class ExchangeOrderController extends BaseController {

    private String PREFIX = "/system/exchangeOrder/";

    @Autowired
    private IExchangeOrderService exchangeOrderService;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "exchangeOrderIndex.html";
    }

    /**
     * 跳转到首页
     */
    @RequestMapping("/kind")
    public String kind() {
        return PREFIX + "exchangeOrderKind.html";
    }

    /**
     * 跳转到首页
     */
    @RequestMapping("/coupon")
    public String coupon() {
        return PREFIX + "exchangeOrderKind.html";
    }

    /**
     * 跳转到统计
     */
    @RequestMapping("/exchangeOrder_statistics")
    public String exchangeOrderStatistics(){
        return PREFIX + "exchangeOrder_Statistics.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/exchangeOrder_add")
    public String exchangeOrderAdd() {
        return PREFIX + "exchangeOrder_add.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/express_update/{exchangeOrderId}")
    public String exchangeOrderKindExpress(@PathVariable Integer exchangeOrderId, Model model) {
        ExchangeOrder exchangeOrder = exchangeOrderService.selectById(exchangeOrderId);
        model.addAttribute("item",exchangeOrder);
        LogObjectHolder.me().set(exchangeOrder);
        return PREFIX + "exchangeOrderKind_edit.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/exchangeOrder_update/{exchangeOrderId}")
    public String exchangeOrderUpdate(@PathVariable Integer exchangeOrderId, Model model) {
        ExchangeOrder exchangeOrder = exchangeOrderService.selectById(exchangeOrderId);
        model.addAttribute("item", exchangeOrder);
        LogObjectHolder.me().set(exchangeOrder);
        return PREFIX + "exchangeOrder_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String number,String exchangeCode,@RequestParam(value = "goodsType", required = false) int goodsType) {
        Page<Map<String, Object>> page = new PageFactory<Map<String, Object>>().defaultPage();
        List<Map<String, Object>> list = this.exchangeOrderService.queryPageData(page,goodsType,number,exchangeCode,page.getOrderByField(),page.isAsc());
        page.setRecords((List<Map<String,Object>>) new ExchangeOrderWarpper(list).warp());

        return super.packForBT(page);
    }

    /**
     * 获取统计数据
     */
    @RequestMapping(value = "/statistics")
    @ResponseBody
    public Object statistics(String beginTime,String endTime) {
        return this.exchangeOrderService.queryCountGroupByTime(beginTime,endTime);
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ExchangeOrder exchangeOrder) {
        exchangeOrderService.insert(exchangeOrder);
        return SUCCESS_TIP;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer exchangeOrderId) {
        exchangeOrderService.deleteById(exchangeOrderId);
        return SUCCESS_TIP;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ExchangeOrder exchangeOrder) {
        exchangeOrderService.updateById(exchangeOrder);
        return SUCCESS_TIP;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/successExchange")
    @ResponseBody
    public Object successExchange(Integer exchangeOrderId){
        ExchangeOrder exchangeOrder = this.exchangeOrderService.selectById(exchangeOrderId);
        if (exchangeOrder != null){
            exchangeOrder.setExchangeStatus(ExchangeStatus.Exchanged.getCode());
            this.exchangeOrderService.updateById(exchangeOrder);
        }
        return SUCCESS_TIP;

    }


    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{exchangeOrderId}")
    @ResponseBody
    public Object detail(@PathVariable("exchangeOrderId") Integer exchangeOrderId) {
        return exchangeOrderService.selectById(exchangeOrderId);
    }
}
