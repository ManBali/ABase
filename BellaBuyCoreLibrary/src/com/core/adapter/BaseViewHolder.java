package com.core.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by sufun_job on 2016/5/25.
 *
 * @description 最
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    /**
     * 设置文字
     *
     * @param id
     * @param text
     * @return
     */
    public BaseViewHolder setText(int id, String text) {
        TextView textView = (TextView) itemView.findViewById(id);
        textView.setText(text);
        return this;
    }

    /**
     * 取得单元格的数据
     *
     * @return
     */
    public View getItemView() {
        return itemView;
    }

    public BaseViewHolder setButtonText(int id, String text) {
        Button btn = (Button) itemView.findViewById(id);
        btn.setText(text);
        return this;
    }

}
