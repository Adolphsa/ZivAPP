package com.zividig.zivapp.tabs;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

/**
 * 实时预览
 * Created by Administrator on 2016-03-14.
 */
public class RealTimeImage extends BasePager {


    public RealTimeImage(Application application) {
        super(application);
    }

    @Override
    public void initData() {
        TextView text = new TextView(mApplication);
        text.setText("实时预览");
        text.setTextSize(20);
        text.setTextColor(Color.RED);
        text.setGravity(Gravity.CENTER);

        basePager_fl.addView(text);
    }
}
