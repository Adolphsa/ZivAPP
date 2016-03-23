package com.zividig.zivapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zividig.zivapp.R;

/**
 * 设置
 * Created by Administrator on 2016-03-18.
 */
public class Setting extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting,container,false);

        //设置标题
        TextView title = (TextView)view.findViewById(R.id.tv_title);
        title.setText("设置");
        return view;
    }
}
