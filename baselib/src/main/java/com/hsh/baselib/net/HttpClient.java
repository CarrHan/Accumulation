package com.hsh.baselib.net;

import android.support.v4.util.ArrayMap;
import com.alibaba.fastjson.JSONObject;
import com.hsh.baselib.BaseLib;
import com.hsh.baselib.cache.ACache;
import com.hsh.baselib.utils.LogUtil;
import com.hsh.baselib.utils.NetWorkUtil;
import com.hsh.baselib.utils.StringUtil;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 作者：Carr on 2016/11/2 13:40
 * 邮箱：1120199941@qq.com
 * 封装retrofit请求 使用建造者模式
 */
public class HttpClient {
    public static String BASE_URL="";
    private static HttpClient mInstance;
    private Call<ResponseBody> mCall;
    private Builder mBuilder;
    private String cacheKey;
    private String mCache;
    public static final int OBJECT=1;//返回数据为json对象
    public static final int ARRAY=2;//返回数据为数组
    private static Retrofit retrofit;
    private static OkHttpClient mOkHttpClient;

    public static HttpClient getInstance() {
        if (mInstance==null){
            synchronized (HttpClient.class){
                if (mInstance==null){
                    mInstance=new HttpClient();
                }
            }
        }
        return mInstance;
    }

    public HttpClient() {
       init();
    }

