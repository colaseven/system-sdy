package com.sdy.core.duiba;

import com.sdy.core.common.constant.Const;
import com.sdy.core.duiba.entity.CreditAuditParams;
import com.sdy.core.duiba.entity.ExpressInfo;
import com.sdy.core.duiba.entity.CreditConfirmParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *         工具类是开发者构造一些请求的url工具类，仅供参考
 */

public class BulidUrl {

    static String AppKey = Const.DB_APP_KEY;  //此处填写开发者自己的appKey
    static String AppSecret = Const.DB_APP_SECRET; //此处填写开发者自己的appSecret

    /**
     * 构建在兑吧商城自动登录的url地址
     *
     * @param uid      用户id
     * @param redirect 免登陆接口回传回来 dbredirect参数
     * @param credits  用户积分余额
     * @return 自动登录的url地址
     */
    public static String buildAutoLoginRequest(String uid, Long credits, String redirect) {
        CreditTool tool = new CreditTool(AppKey, AppSecret);
        String url = Const.DUIBA_URL + "/autoLogin/autologin?";
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("credits", credits + "");
        if (redirect != null) {
            params.put("redirect", redirect);
        }
        String autologinUrl = tool.buildUrlWithSign(url, params);
        return autologinUrl;
    }


    /**
     * 构建向兑吧查询兑换订单状态的url地址
     *
     * @param orderNum 兑吧的订单号
     * @return
     */
    public static String buildCreditOrderStatusRequest(String orderNum, String bizId) {
        CreditTool tool = new CreditTool(AppKey, AppSecret);
        if (orderNum == null) {
            orderNum = "";
        }
        if (bizId == null) {
            bizId = "";
        }
        String url = Const.DUIBA_URL + "/status/orderStatus?";
        Map<String, String> params = new HashMap<String, String>();
        params.put("orderNum", orderNum);
        params.put("bizId", bizId);
        String buildUrl = tool.buildUrlWithSign(url, params);
        return buildUrl;
    }

    /**
     * 构建开发者审核结果的的方法
     *
     * @param params
     * @return 发起请求的url
     */
    public static String buildCreditAuditRequest(CreditAuditParams params) {
        CreditTool tool = new CreditTool(AppKey, AppSecret);
        String url = Const.DUIBA_URL + "/audit/apiAudit?";
        Map<String, String> signParams = new HashMap<String, String>();
        if (params.getPassOrderNums() != null && params.getPassOrderNums().size() > 0) {
            String s = null;
            for (String o : params.getPassOrderNums()) {
                if (s == null) {
                    s = o;
                } else {
                    s += "," + o;
                }
            }
            signParams.put("passOrderNums", s);
        } else {
            signParams.put("passOrderNums", "");
        }
        if (params.getRejectOrderNums() != null && params.getRejectOrderNums().size() > 0) {
            String s = null;
            for (String o : params.getRejectOrderNums()) {
                if (s == null) {
                    s = o;
                } else {
                    s += "," + o;
                }
            }
            signParams.put("rejectOrderNums", s);
        } else {
            signParams.put("rejectOrderNums", "");
        }
        String buildUrl = tool.buildUrlWithSign(url, signParams);
        return buildUrl;
    }

    /**
     * 构建开发者向兑吧发起兑换成功失败的确认通知请求
     *
     * @param p
     * @return
     */
    public static String buildCreditConfirmRequest(CreditConfirmParams p) {
        CreditTool tool = new CreditTool(AppKey, AppSecret);
        String url = Const.DUIBA_URL + "/confirm/confirm?";
        Map<String, String> params = new HashMap<String, String>();
        params.put("success", p.isSuccess() + "");
        params.put("errorMessage", p.getErrorMessage());
        params.put("orderNum", p.getOrderNum());
        String bulidurl = tool.buildUrlWithSign(url, params);
        return bulidurl;
    }


    /**
     * 前置商品查询URL
     *
     * @param count 查询返回的商品数量，最大支持50个
     * @return
     */
    public static String queryForFrontItem(String count) {
        CreditTool tool = new CreditTool(AppKey, AppSecret);
        Map<String, String> params = new HashMap<String, String>();
        params.put("count", count);
        String url = tool.buildUrlWithSign(
                Const.DUIBA_URL + "/queryForFrontItem/query?", params);
        return url;
    }


    /**
     * 构建向兑吧请求增加活动次数的url地址
     *
     * @param uid ：用户id   activityId:活动id, times:增加活动次数, bizId：本次请求开发者订单号，保证唯一性
     * @return
     */

    public static String getActivityTimes(String uid, String activityId, String times, String bizId) {
        CreditTool tool = new CreditTool(AppKey, AppSecret);
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", uid);
        params.put("bizId", bizId);
        params.put("activityId", activityId);
        params.put("times", times);
        String url = tool.buildUrlWithSign(
                "https://activity.m.duiba.com.cn/activityVist/addTimes?", params);
        return url;

    }

    /**
     * 自有商品批量取消发货
     *
     * @param orderNums 最大支持100个
     *                  方法中，对于超过100的会自动截取前100个
     * @return
     */
    public static String batchCancel(List<String> orderNums) {
        CreditTool tool = new CreditTool(AppKey, AppSecret);
        Map<String, String> params = new HashMap<String, String>();
        if (orderNums.size() > 100) {
            orderNums = orderNums.subList(0, 100);
        }
        params.put("orderNums", orderNums.toString().substring(1, orderNums.toString().length() - 1));
        String url = tool.buildUrlWithSign(
                Const.DUIBA_URL + "/sendObject/batchCancel?", params);
        return url;
    }

    /**
     * 自有商品批量发货
     *
     * @return
     * @params info 格式如下
     * 发货的数量，每次请求不超过100个
     * 方法中，对于超过100的会自动截取前100个
     */
    public static String batchSend(List<ExpressInfo> infos) {
        CreditTool tool = new CreditTool(AppKey, AppSecret);
        Map<String, String> params = new HashMap<String, String>();
        StringBuffer expressInfo = new StringBuffer();
        if (infos.size() > 100) {
            infos = infos.subList(0, 100);
        }
        for (ExpressInfo info : infos) {
            expressInfo.append(info);
        }
        expressInfo.deleteCharAt(expressInfo.length() - 1);
        params.put("expressInfo", expressInfo.toString());
        String url = tool.buildUrlWithSign(
                Const.DUIBA_URL + "/sendObject/batchSend?", params);
        return url;
    }
}