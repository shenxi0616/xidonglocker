package com.example.a10692.xidonglocker;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.walknavi.WalkNavigateHelper;
import com.baidu.mapapi.walknavi.adapter.IWEngineInitListener;
import com.baidu.mapapi.walknavi.adapter.IWRoutePlanListener;
import com.baidu.mapapi.walknavi.model.WalkRoutePlanError;
import com.baidu.mapapi.walknavi.params.WalkNaviLaunchParam;
import com.example.a10692.xidonglocker.MyCard.PayCardActivity;
import com.example.a10692.xidonglocker.Order.OrderInfoActivity;
import com.example.a10692.xidonglocker.util.ScreenUtils;
import com.google.gson.Gson;
import com.google.zxing.activity.CaptureActivity;
import com.jaeger.library.StatusBarUtil;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.example.a10692.xidonglocker.util.Constant;

import model.ItemOrder;
import model.Storage;
import model.User;
import okhttp3.Call;
import okhttp3.Response;
import view.ClearEditText;
import view.HttpRequest;
import view.HttpUtil;
import view.IWheelViewSelectedListener;
import view.MyWheelView;
import view.WalkingRouteOverlay;

public class MainActivity extends AppCompatActivity {
    public LocationClient mLocationClient;
    private MapView mapView;
    private BaiduMap baiduMap;
    private Button btn_daohang,btn_test;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private LinearLayout linearLayout;
    private NavigationView navigationView;
    private boolean isFirstLocate = true;
    private double latitude,longitude;
    private LatLng latLng;//获取被点击的Marker的坐标
    private String scanResult;//扫描后返回的储物柜编号
    private SharedPreferences sp ;

    private String storage_order_code;//获取订单编号
    private String msg;//获取msg
    private String now_storage_code,location,type;//声明箱柜编号，地址，类型

    private int usetime = 0;//设置时间轴时间
    private WalkingRouteOverlay overlay;//路线规划
    private String TAG = MainActivity.class.getCanonicalName();
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());


        btn_daohang = findViewById(R.id.btn_daohang);
        drawerLayout = findViewById(R.id.drawerlayout);
        navView = findViewById(R.id.navigation_view);
        linearLayout = findViewById(R.id.linlayout);
        mapView = (MapView) findViewById(R.id.bmapView);
        navigationView = findViewById(R.id.navigation_view);
        sp = getSharedPreferences("login_info", MODE_PRIVATE);
        mapView.removeViewAt(1);//移除百度地图logo
        baiduMap = mapView.getMap();//baiduMap是地图控制器对象
