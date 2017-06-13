package com.hsh.baselib.model;

import com.hsh.baselib.utils.FastJsonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * 作者：Carr on 2016/11/2 11:48
 * 邮箱：1120199941@qq.com
 */

@Data
public class Result {


    /**
     *
     "sign": "E2E2226FCBCAC8F1E4C9AABB1B30A841",
     "reqTime": "",
     "datas": {
     "flag": true,
     "userId": "1"
     },
     "respCode": "0",
     "respMsg": "成功",
     "respTime": "20161112155529"
     */

    private Map<String,?> datas = new HashMap<>();
    protected Map<String, Object> msgs = new HashMap<>();
    private String sign;
    private String respCode;
    private String respMsg;
    private String respTime;

    @SuppressWarnings("unchecked")
    public Object getData(String key) {
        return  this.datas.get(key);
    }

    /**
     * 从datas 获取值
     * @param key
     * @param t
     * @param <T>
     * @return
     */
    public <T> T getData(String key,Class<T> t){
        if(datas.containsKey(key)){
            String json=datas.get(key).toString();
            return FastJsonUtils.parseObject(json,t);
        }
        return null;
    }

    /**
     * 判断datas 是否含有key
     * @param key
     * @return
     */
    public boolean containKey(String key){
        return datas.containsKey(key);
    }

    /**
     * 把json数据转List
     * @param key
     * @param clz
     * @param <T>
     * @return
     */
    public <T> List<T> getList(String key, Class<T> clz){
        if(datas.containsKey(key)){
            String json=datas.get(key).toString();
            return FastJsonUtils.toList(json,clz);
        }
        return null;
    }

}
