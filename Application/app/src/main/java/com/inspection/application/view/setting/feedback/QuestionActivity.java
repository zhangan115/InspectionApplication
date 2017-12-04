package com.inspection.application.view.setting.feedback;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.inspection.application.R;
import com.inspection.application.app.App;
import com.inspection.application.mode.Injection;
import com.inspection.application.view.BaseActivity;

public class QuestionActivity extends BaseActivity implements QuestionContract.View {
    private EditText mTitle;
    private EditText mContent;
    private QuestionContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutAndToolbar(R.layout.activity_question, "意见反馈");
        new QuestionPresenter(Injection.getIntent().provideApplicationRepository(App.getInstance().getModule()), this);
        initViews();
    }

    private void initViews() {
        mTitle = findViewById(R.id.id_question_title);
        mContent = findViewById(R.id.id_question_content);
        findViewById(R.id.id_question_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mContent.getText().toString())) {
                    App.getInstance().showToast("请输入内容");
                    return;
                }
                if (mPresenter != null) {
                    mPresenter.postQuestion(mTitle.getText().toString(), mContent.getText().toString());
                }
            }
        });
    }

    @Override
    public void postSuccess() {
        App.getInstance().showToast("提交成功！");
        finish();
    }

    @Override
    public void postFail() {
        App.getInstance().showToast("提交失败了！");
    }

    @Override
    public void postFinish() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
    }

    @Override
    public void showLoading() {
        showProgressDialog("提交中...");
    }

    @Override
    public void hideLoading() {
        hideProgressDialog();
    }

    @Override
    public void setPresenter(QuestionContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