//        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);//设置地图类型
        baiduMap.setMyLocationEnabled(true);//开启地图的定位图层
        linearLayout.setPadding(0,getStatusBarHeight(MainActivity.this),0,0);
        StatusBarUtil.setTranslucentForImageView(MainActivity.this, 1, null);
        StatusBarUtil.setLightMode(MainActivity.this);//设置状态栏字体颜色

        setDrawlayout();
        WalkNavigate();


        //设置搜索栏的文字
        ClearEditText clearEditText = findViewById(R.id.edit_search);
        clearEditText.getmEdittext().setHint("查找附近的储物柜");
        //给导航按钮设置点击事件

        //设置抽屉侧拉框
        btn_daohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        navView.setCheckedItem(R.id.mywallet);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.mywallet:
                        Toast.makeText(getApplicationContext(),"this is "+menuItem.getItemId()+"",Toast.LENGTH_SHORT).show();
                        MyWalletActivity.actionStart(MainActivity.this);
                        break;
                    case R.id.orderform:
                        Toast.makeText(getApplicationContext(),"this is "+menuItem.getItemId()+"",Toast.LENGTH_SHORT).show();
                        OrderActivity.actionStart(MainActivity.this);
                        break;
                    case R.id.mycard:
                        Toast.makeText(getApplicationContext(),"this is "+menuItem.getItemId()+"",Toast.LENGTH_SHORT).show();
                        MyCardActivity.actionStart(MainActivity.this);
                        break;
                    case R.id.VIP:
                        Toast.makeText(getApplicationContext(),"this is "+menuItem.getItemId()+"",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.about_us:
                        Toast.makeText(getApplicationContext(),"this is "+menuItem.getItemId()+"",Toast.LENGTH_SHORT).show();
                        AboutActivity.actionStart(MainActivity.this);
                        break;
                    case R.id.suggestion:
                        Toast.makeText(getApplicationContext(),"this is "+menuItem.getItemId()+"",Toast.LENGTH_SHORT).show();
                        FeedBackActivity.actionStart(MainActivity.this);
                        break;
                    default:
                        Toast.makeText(getApplicationContext(),"this is null",Toast.LENGTH_SHORT).show();
                }
                return true;

            }
        });

        //对需要权限进行获取
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()){
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this,permissions,1);
        }else {
            requestLocation();
        }

        init();
    }

    private void setDrawlayout() {
        //给侧滑栏头部头像设置点击事件
        View headerView = navigationView.getHeaderView(0);
        navigationView.setItemIconTintList(null);
//        sp.edit().clear().commit();//清除SharedPreferences
        String name = sp.getString("nickname", null);
        if (name != null){
            TextView textView = headerView.findViewById(R.id.head_user_name);
            textView.setText(name);
        }

        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UserInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    //获取手机高度
    private static int getStatusBarHeight(Activity acitivity){

        int resourceId = acitivity.getResources().getIdentifier("status_bar_height", "dimen", "android");

        return acitivity.getResources().getDimensionPixelOffset(resourceId);
    }

    private void navigateTo(BDLocation location){
        //        宿舍坐标latitude:23.148203 longitude:113.034409
        if (isFirstLocate){
            LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());//传入当前的经纬度
            if ( location.getLatitude() == 0 & location.getLongitude() == 0){
                ll = new LatLng(23.148203,113.034409);
            }
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            Log.e(TAG,"latitude:"+latitude+"longitude:"+longitude);
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomBy(16f);//将缩放级别设置为16
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
        MyLocationData.Builder locatiobBuilder = new MyLocationData.Builder();
        locatiobBuilder.latitude(location.getLatitude());
        locatiobBuilder.longitude(location.getLongitude());
        MyLocationData locationData = locatiobBuilder.build();
        baiduMap.setMyLocationData(locationData);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setDrawlayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }


    //坐标转地址的方法,解析获得行政区号和详细地址
    private void initGeoCoder(LatLng point){
        GeoCoder mSearch = GeoCoder.newInstance();
        OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    //没有找到检索结果
                    return;
                } else {
                    //详细地址
                    String address = reverseGeoCodeResult.getAddress();
                    //行政区号
                    int adCode = reverseGeoCodeResult. getCityCode();
                    Log.e(TAG,"address-->"+address+"adCode-->"+adCode);
                }
            }
        };
        mSearch.setOnGetGeoCodeResultListener(listener);

        mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                .location(point)
                // POI召回半径，允许设置区间为0-1000米，超过1000米按1000米召回。默认值为1000
                .radius(500));
    }
    //导航
    private void WalkNavigate(){

        btn_test = findViewById(R.id.btn_test);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //起终点位置
//                requestPost("","");
                Intent i1 = new Intent();
// 步行导航
                String start = latitude+","+longitude;//当前经纬度
                String end = latLng.latitude+","+latLng.longitude;//点击的marker的经纬度
//                String start = "23.148203,113.034409";
//                String end = "23.148253,113.034459";

                i1.setData(Uri.parse("baidumap://map/walknavi?origin="+start+"&destination="+end+"&coord_type=bd09ll&src=andr.baidu.openAPIdemo"));
                startActivity(i1);
//                parseJSONWITHJSONObject("");
            }
        });

    }

    private void routeWalkPlanWithParam() {
    }


    private void init(){
        //定义Maker坐标点
        LatLng point;
//        宿舍坐标latitude:23.148203 longitude:113.034409
//        double location[][] = new double[][]{{39.963175, 116.400244},{39.964175, 116.402244}};
        double location[][] = new double[][]{{23.148203,113.034409},{23.148600, 113.034600}};
        double location2[][] = new double[][]{{0.000915, 0.000015},{0.000135, 0.000135}};
        for (int i = 0;i<2;i++){
            if (latitude == 0 && longitude == 0){
                point = new LatLng(location[i][0], location[i][1]);
            }else {
                point = new LatLng(latitude+location2[i][0], longitude+ location2[i][1]);
            }

            Log.e(TAG,"latitude-->"+latitude+"longitude-->"+longitude);
            //构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.home1);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icon(bitmap);
            //在地图上添加Marker，并显示
            baiduMap.addOverlay(option);
        }


        final RoutePlanSearch mSearch = RoutePlanSearch.newInstance();

        final OnGetRoutePlanResultListener getRouteListener = new OnGetRoutePlanResultListener() {
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
                //创建WalkingRouteOverlay实例
                if(overlay != null){
                    overlay.removeFromMap();
                }
                overlay= new WalkingRouteOverlay(baiduMap);
                if (walkingRouteResult.getRouteLines().size() > 0) {
                    //获取路径规划数据,(以返回的第一条数据为例)
                    //为WalkingRouteOverlay实例设置路径数据
                    overlay.setData(walkingRouteResult.getRouteLines().get(0));
                    //在地图上绘制WalkingRouteOverlay
                    overlay.addToMap();

                    Log.e(TAG,"overlay.getOverlayOptions()-->");
                }
            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

            }

            @Override
            public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

            }

            @Override
            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

            }
        };

        BaiduMap.OnMarkerClickListener listener = new BaiduMap.OnMarkerClickListener() {
            /**
             * 地图 Marker 覆盖物点击事件监听函数
             * @param marker 被点击的 marker
             */
            public boolean onMarkerClick(Marker marker){
                Toast.makeText(MainActivity.this,"坐标："+marker.getPosition(),Toast.LENGTH_SHORT).show();
                mSearch.setOnGetRoutePlanResultListener(getRouteListener);

                PlanNode stNode = PlanNode.withLocation(new LatLng(latitude, longitude));
                PlanNode enNode = PlanNode.withLocation(marker.getPosition());
                latLng = marker.getPosition();
                mSearch.walkingSearch((new WalkingRoutePlanOption())
                        .from(stNode)
                        .to(enNode));
                mSearch.destroy();
//                initGeoCoder(marker.getPosition());
                return true;
            }
        };
        baiduMap.setOnMarkerClickListener(listener);
    }
    private void initLocation() {


        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置为高精度模式
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setCoorType("bd09ll");//设置返回经纬度坐标类型
        mLocationClient.setLocOption(option);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0){
                    for (int result:grantResults){
                        if (result!=PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"必须同意所有权限才可使用程序",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }else {
                    Toast.makeText(this,"发生未知错误",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }




    //设置扫描二维码的点击事件
    public void scanning(View view) {
        // 申请相机权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .CAMERA)) {
                Toast.makeText(this, "请至权限中心打开本应用的相机访问权限", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, Constant.REQ_PERM_CAMERA);
            return;
        }
        // 申请文件读写权限（部分朋友遇到相册选图需要读写权限的情况，这里一并写一下）
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请至权限中心打开本应用的文件读写权限", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constant.REQ_PERM_EXTERNAL_STORAGE);
            return;
        }
        // 二维码扫码
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        startActivityForResult(intent, Constant.REQ_QR_CODE);
    }

    Runnable postRun = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            requestPost(scanResult);
        }
    };

    //    处理二维码扫描结果的回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //扫描结果回调
        if (requestCode == Constant.REQ_QR_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            //将扫描出的信息保存到scanResult
            scanResult = bundle.getString(Constant.INTENT_EXTRA_KEY_QR_SCAN);
            //判断扫描返回的箱柜码
            new Thread(postRun).start();

//            Toast.makeText(MainActivity.this,""+scanResult,Toast.LENGTH_SHORT).show();
            Log.e(TAG,scanResult);
        }


    }

    public class MyLocationListener extends BDAbstractLocationListener {
        //        public void onReceiveLocation(BDLocation location) {
//            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
//            //以下只列举部分获取经纬度相关（常用）的结果信息
//            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
//
//            double latitude = location.getLatitude();    //获取纬度信息
//            double longitude = location.getLongitude();    //获取经度信息
//            float radius = location.getRadius();    //获取定位精度，默认值为0.0f
//
//            String coorType = location.getCoorType();
//            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
//
//            int errorCode = location.getLocType();
//            Log.d("MainActivity latitude:",latitude+"");
//            Log.d("MainActivity longitude:",longitude+"");
//            Log.d("MainActivity radius:",radius+"");
//            Log.d("MainActivity coorType:",coorType);
//            Log.d("MainActivity errorCode",errorCode+"  "+BDLocation.TypeGpsLocation);
//
//            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
//        }


        public void onReceiveLocation(final BDLocation location) {

//            //mapView 销毁后不在处理新接收的位置
//            if (location == null || mapView == null){
//                return;
//            }
//            MyLocationData locData = new MyLocationData.Builder()
//                    .accuracy(location.getRadius())
//                    // 此处设置开发者获取到的方向信息，顺时针0-360
//                    .direction(location.getDirection()).latitude(location.getLatitude())
//                    .longitude(location.getLongitude()).build();
//            baiduMap.setMyLocationData(locData);

            if (location.getLocType() == BDLocation.TypeGpsLocation || location.getLocType() == BDLocation.TypeNetWorkLocation){
                navigateTo(location);
//                init();
            }
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    StringBuilder currentPosition = new StringBuilder();
//                    currentPosition.append("纬度：").append(location.getLatitude()).append("\n");
//                    currentPosition.append("经度：").append(location.getLongitude()).append("\n");
//                    currentPosition.append("定位方式：");
//                    if (location.getLocType() == BDLocation.TypeGpsLocation) {
//                        currentPosition.append("GPS");
//                    } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
//                        currentPosition.append("网络");
//                    }
//                    positionText.setText(currentPosition);
//                }
//            });
        }

        public void onConnectHotSpotMessage(String s, int i) {
        }

    }


