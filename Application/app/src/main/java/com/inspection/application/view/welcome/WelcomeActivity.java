package com.inspection.application.view.welcome;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;


import com.inspection.application.R;
import com.inspection.application.view.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 第一次欢迎界面
 * Created by zhangan on 2017-07-22.
 */

public class WelcomeActivity extends Activity implements View.OnClickListener {

    private static final int[] pics = {R.layout.guide_view1,
            R.layout.guide_view2, R.layout.guide_view3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        transparentStatusBar();
        List<View> views = new ArrayList<>();
        ViewPager viewPager = findViewById(R.id.view_pager);
        for (int i = 0; i < pics.length; i++) {
            View view = LayoutInflater.from(this).inflate(pics[i], null);
            if (i == pics.length - 1) {
                Button startBtn = view.findViewById(R.id.btn_enter);
                startBtn.setTag("enter");
                startBtn.setOnClickListener(this);
            }
            views.add(view);
        }
        viewPager.setAdapter(new GuideViewPagerAdapter(views));
    }

    public void transparentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_enter:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
    }
}
