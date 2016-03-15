package com.zividig.zivapp.tabs;

import android.app.Application;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.zividig.zivapp.R;

/**
 * 车辆信息
 * Created by Administrator on 2016-03-14.
 */
public class CarInfo extends BasePager {


    private  View view;
    private  ImageView speedPoint;
    private  ImageView voltagePoint;
    private  ImageView turnSpeedPoint;
    private  ImageView temperaturePoint;

    public CarInfo(Application application) {
        super(application);

        view = View.inflate(application, R.layout.activity_carinfo, null);
        speedPoint = (ImageView) view.findViewById(R.id.img_speed_point);
        voltagePoint = (ImageView) view.findViewById(R.id.img_voltage_point);
        turnSpeedPoint = (ImageView) view.findViewById(R.id.img_turn_speed_point);
        temperaturePoint = (ImageView) view.findViewById(R.id.img_temperature_point);
    }

    @Override
    public void initData() {

        basePager_fl.removeAllViews(); //必须先移除所有的view,否则会报错
        basePager_fl.addView(view);

        initAnimation();
    }

    public void initAnimation(){
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
}
