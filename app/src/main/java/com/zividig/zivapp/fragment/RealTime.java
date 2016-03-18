package com.zividig.zivapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zividig.zivapp.R;

/**
 * 实时预览
 * Created by Administrator on 2016-03-18.
 */
public class RealTime extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_realtime,container,false);
        return view;
    }
}
