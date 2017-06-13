package com.hsh.baselib.model;

import com.alibaba.fastjson.JSON;
import com.hsh.baselib.utils.MapSortUtil;
import com.hsh.baselib.utils.SignUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者：Carr on 2016/11/2 13:40
 * 邮箱：1120199941@qq.com
 */

public class RequestParam {

    /**
     * 生成请求参数
     *
     * @param map
     * @return
     */
    public static String createParam(Map<String,?> map) {

        if(map==null){
            return "{}";
        }
        Map<String, Object> params = new HashMap<>();

        //时间reqTime
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String reqTime =df.format(new Date());
        params.put("reqTime", reqTime);
        //datas
        params.put("datas",map);
        //sign
        params.put("sign", SignUtil.getSign(map,reqTime).toUpperCase());

        return JSON.toJSONString(params);
    }

}
