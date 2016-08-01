package com.xgx.syzj.utils;

import android.app.Activity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

/**
 * 作者：黄伟财 on 2015/7/17 11:24
 * 说明：FastJson帮助类
 */
public class FastJsonUtil {

    /**
     * json字符串转换成对象
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> T json2Bean(String jsonString,Class<T> cls){
        T t = null;
        try {
            t = JSON.parseObject(jsonString,cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 对象转换成json字符串
     * @param obj
     * @return
     */
    public static String bean2Json(Object obj){
        return JSON.toJSONString(obj);
    }


    /**
     * json字符串转换成List集合
     * @param jsonString
     * @return
     */
    public static <T>List<T> json2List(String jsonString,Class cls){
        List<T> list = null;
        try {
            list = JSON.parseArray(jsonString, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * ArrayList集合转换成json字符串
     * @param obj
     * @return
     */
    public static String list2Json(Object obj){
        return JSONArray.toJSONString(obj);
    }

}
