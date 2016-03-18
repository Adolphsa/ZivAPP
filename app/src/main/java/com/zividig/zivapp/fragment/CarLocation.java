package com.zividig.zivapp.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.SupportMapFragment;
import com.zividig.zivapp.R;

/**
 * 车辆定位
 * Created by Administrator on 2016-03-18.
 */
public class CarLocation extends Fragment {

    SupportMapFragment map;
    private MapView mapView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_location,container,false);
        return view;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapStatus ms = new MapStatus.Builder().overlook(-20).zoom(15).build();
        BaiduMapOptions bo = new BaiduMapOptions().mapStatus(ms)
                .compassEnabled(false).zoomControlsEnabled(false);
        map = SupportMapFragment.newInstance(bo);
        android.support.v4.app.FragmentManager manager = getFragmentManager();
        manager.beginTransaction().add(R.id.map, map, "map_fragment").commit();

        mapView = map.getMapView();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        if (mapView != null){
            mapView.onResume();

        }
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
