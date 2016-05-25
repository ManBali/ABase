package com.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sufun_job on 2016/5/25.
 * @description  最基础的Adapter
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder>{
    public int mResId=0;

    public Context mContext;

    public IAdapterEventDelegate<T>  mdelegate;

    public List<T>  mDatas=new ArrayList<T>();
    public BaseRecyclerAdapter(Context context,int resId ,IAdapterEventDelegate<T>  delegate)
    {
        super();
        this.mResId=resId;
        mContext=context;
        this.mdelegate=delegate;
    }

    @Override
    public abstract  BaseViewHolder  onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(BaseViewHolder holder, int position) ;

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    /**
     * 取得内部的所有的数据
     * @return
     */
    public List<T>  getAllDatas()
    {
        return  mDatas;
    }

    /**
     * 重置数据
     * @param datas
     */
    public void setDatas(List<T>  datas)
    {
        this.mDatas.clear();
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     * @param datas
     */
    public void addDatas(List<T> datas)
    {
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }
}
