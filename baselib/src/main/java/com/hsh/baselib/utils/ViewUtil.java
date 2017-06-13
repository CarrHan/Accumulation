package com.hsh.baselib.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;

/**
 * 作者：Carr on 2016/12/14 13:01
 * 邮箱：1120199941@qq.com
 */


public class ViewUtil {


    /**
     * gridview 高度自适应
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(GridView listView) {
        // 获取listview的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int col =  listView.getNumColumns();
        int totalHeight = 0;
        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
            totalHeight+=DrawTools.dp2px(listView.getContext(),15);
        }


        // 获取listview的布局参数
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // 设置高度
        params.height = totalHeight;
        // 设置margin
        ((ViewGroup.MarginLayoutParams) params).setMargins(DrawTools.dp2px(listView.getContext(),20),DrawTools.dp2px(listView.getContext(),20),DrawTools.dp2px(listView.getContext(),20), DrawTools.dp2px(listView.getContext(),20));
        // 设置参数
        listView.setLayoutParams(params);
    }

}
