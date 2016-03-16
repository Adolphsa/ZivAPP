package com.zividig.zivapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.baidu.mapapi.SDKInitializer;
import com.zividig.zivapp.tabs.BasePager;
import com.zividig.zivapp.tabs.CarInfo;
import com.zividig.zivapp.tabs.CarLife;
import com.zividig.zivapp.tabs.CarLocation;
import com.zividig.zivapp.tabs.RealTimeImage;
import com.zividig.zivapp.tabs.Setting;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private ViewPager viewPager;
    private RadioGroup radioGroup;

    private ArrayList<BasePager> viewPagerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.vp_tabs);
        radioGroup = (RadioGroup) findViewById(R.id.rg_group);

        initData();
    }

    public void initData(){

        radioGroup.check(R.id.rb_realTime); //默认勾选首页

        viewPagerList = new ArrayList<BasePager>();

        viewPagerList.add(new RealTimeImage(getApplication()));
        viewPagerList.add(new CarInfo(getApplication()));
        viewPagerList.add(new CarLocation(getApplication()));
        viewPagerList.add(new CarLife(getApplication()));
        viewPagerList.add(new Setting(getApplication()));

        viewPager.setAdapter(new MyViewPageAdapter()); //为viewPager设置适配器

        //监听radioGroup的点击事件
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.rb_realTime:
                        viewPager.setCurrentItem(0,false);
                        viewPagerList.get(0).basePager_title.setText("实时预览");
                        break;
                    case R.id.rb_carInfo:
                        viewPager.setCurrentItem(1,false);
                        viewPagerList.get(1).basePager_title.setText("车辆信息");
                        break;
                    case R.id.rb_location:
                        viewPager.setCurrentItem(2,false);
                        viewPagerList.get(2).basePager_title.setText("车辆定位");
                        break;
                    case R.id.rb_carLife:
                        viewPager.setCurrentItem(3,false);
                        viewPagerList.get(3).basePager_title.setText("行车生活");
                        break;
                    case R.id.rb_setting:
                        viewPager.setCurrentItem(4,false);
                        viewPagerList.get(4).basePager_title.setText("设置");
                        break;
                }
            }
        });

        //监听viewPager状态的改变
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPagerList.get(position).initData();//页面被选中的时候加载initData方法

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPagerList.get(0).initData(); //默认初始化首页数据
    }

    class MyViewPageAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return viewPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager pager = viewPagerList.get(position);
            container.addView(pager.view);
            return pager.view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }

//        public void addMap(){
//            FragmentManager fm = getFragmentManager();
//            FragmentTransaction transaction = fm.beginTransaction();
//
//            transaction.replace(R.id.fl_basePager,new MyMapFragment());
//            transaction.commit();
//        }
}
