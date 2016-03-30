package com.zividig.zivapp.baidumap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.zividig.zivapp.R;
import com.zividig.zivapp.overlayutil.DrivingRouteOverlay;
import com.zividig.zivapp.overlayutil.TransitRouteOverlay;
import com.zividig.zivapp.overlayutil.WalkingRouteOverlay;

/**
 * 路径规划
 * Created by Administrator on 2016-03-28.
 */
public class CarRoutePlan extends Activity implements OnGetRoutePlanResultListener {

    private MapView mapView;
    private BaiduMap baiduMap;

    RouteLine route = null;
    PlanNode stNode; //开始节点
    PlanNode enNode; //结束节点
    RoutePlanSearch mSearch = null;    // 搜索模块


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_route_plan);

        // 标题
        TextView txtTitle = (TextView) findViewById(R.id.tv_title);
        txtTitle.setText("路径规划");

        //返回按钮
        Button btnBack = (Button) findViewById(R.id.btn_back);
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mapView = (MapView) findViewById(R.id.car_route_map);
        mapView.showZoomControls(false);
        baiduMap = mapView.getMap();

        // 初始化搜索模块，注册事件监听
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);

        stNode = PlanNode.withLocation(GasStation.startAdd); //开始节点
        enNode = PlanNode.withLocation(GasStation.endAdd);   //结束节点
        System.out.println(GasStation.startAdd + "---" + GasStation.endAdd);
        //从Bundle中取值
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int currentI = bundle.getInt("drive");
        System.out.println(currentI);
        switch (currentI){
            case 0:
                //驾车搜索
                mSearch.drivingSearch(new DrivingRoutePlanOption().from(stNode).to(enNode));
                break;
            case 1:
                //公交搜索
                mSearch.transitSearch(new TransitRoutePlanOption().from(stNode).city(GasStation.city).to(enNode));
                break;
            case 2:
                //步行搜索
                mSearch.walkingSearch(new WalkingRoutePlanOption().from(stNode).to(enNode));
                break;
        }

    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) { //步行路线规划
        if (walkingRouteResult == null || walkingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(getApplicationContext(), "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (walkingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            return;
        }
        if (walkingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {

            route = walkingRouteResult.getRouteLines().get(0);
            System.out.println(route.getDistance() + "步行路线");
            WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(baiduMap);
            baiduMap.setOnMarkerClickListener(overlay);
//            routeOverlay = overlay;
            overlay.setData(walkingRouteResult.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();
        }
    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) { //公交路线规划

        if (transitRouteResult == null || transitRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(getApplicationContext(), "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (transitRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            return;
        }
        if (transitRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
            route = transitRouteResult.getRouteLines().get(0);
            TransitRouteOverlay overlay = new MyTransitRouteOverlay(baiduMap);
            baiduMap.setOnMarkerClickListener(overlay);
//            routeOverlay = overlay;
            overlay.setData(transitRouteResult.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();
        }

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) { //驾车路线规划
        if (drivingRouteResult == null || drivingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(getApplicationContext(), "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (drivingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            return;
        }
        if (drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {

            route = drivingRouteResult.getRouteLines().get(0);
            DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(baiduMap);
//            routeOverlay = overlay;
            baiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(drivingRouteResult.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();
        }

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) { //骑行路线规划

    }

    // 定制驾车RouteOverly
    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        public boolean onRouteNodeClick(int i) {
            if (route.getAllStep() != null
                    && route.getAllStep().get(i) != null) {

                System.out.println("节点被点击" + i);

                //获取节点信息
                Object step = route.getAllStep().get(i);
                LatLng latLng = ((DrivingRouteLine.DrivingStep) step).getEntrance().getLocation();
                String text = ((DrivingRouteLine.DrivingStep) step).getInstructions();
                System.out.println(text);
                //移到中心点
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(latLng);
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
            }
            return true;
        }

    }

    //定制步行RouteOverly
    private class MyWalkingRouteOverlay extends WalkingRouteOverlay {

        public MyWalkingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        public boolean onRouteNodeClick(int i) {
            if (route.getAllStep() != null
                    && route.getAllStep().get(i) != null) {

                System.out.println("节点被点击" + i);

                //获取节点信息
                Object step = route.getAllStep().get(i);
                LatLng latLng = ((WalkingRouteLine.WalkingStep) step).getEntrance().getLocation();
                String text = ((WalkingRouteLine.WalkingStep) step).getInstructions();
                System.out.println(text);
                //移到中心点
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(latLng);
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    }

    //定制公交RouteOverly
    private class MyTransitRouteOverlay extends TransitRouteOverlay {

        public MyTransitRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        public boolean onRouteNodeClick(int i) {
            if (route.getAllStep() != null
                    && route.getAllStep().get(i) != null) {

                System.out.println("节点被点击" + i);

                //获取节点信息
                Object step = route.getAllStep().get(i);
                LatLng latLng = ((TransitRouteLine.TransitStep) step).getEntrance().getLocation();
                String text = ((TransitRouteLine.TransitStep) step).getInstructions();
                System.out.println(text);
                //移到中心点
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(latLng);
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
            }
            return true;
        }

    }
    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mSearch.destroy();
        mapView.onDestroy();
        super.onDestroy();
    }
}
