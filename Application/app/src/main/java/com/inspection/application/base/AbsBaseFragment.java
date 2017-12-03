package com.inspection.application.base;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;

/**
 * fragment base
 * Created by zhangan on 2017-06-21.
 */

public abstract class AbsBaseFragment extends Fragment {


    public int findColorById(int color) {
        return getResources().getColor(color);
    }

    public String findStrById(int str) {
        return getResources().getString(str);
    }

    public Drawable findDrawById(int draw) {
        return getResources().getDrawable(draw);
    }


}
