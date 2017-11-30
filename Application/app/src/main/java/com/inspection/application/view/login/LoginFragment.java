package com.inspection.application.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import com.inspection.application.R;
import com.inspection.application.mode.bean.User;
import com.inspection.application.view.MvpFragment;
import com.inspection.application.view.main.MainActivity;

import java.util.List;

/**
 * 登陆界面
 * Created by zhangan on 2017-06-22.
 */

public class LoginFragment extends MvpFragment<LoginContract.Presenter> implements LoginContract.View, View.OnClickListener {

    private AppCompatMultiAutoCompleteTextView mNameEt;
    private EditText mPassEt;

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fmg_login, container, false);
        mNameEt = (AppCompatMultiAutoCompleteTextView) rootView.findViewById(R.id.edit_username);
        mPassEt = (EditText) rootView.findViewById(R.id.edit_password);
        rootView.findViewById(R.id.tv_login).setOnClickListener(this);
        rootView.findViewById(R.id.tv_forget_password).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void loginSuccess() {
        startHomeActivity();
    }

    @Override
    public void loginFail() {

    }

    @Override
    public void loginLoading() {
        showProgressDialog("登陆中...");
    }

    @Override
    public void loginHideLoading() {
        hideProgressDialog();
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void showHistoryUser(@NonNull List<User> userList) {

    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                checkUserInfo();
                break;
            case R.id.tv_forget_password:
//                startActivity(new Intent(getActivity(), RegisterActivity.class));
                break;
        }
    }

    int startTestTimes;

    private void checkUserInfo() {
        String userName = mNameEt.getEditableText().toString().trim();
        String userPass = mPassEt.getEditableText().toString().trim();
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPass)) {
            return;
        }
        if (mPresenter != null) {
            mPresenter.login(userName, userPass);
        }

    }

    private void startHomeActivity() {
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();
    }
}
