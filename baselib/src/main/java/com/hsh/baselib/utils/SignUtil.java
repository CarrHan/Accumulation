package com.hsh.baselib.utils;

import com.hsh.baselib.BaseLib;
import com.hsh.baselib.constanst.SPConstanst;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 作者：Carr on 2016/11/12 16:18
 * 邮箱：1120199941@qq.com
 */


public class SignUtil {


    /**
     * 请求参数生成签名
     *
     * @param map
     * @param reqTime
     * @return
     */
    public static String getSign(Map<String, ?> map, String reqTime) {

        //过滤string 基本类型的k-v
        HashMap<String, String> hashMap = new HashMap<>();
        Iterator<String> keys = map.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            if (map.get(key) != null) {
                Class<?> clz = map.get(key).getClass();
                if (String.class.isAssignableFrom(clz) || ReflectUtil.isPrimitive(clz)) {
                    if (!map.get(key).toString().equals(""))
                        hashMap.put(key, map.get(key).toString());
                }
            }
        }
        hashMap.put("reqTime", reqTime);

        //首字母排序
        Map<String, String> sortMap = MapSortUtil.sortMapByKey(hashMap);
        //转为K=V&K=V字符串
        String mapString = coverMap2String(sortMap);
        LogUtil.e("map 排序后的字符串---" + mapString);
        String userId = "";
        if (hashMap.containsKey("token")) {
            userId = hashMap.get("token").toString();
        } else if (hashMap.containsKey("userId")) {
            userId = hashMap.get("userId").toString();
        }
        String cipher = buildCipher(userId, SPUtils.getPrefString(BaseLib.getContext(), SPConstanst.PASSWORD_KEY, ""), reqTime);
        LogUtil.e("cipher字符串---" + cipher);
        LogUtil.e("sign字符串---" + StringUtil.encodeByMD5(mapString + cipher));
        return StringUtil.encodeByMD5(mapString + cipher);
    }


    /**
     * 生成cipher
     *
     * @param userId   用户ID
     * @param password 用户密码 加密后的
     * @param reqTime  时间戳
     * @return
     */
    private static String buildCipher(String userId, String password, String reqTime) {

        //用户ID+时间 MD5加密后取前16位
        String aes128Key = StringUtil.encodeByMD5(userId + reqTime).toUpperCase().substring(0, 16);
        try {
            String aesPassword = AES128Utils.encrypt(password, aes128Key);
            String md5Password = StringUtil.encodeByMD5(aesPassword).toUpperCase();
            return md5Password;
        } catch (Exception e) {
            LogUtil.e("PasswordSecurityUtils密码加密失败!");
        }
        return null;
    }


    /**
     * <P>把Map的参数按String排序,拼接成key=value&key=value形式,并把sign的健值去掉</P>
     *
     * @param data
     * @return
     * @throws
     */
    private static String coverMap2String(Map<String, String> data) {
        TreeMap<String, String> tree = new TreeMap<String, String>();
        Iterator<Map.Entry<String, String>> it;
        for (it = data.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, String> en = it.next();
            if (!"sign".equals(en.getKey().trim()))
                tree.put(en.getKey(), en.getValue());
        }
        it = tree.entrySet().iterator();
        StringBuilder sb = new StringBuilder();
        Map.Entry<String, String> en;

        for (; it.hasNext(); sb.append(new StringBuilder(en.getKey()).append("=").append(en.getValue()).append("&")).toString()) {
            en = it.next();
        }

        return sb.substring(0, sb.length() - 1);
    }


}
