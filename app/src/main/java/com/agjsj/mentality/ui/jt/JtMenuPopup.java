package com.agjsj.mentality.ui.jt;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.agjsj.mentality.R;
import com.agjsj.mentality.popupwindow.BasePopupWindow;


/**
 * Created by 大灯泡 on 2016/1/22.
 * 菜单。
 */
public class JtMenuPopup extends BasePopupWindow implements View.OnClickListener {

    private TextView tx1;
    private TextView tx2;

    private int[] viewLocation;


    private Context context;

    public JtMenuPopup(Activity context) {
        super(context, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.context = context;
        viewLocation = new int[2];
        findViewById(R.id.tx_1).setOnClickListener(this);
        findViewById(R.id.tx_2).setOnClickListener(this);
    }

    @Override
    protected Animation initShowAnimation() {
        AnimationSet set = new AnimationSet(true);
        set.setInterpolator(new DecelerateInterpolator());
        set.addAnimation(getScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0));
        set.addAnimation(getDefaultAlphaAnimation());
        return set;
        //return null;
    }

    @Override
    public Animator initShowAnimator() {
       /* AnimatorSet set=new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(mAnimaView,"scaleX",0.0f,1.0f).setDuration(300),
                ObjectAnimator.ofFloat(mAnimaView,"scaleY",0.0f,1.0f).setDuration(300),
                ObjectAnimator.ofFloat(mAnimaView,"alpha",0.0f,1.0f).setDuration(300*3/2));*/
        return null;
    }

    @Override
    public void showPopupWindow(View v) {
        try {
            v.getLocationOnScreen(viewLocation);
            mPopupWindow.showAtLocation(v, Gravity.RIGHT | Gravity.TOP, (int) (v.getWidth() * 1.5),
                    viewLocation[1] + ((v.getHeight() * 2 / 3)));
            if (initShowAnimation() != null && mAnimaView != null) {
                mAnimaView.clearAnimation();
                mAnimaView.startAnimation(initShowAnimation());
            }
            if (initShowAnimation() == null && initShowAnimator() != null && mAnimaView != null) {
                initShowAnimator().start();
            }
        } catch (Exception e) {
            Log.w("error", "error");
        }
    }

    @Override
    public View getClickToDismissView() {
        return null;
    }

    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.popup_jt_menu);
    }

    @Override
    public View initAnimaView() {
        return getPopupWindowView().findViewById(R.id.popup_contianer);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tx_1:
                startActivity(SendJtActivity.class, null, false);
                dismiss();
                break;
            case R.id.tx_2:
                startActivity(ShareJtActivity.class, null, false);
                dismiss();
                break;
            default:
                break;

        }

    }
}
