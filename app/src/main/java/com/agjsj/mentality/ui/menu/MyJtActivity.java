package com.agjsj.mentality.ui.menu;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.RelativeLayout;

import com.agjsj.mentality.R;
import com.agjsj.mentality.ui.jt.JtFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyJtActivity extends FragmentActivity {

    @Bind(R.id.main_content)
    RelativeLayout mainContent;
    private MyJtFragment myJtFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_jt);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        myJtFragment = new MyJtFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content,myJtFragment).commit();

    }
}
