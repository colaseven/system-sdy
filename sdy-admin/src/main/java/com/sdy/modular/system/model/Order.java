package com.sdy.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * 订单表
 */
@TableName("sys_order")
public class Order extends Model<Order> {


    private static final long serialVersionUID = 1L;


    @TableId(value="id", type= IdType.AUTO)
    private long id;

    /**
     * 商铺名称
     */
    private String shopName;

    /**
     * 订单编号
     */
    private String orderNumber;

    /**
     * 系统编号
     */
    private String systemNumber;

    /**
     * 订单标记
     */
    private String orderMark;

    /**
     * 实付总计
     */
    private String totalPayment;

    /**
     * 优惠总计
     */
    private String discountTotal;

    /**
     * 仓库编码
     */
    private String  repertoryNumber;

    /**
     * 仓库名称
     */
    private String repertoryName;

    /**
     *卖家留言
     */
    private String buyerMessage;

    /**
     * 备注
     */
    private String remark;

    /**
     * 打印备注
     */
    private String printRemark;

    /**
     * 系统订单状态
     */
    private String systemOrderStatus;

    /**
     * 线上订单状态
     */
    private String onlineOrderStatus;

    /**
     * 批次流水号
     */
    private String batchNumber;

    /**
     * 明细状态
     */
    private String detailStatus;

    /**
     * 原订单号
     */
    private String oldOrderNumber;

    /**
     * 商品编码
     */
    private String goodsCode;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 规格名称
     */
    private String formName;

    /**
     * 线上商品编码
     */
    private String onlineGoodsCode;

    /**
     * 线上商品名称
     */
    private String onlineGoodsName;

    /**
     * 线上规格
     */
    private String onlineFormName;

    /**
     * 单价
     */
    private String singlePrice;

    /**
     * 折后单价
     */
    private String discountSinglePrice;

    /**
     * 数量
     */
    private String countNum;

    /**
     * 单位
     */
    private String unit;

    /**
     * 应收
     */
    private String receivableAmount;

    /**
     * 销售金额
     */
    private String salesAmount;

    /**
     * 明细备注
     */
    private String detailRemark;

    /**
     * 发票抬头
     */
    private String invoiceTitle;

    /**
     * 开票内容
     */
    private String invoiceContent;

    /**
     * 开户银行
     */
    private String openingBank;

    /**
     * 银行账户
     */
    private String bankAccount;

    /**
     *开票税号
     */
    private String invoiceTaxNumber;

    /**
     * 开票地址
     */
    private String invoiceAddress;

    /**
     * 开票电话
     */
    private String invoicePhoneNumber;

    /**
     * 开票邮箱
     */
    private String invoiceEmail;

    /**
     * 快递公司
     */
    private String express;

    /**
     * 快递单号
     */
    private String expressNumber;

    /**
     * 快递成本
     */
    private String expressCost;

    /**
     * 重量
     */
    private String weight;

    /**
     * 体积
     */
    private String volume;

    /**
     * 邮费
     */
    private String postage;

    /**
     * 服务费
     */
    private String serviceCharge;

    /**
     * 账号
     */
    private String account;

    /**
     * 收货人
     */
    private String consignee;

    /**
     * 手机
     */
    private String phoneNumber;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String district;

    /**
     * 详细地址
     */
    private String detailAddress;

    /**
     * 邮编
     */
    private String zipCode;

    /**
     * 下单时间
     */
    private String orderTime;

    /**
     * 付款时间
     */
    private String paymentTime;

    /**
     * 打单时间
     */
    private String singleTime;

    /**
     * 发货时间
     */
    private String deliveryTime;

    /**
     * 完成时间
     */
    private String finishTime;

    /**
     * 交易佣金
     */
    private String commission;

    /**
     * 信用卡佣金
     */
    private String creditCardCommission;

    /**
     * 返点积分
     */
    private String rebateIntegral;

    /**
     * 积分
     */
    private String credits;

    /**
     * 审单员
     */
    private String checker;

    /**
     * 打单员
     */
    private String singlePlayer;

    /**
     * 配货员
     */
    private String distributor;

    /**
     * 验货员
     */
    private String inspector;

    /**
     * 打包员
     */
    private String baler;

    /**
     * 称重员
     */
    private String weigher;

    /**
     * 发货员
     */
    private String shipper;

    /**
     * 业务员
     */
    private String salesman;

    /**
     * 绑定手机号
     */
    private String bindingPhoneNumber;

    /**
     * openId
     */
    private String openId;

    /**
     * 完成年份
     */
    private String finishYear;

    /**
     * 完成月份
     */
    private String finishMonth;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getSystemNumber() {
        return systemNumber;
    }

    public void setSystemNumber(String systemNumber) {
        this.systemNumber = systemNumber;
    }

    public String getOrderMark() {
        return orderMark;
    }

    public void setOrderMark(String orderMark) {
        this.orderMark = orderMark;
    }

