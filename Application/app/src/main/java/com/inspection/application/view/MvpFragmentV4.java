package com.inspection.application.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.library.base.BasePresenter;


/**
 * Created by zhangan on 2017-06-22.
 */

public class MvpFragmentV4<T extends BasePresenter> extends BaseFragmentV4 {
    @Nullable
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
