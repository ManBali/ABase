/**
 * @(#) z.sye.space.refreshrecyclerview.manager 2015/11/19;
 * <p>
 * Copyright (c), 2009 深圳孔方兄金融信息服务有限公司（Shenzhen kfxiong
 * Financial Information Service Co. Ltd.）
 * <p>
 * 著作权人保留一切权利，任何使用需经授权。
 */
package space.sye.z.library.manager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnBothRefreshListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import space.sye.z.library.adapter.RefreshRecyclerViewAdapter;
import space.sye.z.library.listener.LoadMoreRecyclerListener;

/**
 * The Management Class for RefreshRecyclerViewAdapter
 * Created by Syehunter on 2015/11/19.
 */
public class RefreshRecyclerAdapterManager {

    private RecyclerView recyclerView;
    private RefreshRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerMode mode;
    private RecyclerStyle style;

    private OnBothRefreshListener mOnBothRefreshListener;
    private OnRefreshListener mOnRefreshListener;
    private OnLoadMoreListener mOnLoadMoreListener;
    private RefreshRecyclerViewAdapter.OnItemClickListener mOnItemClickListener;
    private RefreshRecyclerViewAdapter.OnItemLongClickListener mOnItemLongClickListener;
    private RecyclerView.ItemDecoration mDecor;
    private RecyclerView.ItemAnimator mItemAnimator;
    private LoadMoreRecyclerListener loadMoreRecyclerListener;

    private SwipeToLoadLayout swipeToLoadLayout;

    public RefreshRecyclerAdapterManager(
            RecyclerView.Adapter adapter, RecyclerView.LayoutManager layoutManager) {
        this.mAdapter = new RefreshRecyclerViewAdapter(adapter);

        if (null == layoutManager){
            throw new NullPointerException("Couldn't resolve a null object reference of LayoutManager");
        }
        this.mLayoutManager = layoutManager;
        if (layoutManager instanceof GridLayoutManager){
            //如果是header或footer，设置其充满整列
            ((GridLayoutManager)layoutManager).setSpanSizeLookup(
                    new HeaderSapnSizeLookUp(mAdapter, ((GridLayoutManager) layoutManager).getSpanCount()));
        }
        this.mLayoutManager = layoutManager;
    }

    private RefreshRecyclerAdapterManager getInstance(){
        return RefreshRecyclerAdapterManager.this;
    }

    public RefreshRecyclerAdapterManager addHeaderView(View v){
        mAdapter.addHeaderView(v);
        return getInstance();
    }

    public RefreshRecyclerAdapterManager addHeaderView(View v, int position){
        mAdapter.addHeaderView(v, position);
        return getInstance();
    }

    public RefreshRecyclerAdapterManager addFooterView(View v){
        mAdapter.addFooterView(v);
        return getInstance();
    }

    public RefreshRecyclerAdapterManager removeHeaderView(View v){
        mAdapter.removeHeader(v);
        return getInstance();
    }

    public RefreshRecyclerViewAdapter getAdapter(){
        return mAdapter;
    }

    public RefreshRecyclerAdapterManager setOnBothRefreshListener(OnBothRefreshListener onBothRefreshListener){
        this.mOnBothRefreshListener = onBothRefreshListener;
        return getInstance();
    }

    public RefreshRecyclerAdapterManager setOnRefreshListener(OnRefreshListener onRefreshListener){
        this.mOnRefreshListener = onRefreshListener;
        return getInstance();
    }

    public RefreshRecyclerAdapterManager setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener){
        this.mOnLoadMoreListener = onLoadMoreListener;
        return getInstance();
    }

    public RefreshRecyclerAdapterManager removeFooterView(View v){
        mAdapter.removeFooter(v);
        return getInstance();
    }

    public RefreshRecyclerAdapterManager setMode(RecyclerMode mode){
        this.mode = mode;
        return getInstance();
    }

    public RefreshRecyclerAdapterManager setStyle(RecyclerStyle style){
        this.style = style;
        return getInstance();
    }

//    public RefreshRecyclerAdapterManager setLayoutManager(RecyclerView.LayoutManager layoutManager){
//        if (null == layoutManager){
//            throw new NullPointerException("Couldn't resolve a null object reference of LayoutManager");
//        }
//        this.mLayoutManager = layoutManager;
//        if (layoutManager instanceof GridLayoutManager){
//            //如果是header或footer，设置其充满整列
//            ((GridLayoutManager)layoutManager).setSpanSizeLookup(
//                    new HeaderSapnSizeLookUp(mAdapter, ((GridLayoutManager) layoutManager).getSpanCount()));
//        }
//        return getInstance();
//    }

    public RefreshRecyclerAdapterManager setOnItemClickListener
            (RefreshRecyclerViewAdapter.OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
        return getInstance();
    }

    public RefreshRecyclerAdapterManager setOnItemLongClickListener(
            RefreshRecyclerViewAdapter.OnItemLongClickListener onItemLongClickListener){
        this.mOnItemLongClickListener = onItemLongClickListener;
        return getInstance();
    }