//    private void requestPost(final String tel, String code) {
//        try {
//            String baseUrl = "http://192.168.43.10:8080/locker/getMainStorageList";
//            //使用okhttp3与数据库进行连接
//
//            String jsondata = "{\"code\":0,\"msg\":\"查找成功\",\"data\":[{\"storage_code\":\"#5133d\",\"put_in_time\":\"2019-07-29 13:46:52.0\",\"province\":\"广东省\",\"city\":\"佛山市\",\"concrete_location\":\"狮山\",\"longitude\":55.44,\"latitude\":954.66},{\"storage_code\":\"4545454\",\"put_in_time\":\"2019-08-29 10:50:26.0\",\"province\":\"广东\",\"city\":\"珠海\",\"concrete_location\":\"是对的\",\"longitude\":50.0,\"latitude\":940.0}]}";
//            HttpUtil.sendOkHttpRequestPosition(baseUrl,"55.44","954.66",new okhttp3.Callback(){
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(MainActivity.this,"连接失败",Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    String responseResult = response.body().string();
//                    Log.e(TAG,"responseResult-->>"+responseResult);
//                }
//            });
//
//        } catch (Exception e) {
//            Log.e(TAG, e.toString());
//        }
//    }


    //储物柜使用的，打开储物柜发送storage_order_code和type(0打开,1关闭)
    //msg=本人柜子/他人柜子/柜子空闲
    private void requestPost(String scanResult) {
        try {
            String baseUrl2 = "http://192.168.43.6:8080/locker/getNowStorageStatus";//当前储物柜状态
            String baseUrl = "http://10.4.123.236:8080/locker/getNowStorageStatus";
            String baseUrl3 = "http://172.20.10.9:8080/locker/getNowStorageStatus";
            baseUrl = new HttpRequest().baseUrlGetNowStorageStatus;
            //使用okhttp3与数据库进行连接
//            scanResult = "201909043612361114";//储物柜编号先写死,201909043612361213
            Log.e(TAG,"token-->"+sp.getString("token",null));
            HttpUtil.sendOkHttpRequestOrderStart(baseUrl,scanResult,sp.getString("token",null),new okhttp3.Callback(){
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,"连接失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseResult = response.body().string();
                    Log.e(TAG,"responseResult-->>"+responseResult);
//                    responseResult = "{\"code\":0,\"msg\":\"本人柜子\",\"data\":{\"now_storage_code\":\"12345\",\"storage_code\":\"321122\",\"son_storage_code\":\"1222\",\"location\":\"佛山\",\"dimensions\":\"5x5\",\"type\":\"中\",\"description\":null,\"status\":1}}"

                    //此处获取储物柜编号和地址
                    String msg = parseJSONWITHJSONObject(responseResult);
//                    CreateOrderActivity.actionStart(MainActivity.this,responseResult);
                    switch (msg){
                        case "柜子空闲":
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showDialog();
                                }
                            });
                            break;
                        case "本人柜子":
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showDialogUse();
                                }
                            });
                            break;
                        case "他人柜子":
                            showToastInThread(MainActivity.this,"该柜子已被使用!");
                            break;
                        case "超时未支付":
                            showToastInThread(MainActivity.this,"超时未支付，请去订单表结算超时费用!");
                            break;
                        default:

                            break;
                    }

                }
            });

        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }


    //开关储物柜
    private void requestPostOpen(int type,String storage_order_code) {
        try {
//            10.4.123.236
            String baseUrl = "http://10.4.123.236:8080/locker/addUseRecord";
            String baseUrl2 = "http://192.168.43.6:8080/locker/addUseRecord";
            String baseUrl3 = "http://172.20.10.9:8080/locker/addUseRecord";
            baseUrl = new HttpRequest().baseUrlAddUseRecord;


            //使用okhttp3与数据库进行连接
            Log.e(TAG,"token-->"+sp.getString("token",null));
            Log.e(TAG,"requestPostOpen  storage_order_code-->"+storage_order_code);
            HttpUtil.sendOkHttpRequestOpen(baseUrl2,storage_order_code,type,sp.getString("token",null),new okhttp3.Callback(){
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,"连接失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String responseResult = response.body().string();
                    Log.e(TAG,"responseResult-->>"+responseResult);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }


    private void showDialogUse() {
        View view = LayoutInflater.from(this).inflate(R.layout.use_dialog,null,false);
        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();

        TextView btn_open = view.findViewById(R.id.btn_open);
        TextView btn_close = view.findViewById(R.id.btn_close);
        Log.e(TAG,"storage_order_code-->"+storage_order_code);

        //点击对话框以外的区域是否让对话框消失
        dialog.setCancelable(false);
        //打开储物柜
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPostOpen(0,storage_order_code);
                requestPostOpen(1,storage_order_code);
                dialog.dismiss();
            }
        });
        //关闭弹窗
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();

            }
        });



        dialog.show();
        //此处设置位置窗体大小，我这里设置为了手机屏幕宽度的3/4  注意一定要在show方法调用后再写设置窗口大小的代码，否则不起效果会
        dialog.getWindow().setLayout((ScreenUtils.getScreenWidth(this)/4*3),LinearLayout.LayoutParams.WRAP_CONTENT);
    }



    //使用时间滑动弹窗
    private void showDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.alert_dialog,null,false);
        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();


        final MyWheelView myWheelView = view.findViewById(R.id.wheel_view);
        List<String> data = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            data.add(i + "小时");
        }
        myWheelView.setDataWithSelectedItemIndex(data, 0);

        myWheelView.setWheelViewSelectedListener(new IWheelViewSelectedListener() {
            @Override
            public void wheelViewSelectedChanged(MyWheelView myWheelView, List<String> data, int position) {
                Log.d(TAG, "wheelViewSelectedChanged: " + data.get(position));
            }
        });

        Button btn_cancel_high_opion = view.findViewById(R.id.btn_cancel_high_opion);
        Button btn_agree_high_opion = view.findViewById(R.id.btn_agree_high_opion);

        btn_cancel_high_opion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SharedPreferencesUnitls.setParam(getApplicationContext(),"HighOpinion","false");
                //... To-do
                Log.e(TAG, "getSelectedItemIndex(): " + myWheelView.getSelectedItemIndex());
                dialog.dismiss();
            }
        });

        btn_agree_high_opion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //... To-do

                usetime = myWheelView.getSelectedItemIndex()+1;//使用时间
                Log.e(TAG, "getSelectedItemIndex(): " + usetime);

                Log.e(TAG, "now_storage_code,location,type,usetime: " + now_storage_code+","+location+","+type+","+usetime);
