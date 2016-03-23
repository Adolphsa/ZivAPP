package com.zividig.zivapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zividig.zivapp.MainActivity;
import com.zividig.zivapp.R;
import com.zividig.zivapp.baidumap.GasStation;

/**
 * 车辆生活
 * Created by Administrator on 2016-03-18.
 */
public class CarLife extends Fragment {

    private Button violationQuery;
    private MainActivity main;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_life,container,false);
        
        //回去MainActivity对象
        main = (MainActivity) getActivity();

        //设置标题
        TextView title = (TextView)view.findViewById(R.id.tv_title);
        title.setText("车辆生活");

        //违章查询按钮
        violationQuery = (Button) view.findViewById(R.id.bt_car_life_query);
        violationQuery.setOnClickListener(new violationQueryListener());

        //加油站按钮
        Button gasStation = (Button) view.findViewById(R.id.bt_car_gas_station);
        gasStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("加油站按钮被点击了");
                //加油站
                Intent intent = new Intent();
                intent.setClass(main.getApplicationContext(), GasStation.class);
                main.startActivity(intent);
            }
        });

        return view;
    }

    class violationQueryListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            System.out.println("违章查询按钮被点击了");
            //开启违章查询的类
            Intent weizhangIntent = new Intent();
            weizhangIntent.setClass(main.getApplicationContext(), com.deahu.activity.MainActivity.class);
            main.startActivity(weizhangIntent);


        }
    }

}
