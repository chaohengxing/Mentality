package com.agjsj.mentality.myview;

import android.content.Context;
import android.util.AttributeSet;

import com.tubb.smrv.SwipeHorizontalMenuLayout;

/**
 * Created by MyPC on 2016/11/7.
 */

public class MySwipeHorizontalMenuLayout extends SwipeHorizontalMenuLayout {
    public MySwipeHorizontalMenuLayout(Context context) {
        super(context);
    }

    public MySwipeHorizontalMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySwipeHorizontalMenuLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean isCurrentSwipeExit() {
        if (mCurrentSwiper != null && isMenuOpen()) {
            return true;
        } else {
            return false;
        }
    }
}
