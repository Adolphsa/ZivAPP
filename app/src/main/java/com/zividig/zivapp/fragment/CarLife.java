package com.zividig.zivapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zividig.zivapp.MainActivity;
import com.zividig.zivapp.R;

/**
 * 车辆生活
 * Created by Administrator on 2016-03-18.
 */
public class CarLife extends Fragment {

    private Button violationQuery;

    private FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_life,container,false);
        violationQuery = (Button) view.findViewById(R.id.bt_car_life_query);
        violationQuery.setOnClickListener(new violationQueryListener());
        return view;
    }

    class violationQueryListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            System.out.println("按钮被点击了");

            //开启违章查询的类
            MainActivity main = (MainActivity) getActivity(); //获取Activity对象
            main.startViolationQuery();

        }
    }

}
