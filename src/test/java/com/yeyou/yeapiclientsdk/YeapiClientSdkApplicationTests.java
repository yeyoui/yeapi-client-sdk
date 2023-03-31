package com.yeyou.yeapiclientsdk;

import com.yeyou.yeapiclientsdk.client.YeApiClient;
import com.yeyou.yeapiclientsdk.exception.SdkInvokeException;
import com.yeyou.yeapiclientsdk.model.User;
import com.yeyou.yeapiclientsdk.utils.CustomizeInvokeUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;


@Slf4j
class YeapiClientSdkApplicationTests {
    @Resource
    private YeApiClient yeApiClient;

    @Test
    void contextLoads() {
        YeApiClient yeApiClient = new YeApiClient("yeyoui","123456");
        String yeyoui1 = yeApiClient.getNameByGet("yeyoui1");
        String yeyoui2 = yeApiClient.getNameByPost("yeyoui2");
        String yeyoui3 = yeApiClient.getUserByPost(new User("yeyoui3"));

        System.out.println(yeyoui1);
        System.out.println(yeyoui2);
        System.out.println(yeyoui3);
    }

    @Test
    void testReflectionInvokeMethod() throws SdkInvokeException {
//        String classPath="com.yeyou.yeapiclientsdk.clientTest.InvokedMethodTest";
        String methodName="testInvoke";
        String methodHadRet="testInvokeRetInteger";
        String methodRetUser="testInvokeRetUser";

        //无返回值
        String s1 = "[{\"type\":\"String\",\"value\":\"yeyouii\"}]";
        Object o = CustomizeInvokeUtils.invokeYeApiClientMethod(yeApiClient, methodName, s1);
        assert o==null;

        String s2 = "[{\"type\":\"Double\",\"value\":\"0.1111\"}]";
        o = CustomizeInvokeUtils.invokeYeApiClientMethod(yeApiClient, methodName, s2);
        assert o==null;

        //返回整形
        String s3 = "[{\"type\":\"Integer\",\"value\":\"100\"}]";
        Integer num = (Integer) CustomizeInvokeUtils.invokeYeApiClientMethod(yeApiClient, methodHadRet, s3);
        System.out.println((1+num)+" is int");

        //返回User
        String s4 = "[{\"type\":\"User\",\"value\":\"lhy123212\"}]";
        User user = (User) CustomizeInvokeUtils.invokeYeApiClientMethod(yeApiClient, methodRetUser, s4);
        System.out.println(user);
    }

}
