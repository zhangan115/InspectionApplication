package com.library.base;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.sito.library.R;

/**
 * fragment base
 * Created by zhangan on 2017-06-21.
 */

public abstract class AbsBaseFragment extends Fragment implements DialogInterface.OnCancelListener {


    public int findColorById(int color) {
        return getResources().getColor(color);
    }

    public String findStrById(int str) {
        return getResources().getString(str);
    }

    public Drawable findDrawById(int draw) {
        return getResources().getDrawable(draw);
    }

    @Override
    public void onCancel(DialogInterface dialog) {

    }

}
