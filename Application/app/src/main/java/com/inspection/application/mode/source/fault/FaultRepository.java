package com.inspection.application.mode.source.fault;

import android.content.Context;

import com.inspection.application.view.ApplicationModule;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * fault repository
 * Created by pingan on 2017/12/5.
 */
@Singleton
public class FaultRepository implements FaultDataSource {

    @Inject
    public FaultRepository(Context context) {

    }


}