//                跳转到生成订单页面
                CreateOrderActivity.actionStart(MainActivity.this,now_storage_code,location,type,usetime+"");


                dialog.dismiss();
            }
        });

        dialog.show();
        //此处设置位置窗体大小，我这里设置为了手机屏幕宽度的3/4  注意一定要在show方法调用后再写设置窗口大小的代码，否则不起效果会
        dialog.getWindow().setLayout((ScreenUtils.getScreenWidth(this)/4*3),LinearLayout.LayoutParams.WRAP_CONTENT);
    }

//    Runnable postRuncreate = new Runnable() {
//
//        @Override
//        public void run() {
//            // TODO Auto-generated method stub
//            requestCreateOrder(MainActivity.this,sp.getString("token",null),"","");
////            Log.e(TAG,"requestCreateOrder-->"+str);
//        }
//    };


    //处理发送储物柜码后的返回结果
    private String parseJSONWITHJSONObject(String jsondata) {
//        jsondata = "{\"code\":0,\"msg\":\"查找成功\",\"data\":[{\"user_code\":49,\"username\":\"5\",\"nickname\":\"1\",\"account\":\"666\",\"password\":\"123\",\"identify\":\"131\",\"identification\":0,\"strorage_number\":0,\"coupun_number\":0,\"register_time\":\"2019-07-27 11:04:37.0\",\"binding\":\"8\",\"email\":\"336\",\"profile_photo_url\":\"10\",\"balance\":0.0}]}";
//        jsondata = "{\"code\":0,\"msg\":\"查找成功\",\"data\":[{\"now_storage_code\":\"12345\",\"storage_code\":\"321122\",\"son_storage_code\":\"1222\",\"location\":\"佛山\",\"status\":0}]}";
        String msg = null;

        try {
            Gson gson = new Gson();
            Storage storage =gson.fromJson(jsondata,Storage.class);
            msg = storage.getMsg();
//            status = storage.getData().getStatus();
            now_storage_code = storage.getData().getNow_storage_code();
            location = storage.getData().getLocation();
            type = storage.getData().getType()+"";
            storage_order_code = storage.getData().getStorage_order_code();

            Log.e(TAG,"Now_storage_code-->>"+storage.getData().getNow_storage_code());
            Log.e(TAG,"Son_storage_code()-->>"+storage.getData().getSon_storage_code());
            Log.e(TAG,"Storage_code()-->>"+storage.getData().getStorage_code());
            Log.e(TAG,"Location()-->>"+storage.getData().getLocation());
            Log.e(TAG,"status-->>"+storage.getData().getStatus());
            Log.e(TAG,"storage_order_code-->>"+storage_order_code);
//            code = user.getUser_code();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }



    // 实现在子线程中显示Toast
    private void showToastInThread(final Context context, final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    //判断是否处于主线程
    public boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }


//    //HttpRequest无用
//    public void requestCreateOrder(final Context context,String token,String storage_oredr_code,String pre_end_time) {
//        try {
////            10.4.123.236
//            String baseUrl = "http://10.4.123.236:8080/locker/createStoreLockerOrder";
//            String baseUrl2 = "http://192.168.43.10:8080/locker/createStoreLockerOrder";
//            //使用okhttp3与数据库进行连接
//            HttpUtil.sendOkHttpRequestOrderCreate(baseUrl,storage_oredr_code,pre_end_time,token,new okhttp3.Callback(){
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    Toast.makeText(context,"连接失败",Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    String responseResult = response.body().string();
//                    Log.e(TAG,"responseResult-->"+responseResult);
////                    CreateOrderActivity.actionStart(context,responseResult);
////                  responseResult = "{"code":0,"msg":"生成储物柜订单","data":{"storage_order_code":"024461509320","now_storage_code":"12345","start_time":null,"end_time":null,"pre_end_time":"2019-08-31 17:09:34.0","create_time":"2019-08-31 15:09:34.0","order_status":0,"has_overtime":null,"account":"17765602446","has_pay_fare":3.0}}"
//                }
//            });
//
//        } catch (Exception e) {
//        }
//    }
}
