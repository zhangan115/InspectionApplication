package com.inspection.application.view.equipment;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inspection.application.R;
import com.inspection.application.mode.bean.equipment.EquipmentBean;
import com.inspection.application.mode.bean.equipment.RoomListBean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class EquipListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private int groupLayout, childLayout;
    private List<RoomListBean> data = new ArrayList<>();

    private ItemClickListener listener;

    interface ItemClickListener {

        void onItemClick(EquipmentBean equipName);

    }

    void setItemListener(ItemClickListener listener) {
        this.listener = listener;
    }

    EquipListAdapter(Context context, int groupLayout, int childLayout) {
        this.context = context;
        this.groupLayout = groupLayout;
        this.childLayout = childLayout;
    }

    public void setData(List<RoomListBean> data) {
        this.data = data;
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data.get(groupPosition).getEquipments().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).getEquipments().get(childPosition);
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
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupViewHolder holder;
        if (convertView == null) {
            holder = new GroupViewHolder();
            convertView = LayoutInflater.from(context).inflate(groupLayout, null);
            holder.mGroup = convertView.findViewById(R.id.ll_item_group);
            holder.mPlace = convertView.findViewById(R.id.id_item_equip_place);
            holder.mCount = convertView.findViewById(R.id.id_item_equip_mum);
            holder.stateIv = convertView.findViewById(R.id.iv_state);
            holder.mLine = convertView.findViewById(R.id.id_item_equip_line);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        holder.mPlace.setText(data.get(groupPosition).getRoomName());
        holder.mCount.setText(String.valueOf(data.get(groupPosition).getEquipments().size()));
        if (data.size() == 1) {
            holder.mLine.setVisibility(View.VISIBLE);
        } else if (groupPosition == 0) {
            holder.mLine.setVisibility(View.VISIBLE);
        } else if (groupPosition == data.size() - 1) {
            holder.mLine.setVisibility(View.GONE);
        } else {
            holder.mLine.setVisibility(View.VISIBLE);

        }
        if (isExpanded) {
//            holder.stateIv.setImageDrawable(context.getResources().getDrawable(R.drawable.bg_employee_arrow_open));
            if (groupPosition == data.size() - 1) {
                holder.mLine.setVisibility(View.VISIBLE);
            }
        } else {
            if (groupPosition == data.size() - 1) {
                holder.mLine.setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder holder;
        if (convertView == null) {
            holder = new ChildViewHolder();
            convertView = LayoutInflater.from(context).inflate(childLayout, null);
            holder.mName = (TextView) convertView.findViewById(R.id.id_item_equip_name);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        holder.mName.setText(String.format("%s%s", data.get(groupPosition).getEquipments().get(childPosition).getEquipmentName()
                , TextUtils.isEmpty(data.get(groupPosition).getEquipments().get(childPosition).getEquipmentSn()) ? ""
                        : "(" + data.get(groupPosition).getEquipments().get(childPosition).getEquipmentSn() + ")"));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener == null) {
                    return;
                }
                listener.onItemClick(data.get(groupPosition).getEquipments().get(childPosition));
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * 外部显示ViewHolder
     */
    private class GroupViewHolder {
        LinearLayout mGroup;
        View mLine;
        TextView mPlace;
        TextView mCount;
        ImageView stateIv;
    }

    /**
     * 内部显示ViewHolder
     */
    private class ChildViewHolder {
        TextView mName;
        View mLine;
        LinearLayout mChild;
    }
}
