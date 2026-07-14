package com.ccc.miaoshav1.util;


import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

@Component
public class MD5Util {


    /**
     *  针对字符串的普通MD5加密
     * @param str
     * @return
     */
    public static String commonMD5(String str){
        if(str == null || StringUtils.isEmpty(str)) return null;
        return DigestUtils.md5Hex(str);
    }

    public static String saltMD5(String str,String salt){
        if(str == null || StringUtils.isEmpty(str)) return null;
        if(salt == null || StringUtils.isEmpty(salt)) return null;
        // 将盐值拼接到需要加密字符前
        return DigestUtils.md5Hex(salt + str);
    }
}
