package com.yeyou.yeapiclientsdk.utils;

import com.google.gson.Gson;
import com.yeyou.yeapiclientsdk.client.YeApiClient;
import com.yeyou.yeapiclientsdk.exception.SdkInvokeException;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * 通过名称匹配来调用方法
 */
@Slf4j
public class CustomizeInvokeUtils {

    public static Object invokeYeApiClientMethod(YeApiClient yeApiClient, String methodName, String JsonParams) throws SdkInvokeException {
        //解析json参数，获取包装类和参数值
        ArrayList<Pair<Class<?>, String>> paramsPairs = ParseParamsUtils.getBasicClassTypeAndValueByJson(JsonParams);
        //填充类型和参数值信息
        Gson gson=new Gson();
        Class<?>[] types=new Class[paramsPairs.size()];
        Object[] values=new Object[paramsPairs.size()];
        for (int i = 0; i < paramsPairs.size(); i++) {
            Class<?> classInfo = paramsPairs.get(i).getKey();
            types[i]= classInfo;
            if(!String.class.equals(classInfo)){
                try {
                    //通过valueOf获取包装类对象
                    values[i]=(classInfo.getMethod("valueOf", String.class).invoke(null,paramsPairs.get(i).getValue()));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    log.error("通过反射获取参数值类型时出现问题",e);
                    throw new SdkInvokeException("通过反射获取参数值类型时出现问题");
                } catch (NoSuchMethodException e){
                    //通过json获取自定义对象
                    values[i]=gson.fromJson(paramsPairs.get(i).getValue(),classInfo);
                    if(values[i]==null) throw new SdkInvokeException("不能匹配自定义对象");
                }
            }else{
                //本来就是String类型了，无需再进行转换
                values[i]=paramsPairs.get(i).getValue();
            }
        }
        try {
            //获取YeApiClientClass对象
            Class<?> classInfo = yeApiClient.getClass();
            //方法名
            Method method = classInfo.getMethod(methodName,types);
            return method.invoke(yeApiClient, values);
        } catch (InvocationTargetException | NoSuchMethodException |
                 IllegalAccessException e) {
            log.error("反射调用方法时出现问题",e);
            throw new SdkInvokeException("反射调用方法时出现问题");
        }
    }
}
