package com.zividig.zivapp.tabs;

import android.app.Application;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zividig.zivapp.R;

/**
 * 页签viewPager的基类
 * Created by Administrator on 2016-03-14.
 */
public class BasePager {

    public Application mApplication;
    public View view;
    public TextView basePager_title;
    public FrameLayout basePager_fl;

    public BasePager(Application application){
        mApplication = application;
        initView();
    }

    //初始化布局
    public void initView(){
        //加载ViewPager的布局文件
        view = View.inflate(mApplication, R.layout.activity_basepager,null);

        basePager_title = (TextView) view.findViewById(R.id.tv_basePager_title);
        basePager_fl = (FrameLayout) view.findViewById(R.id.fl_basePager);
    }

    //初始化数据
    public void initData(){}
}
