package com.hsh.baselib.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hsh.baselib.presenter.interfaces.IBasePresenter;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public abstract class AbsAdapter<P extends IBasePresenter, D> extends RecyclerView.Adapter<AbsViewHolder> {
    protected P presenter;
    protected List<D> datas;
    protected Context context;
    protected int[] layoutId;
    protected AbsViewHolder[] viewHolders;

    public AbsAdapter(@NonNull P presenter, @NonNull int... layoutId) {
        this.datas = new ArrayList<>();
        this.presenter = presenter;
        this.layoutId = layoutId;
        viewHolders = new AbsViewHolder[layoutId.length];
    }

    @Override
    public AbsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        for (int i = 0; i < layoutId.length; i++) {
            viewHolders[i] = new AbsViewHolder(
                    LayoutInflater.from(context)
                            .inflate(layoutId[i], parent, false)
            );
        }
        return viewHolders[viewType];
    }

    @Override
    public int getItemCount() {
        return null == datas ? 0 : datas.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract int getItemViewType(int position);

    @Override
    abstract public void onBindViewHolder(AbsViewHolder holder, int position);

    public void setDatas(List<D> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        this.notifyDataSetChanged();
    }

}
