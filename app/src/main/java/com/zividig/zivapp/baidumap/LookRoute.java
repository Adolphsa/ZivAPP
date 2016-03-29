package com.zividig.zivapp.baidumap;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.viewpagerindicator.IconPagerAdapter;
import com.viewpagerindicator.TabPageIndicator;
import com.zividig.zivapp.R;

import java.util.ArrayList;

/**
 * 查看路线
 * Created by Administrator on 2016-03-29.
 */
public class LookRoute extends FragmentActivity {

    private static final int[] ICONS = new int[] {
            R.mipmap.route_drive_icon,
            R.mipmap.route_bus_icon,
            R.mipmap.route_walk_icon
    };
    private static final int[] ICONS_SLECTER = new int[] {
            R.mipmap.route_drive_icon_slecter,
            R.mipmap.route_bus_icon_slecter,
            R.mipmap.route_walk_icon_slecter
    };

    private ArrayList<BasePager> arrayList;
    private ViewPager pager;
    private TabPageIndicator indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_route);

        // 标题
        TextView txtTitle = (TextView) findViewById(R.id.tv_title);
        txtTitle.setText("查看路线");

        //返回按钮
        Button btnBack = (Button) findViewById(R.id.btn_back);
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pager = (ViewPager)findViewById(R.id.pager);
        pager.addOnPageChangeListener(new MyPagerChangerListener()); //增加监听
        indicator = (TabPageIndicator)findViewById(R.id.indicator);

        initDate();
    }

    public void initDate(){
        arrayList = new ArrayList<BasePager>();

        for (int i=0; i<3; i++){
            BasePager basePager = new BasePager(getApplicationContext());
            arrayList.add(basePager);
        }

        pager.setAdapter(new ViewPagerAdapter());
        indicator.setViewPager(pager);
    }

    /**
     * ViewPager的改变监听
     */
    class MyPagerChangerListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            System.out.println("选中了" + position);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }


    }

    /**
     * ViewPager的适配器
     */
    class ViewPagerAdapter extends PagerAdapter implements IconPagerAdapter{

        @Override
        public int getIconResId(int index) {
            return ICONS[index];
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager basePager = arrayList.get(position);
            container.addView(basePager.rootView);
            basePager.initData(position);
            return basePager.rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
