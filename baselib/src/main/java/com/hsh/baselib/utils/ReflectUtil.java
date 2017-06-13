package com.hsh.baselib.utils;

/**
 * 作者：Carr on 2016/11/14 11:25
 * 邮箱：1120199941@qq.com
 */


public class ReflectUtil {



    /**
     * 判断clazz是否是基本数据类型
     * @param clazz
     * @return
     */
    public static final boolean isPrimitive(final Class clazz) {
        if (clazz.isPrimitive())
            return true;
        if (Boolean.class.isAssignableFrom(clazz)
                || Byte.class.isAssignableFrom(clazz)
                || Character.class.isAssignableFrom(clazz)
                || Double.class.isAssignableFrom(clazz)
                || Float.class.isAssignableFrom(clazz)
                || Integer.class.isAssignableFrom(clazz)
                || Long.class.isAssignableFrom(clazz)
                || Short.class.isAssignableFrom(clazz)) {
            return true;
        }
        return false;
    }


}
