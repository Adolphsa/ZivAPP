package com.zividig.zivapp.baidumap;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.common.SysOSUtil;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
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

import java.util.zip.Inflater;

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

        poiSearch = PoiSearch.newInstance(); //生成检索对象
        poiSearch.setOnGetPoiSearchResultListener(poiSearchListener);

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


        if (ll != null){
            System.out.println("执行检索");
            nearSearch(ll);
        }

    }

    //发起附近检索
    public void nearSearch(LatLng ll){
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

                baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        System.out.println("Marker被点击了");

                        //移到中心点
                        MapStatus.Builder builder = new MapStatus.Builder();
                        builder.target(marker.getPosition());
                        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

                        //获取InfoWindow的布局文件
                        View view = View.inflate(getApplicationContext(),R.layout.layput_infowindow,null);
                        TextView textName = (TextView) view.findViewById(R.id.tv_popwindow_name);
                        TextView textAddress = (TextView) view.findViewById(R.id.tv_popwindow_address);

                        //设置要显示的信息
                        textName.setText(poiDetailResult.getName()); //名称
                        textAddress.setText(poiDetailResult.getAddress()); //地址

                        //InfoWindow的点击事件监听
                        InfoWindow.OnInfoWindowClickListener listener = null;
                        listener = new InfoWindow.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick() {
                                System.out.println("InfoWinsow被点击");
                                baiduMap.hideInfoWindow(); //隐藏InfoWindow
                            }
                        };
                        LatLng ll = marker.getPosition();
                        InfoWindow mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(view), ll, -47, listener);
                        baiduMap.showInfoWindow(mInfoWindow);

//                        AlphaAnimation alpha = new AlphaAnimation(0,1); //0是完全透明
//                        alpha.setDuration(1000);
//                        alpha.setFillAfter(true);
//                        if (llMapInfo.isShown()){
//                            llMapInfo.setVisibility(View.INVISIBLE);
//                        }
//                        llMapInfo.setVisibility(View.VISIBLE);
//                        llMapInfo.setAnimation(alpha);
                        return true;
                    }
                });

            }
        }
    };

//    public void initPopWindow(){
//        View popView = View.inflate(getApplicationContext(),R.layout.layout_popwindow,null);
//        PopupWindow popupWindow = new PopupWindow(popView, WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
//        popupWindow.setFocusable(true);
////        popupWindow.setOutsideTouchable(false); //点击外面的区域不关系
//
//        // 实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(Color.GRAY);
//        popupWindow.setBackgroundDrawable(dw);
//
//        TextView textView = (TextView) popView.findViewById(R.id.popwindow_tv_title);
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("popWindow中的文本被点击了");
//            }
//        });
//
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                System.out.println("popWindow消失");
//            }
//        });
//
//        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return false; //点击外部区域不消失
//            }
//        });
//
//        //在底部显示
//        popupWindow.showAtLocation(GasStation.this.findViewById(R.id.tv_title), Gravity.BOTTOM, 0, 0);
//    }

    //继承POI工具类中的PoiOverlay
    class MyPoiOverlay extends PoiOverlay {
        public MyPoiOverlay(BaiduMap baiduMap){
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int i) { //可以点击
            super.onPoiClick(i);
            PoiInfo poiInfo = getPoiResult().getAllPoi().get(i);
            poiSearch.searchPoiDetail(new PoiDetailSearchOption()
                    .poiUid(poiInfo.uid));
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
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }

            //发起附近检索
            nearSearch(ll);

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
