package com.yeyou.yeapiclientsdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * 签名工具
 */
public class SignUtils {
    public static String getSign(String body,String secretKey){
        Digester digester = new Digester(DigestAlgorithm.SHA256);
        String content=body+"."+secretKey;
        return digester.digestHex(content);
    }
}
