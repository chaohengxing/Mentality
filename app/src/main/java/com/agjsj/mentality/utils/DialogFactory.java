package com.agjsj.mentality.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.BadTokenException;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;


import com.agjsj.mentality.R;

import java.util.Calendar;

/**
 * @author hwz
 * @time 2015-5-27 下午2:41:03
 * @tag setDialog 中添加 标识符key
 */
public class DialogFactory {

    private Button mRightBt;
    private Button mLeftBt;
    private View mBtDivider;
    private MDialogListener dialogListener = null;
    private AlertDialog dialog;
    private Dialog mDialog;
    private DatePicker mDatePicker;
    private Calendar mCalendar;
    private int mYear;
    private int mMonth;
    private int mDay;
    private boolean isFirst = true;

    public interface MDialogListener {
        public void leftbt(int key);

        public void rightbt(int key);
    }

    ;

    public interface DataTimeResult {
        public void result(int year, int month);
    }

    ;

    public void setInterface(MDialogListener l) {
        this.dialogListener = l;
    }

    public void setDividerVisibility(boolean isShow) {
        if (isShow)
            mBtDivider.setVisibility(View.VISIBLE);
        else
            mBtDivider.setVisibility(View.GONE);
    }

    public void setleftVisibility(boolean isShow) {
        if (isShow)
            mLeftBt.setVisibility(View.VISIBLE);
        else
            mLeftBt.setVisibility(View.GONE);
    }

    public void setrightVisibility(boolean isShow) {
        if (isShow)
            mRightBt.setVisibility(View.VISIBLE);
        else
            mRightBt.setVisibility(View.GONE);
    }

    public AlertDialog getDialog(Context context, String title, String titletext, String lefttext,
                                 String rightext, MDialogListener mDialogListener) {
        this.dialogListener = mDialogListener;
        AlertDialog alertDialog = getDialog(context, title, titletext, lefttext, rightext, 1);
        return alertDialog;
    }

    // 弹出的dialog不带标题
    public AlertDialog getDialog(Context context, String titletext, String lefttext, String
            rightext,
                                 MDialogListener mDialogListener) {

        this.dialogListener = mDialogListener;
        AlertDialog alertDialog = getDialog(context, "", titletext, lefttext, rightext, 1);
        return alertDialog;

    }

    /**
     * 弹出的dialog只显示一个按钮
     *
     * @param titletext
     * @param Oktext    实际是左边的文字
     * @return
     */
    public AlertDialog getDialog(Context context, String titletext, String Oktext,
                                 MDialogListener mDialogListener) {
        this.dialogListener = mDialogListener;
        AlertDialog alertDialog = getDialog(context, "", titletext, Oktext, "", 1);
        return alertDialog;
    }