    private void init(){
        if(mOkHttpClient==null){
            mOkHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new MyInterceptor())
                    .connectTimeout(NetConfig.REQUEST_TIME_OUT, TimeUnit.SECONDS) //设置连接超时
                    .readTimeout(NetConfig.RESPONSE_TIME_OUT, TimeUnit.SECONDS)
                    .build();
        }
        if(retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(mOkHttpClient)
                    .build();
        }
    }

    public Builder getBuilder() {
        return mBuilder;
    }

    public void setBuilder(Builder builder) {
        this.mBuilder = builder;
        this.cacheKey = StringUtil.buffer(builder.url, builder.params.toString());
        mCache= ACache.get().getAsString(cacheKey);
    }

    /**
     * post请求
     * @param onResultListener
     */
    public void postParam(final OnResultListener onResultListener) {

        if (onResultListener.onCache(mCache)) return;
        if (parseCache(mCache,onResultListener)) return;
        if (!NetWorkUtil.isAvailable(BaseLib.getContext())) {
            handlerError("无法连接网络",onResultListener);
            return;
        }
        mCall =retrofit.create(Params.class)
                .params(mBuilder.url, mBuilder.params);
        request(onResultListener);
    }


    /**
     * post请求
     * @param onResultListener
     */
    public void postBody(final OnResultListener onResultListener) {

        if (onResultListener.onCache(mCache)) return;
       // if (parseCache(mCache,onResultListener)) return;
        if (!NetWorkUtil.isAvailable(BaseLib.getContext())) {
            handlerError("无法连接网络",onResultListener);
            return;
        }
        mCall =retrofit.create(Params.class).params(mBuilder.url,mBuilder.requestBody);
        request(onResultListener);
    }


    /**
     * get请求
     * @param onResultListener  不可为空
     */
    public void get(final OnResultListener onResultListener){
        if (onResultListener.onCache(mCache)) return;

        if (parseCache(mCache,onResultListener)) return;

        if (!NetWorkUtil.isAvailable(BaseLib.getContext())) {
            handlerError("无法连接网络",onResultListener);
            return;
        }
        if (!mBuilder.params.isEmpty()){
            String value="";
            String span="";
            for (Map.Entry<String, String> entry :
                    mBuilder.params.entrySet()) {
                String key = entry.getKey();
                String val = entry.getValue();
                if (!value.equals(""))span="&";
                String par=StringUtil.buffer(span,key,"=",val);
                value=StringUtil.buffer(value,par);
            }
            mBuilder.url(StringUtil.buffer(mBuilder.url,"?",value));
        }
        mCall = retrofit.create(Params.class)
                .params(mBuilder.url);
       request(onResultListener);
    }

    /**
     * 文件上传  有bug,未解决
     * @param onProgressListener
     */
    @Deprecated
    public void upload(OnProgressListener onProgressListener){
        Map<String,RequestBody> requestBody=new ArrayMap<>();

        for(Map.Entry<String,File> entry : mBuilder.files.entrySet()){
            UploadRequestBody uploadRequestBody=new UploadRequestBody(entry.getValue(),onProgressListener);
            requestBody.put(entry.getKey(),uploadRequestBody);
        }

        mCall=retrofit.create(Params.class)
                .params(mBuilder.url,mBuilder.params,requestBody);
        mCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                LogUtil.e("上传成功 code="+response.code());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    /**
     * 发起请求
     * @param onResultListener
     */
    private void request(final OnResultListener onResultListener){
        mCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        String result=response.body().string();
                        ACache.get().put(cacheKey, result);
                        parseJson(result,onResultListener);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (response.code() == 404) handlerError("URL请求错误！",onResultListener);
                    else if (response.code()==500) handlerError("服务器繁忙，请稍后重试",onResultListener);
                    else handlerError(response.code()+"网络不稳定，请重试",onResultListener);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
               // t.printStackTrace();
                handlerError("网络不稳定，请重试",onResultListener);
            }
        });
    }

    /**
     * 错误处理
     * @param message
     * @param onResultListener
     */
    private void handlerError(String message,OnResultListener onResultListener){
        onResultListener.onFailure(message);
    }

    /**
     * parse cache data
     * @param json
     * @param onResultListener
     * @return
     */
    @SuppressWarnings("unchecked")
    private boolean parseCache(String json,OnResultListener onResultListener){
        switch (mBuilder.bodyType){
            case OBJECT:
               return onResultListener.onCache(JSONObject.parseObject(mCache,mBuilder.clazz));
            case ARRAY:
                return onResultListener.onCache(JSONObject.parseArray(json,mBuilder.clazz));
            default:
                return false;
        }
    }

    /**
     * parse response data
     * @param json
     * @param onResultListener
     */
    @SuppressWarnings("unchecked")
    private void parseJson(String json,OnResultListener onResultListener){
        switch (mBuilder.bodyType){
            case OBJECT:
                onResultListener.onSuccess(JSONObject.parseObject(json,mBuilder.clazz));
                break;
            case ARRAY:
                onResultListener.onSuccess(JSONObject.parseArray(json,mBuilder.clazz));
                break;
        }
    }

    /**
     * Call<ResponseBody> mCall
     * cancel request
     */
    public void cancel() {
        if (mCall != null) {
            LogUtil.e("cancel请求");
            mCall.cancel();
            mCall = null;
        }
    }

    public static final class Builder {
        private String baseUrl = NetConfig.BASE_URL;//给设定默认值，
        private String url;
        private Map<String, String> params = new ArrayMap<>();
        private RequestBody requestBody;
        private Map<String, File> files=new ArrayMap<>();
        private int bodyType=0;//返回数据的类型,因为前期返回是字符串，为了避免修改
        private boolean hasShowLoadding=false;
        private Class clazz;
        public Builder() {
        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder requestBody(RequestBody requestBody){
            this.requestBody=requestBody;
            return this;
        }


        public Builder param(String key, String value) {
            params.put(key, value);
            return this;
        }
        public Builder file(String key, File file){
           files.put(key,file);
            return this;
        }

        public <T> Builder bodyType(int bodyType,Class<T> clazz) {
            this.bodyType = bodyType;
            this.clazz = clazz;
            return this;
        }

        public Builder hasShowLoadding(boolean hasShowLoadding) {
            this.hasShowLoadding = hasShowLoadding;
            return this;
        }

        public HttpClient build() {
            BASE_URL=baseUrl;
            HttpClient httpClient= HttpClient.getInstance();
            httpClient.setBuilder(this);
            return httpClient;
        }
    }

}
