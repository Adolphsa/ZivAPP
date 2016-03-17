package com.zividig.zivapp.tabs;

import android.app.Application;

/**
 * 车辆定位
 * Created by Administrator on 2016-03-14.
 */
public class CarLocation extends BasePager{

    public CarLocation(Application application) {
        super(application);
        System.out.println("车辆定位");

    }


    @Override
    public void initData() {
        System.out.println("执行了车辆定位的initData方法");
    }



}
