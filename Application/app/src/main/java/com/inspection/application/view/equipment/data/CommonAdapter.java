package com.inspection.application.view.equipment.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Yangzb on 2016/8/1.
 * 所有adapter 基类
 * 抽象类不能被实例化
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private int layoutId;

    public CommonAdapter(Context context, List<T> lists, int layoutId) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.layoutId = layoutId;
        this.mDatas = lists;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.getViewHolder(mContext, convertView, parent, layoutId, position);
        conVert(viewHolder, getItem(position));
        return viewHolder.getConvertView();
    }

    public abstract void conVert(ViewHolder viewHolder, T t);
}
