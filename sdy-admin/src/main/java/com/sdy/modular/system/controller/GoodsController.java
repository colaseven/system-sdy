package com.sdy.modular.system.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.sdy.config.properties.GunsProperties;
import com.sdy.core.base.controller.BaseController;
import com.sdy.core.common.constant.factory.PageFactory;
import com.sdy.core.common.exception.BizExceptionEnum;
import com.sdy.core.exception.GunsException;
import com.sdy.core.support.BeanKit;
import com.sdy.core.support.DateTime;
import com.sdy.core.support.GoodsStatus;
import com.sdy.core.support.GoodsType;
import com.sdy.core.util.FileUtil;
import com.sdy.core.util.ToolUtil;
import com.sdy.modular.system.util.MyTimerTask;
import com.sdy.modular.system.util.Result;
import com.sdy.modular.system.util.ResultUtil;
import com.sdy.modular.system.warpper.GoodsWarpper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.sdy.core.log.LogObjectHolder;
import com.sdy.modular.system.model.Goods;
import com.sdy.modular.system.service.IGoodsService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.*;

/**
 * 控制器
 *
 * @author fengshuonan
 * @Date 2018-06-20 13:42:06
 */
@Controller
@RequestMapping("/goods")
public class GoodsController extends BaseController {


    @Autowired
    private GunsProperties gunsProperties;

    private String PREFIX = "/system/goods/";

    @Autowired
    private IGoodsService goodsService;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "goods.html";
    }

    /**
     * 跳转到选择
     */
    @RequestMapping("/goods_select")
    public String goodsSelect() {
        return PREFIX + "goods_select.html";
    }


    /**
     * 跳转到添加
     */
    @RequestMapping("/goods_add/{goodsType}")
    public String goodsAdd(@PathVariable String goodsType,Model model) {
        Goods goods = new Goods();
        goods.setGoodsType(GoodsType.getCodeByText(goodsType).getCode());
        model.addAttribute("item", goods);
        return PREFIX + "goods_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/goods_update/{goodsId}")
    public String goodsUpdate(@PathVariable Integer goodsId, Model model) {
        Goods goods = goodsService.selectById(goodsId);
        model.addAttribute("item",goods);
        LogObjectHolder.me().set(goods);
        return PREFIX + "goods_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String goodsTitle,String goodsStatus) {
        Page<Goods> page = new PageFactory<Goods>().defaultPage();
        if (ToolUtil.isNotEmpty(goodsTitle) || ToolUtil.isNotEmpty(goodsStatus)){
            page.setCurrent(1);
        }
        if ("0".equals(goodsStatus)){
            goodsStatus = null;
        }
        List<Map<String, Object>> list = this.goodsService.queryPageData(page, goodsTitle, goodsStatus, page.getOrderByField(), page.isAsc());
        page.setRecords((List<Goods>) new GoodsWarpper(list).warp());
        return super.packForBT(page);
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Goods goods) {
        goods.setGoodsStatus(GoodsStatus.Grounding.getCode());
        goodsService.insert(goods);
        return SUCCESS_TIP;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String goodsId) {
        if (ToolUtil.isEmpty(goodsId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        List<String> goodsIdList = Arrays.asList(goodsId.split(","));
        goodsService.deleteBatchIds(goodsIdList);
        return SUCCESS_TIP;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Goods goods) {
        goodsService.updateById(goods);
        return SUCCESS_TIP;
    }

    /**
     * 批量上下架
     * @param goodsId
     * @param sign
     * @return
     */
    @RequestMapping(value = "/changeStatus")
    @ResponseBody
    public Object changeStatus(@RequestParam String goodsId,int sign){
        if (ToolUtil.isEmpty(goodsId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        List<String> goodsIdList = Arrays.asList(goodsId.split(","));
        List<Goods> goodsList = this.goodsService.selectBatchIds(goodsIdList);
        if(goodsList != null && goodsList.size() != 0){
            List<Goods> saveList = new ArrayList<>();
            for (Goods goods : goodsList){
                if (goods.getGoodsStatus() == sign)continue;
                goods.setGoodsStatus(sign);
                saveList.add(goods);
            }
            this.goodsService.updateBatchById(goodsList);
        }
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public Object detail(@PathVariable("goodsId") Integer goodsId) {
        return goodsService.selectById(goodsId);
    }

    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    @ResponseBody
    public Result uploadImg(List<MultipartFile> myFileName, HttpSession session, HttpServletRequest request) throws IllegalStateException, IOException {
        List<String> list = new ArrayList<>();
        String[] strings = new String[list.size()];
//        String realName = "";
        if (myFileName != null && myFileName.size() != 0) {
            for (MultipartFile multipartFile : myFileName) {
                String fileName = multipartFile.getOriginalFilename();
                String fileNameExtension = fileName.substring(fileName.indexOf("."), fileName.length());
                // 生成实际存储的真实文件名

                String realName = UUID.randomUUID().toString() + fileNameExtension;

                // "/upload"是你自己定义的上传目录

                String fileSavePath = gunsProperties.getFileUploadPath();
                File uploadFile = new File(fileSavePath, realName);
                multipartFile.transferTo(uploadFile);
                list.add(gunsProperties.getHostPath() + realName);
//                list.add(request.getContextPath() + "/kaptcha/" + realName);
            }
        }
        return ResultUtil.success(list.toArray(strings));
    }

    @RequestMapping(value = "/deleteFile")
    @ResponseBody
    public Object deleteFile(@RequestParam String path){
        path = path.substring(8);
        FileUtil.delete(gunsProperties.getFileUploadPath() + path);
        return SUCCESS_TIP;
    }
}
