package com.agjsj.mentality.ui.appoint;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agjsj.mentality.ui.MainActivity;
import com.agjsj.mentality.R;
import com.agjsj.mentality.ui.SearchActivity;
import com.agjsj.mentality.ui.base.ParentWithNaviActivity;
import com.agjsj.mentality.ui.base.ParentWithNaviFragment;

import butterknife.ButterKnife;

/**
 * Created by HengXing on 2016/10/29.
 */
public class AppointFragment extends ParentWithNaviFragment {
    @Override
    protected String title() {
        return "预约";
    }

    @Override
    public Object left() {
        return R.drawable.logo;
    }

    @Override
    public Object right() {
        return R.drawable.search_icon;
    }

    @Override
    public ParentWithNaviActivity.ToolBarListener setToolBarListener() {
        return new ParentWithNaviActivity.ToolBarListener() {
            @Override
            public void clickLeft() {
                ((MainActivity) getActivity()).switchMenu();
            }

            @Override
            public void clickRight() {
                startActivity(SearchActivity.class,null);
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_appoint, container, false);
        initNaviView();
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