    public AlertDialog getDialog(Context context, String title, String titletext, String lefttext,
                                 String rightext, final int key) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_ios_dialog, null);
        TextView dialog_title = (TextView) view.findViewById(R.id.dialog_title_text);
        TextView titleTv = (TextView) view.findViewById(R.id.dialog_title);
        titleTv.setText(title);
        titleTv.setVisibility(title.equals("") || title == null ? view.GONE : view.VISIBLE);
        mLeftBt = (Button) view.findViewById(R.id.dialog_left_text);
        mRightBt = (Button) view.findViewById(R.id.dialog_right_text);
        mBtDivider = (View) view.findViewById(R.id.dialog_bt_divider);
        if (null == dialogListener || null == rightext || rightext.equals("")) {
            mRightBt.setVisibility(View.GONE);
            mBtDivider.setVisibility(View.GONE);
        }

        dialog_title.setText(titletext);
        mRightBt.setText(rightext);
        mLeftBt.setText(lefttext);
        mLeftBt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (null != dialogListener)
                    dialogListener.leftbt(key);
                dialog.dismiss();
            }
        });
        mRightBt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (null != dialogListener)
                    dialogListener.rightbt(key);
                dialog.dismiss();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CenterDialogStyle);
        dialog = builder.create();
        try {
            dialog.show();
        } catch (BadTokenException e) {
        }
        dialog.setContentView(view);
        return dialog;
    }

    public boolean isDialogShow() {
        return dialog.isShowing();
    }

    /**
     * @param context
     * @return dialog
     */
    public Dialog getTimeDialog(final Context context, final TextView year, final TextView month,
                                final DataTimeResult listener) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context
                .WINDOW_SERVICE);
        Display mDisplay = windowManager.getDefaultDisplay();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_datepicker, null);
        view.setMinimumWidth(mDisplay.getWidth());
        mLeftBt = (Button) view.findViewById(R.id.dialog_time_left);
        mRightBt = (Button) view.findViewById(R.id.dialog_time_right);
        mDatePicker = (DatePicker) view.findViewById(R.id.dialog_time_datepicker);
        // 只显示年月，修改DatePicke布局显示
        if (mDatePicker != null) {
            ((ViewGroup) ((ViewGroup) mDatePicker.getChildAt(0)).getChildAt(0)).getChildAt(1)
                    .getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

                @Override
                public void onGlobalLayout() {
                    // TODO Auto-generated method stub
                    ((ViewGroup) ((ViewGroup) mDatePicker.getChildAt(0)).getChildAt(0))
                            .getChildAt(1)
                            .getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    ((ViewGroup) ((ViewGroup) mDatePicker.getChildAt(0)).getChildAt(0))
                            .getChildAt(2)
                            .setVisibility(View.GONE);
                    WindowManager wm = (WindowManager) context
                            .getSystemService(Context.WINDOW_SERVICE);
                    Display display = wm.getDefaultDisplay();
                    int yearWidth = ((ViewGroup) ((ViewGroup) mDatePicker.getChildAt(0))
                            .getChildAt(0)).getChildAt(0).getWidth();
                    int monthWidth = ((ViewGroup) ((ViewGroup) mDatePicker.getChildAt(0))
                            .getChildAt(0)).getChildAt(1).getWidth();
                    LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT);
                    lp.rightMargin = (display.getWidth() - yearWidth - monthWidth) / 3;
                    ((ViewGroup) ((ViewGroup) mDatePicker.getChildAt(0)).getChildAt(0))
                            .getChildAt(0)
                            .setLayoutParams(lp);
                }
            });
        }

        // 设置日期简略显示 不显示星期几
        // mDatePicker.setCalendarViewShown(false);
        mCalendar = Calendar.getInstance();
        if (isFirst) {
            mYear = mCalendar.get(Calendar.YEAR);
            mMonth = mCalendar.get(Calendar.MONTH);
            mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
            isFirst = false;
        }
        mDatePicker.init(mYear, mMonth, mDay, new OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                if (isDateAfter(view))
                    view.init(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                            mCalendar.get(Calendar.DAY_OF_MONTH), this);
            }
        });

        mLeftBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mDialog.dismiss();
            }
        });

        mRightBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mYear = mDatePicker.getYear();
                mMonth = mDatePicker.getMonth() + 1;
                mDay = mDatePicker.getDayOfMonth();
                year.setText(mYear + "年");
                month.setText(mMonth + "");
                listener.result(mYear, mMonth);
                mDialog.dismiss();
            }
        });
        mDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        mDialog.setContentView(view);
        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        mDialog.show();
        return mDialog;
    }

    public void initDataTime(int year, int month) {
        mDatePicker.init(year, month, 1, new OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                if (isDateAfter(view))
                    view.init(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                            mCalendar.get(Calendar.DAY_OF_MONTH), this);
            }
        });
    }

    /**
     * @return true or fasle
     */
    private boolean isDateAfter(DatePicker tempView) {
        Calendar mCalendar = Calendar.getInstance();
        Calendar tempCalendar = Calendar.getInstance();
        tempCalendar.set(tempView.getYear(), tempView.getMonth(), tempView.getDayOfMonth(), 0, 0,
                0);
        if (tempCalendar.after(mCalendar))
            return true;
        else
            return false;
    }

    private DatePicker findDatePicker(ViewGroup group) {
        if (group != null) {
            for (int i = 0, j = group.getChildCount(); i < j; i++) {
                View child = group.getChildAt(i);
                if (child instanceof DatePicker) {
                    return (DatePicker) child;
                } else if (child instanceof ViewGroup) {
                    DatePicker result = findDatePicker((ViewGroup) child);
                    if (result != null)
                        return result;
                }
            }
        }
        return null;
    }


    /**
     * @param context
     * @return dialog
     */
    public Dialog getBottomDialog(final Context context, View view) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context
                .WINDOW_SERVICE);
        Display mDisplay = windowManager.getDefaultDisplay();
        view.setMinimumWidth(mDisplay.getWidth());
        mDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        mDialog.setContentView(view);
        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        return mDialog;
    }


}
