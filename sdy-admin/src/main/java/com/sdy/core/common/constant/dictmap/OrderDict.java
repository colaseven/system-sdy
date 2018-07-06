package com.sdy.core.common.constant.dictmap;

import com.sdy.core.common.constant.dictmap.base.AbstractDictMap;

public class OrderDict extends AbstractDictMap {
    @Override
    public void init() {
        put("shopName","店铺名称");
        put("orderNumber","订单号");
        put("systemNumber","系统单号");
        put("orderMark","订单标记");
        put("totalPayment","实付总计");
        put("discountTotal","优惠总计");
        put("repertoryNumber","仓库编码");
        put("repertoryName","仓库名称");
        put("buyerMessage","买家留言");
        put("remark","备注");
        put("printRemark","打印备注");
        put("systemOrderStatus","系统订单");
        put("onlineOrderStatus","线上订单");
        put("batchNumber","批次流水");
        put("detailStatus","明细状态");
        put("oldOrderNumber","原订单号");
        put("goodsCode","商品编码");
        put("goodsName","商品名称");
        put("formName","规格名称");
        put("onlineGoodsCode","【线上】商品编码");
        put("onlineGoodsName","【线上】商品标题");
        put("onlineFormName","【线上】规格");
        put("singlePrice","单价");
        put("discountSinglePrice","折后单价");
        put("countNum","数量");
        put("unit","单位");
        put("receivableAmount","应收");
        put("salesAmount","销售金额");
        put("detailRemark","明细备注");
        put("invoiceTitle","发票抬头");
        put("invoiceContent","开票内容");
        put("openingBank","开户银行");
        put("bankAccount","银行账户");
        put("invoiceTaxNumber","开票税号");
        put("invoiceAddress","开票地址");
        put("invoicePhoneNumber","开票电话");
        put("invoiceEmail","开票邮箱");
        put("express","快递公司");
        put("expressNumber","快递单号");
        put("expressCost","快递成本");
        put("weight","重量");
        put("volume","体积");
        put("postage","邮费");
        put("serviceCharge","服务费");
        put("account","帐号");
        put("consignee","收货人");
        put("phoneNumber","手机/电话");
        put("province","省");
        put("city","市");
        put("district","区");
        put("detailAddress","详细地址");
        put("zipCode","邮编");
        put("orderTime","下单时间");
        put("paymentTime","付款时间");
        put("singleTime","打单时间");
        put("deliveryTime","发货时间");
        put("finishTime","完成时间");
        put("commission","交易佣金");
        put("creditCardCommission","信用卡佣金");
        put("rebateIntegral","返点积分");
        put("credits","积分");
        put("checker","审单员");
        put("singlePlayer","打单员");
        put("distributor","配货员");
        put("inspector","验货员");
        put("baler","打包员");
        put("weigher","称重员");
        put("shipper","发货员");
        put("salesman","业务员");
    }

    @Override
    protected void initBeWrapped() {

    }
}
