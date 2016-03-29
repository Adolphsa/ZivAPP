package com.zividig.zivapp.baidumap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.zividig.zivapp.R;
import com.zividig.zivapp.overlayutil.PoiOverlay;

/**
 * 加油站检索
 * Created by Administrator on 2016-03-23.
 */
public class GasStation extends Activity {

    private BaiduMap baiduMap;
    private MapView mapView;

    public LocationClient mLocationClient;
    public BDLocationListener myListener;
    private LatLng ll;
    private boolean isFirstLocation = true;
    private PoiSearch poiSearch;
    private Marker marker;
    PoiOverlay overlay;
    private LinearLayout llMapInfo;

    //需要传递给CarRoutePlan的一些值
    public static LatLng startAdd;
    public static LatLng endAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_station);

        // 标题
        TextView txtTitle = (TextView) findViewById(R.id.tv_title);
        txtTitle.setText("加油站");

        //返回按钮
        Button btnBack = (Button) findViewById(R.id.btn_back);
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        llMapInfo = (LinearLayout) findViewById(R.id.ll_map_info); //地图信息布局文件


        mapView = (MapView) findViewById(R.id.gad_map);
        mapView.showZoomControls(false);
        baiduMap = mapView.getMap();

        initLocation();

    }

    //初始化定位数据
    public void initLocation(){
        System.out.println("initLocation方法执行了");

        baiduMap.setMyLocationEnabled(true);
        //声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        //注册监听函数
        myListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myListener);

        //设置定位参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//高精度
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true); //需要位置信息
        option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向
        mLocationClient.setLocOption(option);
        mLocationClient.start();

    }

    //发起附近检索
    public void nearSearch(LatLng ll){

        poiSearch = PoiSearch.newInstance(); //生成检索对象
        poiSearch.setOnGetPoiSearchResultListener(poiSearchListener);

        //发起附近检索
        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption();
        System.out.println(ll);
        nearbySearchOption.location(ll);
        nearbySearchOption.keyword("加油站");
        nearbySearchOption.radius(5000);// 检索半径，单位是米
        nearbySearchOption.pageCapacity(10);
        nearbySearchOption.pageNum(1);
        poiSearch.searchNearby(nearbySearchOption);// 发起附近检索请求
    }

    //检索监听
    OnGetPoiSearchResultListener poiSearchListener = new OnGetPoiSearchResultListener(){

        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            System.out.println("检索结果");
            //获取POI检索结果
            String error = poiResult.error.toString();
            System.out.println(error);

            if (poiResult == null
                    || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
                Toast.makeText(getApplicationContext(), "未找到结果", Toast.LENGTH_LONG)
                        .show();
                return;
            }

            if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) { //返回结果正常
                baiduMap.clear();
                overlay = new MyPoiOverlay(baiduMap);
                baiduMap.setOnMarkerClickListener(overlay);
                overlay.setData(poiResult);
                overlay.addToMap();
                overlay.zoomToSpan();
                System.out.println("检索结果执行了");

            }
        }

        @Override
        public void onGetPoiDetailResult(final PoiDetailResult poiDetailResult) { //POI详情结果
            //获取Place详情页检索结果
            if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(getApplicationContext(), "抱歉，未找到结果",
                        Toast.LENGTH_SHORT).show();
            } else {// 正常返回结果的时候，此处可以获得很多相关信息
                baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        System.out.println("地图被点击了");
                        baiduMap.hideInfoWindow(); //隐藏infoWindow
                    }

                    @Override
                    public boolean onMapPoiClick(MapPoi mapPoi) {
                        System.out.println("地图上的POI被点击了");
                        return false;
                    }
                });

            }
        }
    };

    //继承POI工具类中的PoiOverlay
    class MyPoiOverlay extends PoiOverlay {
        public MyPoiOverlay(BaiduMap baiduMap){
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int i) { //可以点击
            System.out.println("POI被点击了" + i);
            super.onPoiClick(i);
            PoiInfo poiInfo = getPoiResult().getAllPoi().get(i); //Poi信息
            poiSearch.searchPoiDetail(new PoiDetailSearchOption()
                    .poiUid(poiInfo.uid));

            //当前点的位置
            LatLng currentPointLocation = poiInfo.location;

            //移到中心点
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(currentPointLocation);
            baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

            //获取InfoWindow的布局文件
            View view = View.inflate(getApplicationContext(),R.layout.layput_infowindow,null);
            TextView textName = (TextView) view.findViewById(R.id.tv_popwindow_name);
            TextView textAddress = (TextView) view.findViewById(R.id.tv_popwindow_address);
            Button goThereBtn = (Button) view.findViewById(R.id.btn_go_there);

            //设置要显示的信息
            textName.setText(poiInfo.name); //名称
            textAddress.setText(poiInfo.address); //地址
            endAdd = currentPointLocation;
            //按钮的监听事件
            goThereBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("到哪去按钮被点击了");
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(),CarRoutePlan.class);
                    startActivity(intent);
                    baiduMap.hideInfoWindow(); //隐藏InfoWindow
                }
            });

            LatLng ll = currentPointLocation;
            InfoWindow mInfoWindow = new InfoWindow(view, ll, -47);
            baiduMap.showInfoWindow(mInfoWindow);
            return true;
        }
    }

    /**
     * 定位回调监听类
     */
    class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
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
                startAdd = ll;
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

            }

            //发起附近检索
            if (ll != null){
                System.out.println("执行检索");
                nearSearch(ll);
            }


        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
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
}
