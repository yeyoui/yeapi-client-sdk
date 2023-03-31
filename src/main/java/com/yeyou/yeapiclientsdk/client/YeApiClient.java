package com.yeyou.yeapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.yeyou.yeapiclientsdk.model.User;
import com.yeyou.yeapiclientsdk.utils.SignUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

public class YeApiClient {
    private String accessKey;
    private String secretKey;

    private final String GATEWAY_HOST="http://localhost:8081";


    public YeApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public YeApiClient() {

    }

    public String getNameByGet(String name){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", name);
        return sendMsgByGet(hashMap,"/name/name");
    }

    public String getNameByPost(String name){
        String json = JSONUtil.toJsonStr(name);
        Map<String, String> headerMap = buildHeadMap(json);
        //设置application/json请求头
        headerMap.put("Content-Type","application/json");
        return sendMsgByPost(headerMap,json,"/name/name");
    }

    public String getUserByPost(User user){
        String json = JSONUtil.toJsonStr(user);
        Map<String, String> headerMap = buildHeadMap(json);
        //设置application/json请求头
        headerMap.put("Content-Type","application/json");
        return sendMsgByPost(headerMap,json,"/name/user");
    }

    public Map<String,String> buildHeadMap(String body){
        HashMap<String, String> headerMap = new HashMap<>();
        //body中可能有中文字符，编码为字节码传输
        String translateBody;
        try {
            translateBody= URLEncoder.encode(body,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        //生成签名
        String sign= SignUtils.getSign(translateBody,secretKey);
        //参数1：accessKey：调用标识
        headerMap.put("accessKey",accessKey);
        //参数2：加密的secretKey(sign)：(秘钥不可直接放入请求头)
        headerMap.put("sign",sign);
        //将body转化为字节码放置到请求头传输
        headerMap.put("body", translateBody);
        //参数5：随机数（防止重放攻击）
        headerMap.put("randomNum", RandomUtil.randomNumbers(5));
        //参数6：时间戳秒（用于定时清除存储的随机数）
        headerMap.put("timestamp", String.valueOf(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)));
        return headerMap;
    }

    public String sendMsgByPost(Map<String, String> headerMap,String body,String path){
        String url=GATEWAY_HOST+path;
        return HttpRequest
                .post(url)
                .body(body)
                .addHeaders(headerMap)
                .execute()
                .body();
    }

    public String sendMsgByGet(Map<String, Object> paramsMap,String path){
        String url=GATEWAY_HOST+path;
        return HttpUtil.get(url,paramsMap);
    }



    //测试使用
    @Deprecated
    public void testInvoke(String name){
        System.out.println("name :"+name);
    }
    @Deprecated
    public void testInvoke(Double name){
        System.out.println("name :"+name);
    }
    @Deprecated
    public Integer testInvokeRetInteger(Integer num){
        return num;
    }
    @Deprecated
    public User testInvokeRetUser(User user){
        return user;
    }
}