    public String getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(String totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getDiscountTotal() {
        return discountTotal;
    }

    public void setDiscountTotal(String discountTotal) {
        this.discountTotal = discountTotal;
    }

    public String getRepertoryNumber() {
        return repertoryNumber;
    }

    public void setRepertoryNumber(String repertoryNumber) {
        this.repertoryNumber = repertoryNumber;
    }

    public String getRepertoryName() {
        return repertoryName;
    }

    public void setRepertoryName(String repertoryName) {
        this.repertoryName = repertoryName;
    }

    public String getBuyerMessage() {
        return buyerMessage;
    }

    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPrintRemark() {
        return printRemark;
    }

    public void setPrintRemark(String printRemark) {
        this.printRemark = printRemark;
    }

    public String getSystemOrderStatus() {
        return systemOrderStatus;
    }

    public void setSystemOrderStatus(String systemOrderStatus) {
        this.systemOrderStatus = systemOrderStatus;
    }

    public String getOnlineOrderStatus() {
        return onlineOrderStatus;
    }

    public void setOnlineOrderStatus(String onlineOrderStatus) {
        this.onlineOrderStatus = onlineOrderStatus;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getDetailStatus() {
        return detailStatus;
    }

    public void setDetailStatus(String detailStatus) {
        this.detailStatus = detailStatus;
    }

    public String getOldOrderNumber() {
        return oldOrderNumber;
    }

    public void setOldOrderNumber(String oldOrderNumber) {
        this.oldOrderNumber = oldOrderNumber;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getOnlineGoodsCode() {
        return onlineGoodsCode;
    }

    public void setOnlineGoodsCode(String onlineGoodsCode) {
        this.onlineGoodsCode = onlineGoodsCode;
    }

    public String getOnlineGoodsName() {
        return onlineGoodsName;
    }

    public void setOnlineGoodsName(String onlineGoodsName) {
        this.onlineGoodsName = onlineGoodsName;
    }

    public String getOnlineFormName() {
        return onlineFormName;
    }

    public void setOnlineFormName(String onlineFormName) {
        this.onlineFormName = onlineFormName;
    }

    public String getSinglePrice() {
        return singlePrice;
    }

    public void setSinglePrice(String singlePrice) {
        this.singlePrice = singlePrice;
    }

    public String getDiscountSinglePrice() {
        return discountSinglePrice;
    }

    public void setDiscountSinglePrice(String discountSinglePrice) {
        this.discountSinglePrice = discountSinglePrice;
    }

    public String getCountNum() {
        return countNum;
    }

    public void setCountNum(String countNum) {
        this.countNum = countNum;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getReceivableAmount() {
        return receivableAmount;
    }

    public void setReceivableAmount(String receivableAmount) {
        this.receivableAmount = receivableAmount;
    }

    public String getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(String salesAmount) {
        this.salesAmount = salesAmount;
    }

    public String getDetailRemark() {
        return detailRemark;
    }

    public void setDetailRemark(String detailRemark) {
        this.detailRemark = detailRemark;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public String getOpeningBank() {
        return openingBank;
    }

    public void setOpeningBank(String openingBank) {
        this.openingBank = openingBank;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getInvoiceTaxNumber() {
        return invoiceTaxNumber;
    }

    public void setInvoiceTaxNumber(String invoiceTaxNumber) {
        this.invoiceTaxNumber = invoiceTaxNumber;
    }

    public String getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(String invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    public String getInvoicePhoneNumber() {
        return invoicePhoneNumber;
    }

    public void setInvoicePhoneNumber(String invoicePhoneNumber) {
        this.invoicePhoneNumber = invoicePhoneNumber;
    }

    public String getInvoiceEmail() {
        return invoiceEmail;
    }

    public void setInvoiceEmail(String invoiceEmail) {
        this.invoiceEmail = invoiceEmail;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public String getExpressCost() {
        return expressCost;
    }

    public void setExpressCost(String expressCost) {
        this.expressCost = expressCost;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getPostage() {
        return postage;
    }

    public void setPostage(String postage) {
        this.postage = postage;
    }

    public String getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getSingleTime() {
        return singleTime;
    }

    public void setSingleTime(String singleTime) {
        this.singleTime = singleTime;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getCreditCardCommission() {
        return creditCardCommission;
    }

    public void setCreditCardCommission(String creditCardCommission) {
        this.creditCardCommission = creditCardCommission;
    }

    public String getRebateIntegral() {
        return rebateIntegral;
    }

    public void setRebateIntegral(String rebateIntegral) {
        this.rebateIntegral = rebateIntegral;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getSinglePlayer() {
        return singlePlayer;
    }

    public void setSinglePlayer(String singlePlayer) {
        this.singlePlayer = singlePlayer;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public String getBaler() {
        return baler;
    }

    public void setBaler(String baler) {
        this.baler = baler;
    }

    public String getWeigher() {
        return weigher;
    }

    public void setWeigher(String weigher) {
        this.weigher = weigher;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    public String getBindingPhoneNumber() {
        return bindingPhoneNumber;
    }

    public void setBindingPhoneNumber(String bindingPhoneNumber) {
        this.bindingPhoneNumber = bindingPhoneNumber;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getFinishYear() {
        return finishYear;
    }

    public void setFinishYear(String finishYear) {
        this.finishYear = finishYear;
    }

    public String getFinishMonth() {
        return finishMonth;
    }

    public void setFinishMonth(String finishMonth) {
        this.finishMonth = finishMonth;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
