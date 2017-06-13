package com.hsh.baselib.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Carr on 2017/1/16 20:31
 * 邮箱：1120199941@qq.com
 */


public class AntiShake {

    private List<OneClickUtil> utils = new ArrayList<>();

    public boolean check(Object o) {
        String flag = null;
        if(o == null)
            flag = Thread.currentThread().getStackTrace()[2].getMethodName();
        else
            flag = o.toString();
        for (OneClickUtil util : utils) {
            if (util.getMethodName().equals(flag)) {
                return util.check();
            }
        }
        OneClickUtil clickUtil = new OneClickUtil(flag);
        utils.add(clickUtil);
        return clickUtil.check();
    }

    public boolean check() {
        return check(null);
    }
}
