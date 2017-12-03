package com.inspection.application.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.inspection.application.base.BasePresenter;


/**
 * mvp fragment
 * <p>
 * Created by zhangan on 2017-04-27.
 */

public class MvpFragment<T extends BasePresenter> extends BaseFragment {

    protected T mPresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void onCancel() {
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
    }
}
