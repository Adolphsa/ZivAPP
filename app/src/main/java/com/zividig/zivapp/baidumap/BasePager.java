package com.zividig.zivapp.baidumap;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zividig.zivapp.R;

/**
 * 基本页面
 * Created by Administrator on 2016-03-29.
 */
public class BasePager {
    public Context mContext;
    public View rootView;
    private TextView textView;

    public BasePager(Context context){
        mContext = context;
        rootView = initView();
    }

    //初始化界面
    public  View initView(){
        View view = View.inflate(mContext, R.layout.activity_base_pager,null);
        textView = (TextView) view.findViewById(R.id.base_pager_text);
        return view;
    }

    //初始化数据
    public void initData(int i){
        textView.setText("-----" + i);
    }
}
