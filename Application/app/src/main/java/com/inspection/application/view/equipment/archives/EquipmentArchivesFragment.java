package com.inspection.application.view.equipment.archives;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.bean.equipment.EquipmentBean;
import com.inspection.application.view.MvpFragment;
import com.inspection.application.view.defect.DefectRecordActivity;
import com.inspection.application.view.photo.ViewPagePhotoActivity;
import com.library.utils.DataUtil;
import com.library.utils.GlideUtils;


/**
 * 设备档案
 * Created by zhangan on 2017/10/12.
 */

public class EquipmentArchivesFragment extends MvpFragment<EquipmentArchivesContract.Presenter> implements EquipmentArchivesContract.View, View.OnClickListener {

    @Nullable
    private EquipmentBean bean;

    public static EquipmentArchivesFragment newInstance(EquipmentBean bean) {
        Bundle args = new Bundle();
        args.putParcelable(ConstantStr.KEY_BUNDLE_OBJECT, bean);
        EquipmentArchivesFragment fragment = new EquipmentArchivesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bean = getArguments().getParcelable(ConstantStr.KEY_BUNDLE_OBJECT);
        }
        if (bean == null && getActivity() != null) {
            getActivity().finish();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fmg_equipment_archives, container, false);
        setData(rootView);
        return rootView;
    }

    private void setData(View rootView) {
        rootView.findViewById(R.id.ll_1).setOnClickListener(this);
        rootView.findViewById(R.id.ll_2).setOnClickListener(this);
        if (bean == null) {
            return;
        }
        if (bean.getRoom() != null) {
            ((TextView) rootView.findViewById(R.id.tv_0)).setText(bean.getRoom().getRoomName());
        } else {
            ((TextView) rootView.findViewById(R.id.tv_0)).setText(R.string.un_know_state);
        }
        if (!TextUtils.isEmpty(bean.getEquipmentSn())) {
            ((TextView) rootView.findViewById(R.id.tv_1)).setText(bean.getEquipmentSn());
        } else {
            ((TextView) rootView.findViewById(R.id.tv_1)).setText(R.string.un_know_state);
        }
        if (bean.getEquipmentType() != null) {
            ((TextView) rootView.findViewById(R.id.tv_2)).setText(bean.getEquipmentType().getEquipmentTypeName());
        } else {
            ((TextView) rootView.findViewById(R.id.tv_2)).setText(R.string.un_know_state);
        }
        if (!TextUtils.isEmpty(bean.getEquipmentFsn())) {
            ((TextView) rootView.findViewById(R.id.tv_3)).setText(bean.getEquipmentFsn());
        }
        if (!TextUtils.isEmpty(bean.getManufacturer())) {
            ((TextView) rootView.findViewById(R.id.tv_4)).setText(bean.getManufacturer());
        }
        if (!TextUtils.isEmpty(bean.getSupplier())) {
            ((TextView) rootView.findViewById(R.id.tv_5)).setText(bean.getSupplier());
        }
        if (bean.getWorkingState() != 0) {
            ((TextView) rootView.findViewById(R.id.tv_6)).setText(App.getInstance()
                    .getMapOption().get("13").get(String.valueOf(bean.getWorkingState())));
        }
        if (bean.getEquipmentState() != 0) {
            ((TextView) rootView.findViewById(R.id.tv_7)).setText(App.getInstance()
                    .getMapOption().get("14").get(String.valueOf(bean.getEquipmentState())));
        }
        if (bean.getInstallTime() != 0) {
            ((TextView) rootView.findViewById(R.id.tv_8)).setText(DataUtil.timeFormat(bean.getInstallTime(), "yyyy.MM.dd"));
        }
        if (bean.getManufactureTime() != 0) {
            ((TextView) rootView.findViewById(R.id.tv_9)).setText(DataUtil.timeFormat(bean.getManufactureTime(), "yyyy.MM.dd"));
        }
        if (bean.getStartTime() != 0) {
            ((TextView) rootView.findViewById(R.id.tv_10)).setText(DataUtil.timeFormat(bean.getStartTime(), "yyyy.MM.dd"));
        }
        ImageView equipmentImage = rootView.findViewById(R.id.image);
        GlideUtils.ShowImage(getActivity(), bean.getNameplatePicUrl(), equipmentImage, R.drawable.icon_nameplate);
    }

    @Override
    public void setPresenter(EquipmentArchivesContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.ll_1:
                intent = new Intent(getActivity(), DefectRecordActivity.class);
                intent.putExtra(ConstantStr.KEY_BUNDLE_OBJECT, bean);
                break;
            case R.id.ll_2:

                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
