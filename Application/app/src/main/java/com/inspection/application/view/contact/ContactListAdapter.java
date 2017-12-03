package com.inspection.application.view.contact;

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

import java.util.ArrayList;
import java.util.List;

/**
 * 联系人
 * Created by zhangan on 2017/9/5.
 */

class ContactListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private int groupLayout, childLayout;
    private List<DepartmentBean> data = new ArrayList<>();

    ContactListAdapter(Context context, int groupLayout, int childLayout) {
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
        if (data.size() == 0) {
            holder.division.setVisibility(View.GONE);
//            holder.bgLayout.setBackground(context.getResources().getDrawable(R.drawable.bg));
        } else if (groupPosition == 0) {
            holder.division.setVisibility(View.VISIBLE);
//            holder.bgLayout.setBackground(context.getResources().getDrawable(R.drawable.bg_up));
        } else if (groupPosition == data.size() - 1) {
            holder.division.setVisibility(View.GONE);
//            holder.bgLayout.setBackground(context.getResources().getDrawable(R.drawable.bg_down));
            if (isExpanded) {
//                holder.bgLayout.setBackground(context.getResources().getDrawable(R.drawable.bg_centre));
            }
        } else {
            holder.division.setVisibility(View.VISIBLE);
//            holder.bgLayout.setBackground(context.getResources().getDrawable(R.drawable.bg_centre));
        }
        if (isExpanded) {
//            holder.stateIv.setImageDrawable(context.getResources().getDrawable(R.drawable.bg_employee_arrow_open));
        } else {
//            holder.stateIv.setImageDrawable(context.getResources().getDrawable(R.drawable.bg_employee_arrow));
        }
        holder.nameTv.setText(data.get(groupPosition).getDeptName());
        if (data.get(groupPosition).getUserList() != null) {
            holder.countTv.setText(String.valueOf(data.get(groupPosition).getUserList().size()));
        } else {
            holder.countTv.setText(String.valueOf(0));
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
            holder.positionTV = convertView.findViewById(R.id.tv_position);
            holder.callTv = convertView.findViewById(R.id.tv_call);
            holder.ll_child_layout = convertView.findViewById(R.id.ll_child_layout);
            holder.stateIv = convertView.findViewById(R.id.iv_icon);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        holder.nameTv.setText(data.get(groupPosition).getUserList().get(childPosition).getUser().getRealName());
        if (data.get(groupPosition).getUserList().get(childPosition).getUser().getUserId() == App.getInstance().getCurrentUser().getUserId()) {
//            GlideUtils.ShowCircleImage(context, App.getInstance().getCurrentUser().getPortraitUrl()
//                    , holder.stateIv, R.drawable.mine_head_default);
        } else {
//            GlideUtils.ShowCircleImage(context, data.get(groupPosition).getUserList().get(childPosition).getUser().getPortraitUrl()
//                    , holder.stateIv, R.drawable.mine_head_default);
        }
        holder.ll_child_layout.setTag(R.id.tag_group_position, groupPosition);
        holder.ll_child_layout.setTag(R.id.tag_child_position, childPosition);
        holder.positionTV.setText(data.get(groupPosition).getUserList().get(childPosition).getUser().getUserRoleNames());
        holder.callTv.setText(data.get(groupPosition).getUserList().get(childPosition).getUser().getUserPhone());
        holder.ll_child_layout.setTag(R.id.tag_position, data.get(groupPosition).getUserList().get(childPosition).getUser().getUserPhone());
        holder.ll_child_layout.setOnClickListener(call);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private View.OnClickListener call = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            call((String) v.getTag(R.id.tag_position));
        }
    };

    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
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
        TextView nameTv;
        TextView positionTV;
        TextView callTv;
        LinearLayout ll_child_layout;
    }
}
