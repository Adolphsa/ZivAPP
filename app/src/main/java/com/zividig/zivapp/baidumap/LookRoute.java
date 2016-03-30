package com.zividig.zivapp.baidumap;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
public class LookRoute extends Activity {

    private static final int[] ICONS = new int[] {
            R.drawable.selector_route_drive_icon,
            R.drawable.selector_route_bus_icon,
            R.drawable.selector_route_walk_icon
    };

    private ArrayList<BasePager> arrayList;
    private ViewPager pager;
    private TabPageIndicator indicator;
    private TextView myLocation; //我的位置
    private TextView destination; //目的地
    private BasePager basePager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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

        myLocation = (TextView) findViewById(R.id.tv_my_location);
        destination = (TextView) findViewById(R.id.tv_destination);
        if (GasStation.destination != null){
            destination.setText(GasStation.destination);
        }

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

        ViewPagerAdapter adapter = new ViewPagerAdapter();
        pager.setAdapter(adapter);
        indicator.setViewPager(pager);

        arrayList.get(0).initData(0);//执行默认首页的初始化方法
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
            basePager.initData(position);
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
            basePager = arrayList.get(position);
            container.addView(basePager.rootView);
            return basePager.rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
