package com.zividig.zivapp;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.widget.RadioGroup;

import com.baidu.mapapi.SDKInitializer;
import com.igexin.sdk.PushManager;
import com.zividig.zivapp.fragment.CarInfo;
import com.zividig.zivapp.fragment.CarLife;
import com.zividig.zivapp.fragment.CarLocation;
import com.zividig.zivapp.fragment.RealTime;
import com.zividig.zivapp.fragment.Setting;

/**
 * 主页面
 * Created by Administrator on 2016-03-18.
 */
public class MainActivity extends FragmentActivity {

    private final static String FRAGMENT_Car_Life = "fragment_car_life";

    private Fragment realTimeFragment;
    private Fragment carInfoFragment;
    private Fragment carLocationFragment;
    private Fragment carLifeFragment;
    private Fragment settingFragment;


    private FragmentManager fragmentManager;
    private RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //调用百度初始化程序
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        fragmentManager = getSupportFragmentManager();

        //调用个推初始化程序
        PushManager.getInstance().initialize(this.getApplicationContext());

        initView();
    }

    public void initView(){
        radioGroup = (RadioGroup) findViewById(R.id.rg_group);

        radioGroup.check(R.id.rb_realTime); //默认勾选首页
        setDefaultFragment();

        //设置radioButton的选中监听事件
        radioGroup.setOnCheckedChangeListener(new RadioButtonListener());

    }

    //设置默认的Fragment
    public void setDefaultFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        realTimeFragment = new RealTime();
        transaction.add(R.id.framelayout, realTimeFragment).commit();
    }

    //隐藏所有Fragment
    public void hideFragment(FragmentTransaction transaction){

        if (realTimeFragment != null){
            transaction.hide(realTimeFragment);
        }
        if (carInfoFragment != null){
            transaction.hide(carInfoFragment);
        }
        if (carLocationFragment != null){
            transaction.hide(carLocationFragment);
        }
        if (carLifeFragment != null){
            transaction.hide(carLifeFragment);
        }
        if (settingFragment != null){
            transaction.hide(settingFragment);
        }


    }

    class RadioButtonListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            hideFragment(transaction);
            switch (checkedId){
                case R.id.rb_realTime: //实时预览
                    if (realTimeFragment == null){
                        realTimeFragment = new RealTime();
                        transaction.add(R.id.framelayout, realTimeFragment);
                    }else {
                        transaction.show(realTimeFragment);
                    }
                    break;

                case R.id.rb_carInfo: //车辆信息
                    if (carInfoFragment == null){
                        carInfoFragment = new CarInfo();
                        transaction.add(R.id.framelayout, carInfoFragment);
                    }else {
                        transaction.show(carInfoFragment);
                    }
                    break;

                case R.id.rb_location: //车辆定位
                    if (carLocationFragment == null){
                        carLocationFragment = new CarLocation();
                        transaction.add(R.id.framelayout, carLocationFragment);
                    }else {
                        transaction.show(carLocationFragment);
                    }
                    break;

                case R.id.rb_carLife: //车辆生活
                    if (carLifeFragment == null){
                        carLifeFragment = new CarLife();
                        transaction.add(R.id.framelayout, carLifeFragment,FRAGMENT_Car_Life);
                    }else {
                        transaction.show(carLifeFragment);
                    }

                    break;

                case R.id.rb_setting: //设置
                    if (settingFragment == null){
                        settingFragment = new Setting();
                        transaction.add(R.id.framelayout, settingFragment);
                    }else {
                        transaction.show(settingFragment);
                    }
                    break;
            }

            transaction.commit();
        }
    }
}
