package com.yeyou.yeapiclientsdk.utils;

import com.yeyou.yeapiclientsdk.exception.SdkInvokeException;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 反射工具类
 * 主要通过反射获取方法参数和类型
 */
@Slf4j
public class ParseParamsUtils {

    /**
     * 通过反射将用户传递的参数提取出来
     * @param jsonParams 传递的json example [{"type":"参数","value":"参数"}\n{...}]
     * @return Pair列表，key为类名，value为参数值
     */
    public static ArrayList<Pair<Class<?>,String>> getBasicClassTypeAndValueByJson(String jsonParams) throws SdkInvokeException {
        //提取参数信息
        jsonParams=jsonParams.substring(1,jsonParams.length()-1);
        String[] paramJsons = jsonParams.split("\n");
        //存放参数类型和值的列表
        ArrayList<Pair<Class<?>,String>> paramsList = new ArrayList<>(paramJsons.length);
        //正则表达式匹配
        Pattern patternType= Pattern.compile("^\\{\"type\":\".*?\",");
        Pattern patternValue = Pattern.compile("\"value\":.*?\"}$");

        for (String paramJson : paramJsons) {
            Matcher matcherType = patternType.matcher(paramJson);
            Matcher matcherValue = patternValue.matcher(paramJson);
            if(matcherType.find(0) && matcherValue.find(0)){
                //匹配的字段
                String rowType = matcherType.group(0);
                String rowVal = matcherValue.group(0);
                //截取有效信息
                Class<?> paramType = getClassByName(rowType.substring(9, rowType.length() - 2));
                String paramValue = rowVal.substring(9, rowVal.length() - 2);

                paramsList.add(new Pair<>(paramType,paramValue));
            }
        }
        return paramsList;
    }

    /**
     * 通过名称获取类
     * @param className 类名
     * @return 反射得到的类
     */
    private static Class<?> getClassByName(String className) throws SdkInvokeException{
        try {
            //尝试获取包装类
            return Class.forName("java.lang." + className);
        } catch (ClassNotFoundException e) {
            //尝试获取model包内的类
            try {
                return Class.forName("com.yeyou.yeapiclientsdk.model." + className);
            } catch (ClassNotFoundException ex) {
                throw new SdkInvokeException("获取类时出现问题");
            }
        }
    }

}
