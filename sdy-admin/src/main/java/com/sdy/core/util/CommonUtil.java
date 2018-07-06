package com.sdy.core.util;

/**
 * Created by zhangzheng on 2018-5-11.
 */
public class CommonUtil {

    public static String formatMethodName(String methodName) {
        methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
        return methodName;

    }
}
