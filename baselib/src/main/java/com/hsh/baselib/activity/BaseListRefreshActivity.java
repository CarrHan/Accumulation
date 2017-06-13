package com.hsh.baselib.activity;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.View;

import com.hsh.baselib.R;
import com.hsh.baselib.adapter.BaseRecyclerAdapter;
import com.hsh.baselib.adapter.base_recyclerview_adapter.CommonAdapter;
import com.hsh.baselib.adapter.base_recyclerview_adapter.MultiItemTypeAdapter;
import com.hsh.baselib.model.IPage;
import com.hsh.baselib.presenter.BaseActivityPresenter;
import com.hsh.baselib.utils.LogUtil;
import com.hsh.baselib.view.IBaseRefreshView;
import com.hsh.baselib.widget.recyclerview_wraper.LoadMoreWrapper;


/**
 * 下拉刷新BaseActivity  swiperefreshlayout recyclerview
 * Created by Carr on 16/06/14.
 */
public abstract class BaseListRefreshActivity extends BaseNoPresenterActivity implements IBaseRefreshView {


    protected SwipeRefreshLayout swipeRefreshLayout;
    protected RecyclerView recyclerView;

    protected LoadMoreWrapper loadMoreWrapper;
    // Adapter
    protected CommonAdapter commonAdapter;

    // LayoutManager
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void initialize() {
        swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recyclerView=(RecyclerView) findViewById(R.id.recycleView);
        commonAdapter = getAdapter();
        mLayoutManager = new LinearLayoutManager(mContext);
        initRefreshLayout();
        commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                clickItem(view,position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    /**
     * 初始化下拉刷新布局
     */
    private void initRefreshLayout() {
        initSwipeRefreshLayout();
        initRecyclerView();
    }


    private void initSwipeRefreshLayout(){
        if(swipeRefreshLayout==null){
            throw new IllegalStateException("swiperefreshlayout is null,please add the right id to the layout");
        }
        swipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright,R.color.holo_green_light,R.color.holo_orange_light,R.color.holo_red_light);
        swipeRefreshLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullToRefresh();
            }
        });
    }


    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        if (recyclerView == null) {
            throw new IllegalStateException("please add RecyclerView in your include_review_order_content with id is recycleView...");
        }
        if (mLayoutManager == null) {
            throw new NullPointerException("RecyclerView's LayoutManager can not be null...");
        }
        if (!(mLayoutManager instanceof StaggeredGridLayoutManager)
                && !(mLayoutManager instanceof LinearLayoutManager)) {
            throw new IllegalStateException("this LayoutManager is not StaggeredGridLayoutManager or LinearLayoutManager...");
        }

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        loadMoreWrapper=new LoadMoreWrapper(commonAdapter);
        loadMoreWrapper.setLoadMoreView(R.layout.load_more);
        loadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                LogUtil.e("tag", "for you load more");
                loadmore();
            }
        });
        recyclerView.setAdapter(loadMoreWrapper);
    }



    @Override
    public void showRefreshLayout(Boolean flag) {
        if(swipeRefreshLayout!=null)
            swipeRefreshLayout.setRefreshing(flag);
    }

    /**
     * 获取RecyclerView的adapter
     *
     * @return AbsAdapter
     */
    protected abstract CommonAdapter getAdapter();

    /**
     * 抽象方法  下拉刷新
     */
    public abstract void pullToRefresh();
    public abstract void loadmore();
    public abstract void clickItem(View view,int position);


    @Override
    protected int getLayoutResId() {
        return R.layout.base_activity_refresh_layout;
    }

    /**
     * 设置上拉是否可用
     * @param flag
     */
    public void setLoadMoreEnable(boolean flag){
        if(loadMoreWrapper!=null)
            loadMoreWrapper.setLoadmoreEnble(flag);
    }

    /**
     * 通知更新数据
     */
    public void notifyDataSetChanged(){
        if(loadMoreWrapper!=null)
            loadMoreWrapper.notifyDataSetChanged();
    }

    /**
     * 设置page
     * @param page
     */
    public void setUpPage(IPage page){
        if(page.getPageNo()==page.getPageCount()){
            setLoadMoreEnable(false);
        }else{
            setLoadMoreEnable(true);
        }
    }




}
