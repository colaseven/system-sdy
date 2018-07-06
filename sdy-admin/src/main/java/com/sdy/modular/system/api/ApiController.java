package com.sdy.modular.system.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.sdy.core.base.controller.BaseController;
import com.sdy.core.base.tips.ResultTip;
import com.sdy.core.duiba.entity.CreditConsumeParams;
import com.sdy.core.support.*;
import com.sdy.core.util.*;
import com.sdy.modular.system.model.*;
import com.sdy.config.properties.GunsProperties;
import com.sdy.core.common.constant.Const;
import com.sdy.core.common.exception.BizExceptionEnum;
import com.sdy.core.duiba.BulidUrl;
import com.sdy.core.duiba.CreditTool;
import com.sdy.core.duiba.SignTool;
import com.sdy.core.duiba.entity.CreditNotifyParams;
import com.sdy.core.duiba.result.CreditConsumeResult;
import com.sdy.modular.system.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;


@Api(value = "ApiController", description = "接口API")
@Controller
@RequestMapping("/api")
public class ApiController extends BaseController {


    private final static Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ILevelService levelService;

    @Autowired
    private IBannerService bannerService;

    @Autowired
    GunsProperties gunsProperties;

    @Autowired
    IDuibaService duibaService;

    @Autowired
    IGoodsService goodsService;

    @Autowired
    IExchangeOrderService exchangeOrderService;


    @ApiOperation("获取验证码")
    @RequestMapping(value = "/code/{phoneNumber}", method = RequestMethod.GET)
    @ResponseBody
    public ResultTip getVerificationCode(@PathVariable("phoneNumber") String phoneNumber, HttpServletRequest request) {
        if (ToolUtil.isEmpty(phoneNumber)) {
            return new ResultTip(BizExceptionEnum.REQUEST_NULL.getCode(), BizExceptionEnum.REQUEST_NULL.getMessage(), null);
        }
        //TODO 手机号查询 分绑定状态
        List<Order> list = this.orderService.selectUnbindOrders(phoneNumber);
        if (list == null || list.size() == 0) return new ResultTip(1, "无匹配的手机号码或该手机号码已绑定", null);
        HttpSession session = request.getSession();
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        try {
            String result = SendShortMessageUtil.sendMessage(phoneNumber, code);
            if (result.equals("success")) {
                long createTime = new Date().getTime();
                session.setAttribute(phoneNumber + "@verifyCode", phoneNumber + "|" + code + "|" + createTime);
            }
            System.out.println("验证码 -->" + session.getId());
        } catch (ClientException e) {
            session.removeAttribute(phoneNumber + "@verifyCode");
            e.printStackTrace();
        }
        return new ResultTip(SUCCESS_TIP.getCode(), SUCCESS_TIP.getMessage(), null);
    }

