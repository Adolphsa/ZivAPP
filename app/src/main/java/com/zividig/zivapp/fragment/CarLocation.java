package com.zividig.zivapp.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.zividig.zivapp.R;
import com.zividig.zivapp.baidumap.MyOrientationListener;

import javax.xml.transform.Source;

/**
 * 车辆定位
 * Created by Administrator on 2016-03-18.
 */
public class CarLocation extends Fragment {

    //地图
    private MapView mapView;
    private BaiduMap baiduMap;

    //定位相关
    public double mCurrentLatitude;  //纬度
    public double mCurrentLongitude; //经度
    public float mCurrentAccuracy; //精度
    private LatLng ll;

    private boolean isFirstLocation = true;

    public LocationClient mLocationClient;
    public BDLocationListener myListener;

    //方向传感器
    MyOrientationListener mMyOrientationListener;
    public float mCurrentX;


    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_location, container, false);
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.showZoomControls(false);

        //设置标题
        TextView title = (TextView)view.findViewById(R.id.tv_title);
        title.setText("车辆定位");

        initView();
        initLocation();
        initOrientation();
        return view;
    }


    public void initView(){
        System.out.println("initView方法执行了");
        baiduMap = mapView.getMap();
    }

    //初始化定位数据
    public void initLocation(){
        System.out.println("initLocation方法执行了");

        baiduMap.setMyLocationEnabled(true);
        //声明LocationClient类
        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        //注册监听函数
        myListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myListener);

        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//高精度
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(1000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true); //需要位置信息
        option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    //初始化方向传感器
    public void initOrientation(){
        mMyOrientationListener = new MyOrientationListener(
                getActivity().getApplicationContext());
        mMyOrientationListener
                .setOnOrientationListener(new MyOrientationListener.OnOrientationListener()
                {
                    @Override
                    public void onOrientationChanged(float x)
                    {
                        mCurrentX = (int) x;

                        // 构造定位数据
                        MyLocationData locData = new MyLocationData.Builder()
                                .accuracy(mCurrentAccuracy)
                                        // 此处设置开发者获取到的方向信息，顺时针0-360
                                .direction(mCurrentX)
                                .latitude(mCurrentLatitude)
                                .longitude(mCurrentLongitude).build();
                        // 设置定位数据
                        baiduMap.setMyLocationData(locData);

                        //设置标注
                        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,true,null);
                        baiduMap.setMyLocationConfigeration(config);

                    }
                });
    }

    class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null || mapView == null){
                System.out.println("为空？");
                return;
            }
            //构造定位数据
            MyLocationData locationData = new MyLocationData.Builder()
                    .direction(mCurrentX)
                    .accuracy(bdLocation.getRadius())
                    .latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude())
                    .build();
            baiduMap.setMyLocationData(locationData);

            System.out.println(bdLocation.getLatitude());

            //记录当前的一些数据
            mCurrentAccuracy = bdLocation.getRadius();
            mCurrentLatitude = bdLocation.getLatitude();
            mCurrentLongitude = bdLocation.getLongitude();

            //设置标注
            MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,true,null);
            baiduMap.setMyLocationConfigeration(config);

            //第一次定位时，将地图位置移到当前位置
            if(isFirstLocation){
                System.out.println("第一次定位");
                isFirstLocation = false;
                ll = new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                Toast.makeText(getActivity().getApplicationContext(), bdLocation.getAddrStr(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mMyOrientationListener.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMyOrientationListener.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        // 退出时销毁定位
        mLocationClient.stop();
        // 关闭定位图层
        baiduMap.setMyLocationEnabled(false);
        mapView.onDestroy();
        mapView = null;

        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
