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

import com.zividig.zivapp.R;

/**
 * 车辆信息
 * Created by Administrator on 2016-03-18.
 */
public class CarInfo extends Fragment {

    private ImageView speedPoint;
    private  ImageView voltagePoint;
    private  ImageView turnSpeedPoint;
    private  ImageView temperaturePoint;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_information,container,false);

        speedPoint = (ImageView) view.findViewById(R.id.img_speed_point);
        voltagePoint = (ImageView) view.findViewById(R.id.img_voltage_point);
        turnSpeedPoint = (ImageView) view.findViewById(R.id.img_turn_speed_point);
        temperaturePoint = (ImageView) view.findViewById(R.id.img_temperature_point);

        initView();
        return view;
    }

    public void initView() {

        initAnimation();
    }

    public void initAnimation(){

        System.out.println("动画被调用");
        //速度动画
        RotateAnimation speedRotate = new RotateAnimation(0,-110, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        speedRotate.setDuration(500);
        speedRotate.setFillAfter(true);
        speedPoint.setAnimation(speedRotate);

        //电压动画
        RotateAnimation voltageRotate = new RotateAnimation(0,65, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.8f);
        voltageRotate.setDuration(500);
        voltageRotate.setFillAfter(true);
        voltagePoint.setAnimation(voltageRotate);

        //转速动画
        RotateAnimation turnRotate = new RotateAnimation(0,-130, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        turnRotate.setDuration(500);
        turnRotate.setFillAfter(true);
        turnSpeedPoint.setAnimation(turnRotate);

        //水温动画
        RotateAnimation temperatureRotate = new RotateAnimation(0,-70, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.8f);
        temperatureRotate.setDuration(500);
        temperatureRotate.setFillAfter(true);
        temperaturePoint.setAnimation(temperatureRotate);
    }

    public void onHiddenChanged(boolean hidden){
        System.out.println("方法被调用");
        initAnimation();
    }
}