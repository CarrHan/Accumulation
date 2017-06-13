package com.hsh.baselib.utils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 作者：Carr on 2016/11/12 15:43
 * 邮箱：1120199941@qq.com
 */


public class MapSortUtil {


    /**
     * map根据key排序
     * @param map
     * @return
     */
    public static Map<String,String> sortMapByKey(Map<String,String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String,String> sortMap = new TreeMap(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    //比较器类
    private static class MapKeyComparator implements Comparator<String> {
        public int compare(String str1, String str2) {
            return str1.compareTo(str2);
        }
    }

}