    @ApiOperation("绑定账号")
    @RequestMapping(value = "/bindingAccount", method = RequestMethod.POST)
    @ResponseBody
    public ResultTip bindingAccount(@RequestBody BindForm bindForm, HttpServletRequest request) {
        if (ToolUtil.isEmpty(bindForm) || ToolUtil.isEmpty(bindForm.getPhoneNumber()) || ToolUtil.isEmpty(bindForm.getOpenId()) || ToolUtil.isEmpty(bindForm.getVerifyCode())) {
            return new ResultTip(BizExceptionEnum.REQUEST_NULL.getCode(), BizExceptionEnum.REQUEST_NULL.getMessage(), null);
        }
        String phoneNumber = bindForm.getPhoneNumber();
        HttpSession session = request.getSession();
        System.out.println("绑定 -->" + session.getId());
        String verifyCode = bindForm.getVerifyCode();
        Object sessionValue = session.getAttribute(phoneNumber + "@verifyCode");
        if (ToolUtil.isEmpty(sessionValue)) {
            return new ResultTip(1, "验证码输入错误，请重新输入！", null);
        }

        String openId = bindForm.getOpenId();
        int count = this.orderService.getCountByOpenId(openId);
        if (count > 5) {
            session.removeAttribute(phoneNumber + "@verifyCode");
            return new ResultTip(1, "已绑定的账号已达上限", null);
        }
        List<String> valueList = Arrays.asList(String.valueOf(sessionValue).split("\\|"));
        String number = valueList.get(0);
        String code = valueList.get(1);
        String time = valueList.get(valueList.size() - 1);
        long checkTime = new Date().getTime();
        // 如果超时验证不通过
        float btTime = (checkTime - Long.parseLong(time)) / (1000);
        // 检测是否频繁发送
        if (Const.LIMIT_TIME < btTime) {
            return new ResultTip(1, "验证码过期，请重新获取！", null);
        }
        // 判断输入手机号码和获取验证码手机号码是否一致
        if (!number.equals(phoneNumber)) {
            return new ResultTip(1, "手机号码修改后，请重新获取短信验证码！", null);
        } else if (!code.equals(verifyCode.trim())) {
            return new ResultTip(1, "验证码输入错误，请重新输入！", null);
        }

        List<Order> orderList = this.orderService.selectUnbindOrders(phoneNumber);
        if (orderList == null || orderList.size() == 0) return new ResultTip(1, "该手机号已绑定", null);
        for (Order order : orderList) {
            order.setBindingPhoneNumber(phoneNumber);
            order.setOpenId(openId);
        }
        this.orderService.updateBatchById(orderList);
//        this.orderService.saveAll(orderList);
        ResultTip resultTip = queryCreditsInfo(openId);
        return new ResultTip(SUCCESS_TIP.getCode(), SUCCESS_TIP.getMessage(), null);
    }

    @ApiOperation("查询积分信息")
    @RequestMapping(value = "/creditsInfo/{openId}", method = RequestMethod.GET)
    @ResponseBody
    public ResultTip queryCreditsInfo(@PathVariable("openId") String openId) {
        if (ToolUtil.isEmpty(openId)) {
            return new ResultTip(BizExceptionEnum.REQUEST_NULL.getCode(), BizExceptionEnum.REQUEST_NULL.getMessage(), null);
        }
        String credits = this.orderService.selectCreditsByOpenId(openId);
        credits = ToolUtil.isEmpty(credits) ? "0" : credits;
        Level maxLevel = this.levelService.getMaxLevel();
        Level level = null;
        double upgrading = 0;
        double position = 0;
        if (maxLevel == null) {
            level = new Level();
            level.setStartIntegral(0);
            level.setEndIntegral(0);
            level.setLevelNum(1);
            position = 0;
        } else if (maxLevel.getEndIntegral() < Long.parseLong(credits)) {
            level = maxLevel;
            position = 100;
        } else {
            level = this.levelService.getByCredit(Integer.parseInt(credits));
            double temp = (Long.parseLong(credits) - level.getStartIntegral());
            double temp1 = (level.getEndIntegral() - level.getStartIntegral());
            double result = temp / temp1;
            upgrading = level.getEndIntegral() - Long.parseLong(credits);
            position = Math.round(result * 100);
        }
        Map<String, Object> map = BeanKit.beanToMap(level);
        map.put("upgrading", upgrading);
        map.put("position", position);
        map.put("credits", credits);
        return new ResultTip(SUCCESS_TIP.getCode(), SUCCESS_TIP.getMessage(), map);
    }

    @ApiOperation("查询已绑定的手机号")
    @RequestMapping(value = "/boundAccounts/{openId}", method = RequestMethod.GET)
    @ResponseBody
    public ResultTip queryBoundAccount(@PathVariable("openId") String openId) {
        if (ToolUtil.isEmpty(openId)) {
            return new ResultTip(BizExceptionEnum.REQUEST_NULL.getCode(), BizExceptionEnum.REQUEST_NULL.getMessage(), null);
        }
        List<String> list = this.orderService.selectBoundAccounts(openId);
        if (list == null || list.size() == 0) {
            return new ResultTip(SUCCESS_TIP.getCode(), BizExceptionEnum.DATA_NULL.getMessage(), null);
        }
        return new ResultTip(200, null, list);
    }

