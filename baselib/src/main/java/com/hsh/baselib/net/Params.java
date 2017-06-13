package com.hsh.baselib.net;

import java.util.Map;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * 作者：Carr on 2016/11/2 13:40
 * 邮箱：1120199941@qq.com
 */
public interface Params {


    /**
     * post 请求 接收普通表单参数
     * @param url 请求相对url
     * @param param
     * @return
     */
    @FormUrlEncoded
    @POST
    Call<ResponseBody> params(@Url String url, @FieldMap Map<String, String> param);

    /**
     * post 请求 接收body请求参数
     * @param url 请求相对的url
     * @param body
     * @return
     */
    @POST
    Call<ResponseBody> params(@Url String url, @Body RequestBody body);

    /**
     * get 请求
     * @param url
     * @return
     */
    @GET
    Call<ResponseBody> params(@Url String url);

    /**
     * post 请求文件
     * @param url
     * @param options
     * @param files
     * @return
     */
    @POST
    @Multipart
    Call<ResponseBody> params(@Url String url, @QueryMap Map<String, String> options,
                              @PartMap Map<String, RequestBody> files);


}
