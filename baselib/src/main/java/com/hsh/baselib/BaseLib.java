package com.hsh.baselib;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.hsh.baselib.config.Config;
import com.hsh.baselib.utils.FileUtil;

import java.io.File;

import lombok.Getter;
import lombok.Setter;

/**
 * 作者：Carr on 2016/11/2 13:40
 * 邮箱：1120199941@qq.com
 */
public class BaseLib extends Application{

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();

        initialize(this);
    }
    public static  void initialize(final Context context){
        sContext=context;
    }
    public static Context getContext(){
        return sContext;
    }


    public BaseLib(){
        sContext=this;
        if (sContext==null){
            Log.e("baseLib","baseLib is not initialize");
            return;
        }
        init();
    }

    private void init() {

    }







}
