package com.inspection.application.view.secure;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.mode.Injection;
import com.inspection.application.mode.bean.secure.SecureBean;
import com.inspection.application.view.WebActivity;
import com.inspection.application.view.task.info.TaskInfoActivity;
import com.library.utils.SPHelper;

import java.util.ArrayList;
import java.util.List;


public class SecureActivity extends WebActivity implements View.OnClickListener, SecureContract.View {
    private WebView mContent;
    private TextView mPrevious;//上一页
    private TextView mNext;//下一页
    SecureContract.Presenter mPresenter;
    private long mTaskId;
    private int mPosition;
    private int previous;
    private int next;
    private List<SecureBean.PageListBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getIntent().getStringExtra(ConstantStr.KEY_BUNDLE_STR);
        if (!TextUtils.isEmpty(title)) {
            setLayoutAndToolbar(R.layout.activity_secure, title);
        } else {
            setLayoutAndToolbar(R.layout.activity_secure, "管理规定");
        }
        new SecurePresenter(Injection.getIntent().provideTaskRepository(App.getInstance().getModule()), this);
        long mSecurityId = getIntent().getLongExtra(ConstantStr.KEY_BUNDLE_LONG, -1);
        mTaskId = getIntent().getLongExtra(ConstantStr.KEY_BUNDLE_LONG_1, -1);
        mPresenter.getSecureInfo(mSecurityId);
        mList = new ArrayList<>();
        initView();
        initEvent();
    }

    private void initEvent() {
        mNext.setOnClickListener(this);
        mPrevious.setOnClickListener(this);
    }

    private void initView() {
        mContent = findViewById(R.id.id_secure_content);
        mPrevious = findViewById(R.id.id_previous_page);
        mNext = findViewById(R.id.id_next_page);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.id_previous_page:
                previous = mPosition;
                mNext.setText("下一页");
                if (previous > 0) {
                    --previous;
                    showWeb(mContent, mList.get(previous).getPageContent());
                    mPosition = previous;
                    if (previous == 0) {
                        mPrevious.setVisibility(View.GONE);
                    }
                }
                break;
            case R.id.id_next_page:
                next = mPosition;
                if (next < mList.size() - 1) {
                    mPrevious.setVisibility(View.VISIBLE);
                    mPrevious.setText("上一页");
                    ++next;
                    showWeb(mContent, mList.get(next).getPageContent());
                    mPosition = next;
                    if (next == mList.size() - 1) {
                        mNext.setText("确定");
                    }
                } else {
                    startRoomList();
                }
                break;
        }
    }

    private void startRoomList() {
        SPHelper.write(this, ConstantStr.SECURE_INFO, String.valueOf(mTaskId), true);
        Intent intent = new Intent(this, TaskInfoActivity.class);
        intent.putExtra(ConstantStr.KEY_BUNDLE_LONG, mTaskId);
        startActivity(intent);
        finish();
    }

    @Override
    public void showData(SecureBean secureBean) {
        if (secureBean != null && secureBean.getPageList() != null && secureBean.getPageList().size() > 0) {
            next = 0;
            previous = 0;
            mList.clear();
            mList.addAll(secureBean.getPageList());
            String mContentStr = mList.get(next).getPageContent();
            showWeb(mContent, mContentStr);
            mPrevious.setVisibility(View.GONE);
            if (mList != null && mList.size() == 1) {
                mNext.setText("确定");
            }
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void noData() {
        startRoomList();
    }

    @Override
    public void showMessage(String message) {
        App.getInstance().showToast(message);
    }

    @Override
    public void setPresenter(SecureContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
