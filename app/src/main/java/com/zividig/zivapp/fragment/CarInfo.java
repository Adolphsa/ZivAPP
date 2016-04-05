package com.zividig.zivapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.zividig.zivapp.R;

import java.security.Principal;

/**
 * 车辆信息
 * Created by Administrator on 2016-03-18.
 */
public class CarInfo extends Fragment {

    private ImageView speedPoint;
    private  ImageView voltagePoint;
    private  ImageView turnSpeedPoint;
    private  ImageView temperaturePoint;
    private RotateAnimation speedRotate; //速度动画
    private int [] speedTest = {10,50,100,80,120,60,30,10,50};
    private int i;
    private int xStart,xEnd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.car_information,container,false);

//        speedPoint = (ImageView) view.findViewById(R.id.img_speed_point);
//        voltagePoint = (ImageView) view.findViewById(R.id.img_voltage_point);
//        turnSpeedPoint = (ImageView) view.findViewById(R.id.img_turn_speed_point);
//        temperaturePoint = (ImageView) view.findViewById(R.id.img_temperature_point);

        //设置标题
        TextView title = (TextView)view.findViewById(R.id.tv_title);
        title.setText("车辆信息");

//        initView();
        return view;
    }

    public void initView() {

        initAnimation();
    }

    public void initAnimation(){

        System.out.println("动画被调用");
        //速度动画
        speedRotate = new RotateAnimation(0,110, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        speedRotate.setDuration(1000);
        speedRotate.setFillAfter(false);
        speedRotate.setRepeatCount(Animation.INFINITE);
        speedPoint.setAnimation(speedRotate);


        //电压动画
        RotateAnimation voltageRotate = new RotateAnimation(0,65, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.8f);
        voltageRotate.setDuration(500);
        voltageRotate.setFillAfter(true);
        voltagePoint.setAnimation(voltageRotate);

//        //转速动画
//        RotateAnimation turnRotate = new RotateAnimation(0,-130, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
//        turnRotate.setDuration(500);
//        turnRotate.setFillAfter(true);
//        turnSpeedPoint.setAnimation(turnRotate);

        //水温动画
        RotateAnimation temperatureRotate = new RotateAnimation(0,-70, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.8f);
        temperatureRotate.setDuration(500);
        temperatureRotate.setFillAfter(true);
        temperaturePoint.setAnimation(temperatureRotate);
    }


    public void speedAnimation(int xStart,int xEnd){
        speedRotate = new RotateAnimation(xStart,xEnd, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        speedRotate.setDuration(500);
        speedRotate.setFillAfter(true);
        speedPoint.setAnimation(speedRotate);
        xStart = xEnd;
        xEnd = speedTest[i];
        i++;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){ //隐藏
            System.out.println("车辆信息隐藏了");


        }else { //显示
            System.out.println("车辆信息显示了");
            initView();
        }
    }
}
