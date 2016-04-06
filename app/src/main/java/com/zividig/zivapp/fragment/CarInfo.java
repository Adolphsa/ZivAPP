package com.zividig.zivapp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import java.util.Timer;
import java.util.TimerTask;

/**
 * 车辆信息
 * Created by Administrator on 2016-03-18.
 */
public class CarInfo extends Fragment {

    private ImageView speedPoint;
    private  ImageView oilPoint;
    private  ImageView turnSpeedPoint;
    private  ImageView temperaturePoint;
    private RotateAnimation speedRotate; //速度动画
    private int [] speedTest = {10,50,100,80,120,60,30,150,50};
    private int i;
    private int xStart,xEnd;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0){
                int n = i%9;
                System.out.println("接收到消息了");
                speedRotate = new RotateAnimation(0,speedTest[n], Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                speedRotate.setDuration(1000);
                speedRotate.setFillAfter(true);
                speedRotate.setRepeatCount(Animation.INFINITE);
                speedPoint.setAnimation(speedRotate);
                i++;
            }
        }
    };
    private Timer timer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.car_information,container,false);

        speedPoint = (ImageView) view.findViewById(R.id.img_speed_point);
        oilPoint = (ImageView) view.findViewById(R.id.img_oil_point);
        turnSpeedPoint = (ImageView) view.findViewById(R.id.img_turning_point);
        temperaturePoint = (ImageView) view.findViewById(R.id.img_temperature_point);

        //设置标题
        TextView title = (TextView)view.findViewById(R.id.tv_title);
        title.setText("车辆信息");


        initView();
        return view;
    }

    public void initView() {

        initAnimation();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("执行一次");
                mHandler.sendEmptyMessage(0);
            }
        }, 0, 1000);
    }

    public void initAnimation(){

        System.out.println("动画被调用");
        //速度动画
        speedRotate = new RotateAnimation(0,speedTest[0], Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        speedRotate.setDuration(1000);
        speedRotate.setFillAfter(true);
        speedRotate.setRepeatCount(Animation.INFINITE);
        speedPoint.setAnimation(speedRotate);


        //油压动画
        RotateAnimation oilRotate = new RotateAnimation(0,100, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        oilRotate.setDuration(500);
        oilRotate.setFillAfter(true);
        oilPoint.setAnimation(oilRotate);

        //转速动画
        RotateAnimation turnRotate = new RotateAnimation(0,110, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        turnRotate.setDuration(800);
        turnRotate.setFillAfter(false);
        turnRotate.setRepeatCount(Animation.INFINITE);
        turnSpeedPoint.setAnimation(turnRotate);

        //水温动画
        RotateAnimation temperatureRotate = new RotateAnimation(0,60, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        temperatureRotate.setDuration(500);
        temperatureRotate.setFillAfter(true);
        temperaturePoint.setAnimation(temperatureRotate);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){ //隐藏
            System.out.println("车辆信息隐藏了");
            timer.cancel();
            timer = null;

        }else { //显示
            System.out.println("车辆信息显示了");
            initView();

        }
    }
}
