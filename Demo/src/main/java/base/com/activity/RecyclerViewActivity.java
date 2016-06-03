package base.com.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aspsine.swipetoloadlayout.OnBothRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.core.activity.AbstractActivity;
import com.core.adapter.IAdapterEventDelegate;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import base.com.abase.R;
import base.com.adapter.CaseAdapter;
import base.com.adapter.DividerItemDecoration;
import base.com.model.CaseModel;
import space.sye.z.library.adapter.RefreshRecyclerViewAdapter;
import space.sye.z.library.manager.RecyclerMode;
import space.sye.z.library.manager.RecyclerStyle;
import space.sye.z.library.manager.RecyclerViewManager;

/**
 * Created by sufun_job on 2016/5/24.
 */
@EActivity(R.layout.recyclerview_activity)
public class RecyclerViewActivity extends AbstractActivity {

    @ViewById(R.id.swipe_target)
     RecyclerView recyclerView;
    @ViewById(R.id.swipeToLoadLayout)
     SwipeToLoadLayout swipeToLoadLayout;

    CaseAdapter mAdapter;

    int count=0;
    @AfterViews
    void init() {
        setTitleName("下接刷新的展示与测试");
        initRefreshView();
    }

    /**
     * 初始化下拉刷新的按钮
     */
    void initRefreshView() {

        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
            }
        });
        mAdapter=new CaseAdapter(this, R.layout.case_item, new IAdapterEventDelegate<CaseModel>() {
            @Override
            public void onEevnt(int pos, CaseModel caseModel, int EventType) {

            }
        });
        mAdapter.setDatas(new ArrayList<CaseModel>());
        RecyclerViewManager.getInstance().with(mAdapter,new LinearLayoutManager(this))  //new GridLayoutManager(this, 3)    new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL))
                .setMode(RecyclerMode.BOTH)    //刷新的方向
                .setStyle(RecyclerStyle.CLASSIC)  //展示的类型
/*                .addHeaderView(header)    //添加顶部的Header
                .addHeaderView(header2)    //添加顶部的header
                .addFooterView(footer)*/   //添加底部的FootderView
                .addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST))
                .setOnBothRefreshListener(new OnBothRefreshListener() {
                    @Override
                    public void onRefresh() {  //上拉刷新事件触发
                        count=0;
                        Message msg = new Message();
                        msg.what = 0;
                        mHandler.sendMessageDelayed(msg, 2000);
                    }

                    @Override
                    public void onLoadMore() {    //加载更多事件触发
                        Message msg = new Message();
                        msg.what = 1;
                        mHandler.sendMessageDelayed(msg, 2000);
                    }
                }).setOnItemClickListener(new RefreshRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {

            }
        }).into(recyclerView, swipeToLoadLayout, this);
    }

    /**
     * 取得相磁的数据
     */
    List<CaseModel> getData() {

        List<CaseModel>  datas=new ArrayList<CaseModel>();
        for (int i=count;i<count+10;i++)
        {
            CaseModel bean=new CaseModel();;
            bean.title="this button index is "+i;
            datas.add(bean);
        }
        count=count+10;
        return datas;
    }


    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                     mAdapter.setDatas(getData());
                    swipeToLoadLayout.setRefreshing(false);
                    break;
                case 1:
                    mAdapter.addDatas(getData());
                    swipeToLoadLayout.setLoadingMore(false);
                    break;
            }
            mAdapter.notifyDataSetChanged();
        }
    };
}
