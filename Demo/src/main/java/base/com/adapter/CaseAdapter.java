package base.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.core.adapter.BaseRecyclerAdapter;
import com.core.adapter.BaseViewHolder;
import com.core.adapter.IAdapterEventDelegate;

import base.com.abase.R;
import base.com.model.CaseModel;

/**
 * Created by sufun_job on 2016/5/25.
 *
 * @description 例子Adapter
 */
public class CaseAdapter extends BaseRecyclerAdapter<CaseModel> {

    public CaseAdapter(Context context, int resId, IAdapterEventDelegate<CaseModel> delegate) {
        super(context, resId, delegate);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(mContext).inflate(R.layout.case_item, parent, false);
        BaseViewHolder holder = new BaseViewHolder(item);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        CaseModel bean = mDatas.get(position);
        holder.setButtonText(R.id.id_item, bean.title);
    }
}
