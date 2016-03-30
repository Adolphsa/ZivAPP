package com.zividig.zivapp.baidumap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.utils.DistanceUtil;
import com.zividig.zivapp.R;

import java.text.DecimalFormat;

/**
 * 基本页面
 * Created by Administrator on 2016-03-29.
 */
public class BasePager{
    public Context mContext;
    public View rootView;
    private TextView tvDescribe;
    private TextView tvDistance;
    private int currentI;
    private Button mapNavigation; //调用第三方地图

    public BasePager(Context context){
        mContext = context;
        rootView = initView();
    }

    //初始化界面
    public  View initView(){
        View view = View.inflate(mContext, R.layout.activity_base_pager, null);
        tvDescribe = (TextView) view.findViewById(R.id.tv_describe);
        tvDistance = (TextView) view.findViewById(R.id.tv_distance);
        mapNavigation = (Button) view.findViewById(R.id.bt_map_navigation);

        RelativeLayout rlBasePager = (RelativeLayout) view.findViewById(R.id.rl_base_pager);
        rlBasePager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("被点击了" + currentI);
                Bundle bundle = new Bundle();
                switch (currentI) {
                    case 0:
                        bundle.putInt("drive", 0);
                        break;
                    case 1:
                        bundle.putInt("drive", 1);
                        break;
                    case 2:
                        bundle.putInt("drive", 2);
                        break;
                }
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(mContext, CarRoutePlan.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);

            }
        });

        mapNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"您没有安装第三方地图",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    //初始化数据
    public void initData(int i){
        currentI = i;
        double distance = DistanceUtil.getDistance(GasStation.startAdd, GasStation.endAdd);
        DecimalFormat df = new DecimalFormat("###.00"); //只保留小数点后一位
        switch (i){
            case 0:

                tvDescribe.setText("驾车路线");
                tvDistance.setText( df.format(distance/1000.0) + "公里");

                break;
            case 1:

                tvDescribe.setText("公交路线");
                tvDistance.setText( df.format(distance/1000.0) + "公里");
                break;
            case 2:

                tvDescribe.setText("步行路线");
                tvDistance.setText( df.format(distance/1000.0) + "公里");
                break;
        }
    }

}
