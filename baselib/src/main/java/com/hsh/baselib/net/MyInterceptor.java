package com.hsh.baselib.net;

import com.hsh.baselib.utils.LogUtil;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 作者：Carr on 2016/11/3 11:25
 * 邮箱：1120199941@qq.com
 */
public class MyInterceptor implements Interceptor{

    @Override
    public Response intercept(Chain chain) throws IOException {
        okhttp3.Response response = chain.proceed(chain.request());
        String bodyString = response.body().string();
        LogUtil.e("请求的地址 ---"+ response.request().url());
        LogUtil.e("返回的code---" + response.code());
        LogUtil.e("服务器响应内容 --- " + bodyString);
        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), bodyString))
                .build();
    }
}
