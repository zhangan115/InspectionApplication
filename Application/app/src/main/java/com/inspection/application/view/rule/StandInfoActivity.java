package com.inspection.application.view.rule;

import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;

import com.inspection.application.R;
import com.inspection.application.common.ConstantStr;
import com.inspection.application.view.WebActivity;


public class StandInfoActivity extends WebActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getIntent().getStringExtra(ConstantStr.KEY_TITLE);
        String content = getIntent().getStringExtra(ConstantStr.KEY_BUNDLE_STR);
        if (TextUtils.isEmpty(content)) {
            finish();
            return;
        }
        setLayoutAndToolbar(R.layout.activity_stand_info, title);
        WebView tvContent = findViewById(R.id.id_stand_info);
        showWeb(tvContent, content);
    }

}
