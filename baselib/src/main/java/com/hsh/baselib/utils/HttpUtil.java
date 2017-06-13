package com.hsh.baselib.utils;

import android.content.Intent;

import com.hsh.baselib.BaseLib;
import com.hsh.baselib.constanst.SPConstanst;
import com.hsh.baselib.constanst.StatusCode;
import com.hsh.baselib.model.RequestParam;
import com.hsh.baselib.model.Result;
import com.hsh.baselib.net.HttpClient;
import com.hsh.baselib.net.OnResultListener;

import java.util.Map;

import okhttp3.RequestBody;

/**
 * 作者：Carr on 2016/11/12 13:59
 * 邮箱：1120199941@qq.com
 */


public class HttpUtil {


    public static void executeGet(String url, Map<String, ?> params, final OnResultListener<Result> onResultListener) {

        HttpClient client = new HttpClient.Builder()
                .url(url)
                .bodyType(HttpClient.OBJECT, Result.class) //当要得到已经解析完成的实体类时，添加此方法即可
                .build();
        client.get(new OnResultListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                if (result.getData("flag") != null && (Boolean) (result.getData("flag")) == true) {
                    onResultListener.onSuccess(result);
                } else {
                    onResultListener.onFailure(result.getRespMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                onResultListener.onFailure(message);
            }
        });
    }

    /**
     * http post 请求 参数 body
     *
     * @param url
     * @param params
     * @param onResultListener
     */
    public static void executeBody(final String url, Map<String, Object> params, final OnResultListener<Result> onResultListener) {

        if (url.contains("bizLoginByDevicePassword") || url.contains("bizEmpLoginByDevicePassword")) {
            if (params != null && !params.containsKey("userId")) {
                String userId = SPUtils.getPrefString(BaseLib.getContext(), SPConstanst.USER_ID, "0");
                params.put("userId", userId);
            }
        } else {
            if (params != null && !params.containsKey("token")) {
                String token = SPUtils.getPrefString(BaseLib.getContext(), SPConstanst.TOKEN, "");
                if (!StringUtil.isEmpty(token))
                    params.put("token", token);
            }
        }

        if(NetWorkUtil.isConnected(BaseLib.getContext())==false){
            onResultListener.onFailure("当前网络不可用,请检测网络配置");
            return;
        }

        String paramString = RequestParam.createParam(params);
        LogUtil.e("请求参数---" + paramString);
        LogUtil.e("请求地址--" + url);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), paramString);
        HttpClient client = new HttpClient.Builder()
                .url(url)
                .requestBody(requestBody)
                .bodyType(HttpClient.OBJECT, Result.class) //当要得到已经解析完成的实体类时，添加此方法即可
                .build();
        client.postBody(new OnResultListener<Result>() {
            @Override
            public void onSuccess(Result result) {

                if (result == null) {
                    onResultListener.onFailure("result为空.");
                    return;
                }

                if (result.getRespCode().equals(StatusCode.SUCCESS)) {

                    if (result.containKey("flag")) {

                        /**
                         * 如果是检测员工是否设置密码的链接
                         */
                        if (url.contains("rest/sys/user/hasPassword")) {
                            onResultListener.onSuccess(result);
                            return;
                        }

                        /**
                         * 其他
                         */
                        if (result.getData("flag") != null && (Boolean) (result.getData("flag")) == true) {
                            onResultListener.onSuccess(result);
                        } else {
                            onResultListener.onFailure(result.getRespMsg());
                        }
                    } else {
                        onResultListener.onSuccess(result);
                    }
                } else if (result.getRespCode().equals(StatusCode.TOKEN_INVALUE)) {
                    //token失效
                    onResultListener.onTokenInvalue(result.getRespMsg());
                    Intent intent=new Intent();
                    intent.setAction("data_update");
                    intent.putExtra("result_code",100494);
                    BaseLib.getContext().sendBroadcast(intent);
                } else {
                    onResultListener.onFailure(result.getRespMsg());
                }
            }

            @Override
            public void onFailure(String message) {
                //mView.dismissProgressDialog();
                onResultListener.onFailure(message);
            }
        });
    }

}
