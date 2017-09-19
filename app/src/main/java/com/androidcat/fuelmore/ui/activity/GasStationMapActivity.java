package com.androidcat.fuelmore.ui.activity;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.androidcat.acnet.entity.GasStation;
import com.androidcat.fuelmore.R;
import com.androidcat.fuelmore.utils.DistanceUtils;
import com.androidcat.fuelmore.utils.amaptts.TTSController;
import com.androidcat.utilities.LogUtil;
import com.androidcat.utilities.listener.OnSingleClickListener;

import java.util.List;

/**
 * Project: FuelMore
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-8-22 09:32:40
 * add function description here...
 */
public class GasStationMapActivity extends BaseActivity implements LocationSource {
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
    private MapView mMapView;
    private View naviView;
    private View labelView;
    private TextView nameTv;
    private TextView runTimeTv;
    private TextView addrTv;
    private TextView disTv;
    private View back;
    private View backToListView;

    private boolean isFirstLoc = true;
    private AMap mAMap;
    private AMapNavi mAMapNavi;
    private TTSController mTtsManager;
    public static AMapLocation mCurLoc;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private LocationSource.OnLocationChangedListener mListener;
    private List<GasStation> gasStations;

    private OnSingleClickListener onSingleClickListener = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View view) {
            switch (view.getId()){
                case R.id.back:
                case R.id.backToListView:
                    finish();
                    break;
                case R.id.naviView:
                    launchNavigation();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout(savedInstanceState);
        initAMap();
        initData();
        setListener();
    }

    private void initLayout(Bundle savedInstanceState){
        setContentView(R.layout.activity_gasstation_map);
        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);// 此方法必须重写
        back = findViewById(R.id.back);
        labelView = findViewById(R.id.labelView);
        nameTv = (TextView) findViewById(R.id.nameTv);
        runTimeTv = (TextView) findViewById(R.id.runTimeTv);
        addrTv = (TextView) findViewById(R.id.addrTv);
        disTv = (TextView) findViewById(R.id.disTv);
        backToListView = findViewById(R.id.backToListView);
        naviView = findViewById(R.id.naviView);
        labelView.setVisibility(View.GONE);
    }

    private void initAMap(){
        //初始化地图控制器对象
        if (mAMap == null) {
            mAMap = mMapView.getMap();
        }
        mAMapNavi = AMapNavi.getInstance(getApplicationContext());
        mAMap.setLocationSource(this);// 设置定位监听
        mAMap.getUiSettings().setMyLocationButtonEnabled(true);
        mAMap.getUiSettings().setZoomControlsEnabled(false);
        mAMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
    }

    private void initData(){
        gasStations = getIntent().getParcelableArrayListExtra("gasStations");
        if (gasStations != null && gasStations.size() > 0){
            for (GasStation gasStation : gasStations){
                MarkerOptions markerOption = new MarkerOptions();
                markerOption.draggable(false);//设置Marker不可拖动
                markerOption.position(new LatLng(Double.parseDouble(gasStation.getLatitude()), Double.parseDouble(gasStation.getLongitude())));
                View view = LayoutInflater.from(this).inflate(R.layout.view_gas_station, null);
                markerOption.icon(BitmapDescriptorFactory.fromView(view));
                Marker marker = mAMap.addMarker(markerOption);
                marker.setObject(gasStation);
            }
        }

        mAMap.setOnMarkerClickListener(onMarkerClickListener);
    }

    private void setListener(){
        back.setOnClickListener(onSingleClickListener);
        backToListView.setOnClickListener(onSingleClickListener);
        naviView.setOnClickListener(onSingleClickListener);
    }

    private AMap.OnMarkerClickListener onMarkerClickListener = new AMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            if (mAMap != null) {
                jumpPoint(marker);
                updateLabel(marker);
            }
            return true;
        }
    };

    /**
     * marker点击时跳动一下
     */
    public void jumpPoint(final Marker marker) {
        final long start = SystemClock.uptimeMillis();
        Projection proj = mAMap.getProjection();
        final LatLng markerLatlng = marker.getPosition();
        Point markerPoint = proj.toScreenLocation(markerLatlng);
        markerPoint.offset(0, -100);
        final LatLng startLatLng = proj.fromScreenLocation(markerPoint);
        final long duration = 1500;

        final Interpolator interpolator = new BounceInterpolator();
        baseHandler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * markerLatlng.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * markerLatlng.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                if (t < 1.0) {
                    baseHandler.postDelayed(this, 16);
                }
            }
        });
    }

    private void updateLabel(Marker marker){
        if (marker == null){
            labelView.setVisibility(View.GONE);
            return;
        }
        labelView.setVisibility(View.VISIBLE);
        GasStation gasStation = (GasStation) marker.getObject();
        nameTv.setText(gasStation.getPoint_name());
        runTimeTv.setText(gasStation.getBusiness_time());
        addrTv.setText(gasStation.getPoint_adress());
        if (mCurLoc != null){
            LatLng start = new LatLng(mCurLoc.getLatitude(),mCurLoc.getLongitude());
            LatLng end = new LatLng(Double.parseDouble(gasStation.getLatitude()),Double.parseDouble(gasStation.getLongitude()));
            String dis = DistanceUtils.getDistanceText(start,end);
            disTv.setText(dis);
        }else {
            disTv.setText("距离未知");
        }
    }

    /**
     * 初始化定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(getApplicationContext());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }

    /**
     * 默认的定位参数
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private AMapLocationClientOption getDefaultOption(){
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(10000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if(location.getErrorCode() == 0){
                    if (mListener != null){
                        mListener.onLocationChanged(location);
                        mCurLoc = location;
                    }
                    if (isFirstLoc){
                        LatLng mylocation = new LatLng(location.getLatitude(), location.getLongitude());
                        mAMap.moveCamera(CameraUpdateFactory.zoomTo(13));
                        mAMap.moveCamera(CameraUpdateFactory.changeLatLng(mylocation));
                        isFirstLoc = false;
                    }
                    LogUtil.e("tag", location.getLatitude() + "--" + location.getLongitude());
                } else {
                    //定位失败
                }
                //解析定位结果，
            } else {

            }
        }
    };

    private void moveToMe(){
        if (mCurLoc == null || mAMap == null){
            return;
        }
        LatLng mylocation = new LatLng(mCurLoc.getLatitude(), mCurLoc.getLongitude());
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        mAMap.moveCamera(CameraUpdateFactory.changeLatLng(mylocation));
    }

    /**
     * 开始定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void startLocation(){
        //根据控件的选择，重新设置定位参数
        //resetOption();
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 停止定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void stopLocation(){
        // 停止定位
        locationClient.stopLocation();
    }

    /**
     * 销毁定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void destroyLocation(){
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            stopLocation();
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    /**
     * 设置自定义定位蓝点
     */
    private void setupLocationStyle() {
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.gps_point));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(STROKE_COLOR);
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(1);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(FILL_COLOR);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);
        // 将自定义的 myLocationStyle 对象添加到地图上
        mAMap.setMyLocationStyle(myLocationStyle);
    }

    private void launchNavigation(){
        if (mCurLoc == null){
            showToast("尚未定位成功，请定位成功后再试");
            return;
        }
        GasStation station = gasStations.get(0);
        Poi start = new Poi("我的位置", new LatLng(mCurLoc.getLatitude(),mCurLoc.getLongitude()), "");
        Poi end = new Poi(station.getPoint_name(), new LatLng(Double.parseDouble(station.getLatitude()), Double.parseDouble(station.getLongitude())), "");
        AmapNaviPage.getInstance().showRouteActivity(this, new AmapNaviParams(start, null, end, AmapNaviType.DRIVER), new INaviInfoCallback() {
            @Override
            public void onInitNaviFailure() {

            }

            @Override
            public void onGetNavigationText(String s) {

            }

            @Override
            public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

            }

            @Override
            public void onArriveDestination(boolean b) {

            }

            @Override
            public void onStartNavi(int i) {

            }

            @Override
            public void onCalculateRouteSuccess(int[] ints) {

            }

            @Override
            public void onCalculateRouteFailure(int i) {

            }

            @Override
            public void onStopSpeaking() {

            }
        });
    }

    @Override
    public void activate(LocationSource.OnLocationChangedListener listener) {
        mListener = listener;
        if (locationClient == null) {
            setupLocationStyle();
            initLocation();
        }
        startLocation();
    }

    @Override
    public void deactivate() {
        mListener = null;
        destroyLocation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
        destroyLocation();
    }
    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
}
