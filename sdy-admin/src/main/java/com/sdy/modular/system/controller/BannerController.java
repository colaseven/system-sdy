package com.sdy.modular.system.controller;

import com.sdy.config.properties.GunsProperties;
import com.sdy.core.base.controller.BaseController;
import com.sdy.core.util.FileUtil;
import com.sdy.modular.system.service.IBannerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.sdy.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.sdy.modular.system.model.Banner;


@Controller
@RequestMapping("/banner")
public class BannerController extends BaseController {

    private String PREFIX = "/system/banner/";

    @Autowired
    private IBannerService bannerService;


    @Autowired
    private GunsProperties gunsProperties;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "banner.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/banner_add")
    public String bannerAdd() {
        return PREFIX + "banner_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/banner_update/{bannerId}")
    public String bannerUpdate(@PathVariable Integer bannerId, Model model) {
        Banner banner = bannerService.selectById(bannerId);
        model.addAttribute("item", banner);
        LogObjectHolder.me().set(banner);
        return PREFIX + "banner_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return bannerService.selectList(null);
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Banner banner) {
        bannerService.insert(banner);
        return SUCCESS_TIP;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer bannerId) {
        Banner oldBanner = this.bannerService.selectById(bannerId);
        String path = oldBanner.getPath();
        FileUtil.delete(gunsProperties.getFileUploadPath() + path);
        bannerService.deleteById(bannerId);
        return SUCCESS_TIP;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Banner banner) {
        Banner oldBanner = this.bannerService.selectById(banner.getId());
        String path = oldBanner.getPath();
        if (!banner.getPath().equalsIgnoreCase(path)) {
            FileUtil.delete(gunsProperties.getFileUploadPath() + path);
        }
        bannerService.updateById(banner);
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{bannerId}")
    @ResponseBody
    public Object detail(@PathVariable("bannerId") Integer bannerId) {
        return bannerService.selectById(bannerId);
    }
}
