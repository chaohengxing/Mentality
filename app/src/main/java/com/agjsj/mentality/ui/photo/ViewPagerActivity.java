/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.agjsj.mentality.ui.photo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.agjsj.mentality.R;
import com.agjsj.mentality.utils.PicassoUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import uk.co.senab.photoview.PhotoView;


public class ViewPagerActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private List<String> sDrawables = new ArrayList<>();
    private int currentPosition;
    private SamplePagerAdapter pagerAdapter;

    private LinearLayout pointLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        ViewPager mViewPager = (HackyViewPager) findViewById(R.id.view_pager);
        pointLayout = (LinearLayout) findViewById(R.id.viewpager_point_layout);
//        setContentView(mViewPager);

        sDrawables.clear();
        sDrawables.addAll((Collection<? extends String>) getIntent().getBundleExtra(getPackageName()).getSerializable("imageUrls"));
        currentPosition = getIntent().getBundleExtra(getPackageName()).getInt("position", 0);

        //给pointlayout添加几个小圆点
        for (int i = 0; i < sDrawables.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.write_point);
            if (i == currentPosition) {
                imageView.setImageResource(R.drawable.blue_point);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(30, 30);
            params.setMargins(30, 0, 30, 0);
            imageView.setLayoutParams(params);
            pointLayout.addView(imageView);
        }

        pagerAdapter = new SamplePagerAdapter(this, pointLayout, sDrawables);

        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setCurrentItem(currentPosition);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < pointLayout.getChildCount(); i++) {
            if (position == i) {
                ((ImageView) pointLayout.getChildAt(i)).setImageResource(R.drawable.blue_point);
            } else {
                ((ImageView) pointLayout.getChildAt(i)).setImageResource(R.drawable.write_point);

            }


        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    static class SamplePagerAdapter extends PagerAdapter {
        private Context context;
        private LinearLayout pointLayout;
        private List<String> sDrawables;

        public SamplePagerAdapter(Context context, LinearLayout pointLayout, List<String> sDrawables) {
            this.context = context;
            this.pointLayout = pointLayout;
            this.sDrawables = sDrawables;

        }

        @Override
        public int getCount() {
            return sDrawables.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            photoView.setBackgroundResource(R.color.black);
            if (TextUtils.isEmpty(sDrawables.get(position))) {
                PicassoUtils.loadResourceImage(R.drawable.logo, photoView);
            } else {
                PicassoUtils.loadImage(sDrawables.get(position), R.drawable.logo, R.drawable.logo, photoView);
            }

            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}
