package com.inspection.application.view.inject;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.mode.Injection;
import com.inspection.application.mode.bean.inject.InjectEquipment;
import com.inspection.application.mode.bean.inject.InjectRoomBean;
import com.inspection.application.mode.bean.inject.OilList;
import com.inspection.application.view.BaseActivity;
import com.inspection.application.widget.InjectionView;
import com.library.adapter.RVAdapter;

import java.util.ArrayList;
import java.util.List;

public class InjectActivity extends BaseActivity implements View.OnClickListener, InjectContract.View {
    //view
    private RelativeLayout mNoDataLayout;
    private RecyclerView mExpendRecycleView;
    private LinearLayout ll_choose_local;
    private TextView mStation;
    private EditText mSearchEditText;
    private ImageView needInjectIB;
    private ImageView cleanEditTv;
    //data
    private List<String> roomList;
    private boolean needInject;
    private InjectContract.Presenter mPresenter;
    private List<InjectRoomBean> mRoomBeanList;
    private InjectRoomBean currentRoomBean;
    private List<InjectEquipment> mEquipment;
    private List<InjectEquipment> mAllEquipment;
    private List<OilList> oilLists;
    private int mPosition;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.activity_inject_list, R.string.main_inject_manager);
        new InjectPresenter(Injection.getIntent().provideInjectRepository(), this);
        initView();
        initData();
        mPresenter.getRoomList();
    }

    private void initData() {
        roomList = new ArrayList<>();
        mRoomBeanList = new ArrayList<>();
        mEquipment = new ArrayList<>();
        mAllEquipment = new ArrayList<>();
        RVAdapter<InjectEquipment> adapter = new RVAdapter<InjectEquipment>(mExpendRecycleView, mEquipment, R.layout.item_inject_list) {
            @Override
            public void showData(ViewHolder vHolder, InjectEquipment data, int position) {
                TextView tv_days = (TextView) vHolder.getView(R.id.tv_days);
                TextView mId = (TextView) vHolder.getView(R.id.id_greas_item_id);
                if (TextUtils.isEmpty(data.getEquipmentSn())) {
                    mId.setText(data.getEquipmentName());
                } else {
                    mId.setText(data.getEquipmentSn());
                }
                ImageView mImg = (ImageView) vHolder.getView(R.id.id_greas_item_img);

                String days;
                if (data.getInjectionOil() == null) {
                    days = "";
                    mImg.setImageDrawable(findDrawById(R.drawable.icon_lack_oil));
                    mId.setTextColor(findColorById(R.color.text_red));
                    tv_days.setTextColor(findColorById(R.color.text_red));
                } else {
                    if (data.getTime() <= 0) {
                        mImg.setImageDrawable(findDrawById(R.drawable.icon_full_oil));
                        mId.setTextColor(findColorById(R.color.text_green));
                        tv_days.setTextColor(findColorById(R.color.colorWhite));
                    } else {
                        mImg.setImageDrawable(findDrawById(R.drawable.icon_lack_oil));
                        mId.setTextColor(findColorById(R.color.text_red));
                        tv_days.setTextColor(findColorById(R.color.text_red));
                    }
                    days = (-1 * data.getTime()) + "天";
                }
                tv_days.setText(days);
            }
        };
        mExpendRecycleView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mPosition = position;
                if (oilLists == null || oilLists.size() == 0) {
                    mPresenter.getOilList();
                } else {
                    createDialog(position).show();
                }
            }
        });
    }

    private void initView() {
        mExpendRecycleView = findViewById(R.id.recycleViewId);
        mNoDataLayout = findViewById(R.id.layout_no_data);
        mExpendRecycleView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        mStation = findViewById(R.id.id_greasing_station);
        ll_choose_local = findViewById(R.id.ll_choose_local);
        cleanEditTv = findViewById(R.id.iv_clean_edit);
        cleanEditTv.setOnClickListener(this);
        ll_choose_local.setOnClickListener(this);
        findViewById(R.id.ll_choose_type).setOnClickListener(this);
        needInjectIB = findViewById(R.id.iv_choose_type_icon);
        mSearchEditText = findViewById(R.id.edit_search);
        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!TextUtils.isEmpty(mSearchEditText.getText().toString())) {
                        mPresenter.searchEquipment(mAllEquipment, mSearchEditText.getText().toString());
                        return true;
                    }
                }
                return false;
            }
        });
        mSearchEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPresenter.searchEquipment(mAllEquipment, s.toString());
                if (s.length() > 0) {
                    cleanEditTv.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_choose_local:
                new MaterialDialog.Builder(InjectActivity.this)
                        .items(roomList)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                mStation.setText(roomList.get(position));
                                currentRoomBean = mRoomBeanList.get(position);
                                mPresenter.getRoomEquipmentList(currentRoomBean.getRoomId());
                            }
                        })
                        .show();
                break;
            case R.id.ll_choose_type:
                needInject = !needInject;
                if (needInject) {
//                    needInjectIB.setBackground(findDrawById(R.drawable.choose_select));
                    mPresenter.getNeedInjectEqu(mAllEquipment);
                } else {
//                    needInjectIB.setBackground(findDrawById(R.drawable.choose_normal));
                    showNeedInjectEqu(mAllEquipment);
                }
                break;
            case R.id.iv_clean_edit:
                mSearchEditText.setText("");
                mSearchEditText.setSelection(0);
                break;
        }
    }

    @Override
    public void setPresenter(InjectContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void noData() {
        mEquipment.clear();
        mExpendRecycleView.getAdapter().notifyDataSetChanged();
        mNoDataLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRoomList(List<InjectRoomBean> roomBeanList) {
        mNoDataLayout.setVisibility(View.GONE);
        ll_choose_local.setVisibility(View.VISIBLE);
        this.mRoomBeanList = roomBeanList;
        for (int i = 0; i < roomBeanList.size(); i++) {
            roomList.add(roomBeanList.get(i).getRoomName());
        }
        mStation.setText(roomBeanList.get(0).getRoomName());
        currentRoomBean = roomBeanList.get(0);
        mPresenter.getRoomEquipmentList(currentRoomBean.getRoomId());
    }

    @Override
    public void getRoomListError() {
        ll_choose_local.setVisibility(View.GONE);
    }

    @Override
    public void showRoomEquipment(List<InjectEquipment> injectEquipments) {
        mAllEquipment = injectEquipments;
        mEquipment.clear();
        mEquipment.addAll(injectEquipments);
        mExpendRecycleView.getAdapter().notifyDataSetChanged();
        if (needInject) {
            mPresenter.getNeedInjectEqu(mAllEquipment);
        }
    }

    @Override
    public void getEquipmentError() {

    }

    @Override
    public void injectSuccess(int position) {
        mPresenter.getRoomEquipmentList(currentRoomBean.getRoomId());
    }

    @Override
    public void injectFail(int position) {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        mEquipment.get(position).setInject(false);
        mExpendRecycleView.getAdapter().notifyItemChanged(position);
        App.getInstance().showToast("提交失败");
    }

    @Override
    public void showNeedInjectEqu(List<InjectEquipment> injectEquipments) {
        mEquipment.clear();
        mEquipment.addAll(injectEquipments);
        mExpendRecycleView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showOilList(List<OilList> data) {
        this.oilLists = data;
        createDialog(mPosition).show();
    }

    @Override
    public void noOilList() {
        App.getInstance().showToast("没有油料");
    }

    private AlertDialog createDialog(int position) {
        InjectionView injectionView = new InjectionView(InjectActivity.this, mEquipment.get(position), position, oilLists) {
            @Override
            public void injectEquipment(int position, Integer cycle, Long oilId) {
                mEquipment.get(position).setInject(true);
                mExpendRecycleView.getAdapter().notifyItemChanged(position);
                mPresenter.injectionEquipment(mEquipment.get(position).getEquipmentId(), cycle, oilId, position);
            }

            @Override
            public void cancel() {
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
            }
        };
        alertDialog = new AlertDialog.Builder(this, R.style.dialog)
                .setView(injectionView)
                .create();
        return alertDialog;
    }

    @Override
    protected void onPause() {
        super.onPause();
        App.getInstance().hideSoftKeyBoard(this);
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
    }

}
