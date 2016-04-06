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
import com.zividig.zivapp.baidumap.TrackRecord;

/**
 * 车辆生活
 * Created by Administrator on 2016-03-18.
 */
public class CarLife extends Fragment implements View.OnClickListener{

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

        ViolationQueryListener listener = new ViolationQueryListener();
        //违章查询
        violationQuery = (Button) view.findViewById(R.id.bt_car_life_query);
        violationQuery.setOnClickListener(listener);

        //轨迹查询
        Button trackQuery = (Button) view.findViewById(R.id.bt_car_life_track_query);
        trackQuery.setOnClickListener(listener);

        //加油站
        Button gasStation = (Button) view.findViewById(R.id.bt_car_gas_station);
        gasStation.setOnClickListener(this);


        //银行
        Button bank = (Button) view.findViewById(R.id.bt_bank);
        bank.setOnClickListener(this);

        //酒店
        Button hotel = (Button) view.findViewById(R.id.bt_hotel);
        hotel.setOnClickListener(this);

        //停车场
        Button parking = (Button) view.findViewById(R.id.bt_parking);
        parking.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()){

            //加油站
            case R.id.bt_car_gas_station:
                System.out.println("加油站按钮被点击了");
                bundle.putInt("service", 0);
                break;

            //银行
            case R.id.bt_bank:
                System.out.println("银行被点击了");
                bundle.putInt("service", 1);
                break;

            //酒店
            case R.id.bt_hotel:
                System.out.println("酒店被点击了");
                bundle.putInt("service", 2);
                break;

            //停车场
            case R.id.bt_parking:
                System.out.println("停车场被点击了");
                bundle.putInt("service", 3);
                break;
        }

        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(getContext(), GasStation.class);
        main.startActivity(intent);
    }

    class ViolationQueryListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_car_life_query:
                    System.out.println("违章查询按钮被点击了");
                    //开启违章查询的类
                    Intent weizhangIntent = new Intent();
                    weizhangIntent.setClass(getContext(), com.deahu.activity.MainActivity.class);
                    main.startActivity(weizhangIntent);
                    break;
                case R.id.bt_car_life_track_query:
                    System.out.println("轨迹查询被点击了");
                    Intent trackRecordIntent = new Intent();
                    trackRecordIntent.setClass(getContext(), TrackRecord.class);
                    main.startActivity(trackRecordIntent);
                    break;
            }



        }
    }

}
