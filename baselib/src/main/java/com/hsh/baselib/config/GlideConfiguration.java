package com.hsh.baselib.config;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;
import com.hsh.baselib.BaseLib;
import com.hsh.baselib.utils.AppUtil;
import com.hsh.baselib.utils.LogUtil;

/**
 * Glide属性配置
 */
public class GlideConfiguration implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        String path= AppUtil.getPicCachePath(BaseLib.getContext());
        //LogUtil.e("图片缓存路径---"+path);
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        builder.setDiskCache(new DiskLruCacheFactory(//
                path,//
                300));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
    }
}
