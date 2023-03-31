package com.yeyou.yeapiclientsdk;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import com.yeyou.yeapiclientsdk.exception.SdkInvokeException;
import com.yeyou.yeapiclientsdk.model.Pet;
import com.yeyou.yeapiclientsdk.model.User;
import com.yeyou.yeapiclientsdk.utils.ParseParamsUtils;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
public class ReflectionInvokeTest {
    @Test
    public void testJson() throws SdkInvokeException {
//        String s = "[{\"type\":\"String\",\"value\":\"yeyouii\"}\n{\"type\":\"Double\",\"value\":\"123.2132\"}]";
        String s = "[{\"type\":\"String\",\"value\":\"yeyouii\"}]";
        for (Pair<Class<?>, String> pair : ParseParamsUtils.getBasicClassTypeAndValueByJson(s)) {
            System.out.println("Type: "+pair.getKey()+" Value: "+pair.getValue());
        }
    }
    @Test
    public void TestGson(){
        JSONObject json1 = JSONUtil.createObj();
        json1.putOnce("name", "lhy123");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Content-Type","application/json");
        System.out.println(HttpRequest.post("http://localhost:8081/name/name").body("lhy123").addHeaders(hashMap).execute().body());
    }

    @Test
    public void TestBeanUtils(){
        Gson gson = new Gson();
        User user = new User("lhy123123",123,new Pet("hhh"));
        String json = gson.toJson(user);
        User yeyoui = gson.fromJson(json, User.class);
        System.out.println("json:"+json+"   Object:"+yeyoui);
    }
}
