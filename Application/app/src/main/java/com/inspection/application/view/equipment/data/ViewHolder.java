package com.inspection.application.view.equipment.data;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Yangzb on 2016/8/1.
 * 通用veiwholder类
 */
public class ViewHolder {
    private SparseArray<View> mViews;//存储itme控件
    private int mPosition;
    private View mConvertView;

    public ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mPosition = position;
        this.mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);//this 就是当前的viewholder

    }

    /**
     * 获取viewholder
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static ViewHolder getViewHolder(Context context, View convertView, ViewGroup parent,
                                           int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        } else {
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mPosition = position;//convertView 复用 但position需要更新
            return viewHolder;
        }
    }

    /**
     * 获取converView
     *
     * @return
     */
    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {//判断当前view有没有存过
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public int getmPosition() {
        return mPosition;
    }

    /**
     * 为textview设置值
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setTvText(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    /**
     * 为imgview设置图片
     *
     * @param viewId
     * @param imgResId
     * @return
     */
    public ViewHolder setImgRes(int viewId, int imgResId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(imgResId);
        return this;
    }

    public ViewHolder setImgBtRes(int viewId, int imgResId) {
        ImageButton imageButton = getView(viewId);
        imageButton.setImageResource(imgResId);
        return this;
    }
}
