package com.sdy.core.common.constant;

/**
 * 系统常量
 */
public interface Const {

    /**
     * 系统默认的管理员密码
     */
    String DEFAULT_PWD = "111111";

    /**
     * 管理员角色的名字
     */
    String ADMIN_NAME = "administrator";

    /**
     * 管理员id
     */
    Integer ADMIN_ID = 1;

    /**
     * 超级管理员角色id
     */
    Integer ADMIN_ROLE_ID = 1;

    /**
     * 接口文档的菜单名
     */
    String API_MENU_NAME = "接口文档";

//    String WX_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    String WX_URL = "https://api.weixin.qq.com/sns/jscode2session";

    String WX_APP_ID = "wx033f009134b50671";
//    String WX_APP_ID = "wx033f009134b50234";

    String WX_APP_SECRET = "a12dfdc5eb67f0bde793836719a5b5fb";

    String DB_APP_KEY="4GBBTHarUef1pVb9izNv5TD2TNaz";

    String DB_APP_SECRET="9nq8Xd4GfUcEn65XK22TqhnVbUo";

    String DUIBA_URL = "https://home.m.duiba.com.cn";

    final static float LIMIT_TIME = 120.0f;


}
