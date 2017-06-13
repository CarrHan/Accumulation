package com.hsh.baselib.net;


/**
 * 作者：Carr on 2016/11/2 13:40
 * 邮箱：1120199941@qq.com
 */
public abstract class OnResultListener<T> {
    public boolean onCache(String cache) {//返回true,表示不再请求网络
        return false;
    }
    public boolean onCache(T object) {//返回true,表示不再请求网络
        return false;
    }

    abstract public  void onSuccess(T object);

    abstract public void onFailure(String message);//根据需求实现

    public void onTokenInvalue(String error){}

}
