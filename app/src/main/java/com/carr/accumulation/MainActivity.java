package com.carr.accumulation;

import com.hsh.baselib.activity.BaseNoPresenterActivity;

import butterknife.OnClick;

public class MainActivity extends BaseNoPresenterActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initialize() {

    }

    /**
     * 点击
     */
    @OnClick(R.id.btnClickMe)
    void click(){
        showTips("Click Me");
    }

}
