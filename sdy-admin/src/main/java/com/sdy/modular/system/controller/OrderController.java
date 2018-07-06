package com.sdy.modular.system.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.sdy.core.base.controller.BaseController;
import com.sdy.core.base.tips.Tip;
import com.sdy.core.exception.GunsException;
import com.sdy.core.util.ToolUtil;
import com.sdy.modular.system.model.Order;
import com.sdy.core.common.annotion.BussinessLog;
import com.sdy.core.common.annotion.Permission;
import com.sdy.core.common.constant.Const;
import com.sdy.core.common.constant.dictmap.OrderDict;
import com.sdy.core.common.constant.factory.PageFactory;
import com.sdy.core.common.exception.BizExceptionEnum;
import com.sdy.core.log.LogObjectHolder;
import com.sdy.modular.system.service.IOrderService;
import com.sdy.modular.system.util.ExcelReaderUtil;
import com.sdy.modular.system.warpper.OrderWarpper;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@Api(value = "OrderController", description = "订单信息操作API")
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(OrderController.class);

    private static String PREFIX = "/system/order/";

    private final static String EXCEL_XLSX = "xlsx";
    private final static String EXCEL_XLS = "xls";

    @Autowired
    private IOrderService orderService;

    /**
     * 跳转到数据导入列表页面
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "order.html";
    }

    /**
     * 获取列表
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String phoneNumber, @RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime) {
        Page<Order> page = new PageFactory<Order>().defaultPage();
//        this.orderService.selectMapsPage()
        if (ToolUtil.isNotEmpty(phoneNumber) || ToolUtil.isNotEmpty(beginTime) || ToolUtil.isNotEmpty(endTime)){
            page.setCurrent(1);
        }
        List<Map<String, Object>> list = this.orderService.pageList(page, phoneNumber, beginTime, endTime, page.getOrderByField(), page.isAsc());
        page.setRecords((List<Order>) new OrderWarpper(list).warp());
        return super.packForBT(page);
    }

    /**
     * 导入数据
     */
    @RequestMapping(method = RequestMethod.POST, path = "/import")
    @ResponseBody
    public Tip importData(@RequestPart("file") MultipartFile file) throws IOException {
        long startTime = System.currentTimeMillis();
        try {
            ExcelReaderUtil.readExcel(file.getInputStream(), file.getOriginalFilename());
            System.out.println((System.currentTimeMillis() - startTime) / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS_TIP;
    }

    /**
     * 修改数据
     *
     * @param order
     * @param result
     * @return
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping("/edit")
    @BussinessLog(value = "修改数据", key = "phoneNumber", dict = OrderDict.class)
    @ResponseBody
    public Tip edit(@Valid Order order, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        Order order1 = this.orderService.selectOrderByBindAccount(order.getPhoneNumber());
        if (order1 != null && ToolUtil.isNotEmpty(order1.getBindingPhoneNumber()) && ToolUtil.isNotEmpty(order1.getOpenId())) {
            order.setBindingPhoneNumber(order1.getBindingPhoneNumber());
            order.setOpenId(order1.getOpenId());
        }
        this.orderService.updateById(order);
        return SUCCESS_TIP;
    }

    /**
     * 跳转到订单详情列表页面
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/order_edit/{id}")
    public String orderEdit(@PathVariable Long id, Model model) {
        if (ToolUtil.isEmpty(id)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        Order order = this.orderService.selectById(id);
        model.addAttribute(order);
        LogObjectHolder.me().set(order);
        return PREFIX + "order_edit.html";
    }

    /**
     * 删除数据（物理删除）
     */
    @RequestMapping("/delete")
    @BussinessLog(value = "删除订单数据", key = "id", dict = OrderDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip delete(@RequestParam String id) {
        if (ToolUtil.isEmpty(id)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        List<String> orderIdList = Arrays.asList(id.split(","));
        this.orderService.deleteBatchIds(orderIdList);
        return SUCCESS_TIP;
    }
}
