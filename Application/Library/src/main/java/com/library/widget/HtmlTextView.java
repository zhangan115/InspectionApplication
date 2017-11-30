package com.library.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;

import com.library.utils.LocalImageGetter;
import com.library.utils.UrlImageGetter;
import com.orhanobut.logger.Logger;

import org.xml.sax.XMLReader;

/**
 * Created by zhangan on 2017-06-15.
 */

public class HtmlTextView extends android.support.v7.widget.AppCompatTextView {

    public static final String TAG = "HtmlTextView";
    public static final boolean DEBUG = false;

    public HtmlTextView(Context context) {
        super(context);
    }

    public HtmlTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setHtmlFromString(String html, boolean useLocalDrawables) {
        Html.ImageGetter imgGetter;
        if (useLocalDrawables) {
            imgGetter = new LocalImageGetter(getContext());
        } else {
            imgGetter = new UrlImageGetter(getContext(), this);
        }
        try {
            setText(Html.fromHtml(html, imgGetter, new MTagHandler()));
            setMovementMethod(LinkMovementMethod.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Html.TagHandler tagHandler = new Html.TagHandler() {
        boolean first = true;
        String parent = null;
        int index = 1;

        @Override
        public void handleTag(boolean opening, String tag, Editable output,
                              XMLReader xmlReader) {
            Logger.d(tag + " output " + output + "opening " + opening);
            if (tag.equals("ul")) parent = "ul";
            else if (tag.equals("ol")) parent = "ol";
            if (tag.equals("li")) {
                if (parent.equals("ul")) {
                    if (first) {
                        output.append("\n\t•");
                        first = false;
                    } else {
                        first = true;
                    }
                } else {
                    if (first) {
                        output.append("\n\t" + index + ". ", 0, output.length() + 1);
                        first = false;
                        index++;
                    } else {
                        first = true;
                    }
                }
            }
        }

    };


}