    @ApiOperation("查询openId")
    @RequestMapping(value = "/getOpenId/{code}", method = RequestMethod.GET)
    @ResponseBody
    public ResultTip getOpenId(@PathVariable("code") String code) {
        if (ToolUtil.isEmpty(code)) {
            return new ResultTip(BizExceptionEnum.REQUEST_NULL.getCode(), BizExceptionEnum.REQUEST_NULL.getMessage(), null);
        }
        Map<String, String> param = new HashMap<>();
        param.put("appid", Const.WX_APP_ID);  //开发者设置中的appId
        param.put("secret", Const.WX_APP_SECRET); //开发者设置中的appSecret
        param.put("js_code", code); //小程序调用wx.login返回的code
        param.put("grant_type", "authorization_code");    //默认参数
        String result = HttpKit.sendGet(Const.WX_URL, param);
        logger.info("result--> " + result);
        return new ResultTip(200, null, JSONObject.parseObject(result));
    }

    @ApiOperation("查询积分来源")
    @RequestMapping(value = "/getGroupOrder/{openId}", method = RequestMethod.GET)
    @ResponseBody
    public ResultTip getGroupOrder(@PathVariable("openId") String openId) {
        if (ToolUtil.isEmpty(openId)) {
            return new ResultTip(BizExceptionEnum.DATA_NULL.getCode(), BizExceptionEnum.DATA_NULL.getMessage(), null);
        }
        SourceList sourceList = null;
        try {

            List<Map<String, String>> list = this.orderService.selectGroupOrder(openId);
            if (list == null || list.size() == 0) {
                return new ResultTip(SUCCESS_TIP.getCode(), BizExceptionEnum.DATA_NULL.getMessage(), null);
            }
            sourceList = new SourceList();
            Map<String, SourceData> sourceDataMap = new HashMap<>();
            Map<String, YearData> yearDataMap = new HashMap<>();
            for (Map<String, String> map : list) {

                String finishYear = map.get("finishYear");
                String orderInfo = map.get("orderInfo");
                String finishMonth = map.get("finishMonth");
                String monthUpper = Convert.monthUpperCase(Integer.parseInt(finishMonth));
                SourceData sourceData = (sourceDataMap.containsKey(finishYear) && sourceDataMap.get(finishYear) != null) ? sourceDataMap.get(finishYear) : new SourceData();
                sourceData.setSourceYear(ToolUtil.isEmpty(sourceData.getSourceYear()) ? finishYear : sourceData.getSourceYear());
                YearList yearList = sourceData.getYearList() == null ? new YearList() : sourceData.getYearList();
                YearData<MonthList> yearData = (yearDataMap.containsKey(finishYear + finishMonth) && yearDataMap.get(finishYear + finishMonth) != null) ? yearDataMap.get(finishYear) : new YearData();
                yearData.setSourceMonth(ToolUtil.isEmpty(yearData.getSourceMonth()) ? monthUpper : yearData.getSourceMonth());
                MonthList monthList = yearData.getMonthList() == null ? new MonthList() : yearData.getMonthList();
                List<List<String>> lists = CollectionKit.subListBySize(Arrays.asList(orderInfo.split(",")), 5);
                for (List<String> list1 : lists) {
                    MonthData monthData = new MonthData();
                    monthData.setId(list1.get(0));
                    monthData.setOrderNumber(list1.get(1));
                    monthData.setFinishTime(list1.get(2));
                    monthData.setSalesAmount(list1.get(3));
                    monthData.setShopName(String.valueOf(ShopType.getCodeByText(list1.get(4)).getCode()));
                    monthList.add(monthData);
                }
                yearData.setMonthList(monthList);
                yearDataMap.put(finishYear + finishMonth, yearData);
                yearList.add(yearData);
                sourceData.setYearList(yearList);
                if (sourceList.contains(sourceData)) continue;
                sourceList.add(sourceData);
                sourceDataMap.put(finishYear, sourceData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResultTip(SUCCESS_TIP.getCode(), SUCCESS_TIP.getMessage(), new GroupOrder(sourceList));
    }

    @ApiOperation("查询积分详情")
    @RequestMapping(value = "/getOrderDetail/{orderId}", method = RequestMethod.GET)
    @ResponseBody
    public ResultTip getOrderDetail(@PathVariable("orderId") String orderId) {
        if (ToolUtil.isEmpty(orderId)) {
            return new ResultTip(BizExceptionEnum.REQUEST_NULL.getCode(), BizExceptionEnum.REQUEST_NULL.getMessage(), null);
        }
        Map<String, Object> detailInfoMap = this.orderService.getDetail(orderId);
        return new ResultTip(SUCCESS_TIP.getCode(), SUCCESS_TIP.getMessage(), detailInfoMap);
    }

    @ApiOperation("查询banner")
    @RequestMapping(value = "/getBanners", method = RequestMethod.GET)
    @ResponseBody
    public ResultTip getBanners() {
        ResultTip resultTip = null;
        try {
            List<Map<String, Object>> list = this.goodsService.getGoodsBanner();
            for (Map<String, Object> map : list) {
                String path = String.valueOf(map.get("pic_url"));
                if (ToolUtil.isEmpty(path))continue;
                String url = gunsProperties.getHostPath() + path;
                map.put("pic_url", url);
            }
            resultTip = new ResultTip(SUCCESS_TIP.getCode(), SUCCESS_TIP.getMessage(), list);
        } catch (Exception e) {
            e.printStackTrace();
            resultTip = new ResultTip(BizExceptionEnum.SERVER_ERROR.getCode(), BizExceptionEnum.SERVER_ERROR.getMessage(), null);
        }

        return resultTip;
    }

    @ApiOperation("积分兑换")
    @RequestMapping(value = "/exchangeGoods",method = RequestMethod.POST)
    @ResponseBody
    public synchronized ResultTip exchangeGoods(@RequestBody ExchangeForm exchangeForm,HttpServletRequest request){
        if (exchangeForm == null || exchangeForm.getGoodsId() == 0 || ToolUtil.isEmpty(exchangeForm.getOpenId())){
            return new ResultTip(1,"参数有误",null);
        }
        Goods goods = this.goodsService.selectById(exchangeForm.getGoodsId());
        if (goods == null){
            return new ResultTip(1,"参数有误",null);
        }
        String credits = this.orderService.selectCreditsByOpenId(exchangeForm.getOpenId());
        if (ToolUtil.isEmpty(credits)){
            return new ResultTip(1,"积分余额不足",null);
        }
        int goodsType = goods.getGoodsType();
        int surplusStock = Integer.parseInt(goods.getSurplusStock());
        if (GoodsType.Kind.getCode() == goodsType && (ToolUtil.isEmpty(exchangeForm.getLinkMan()) || ToolUtil.isEmpty(exchangeForm.getAddress())
            || ToolUtil.isEmpty(exchangeForm.getPhoneNumber()) || ToolUtil.isEmpty(exchangeForm.getProvince()) || ToolUtil.isEmpty(exchangeForm.getCity())
            || ToolUtil.isEmpty(exchangeForm.getDistrict()))){
            return new ResultTip(1,"参数有误",null);
        }
        int count = exchangeForm.getCount() == 0 ? 1 : exchangeForm.getCount();
        if (count > surplusStock){
            return  new ResultTip(1,"库存不足",null);
        }
        int actualCredit = Integer.parseInt(goods.getExchangePrice());
        int totalCredit = actualCredit * count;
        if (totalCredit > Integer.parseInt(credits)){
            return  new ResultTip(1,"积分余额不足",null);
        }

        consumeCredit(exchangeForm.getOpenId(),totalCredit);
        String exchangeCode = null;
        if (GoodsType.Coupon.getCode() == goodsType){
            exchangeCode = RandomUtil.getRandomString();
        }
        surplusStock = surplusStock - count;
        goods.setSurplusStock(String.valueOf(surplusStock));
        this.goodsService.updateById(goods);
        DateTime dateTime = new DateTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        ExchangeOrder exchangeOrder = new ExchangeOrder(String.valueOf(System.currentTimeMillis()),totalCredit, DateTimeKit.formatDateTime(dateTime),exchangeCode,exchangeForm.getLinkMan(),exchangeForm.getPhoneNumber(),exchangeForm.getProvince(),exchangeForm.getCity(),exchangeForm.getDistrict(),exchangeForm.getAddress(),exchangeForm.getCode(),exchangeForm.getRemark(),exchangeForm.getGoodsId(),goodsType,exchangeForm.getOpenId(),String.valueOf(year),String.valueOf(month),ExchangeStatus.UnExchanged.getCode());
        this.exchangeOrderService.insert(exchangeOrder);
        String surplusCredits = this.orderService.selectCreditsByOpenId(exchangeForm.getOpenId());
        JSONObject resultJSON = new JSONObject();
        resultJSON.put("exchangeCode",exchangeCode);//兑换优惠券的券码
        resultJSON.put("credits",surplusCredits);//用户剩余积分
        return new ResultTip(SUCCESS_TIP.getCode(),SUCCESS_TIP.getMessage(),resultJSON);
    }

    private void consumeCredit(String openId,int credits){
        List<Order> orderList = this.orderService.selectOrderByOpenId(openId);
        if (orderList == null || orderList.size() == 0) {
            return;
        }

        for (Order order : orderList) {
            if (credits < 0) break;
            String orderCredit = order.getCredits();
            credits = credits - Integer.parseInt(orderCredit);
            Map<String, Object> temp = new HashMap<>();
            temp.put("id", order.getId());
            if (credits < 0) {
                orderCredit = String.valueOf((0 - credits));
                order.setCredits(orderCredit);
            } else {
                order.setCredits("0");
            }
        }
        this.orderService.updateBatchById(orderList);
    }


    @ApiOperation("兑吧免登录")
    @RequestMapping(value = "/duiba/{openId}/{credits}/{signId}", method = RequestMethod.GET)
    @ResponseBody
    public ResultTip authLogin(@PathVariable("openId") String openId, @PathVariable("credits") String credits, @PathVariable("signId") String signId) {
        if (ToolUtil.isEmpty(openId) || ToolUtil.isEmpty(credits) || ToolUtil.isEmpty(signId)) {
            return new ResultTip(BizExceptionEnum.REQUEST_NULL.getCode(), BizExceptionEnum.REQUEST_NULL.getMessage(), null);
        }
        String redirect = null;
        switch (signId) {
            case "record":
                redirect = "https://trade.m.duiba.com.cn/crecord/record";
                break;
            case "auth":
                redirect = null;
                break;
            case "banner1":
                redirect = "https://goods.m.duiba.com.cn/mobile/detail?itemId=31605";
                break;
            case "banner2":
                redirect = "https://goods.m.duiba.com.cn/mobile/appItemDetail?appItemId=1892402";
                break;
            case "banner3":
                redirect = "https://goods.m.duiba.com.cn/mobile/detail?itemId=31605";
                break;
            default:
                Banner banner = this.bannerService.selectById(Integer.parseInt(signId));
                redirect = banner == null ? null : banner.getUrl();
                break;
        }
        String autologinUrl = BulidUrl.buildAutoLoginRequest(openId, Long.parseLong(credits), redirect);
        return new ResultTip(200, null, autologinUrl);
    }


    /**
     * 积分消耗回调
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/consume")
    @ResponseBody
    public Object consume(HttpServletRequest request) {
        CreditTool tool = new CreditTool(Const.DB_APP_KEY, Const.DB_APP_SECRET);
        boolean success = false;
        String errorMessage = "";
        String bizId = null;
        Long surplusCredit = null;
        try {
            CreditConsumeParams params = tool.parseCreditConsume(request);
            logger.info(BeanKit.beanToMap(params).toString());
            bizId = System.currentTimeMillis() + SignTool.genRandomStr(10); //开发者业务订单号，保证唯一不重复
            //credits = getCredits(); // getCredits()是根据开发者自身业务，获取的用户最新剩余积分数。
            Long credits = params.getCredits();
            String openId = params.getUid();
            List<Order> orderList = this.orderService.selectOrderByOpenId(openId);
            if (orderList == null || orderList.size() == 0) {
                return "";
            }

            Duiba duiba = new Duiba();
            List<Map<String, Object>> tempList = new ArrayList<>();
            for (Order order : orderList) {
                if (credits < 0) break;
                String orderCredit = order.getCredits();
                credits = credits - Integer.parseInt(orderCredit);
                Map<String, Object> temp = new HashMap<>();
                temp.put("id", order.getId());
                Long consumeCredits = null;
                if (credits < 0) {
                    consumeCredits = credits + Long.parseLong(orderCredit);
                    orderCredit = String.valueOf((0 - credits));
                    order.setCredits(orderCredit);
                } else {
                    order.setCredits("0");
                    consumeCredits = Long.parseLong(orderCredit);
                }
                temp.put("consumeCredits", consumeCredits);
                duiba.setBizId(bizId);
                duiba.setOrderNumber(params.getOrderNum());
                tempList.add(temp);
            }
            duiba.setData(JSONArray.toJSONString(tempList));
            this.orderService.updateBatchById(orderList);
            this.duibaService.insert(duiba);
            String surplusCredits = this.orderService.selectCreditsByOpenId(openId);
            surplusCredit = ToolUtil.isEmpty(surplusCredits) ? 0 : Long.parseLong(surplusCredits);
            success = true;
        } catch (Exception e) {
            success = false;
            errorMessage = e.getMessage();
            e.printStackTrace();
        }
        CreditConsumeResult ccr = new CreditConsumeResult(success);
        ccr.setBizId(bizId);
        ccr.setErrorMessage(errorMessage);
        ccr.setCredits(surplusCredit);
        logger.info(ccr.toString());
        return JSONObject.parseObject(ccr.toString());//返回扣积分结果json信息
    }

    /**
     * 兑换结果回调
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/notify")
    @ResponseBody
    public String creditsNotify(HttpServletRequest request) {
        CreditTool tool = new CreditTool(Const.DB_APP_KEY, Const.DB_APP_SECRET);
        try {
            CreditNotifyParams params = tool.parseCreditNotify(request);//利用tool来解析这个请求
            logger.info(BeanKit.beanToMap(params).toString());
            String orderNum = params.getOrderNum();
            if (ToolUtil.isEmpty(orderNum)) return "failure";
            Duiba duiba = this.duibaService.selectByOrderNum(orderNum);
            if (duiba == null || ToolUtil.isNotEmpty(duiba.getStatus())) return "failure";
            if (params.isSuccess()) {
                //兑换成功
                duiba.setStatus("success");
            } else {
                //兑换失败，根据orderNum，对用户的金币进行返还，回滚操作
                duiba.setStatus("failure");
                String data = duiba.getData();
                JSONArray jsonArray = JSONArray.parseArray(data);
                List<Long> idList = new ArrayList<>();
                Map<Long, Object> tempMap = new HashMap<>();
                for (Object object : jsonArray) {
                    JSONObject jsonObject = (JSONObject) object;
                    idList.add(jsonObject.getLong("id"));
                    tempMap.put(jsonObject.getLong("id"), jsonObject.get("consumeCredits"));
                }
                List<Order> orderList = this.orderService.selectBatchIds(idList);
                if (orderList == null || orderList.size() == 0) return "failure";
                for (Order order : orderList) {
                    order.setCredits(String.valueOf(tempMap.get(order.getId())));
                }
                this.orderService.updateBatchById(orderList);
            }
            this.duibaService.updateById(duiba);
        } catch (Exception e) {
            e.printStackTrace();
            return "failure";
        }
        return "ok";
    }
    @ApiOperation("查询商品")
    @RequestMapping(value = "/goods", method = RequestMethod.GET)
    @ResponseBody
    public ResultTip queryGoods(@ApiParam(value = "商品标题", required = false)@RequestParam(value = "goodsTitle", required = false)String goodsTitle) {
        List<Map<String,Object>> list = this.goodsService.queryGoods(goodsTitle);
        if (list != null && list.size() != 0){
            for (Map<String,Object> map : list){
                String logoPic =gunsProperties.getHostPath() + map.get("logoPic");
//                TODO 本地调试先写死
//                String logoPic =gunsProperties.getHostPath() + "ef7182c0-e297-476e-8565-ac1b9d29b9a5.jpg";
                map.put("logoPic",logoPic);
            }
        }
        return new ResultTip(200, null, list == null ? new ArrayList<>() : list);
    }

    @ApiOperation("查询商品详情")
        @RequestMapping(value = "/goodsDetail/{goodsId}",method = RequestMethod.GET)
    @ResponseBody
    public ResultTip queryGoodsDetail(@PathVariable("goodsId")Integer goodsId){
        Goods goods = this.goodsService.selectById(goodsId);
        if (goods == null){
            return new ResultTip(1,"未找到对应商品",null);
        }
//        goods.setLogoPic(gunsProperties.getHostPath() + "ef7182c0-e297-476e-8565-ac1b9d29b9a5.jpg");
        goods.setLogoPic(gunsProperties.getHostPath() +goods.getLogoPic());
        return new ResultTip(SUCCESS_TIP.getCode(),SUCCESS_TIP.getMessage(),goods);
    }

    @ApiOperation("查询兑换订单")
    @RequestMapping(value = "/exchangeOrders/{openId}",method = RequestMethod.GET)
    @ResponseBody
    public ResultTip queryExchangeOrders(@PathVariable("openId") String openId){
        if (ToolUtil.isEmpty(openId)){
            return new ResultTip(1,"参数有误",null);
        }
        SourceList sourceList = null;
        try {
            List<Map<String,Object>> list = this.exchangeOrderService.queryOrdersByOpenId(openId);
            if (list == null || list.size() == 0) {
                return new ResultTip(SUCCESS_TIP.getCode(), BizExceptionEnum.DATA_NULL.getMessage(), null);
            }
            sourceList = new SourceList();
            Map<String, SourceData> sourceDataMap = new HashMap<>();
            Map<String, YearData> yearDataMap = new HashMap<>();
            for (Map<String, Object> map : list) {
                String exchangeYear = String.valueOf(map.get("exchangeYear"));
                String exchangeOrderInfo = String.valueOf(map.get("exchangeOrderInfo"));
                String exchangeMonth = String.valueOf(map.get("exchangeMonth"));
                String monthUpper = Convert.monthUpperCase(Integer.parseInt(exchangeMonth));
                SourceData sourceData = (sourceDataMap.containsKey(exchangeYear) && sourceDataMap.get(exchangeYear) != null) ? sourceDataMap.get(exchangeYear) : new SourceData();
                sourceData.setSourceYear(ToolUtil.isEmpty(sourceData.getSourceYear()) ? exchangeYear : sourceData.getSourceYear());
                YearList yearList = sourceData.getYearList() == null ? new YearList() : sourceData.getYearList();
                YearData<MonthOrderList> yearData = (yearDataMap.containsKey(exchangeYear + exchangeMonth) && yearDataMap.get(exchangeYear + exchangeMonth) != null) ? yearDataMap.get(exchangeYear) : new YearData();
                yearData.setSourceMonth(ToolUtil.isEmpty(yearData.getSourceMonth()) ? monthUpper : yearData.getSourceMonth());
                MonthOrderList monthOrderList = yearData.getMonthList() == null ? new MonthOrderList(): yearData.getMonthList();
                List<List<String>> lists = CollectionKit.subListBySize(Arrays.asList(exchangeOrderInfo.split(",")), 5);
                for (List<String> list1 : lists) {
                    MonthOrderData monthOrderData = new MonthOrderData();
                    monthOrderData.setId(list1.get(0));
                    monthOrderData.setCredits(list1.get(1));
                    monthOrderData.setExchangeTime(list1.get(2));
                    monthOrderData.setGoodsTitle(list1.get(3));
                    monthOrderData.setLogoPic(gunsProperties.getHostPath() + list1.get(4));
//                    monthOrderData.setLogoPic(gunsProperties.getHostPath() + "ef7182c0-e297-476e-8565-ac1b9d29b9a5.jpg");
                    monthOrderList.add(monthOrderData);
                }
                yearData.setMonthList(monthOrderList);
                yearDataMap.put(exchangeYear + exchangeMonth, yearData);
                yearList.add(yearData);
                sourceData.setYearList(yearList);
                if (sourceList.contains(sourceData)) continue;
                sourceList.add(sourceData);
                sourceDataMap.put(exchangeYear, sourceData);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return new ResultTip(SUCCESS_TIP.getCode(),SUCCESS_TIP.getMessage(),new GroupOrder(sourceList));
    }

    @ApiOperation("查询兑换订单")
    @RequestMapping(value = "/exchangeOrderDetail/{exchangeOrderId}",method = RequestMethod.GET)
    @ResponseBody
    public ResultTip queryExchangeOrderDetail(@PathVariable("exchangeOrderId") Integer exchangeOrderId){
        if (ToolUtil.isEmpty(exchangeOrderId)){
            return new ResultTip(1,"参数有误",null);
        }
        ExchangeOrder exchangeOrder = this.exchangeOrderService.selectById(exchangeOrderId);
        if (exchangeOrder ==null){
            return  new ResultTip(1,"参数有误",null);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("number",exchangeOrder.getNumber());
        jsonObject.put("goodsType",exchangeOrder.getGoodsType());
        if(exchangeOrder.getGoodsType() == GoodsType.Coupon.getCode()){
            jsonObject.put("exchangeStatus",exchangeOrder.getExchangeStatus());
            jsonObject.put("exchangeCode",exchangeOrder.getExchangeCode());
        }else{
            jsonObject.put("linkman",exchangeOrder.getLinkMan());
            jsonObject.put("phoneNumber",exchangeOrder.getPhoneNumber());
            jsonObject.put("detailAddress",exchangeOrder.getProvince() + " " + exchangeOrder.getCity() + " " + exchangeOrder.getDistrict() + " " + exchangeOrder.getAddress());
            jsonObject.put("express",ToolUtil.isEmpty(exchangeOrder.getExpress()) ? "暂无数据": exchangeOrder.getExpress());//快递公司
            jsonObject.put("expressNumber",ToolUtil.isEmpty(exchangeOrder.getExpressNumber()) ? "暂无数据": exchangeOrder.getExpressNumber());//快递单号
        }
        return new ResultTip(SUCCESS_TIP.getCode(),SUCCESS_TIP.getMessage(),jsonObject);
    }


}
