package com.inspection.application.view.employee;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.mode.bean.customer.DepartmentBean;
import com.inspection.application.mode.bean.customer.EmployeeBean;
import com.library.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 联系人
 * Created by zhangan on 2017/9/5.
 */

class EmployeeAdapter extends BaseExpandableListAdapter {
    private Context context;
    private int groupLayout, childLayout;
    private List<DepartmentBean> data = new ArrayList<>();

    EmployeeAdapter(Context context, int groupLayout, int childLayout) {
        this.context = context;
        this.groupLayout = groupLayout;
        this.childLayout = childLayout;
    }

    public void setData(List<DepartmentBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (data.get(groupPosition).getUserList() == null) {
            return 0;
        }
        return data.get(groupPosition).getUserList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (data.get(groupPosition).getUserList() == null) {
            return 0;
        }
        return data.get(groupPosition).getUserList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupViewHolder holder;
        if (convertView == null) {
            holder = new GroupViewHolder();
            convertView = LayoutInflater.from(context).inflate(groupLayout, null);
            holder.bgLayout = convertView.findViewById(R.id.ll_item_group);
            holder.division = convertView.findViewById(R.id.division_id);
            holder.stateIv = convertView.findViewById(R.id.iv_state);
            holder.nameTv = convertView.findViewById(R.id.tv_name);
            holder.countTv = convertView.findViewById(R.id.tv_count);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        if (isExpanded) {
            holder.stateIv.setImageDrawable(context.getResources().getDrawable(R.drawable.list_narrow_top_normal));
            holder.bgLayout.setBackground(context.getResources().getDrawable(R.drawable.bg_top));
            holder.division.setVisibility(View.VISIBLE);
        } else {
            holder.stateIv.setImageDrawable(context.getResources().getDrawable(R.drawable.list_narrow_under_normal));
            holder.bgLayout.setBackground(context.getResources().getDrawable(R.drawable.bg_whit_shadw_2));
            holder.division.setVisibility(View.GONE);
        }
        holder.nameTv.setText(data.get(groupPosition).getDeptName());
        if (data.get(groupPosition).getUserList() != null) {
            holder.countTv.setText(String.format("%s名", String.valueOf(data.get(groupPosition).getUserList().size())));
        } else {
            holder.countTv.setText(String.format("%s名", String.valueOf(0)));
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder holder;
        if (convertView == null) {
            holder = new ChildViewHolder();
            convertView = LayoutInflater.from(context).inflate(childLayout, null);
            holder.nameTv = convertView.findViewById(R.id.tv_name);
            holder.ll_child_layout = convertView.findViewById(R.id.ll_child_layout);
            holder.stateIv = convertView.findViewById(R.id.iv_icon);
            holder.chooseIv = convertView.findViewById(R.id.iv_state);
            holder.positionTV = convertView.findViewById(R.id.tv_position);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        holder.nameTv.setText(data.get(groupPosition).getUserList().get(childPosition).getUser().getRealName());
        if (data.get(groupPosition).getUserList().get(childPosition).getUser().getUserId() == App.getInstance().getCurrentUser().getUserId()) {
            GlideUtils.ShowCircleImage(context, App.getInstance().getCurrentUser().getPortraitUrl()
                    , holder.stateIv, R.drawable.icon_monitor);
        } else {
            GlideUtils.ShowCircleImage(context, data.get(groupPosition).getUserList().get(childPosition).getUser().getPortraitUrl()
                    , holder.stateIv, R.drawable.icon_monitor);
        }
        holder.ll_child_layout.setTag(R.id.tag_group_position, groupPosition);
        holder.ll_child_layout.setTag(R.id.tag_child_position, childPosition);
        holder.ll_child_layout.setOnClickListener(onClickListener);
        holder.positionTV.setText(data.get(groupPosition).getUserList().get(childPosition).getUser().getUserRoleNames());
        GlideUtils.ShowCircleImage(context, data.get(groupPosition).getUserList().get(childPosition).getUser().getPortraitUrl(), holder.stateIv, R.drawable.icon_monitor);
        if (isLastChild) {
            holder.ll_child_layout.setBackground(context.getResources().getDrawable(R.drawable.bg_bottom));
        } else {
            holder.ll_child_layout.setBackground(context.getResources().getDrawable(R.drawable.bg_center));
        }
        if (data.get(groupPosition).getUserList().get(childPosition).isSelect()) {
            holder.chooseIv.setImageDrawable(context.getResources().getDrawable(R.drawable.choose_select));
        } else {
            holder.chooseIv.setImageDrawable(context.getResources().getDrawable(R.drawable.choose_normal));
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int groupPosition = (int) v.getTag(R.id.tag_group_position);
            int childPosition = (int) v.getTag(R.id.tag_child_position);
            EmployeeBean employ = data.get(groupPosition).getUserList().get(childPosition);
            if (employ.getUser().getUserId() == App.getInstance().getCurrentUser().getUserId()) {
                return;
            }
            boolean isSelect = !employ.isSelect();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getUserList() == null || data.get(i).getUserList().size() == 0) {
                    continue;
                }
                for (int j = 0; j < data.get(i).getUserList().size(); j++) {
                    if (data.get(i).getUserList().get(j).getUser().getUserId() == employ.getUser().getUserId()) {
                        data.get(i).getUserList().get(j).setSelect(isSelect);
                    }
                }
            }
            notifyDataSetChanged();
        }
    };

    public ArrayList<EmployeeBean> getSelectEmployee() {
        ArrayList<EmployeeBean> employeeBeen = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getUserList() == null) {
                continue;
            }
            for (int j = 0; j < data.get(i).getUserList().size(); j++) {
                if (data.get(i).getUserList().get(j).isSelect()) {
                    boolean needAdd = true;
                    for (int k = 0; k < employeeBeen.size(); k++) {
                        if (employeeBeen.get(k).getUser().getUserId() == data.get(i).getUserList().get(j).getUser().getUserId()) {
                            needAdd = false;
                        }
                    }
                    if (needAdd) {
                        employeeBeen.add(data.get(i).getUserList().get(j));
                    }
                }
            }
        }
        return employeeBeen;
    }

    /**
     * 外部显示ViewHolder
     */
    private class GroupViewHolder {
        LinearLayout bgLayout;
        View division;
        ImageView stateIv;
        TextView nameTv;
        TextView countTv;
    }

    /**
     * 内部显示ViewHolder
     */
    private class ChildViewHolder {
        ImageView stateIv;
        ImageView chooseIv;
        TextView nameTv;
        TextView positionTV;
        LinearLayout ll_child_layout;
    }
}
