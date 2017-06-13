package com.hsh.baselib.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者：Carr on 2016/11/5 11:20
 * 邮箱：1120199941@qq.com
 */


public class BaseRecyclerAdapter extends RecyclerView.Adapter  {

    protected Context context;

    public interface OnItemClickLitener {
        void onItemClick(View view, Integer id);
    }

    public interface OnLongClickListener {
        void onLongClick(View view, Integer id);
    }

    public OnItemClickLitener mOnItemClickLitener;
    public OnLongClickListener mOnLongClickListener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public void setmOnLongClickListener(OnLongClickListener mOnLongClickListener) {
        this.mOnLongClickListener = mOnLongClickListener;
    }


    public BaseRecyclerAdapter(Context context){
        this.context=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
