package com.sdy.jwt;

import com.alibaba.fastjson.JSON;
import com.sdy.core.util.MD5Util;
import com.sdy.rest.common.SimpleObject;
import com.sdy.rest.modular.auth.converter.BaseTransferEntity;
import com.sdy.rest.modular.auth.security.impl.Base64SecurityAction;

/**
 * jwt测试
 */
public class DecryptTest {

    public static void main(String[] args) {

        String salt = "0iqwhi";

        SimpleObject simpleObject = new SimpleObject();
        simpleObject.setUser("stylefeng");
        simpleObject.setAge(12);
        simpleObject.setName("ffff");
        simpleObject.setTips("code");

        String jsonString = JSON.toJSONString(simpleObject);
        String encode = new Base64SecurityAction().doAction(jsonString);
        String md5 = MD5Util.encrypt(encode + salt);

        BaseTransferEntity baseTransferEntity = new BaseTransferEntity();
        baseTransferEntity.setObject(encode);
        baseTransferEntity.setSign(md5);

        System.out.println(JSON.toJSONString(baseTransferEntity));
    }
}
