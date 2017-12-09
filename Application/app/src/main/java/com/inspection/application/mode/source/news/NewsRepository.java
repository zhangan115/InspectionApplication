package com.inspection.application.mode.source.news;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 消息 repository
 * Created by pingan on 2017/12/9.
 */
@Singleton
public class NewsRepository implements NewsDataSource {

    @Inject
    public NewsRepository(Context context) {

    }

}