//
//    public void onRefreshCompleted(){
//        if (null == recyclerView){
//            throw new NullPointerException("recyclerView is null");
//        }
//        if (null == mAdapter){
//            throw new NullPointerException("adapter is null");
//        }
//        if (RecyclerMode.BOTH == mode || RecyclerMode.TOP == mode){
//            recyclerView.refreshComplete();
//        }
//        if (RecyclerMode.BOTH == mode || RecyclerMode.BOTTOM == mode){
//            if(null != loadMoreRecyclerListener){
//                loadMoreRecyclerListener.onRefreshComplete();
//            }
//        }
//
//    }

    public RecyclerView getRecyclerView(){
        if (null == recyclerView){
            throw new NullPointerException("Couldn't resolve a null object reference of RecyclerView");
        }
        return recyclerView;
    }

    public RefreshRecyclerAdapterManager setItemAnimator(RecyclerView.ItemAnimator itemAnimator){
        this.mItemAnimator = itemAnimator;
        return getInstance();
    }

    public RefreshRecyclerAdapterManager addItemDecoration(RecyclerView.ItemDecoration decor){
        this.mDecor = decor;
        return getInstance();
    }

    public RefreshRecyclerAdapterManager setRefreshHeaderView(View view){
        this.swipeToLoadLayout.setRefreshHeaderView(view);
        return getInstance();
    }

    public RefreshRecyclerAdapterManager setLoadMoreFooterView(View view){
        this.swipeToLoadLayout.setLoadMoreFooterView(view);
        return getInstance();
    }


    public void into(RecyclerView recyclerView, SwipeToLoadLayout swipeToLoadLayout, Context context){
        if (null == recyclerView || null == swipeToLoadLayout){
            throw new NullPointerException("Couldn't resolve a null object reference of RecyclerView or SwipeToLoadLayout");
        }

        mAdapter.putLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        //为RefreshRecyclerView添加滚动监听
        loadMoreRecyclerListener = new LoadMoreRecyclerListener(context, mode);
        loadMoreRecyclerListener.setMode(mode);
        recyclerView.addOnScrollListener(loadMoreRecyclerListener);
        if (RecyclerMode.BOTH == mode){
            if (null != mOnBothRefreshListener){
                loadMoreRecyclerListener.setOnBothRefreshListener(mOnBothRefreshListener);
                swipeToLoadLayout.setOnBothRefreshListener(mOnBothRefreshListener);
            }
        } else if (RecyclerMode.TOP == mode){
            if(null != mOnRefreshListener){
                swipeToLoadLayout.setOnRefreshListener(mOnRefreshListener);
                swipeToLoadLayout.setLoadMoreEnabled(false);
            }
        } else if (RecyclerMode.BOTTOM == mode){
            if (null != mOnLoadMoreListener){
                loadMoreRecyclerListener.setOnLoadMoreListener(mOnLoadMoreListener);
                swipeToLoadLayout.setOnLoadMoreListener(mOnLoadMoreListener);
                swipeToLoadLayout.setRefreshEnabled(false);
            }
        } else if(RecyclerMode.NONE == mode){
            swipeToLoadLayout.setLoadMoreEnabled(false);
            swipeToLoadLayout.setRefreshEnabled(false);
        }

        //设置 刷新的样式
        if(RecyclerStyle.CLASSIC == style){
            swipeToLoadLayout.setSwipeStyle(SwipeToLoadLayout.STYLE.CLASSIC);
        }
        if(RecyclerStyle.ABOVE == style){
            swipeToLoadLayout.setSwipeStyle(SwipeToLoadLayout.STYLE.ABOVE);
        }
        if(RecyclerStyle.BLEW == style){
            swipeToLoadLayout.setSwipeStyle(SwipeToLoadLayout.STYLE.BLEW);
        }
        if(RecyclerStyle.SCALE == style){
            swipeToLoadLayout.setSwipeStyle(SwipeToLoadLayout.STYLE.SCALE);
        }

        recyclerView.addItemDecoration(mDecor);
        recyclerView.setItemAnimator(mItemAnimator);

        mAdapter.setOnItemClickListener(mOnItemClickListener);
        mAdapter.setOnItemLongClickListener(mOnItemLongClickListener);
        recyclerView.setLayoutManager(mLayoutManager);
        this.recyclerView = recyclerView;
        this.swipeToLoadLayout = swipeToLoadLayout;
    }

}
