package com.sdy.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 积分等级表
 * </p>
 */
@TableName("sys_level")
public class Level extends Model<Level> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 等级
     */
    private Integer levelNum;
    /**
     * 开始积分
     */
    private Integer startIntegral;
    /**
     * 结束积分
     */
    private Integer endIntegral;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(Integer levelNum) {
        this.levelNum = levelNum;
    }

    public Integer getStartIntegral() {
        return startIntegral;
    }

    public void setStartIntegral(Integer startIntegral) {
        this.startIntegral = startIntegral;
    }

    public Integer getEndIntegral() {
        return endIntegral;
    }

    public void setEndIntegral(Integer endIntegral) {
        this.endIntegral = endIntegral;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Level{" +
        "id=" + id +
        ", levelNum=" + levelNum +
        ", startIntegral=" + startIntegral +
        ", endIntegral=" + endIntegral +
        "}";
    }
}
