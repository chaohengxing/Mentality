package com.agjsj.mentality.ui.menu;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.RelativeLayout;

import com.agjsj.mentality.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyAppointActivity extends FragmentActivity {

    @Bind(R.id.main_content)
    RelativeLayout mainContent;

    private MyAppointFragment myAppointFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appoint);
        ButterKnife.bind(this);
        initView();
    }
    private void initView() {

        myAppointFragment = new MyAppointFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content,myAppointFragment).commit();

    }
}
