package com.zividig.zivapp.baidumap;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;

/**
 * 地图定位类
 * Created by Administrator on 2016-03-24.
 */
public class BaseMapFunction implements BDLocationListener{

    public LocationClient mLocationClient;
    public LatLng ll;
    private boolean isFirstLocation = true;

    public MapView mapView;
    public BaiduMap baiduMap;
    public Context context;

    public BaseMapFunction(Context context, MapView mapView, BaiduMap baiduMap){
        this.context = context;
        this.mapView = mapView;
        this.baiduMap = baiduMap;
    }

    //初始化定位数据
    public void initLocation(){
        System.out.println("initLocation方法执行了");

        baiduMap.setMyLocationEnabled(true);
        //声明LocationClient类
        mLocationClient = new LocationClient(context);
        //注册监听函数
        mLocationClient.registerLocationListener(this);

        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//高精度
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true); //需要位置信息
        option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向
        mLocationClient.setLocOption(option);
        mLocationClient.start();
        System.out.println("开始监听");
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {

        System.out.println("定位回调方法执行了");
        if (bdLocation == null || mapView == null){
            System.out.println("为空？");
            return;
        }
        //构造定位数据
        MyLocationData locationData = new MyLocationData.Builder()
                .accuracy(bdLocation.getRadius())
                .latitude(bdLocation.getLatitude())
                .longitude(bdLocation.getLongitude())
                .build();
        baiduMap.setMyLocationData(locationData);

        System.out.println(bdLocation.getLatitude());

        //设置标注
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,true,null);
        baiduMap.setMyLocationConfigeration(config);

        //第一次定位时，将地图位置移到当前位置
        if(isFirstLocation){
            System.out.println("第一次定位");
            isFirstLocation = false;
            ll = new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
            System.out.println("基本定位" + ll);
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(18.0f);
            baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            Toast.makeText(context, bdLocation.getAddrStr(), Toast.LENGTH_SHORT).show();
        }

    }


}
