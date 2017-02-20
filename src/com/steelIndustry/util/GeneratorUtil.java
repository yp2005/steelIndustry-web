package com.steelIndustry.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * 生成器 TODO What the class does
 * 
 * @author wanglei
 * @date 2016年11月14日-下午3:20:51
 *
 */
public class GeneratorUtil {

    /**
     * 生成相对唯一短码
     * 
     * @author wanglei
     * @date 2016年11月14日-下午3:20:59
     *
     * @param codeAlias
     * @return
     */
    public static String generatorCode(String codeAlias) {

        return codeAlias + "";
    }

    public static String createUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String md5(String s) throws Exception {
        byte[] source = s.getBytes("utf-8");
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
        md.update(source);
        byte tmp[] = md.digest();
        char str[] = new char[32];
        int k = 0;
        for (int i = 0; i < 16; i++) {
            str[k++] = hexDigits[tmp[i] >>> 4 & 0xf];
            str[k++] = hexDigits[tmp[i] & 0xf];
        }

        return new String(str);
    }
    
    public static void main(String[] args) {
        try {
            System.out.println(md5("1487559178401"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
