package com.sdy.modular.system.controller;

import com.sdy.core.base.controller.BaseController;
import com.sdy.core.common.constant.Const;
import com.sdy.core.common.constant.dictmap.LevelDict;
import com.sdy.core.log.LogObjectHolder;
import com.sdy.core.support.BeanKit;
import com.sdy.modular.system.warpper.LevelWarpper;
import com.sdy.core.common.annotion.BussinessLog;
import com.sdy.core.common.annotion.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import com.sdy.modular.system.model.Level;
import com.sdy.modular.system.service.ILevelService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制器
 */
@Controller
@RequestMapping("/level")
public class LevelController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(LevelController.class);

    private String PREFIX = "/system/level/";

    @Autowired
    private ILevelService levelService;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "level.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/level_add")
    public String levelAdd(Model model) {
        List<Map<String, Object>> list = this.levelService.list();
        Level level = new Level();
        if (list == null || list.size() == 0) {
            level.setLevelNum(1);
            level.setStartIntegral(0);
        } else {
            Level lastLevel = BeanKit.mapToBean(list.get(list.size() - 1), Level.class);
            level.setLevelNum(list.size() + 1);
            level.setStartIntegral(lastLevel.getEndIntegral() + 1);
        }
        model.addAttribute(level);
        LogObjectHolder.me().set(level);
        return PREFIX + "level_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/level_update/{levelId}")
    public String levelUpdate(@PathVariable Integer levelId, Model model) {
        Level level = levelService.selectById(levelId);
        model.addAttribute(level);
        LogObjectHolder.me().set(level);
        return PREFIX + "level_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list() {
        List<Map<String, Object>> list = this.levelService.list();
        return super.warpObject(new LevelWarpper(list));
    }

    /**
     * 获取列表描述
     */

    @RequestMapping(value = "/count")
    @ResponseBody
    public int count() {
        List<Map<String, Object>> list = this.levelService.list();
        return list == null || list.size() == 0 ? 1 : list.size() + 1;
    }

    /**
     * 新增
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Level level) {
        levelService.insert(level);
        return SUCCESS_TIP;
    }

    /**
     * 删除
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer levelId) {
        levelService.deleteById(levelId);
        return SUCCESS_TIP;
    }

    /**
     * 修改
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping(value = "/edit")
    @BussinessLog(value = "修改等级", key = "levelNum", dict = LevelDict.class)
    @ResponseBody
    public Object update(Level level) {
        genLevel(level);
        levelService.updateById(level);
        return SUCCESS_TIP;
    }

    private void genLevel(Level level) {
        int endIntegral = level.getEndIntegral();
        Level oldLevel = this.levelService.selectById(level.getId());
        Level tempLevel = null;
        if (oldLevel.getEndIntegral() > endIntegral) {
            Map<String, Object> map = new HashMap<>();
            map.put("levelNum", level.getLevelNum() + 1);
            List<Level> levelList = this.levelService.selectByMap(map);
            if (levelList == null || levelList.size() == 0) return;
            tempLevel = levelList.get(0);
            tempLevel.setStartIntegral(endIntegral + 1);
        } else {
            Level maxLevel = this.levelService.getMaxLevel();
            tempLevel = this.levelService.getByIntegral(endIntegral);
            if (tempLevel == null) {
                Map<String, Object> map = new HashMap<>();
                map.put("levelNum", level.getLevelNum() + 1);
                List<Level> levelList = this.levelService.selectByMap(map);
                if (levelList == null || levelList.size() == 0) return;
                tempLevel = levelList.get(0);
            }
            if (tempLevel.getLevelNum() == level.getLevelNum() + 1 && endIntegral < maxLevel.getEndIntegral()) {
                tempLevel.setStartIntegral(endIntegral + 1);
            } else if (maxLevel.getEndIntegral() <= endIntegral && maxLevel.getLevelNum() != level.getLevelNum()) {
                this.levelService.deleteByIntegral(endIntegral, level.getId(), level.getLevelNum());
            } else {
                this.levelService.deleteByIntegral(endIntegral, level.getId(), level.getLevelNum());
                tempLevel.setStartIntegral(endIntegral + 1);
                tempLevel.setLevelNum(level.getLevelNum() + 1);
                List<Level> levelList = this.levelService.selectByLevel(tempLevel.getLevelNum());
                if (levelList != null && levelList.size() != 0) {
                    int newNumber = tempLevel.getLevelNum() + 1;
                    for (Level tempLevel1 : levelList) {
                        tempLevel1.setLevelNum(newNumber);
                        newNumber++;
                    }
                    this.levelService.updateBatchById(levelList);
                }
            }
        }
        if (tempLevel != null) {
            this.levelService.updateById(tempLevel);
        }

    }

//    private void genLevel(Level level){
//        int endIntegral = level.getEndIntegral();
//        Level tempLevel = this.levelService.getByIntegral(endIntegral);
//        Level maxLevel = this.levelService.getMaxLevel();
//        Level level1 = null;
//        if ((maxLevel.getEndIntegral() <= endIntegral || (tempLevel != null && tempLevel.getLevelNum() != (level.getLevelNum() + 1))) && maxLevel.getLevelNum() != level.getLevelNum()){
//            level1 = tempLevel;
//            this.levelService.deleteByIntegral(endIntegral,level.getId());
//            level1.setLevelNum(level.getLevelNum() + 1);
//            level1.setStartIntegral(endIntegral + 1);
//        }else if(maxLevel.getLevelNum() == level.getLevelNum()){
//            level1 = level;
//        } else{
//            level1 = this.levelService.getByIntegral(endIntegral);
//
//            if (level1 != null) {
//                int levelNum = level.getLevelNum();
//                level1.setLevelNum(levelNum + 1);
//            } else {
//                Map<String, Object> map = new HashMap<>();
//                map.put("levelNum", level.getLevelNum() + 1);
//                List<Level> levelList = this.levelService.selectByMap(map);
//                if (levelList == null || levelList.size() == 0) return;
//                level1 = levelList.get(0);
//            }
//            level1.setStartIntegral(endIntegral + 1);
//        }
//        this.levelService.updateById(level1);
//
//    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{levelId}")
    @ResponseBody
    public Object detail(@PathVariable("levelId") Integer levelId) {
        return levelService.selectById(levelId);
    }
}
