package com.zividig.zivapp.tabs;

import android.app.Application;
import android.view.View;

import com.zividig.zivapp.MainActivity;
import com.zividig.zivapp.R;

/**
 * 车辆定位
 * Created by Administrator on 2016-03-14.
 */
public class CarLocation extends BasePager{

    private View view;


    public CarLocation(Application application) {
        super(application);

        view = View.inflate(application,R.layout.activity_car_location,null);
        System.out.println("车辆定位");
    }



    @Override
    public void initData() {

        basePager_fl.removeAllViews(); //必须先移除所有的view,否则会报错
        basePager_fl.addView(view);


    }


}
