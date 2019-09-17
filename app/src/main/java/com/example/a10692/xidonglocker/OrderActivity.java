package com.example.a10692.xidonglocker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import adapter.OrderAdapter;
import adapter.OrderUnDoneAdapter;
import model.ItemOrder;
import model.Order;
import model.Storage;
import okhttp3.Call;
import okhttp3.Response;
import view.HttpRequest;
import view.HttpUtil;

public class OrderActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private TextView textView;

    private ArrayList<String> location = new ArrayList<String>();
    private ArrayList<String> createtime = new ArrayList<String>();
    private ArrayList<String> storagesize = new ArrayList<String>();
    private ArrayList<String> order_status = new ArrayList<String>();
    private ArrayList<String> getHas_pay_fare = new ArrayList<String>();
    private ArrayList<String> storage_order_code = new ArrayList<String>();
    private ArrayList<Integer> has_overtime = new ArrayList<Integer>();
    private ArrayList<String> type = new ArrayList<String>();
    private Order order;

    private List<ItemOrder> mList = new ArrayList<>();//存放进行中订单
    private List<ItemOrder> mList2 = new ArrayList<>();
    private RecyclerView continuanceView;
    private RecyclerView endingView;
    private OrderAdapter orderAdapter;
    private OrderUnDoneAdapter orderUnDoneAdapter;
    private String end_time;
    private SwipeRefreshLayout refreshLayout;
    private LinearLayoutManager layoutManager;
    private String TAG = OrderActivity.class.getCanonicalName();
    private int lastVisibleItem = 0;
    private final int PAGE_COUNT = 2;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        StatusBarUtil.setTransparent(OrderActivity.this);//设置沉浸式状态栏
        StatusBarUtil.setLightMode(OrderActivity.this);//设置状态栏字体颜色
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        sp = getSharedPreferences("login_info", MODE_PRIVATE);
        textView  = toolbar.findViewById(R.id.myTitle);
        textView.setText("我的订单");
//        new Thread(postRun).start();
//        init();



//        parseJSONWITHJSONObject("");

    }
    Runnable postRun = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            requestPost();
        }
    };

    public void requestPost() {
        try {
//            10.4.123.236
            String baseUrl = new HttpRequest().baseUrlGetSmallOrderList;

            //使用okhttp3与数据库进行连接
            Log.e(TAG,"token-->"+sp.getString("token",null));
            HttpUtil.sendOkHttpRequestOrder(baseUrl,sp.getString("token",null),new okhttp3.Callback(){
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(OrderActivity.this,"连接失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseResult = response.body().string();
                    Log.e(TAG,"responseResult-->>"+responseResult);
                    parseJSONWITHJSONObject(responseResult);

                    findView();//绑定刷新控件
                    initItemBean();//给mlist赋值
                    Log.e(TAG,"mlist->"+mList.size());
                    Log.e(TAG,"order->"+order.getData().size());


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initContinuanceView();//设置未支付订单的内容
                            initEndingView();//设置支付订单的内容
                            initRefreshLayout();
                        }
                    });


//                initRefreshLayout();//调用上拉刷新的方法
                }
            });

        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
    //处理订单表的返回结果
    private void parseJSONWITHJSONObject(String responseResult) {
        String str = "{\"code\":0,\"msg\":\"查找成功\",\"data\":[{\"order_status\":2,\"create_time\":\"2019-08-31 15:07:35.0\",\"location\":\"佛山\",\"has_pay_fare\":3.0,\"storage_order_code\":\"024461507457\",\"account\":null,\"has_overtime\":-1},{\"order_status\":1,\"create_time\":\"2019-08-31 15:09:34.0\",\"location\":\"佛山\",\"has_pay_fare\":3.0,\"storage_order_code\":\"024461509320\",\"account\":null,\"has_overtime\":-1},{\"order_status\":0,\"create_time\":\"2019-09-01 22:47:09.0\",\"location\":\"佛山\",\"has_pay_fare\":7.5,\"storage_order_code\":\"024462247820\",\"account\":null,\"has_overtime\":null}]}";
        try {
            Gson gson = new Gson();
            order =gson.fromJson(responseResult,Order.class);
            for (int i=0;i<order.getData().size();i++){
                Log.e(TAG,"order_status-->>"+order.getData().get(i).getOrder_status());
                Log.e(TAG,"create_time-->>"+order.getData().get(i).getCreate_time());
                Log.e(TAG,"Location-->>"+order.getData().get(i).getLocation());
                Log.e(TAG,"Has_pay_fare-->>"+order.getData().get(i).getHas_pay_fare());
                Log.e(TAG,"Storage_order_code-->>"+order.getData().get(i).getStorage_order_code());
                Log.e(TAG,"Account-->>"+order.getData().get(i).getAccount());
                Log.e(TAG,"Has_overtime-->>"+order.getData().get(i).getHas_overtime());
                //填入网络连接获取的数据
                location.add(order.getData().get(i).getLocation());
                createtime.add(order.getData().get(i).getCreate_time());
                storagesize.add("使用储物柜");
                order_status.add(order.getData().get(i).getOrder_status());
                getHas_pay_fare.add(order.getData().get(i).getHas_pay_fare()+"");
                storage_order_code.add(order.getData().get(i).getStorage_order_code());
                has_overtime.add(order.getData().get(i).getHas_overtime());
                type.add(order.getData().get(i).getType());
            }
//            code = user.getUser_code();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //未支付订单
    private void initContinuanceView() {
//        Log.e(TAG,"initContinuanceView"+order.getData().size());
//        Log.e(TAG,"initContinuanceView"+mList.size());
        continuanceView = (RecyclerView) findViewById(R.id.recycle_continuance);
        orderAdapter = new OrderAdapter(mList,this,false);
//        Log.e(TAG,"orderAdapter->"+orderAdapter.getItemCount());
        layoutManager = new LinearLayoutManager(this);
        continuanceView.setLayoutManager(layoutManager);
        continuanceView.setAdapter(orderAdapter);
        continuanceView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));//添加分割线
    }
    //结束订单
    private void initEndingView() {
        endingView = (RecyclerView) findViewById(R.id.recycle_ending);
        orderUnDoneAdapter = new OrderUnDoneAdapter(getDatas(0, PAGE_COUNT), this, getDatas(0, PAGE_COUNT).size() > 0 ? true : false);
        layoutManager = new LinearLayoutManager(this);
        endingView.setLayoutManager(layoutManager);
        endingView.setAdapter(orderUnDoneAdapter);
        endingView.setItemAnimator(new DefaultItemAnimator());
        endingView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));//添加分割线
        endingView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (orderUnDoneAdapter.isFadeTips() == false && lastVisibleItem + 1 == orderUnDoneAdapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(orderUnDoneAdapter.getRealLastPosition(),orderUnDoneAdapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }

                    if (orderUnDoneAdapter.isFadeTips() == true && lastVisibleItem + 2 == orderUnDoneAdapter.getItemCount()) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                updateRecyclerView(orderUnDoneAdapter.getRealLastPosition(), orderUnDoneAdapter.getRealLastPosition() + PAGE_COUNT);
                            }
                        }, 500);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
    }

    //绑定控件
    private void findView() {
        Log.e(TAG," findView()"+order.getData().size());
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
    }
//获取数据
    private void initItemBean() {
        ItemOrder itemOrder;
        Log.e(TAG,"initItemBean"+order_status);
        for (int i = 0; i < order.getData().size() ; i++){
            itemOrder = new ItemOrder(createtime.get(i),location.get(i),storagesize.get(i),getHas_pay_fare.get(i),storage_order_code.get(i),Integer.parseInt(order_status.get(i)));
            itemOrder.setBoxsize(type.get(i));
            itemOrder.setOvertime(has_overtime.get(i));
            if (!(order_status.get(i).equals("2"))){
                mList.add(itemOrder);
            }else {
                mList2.add(itemOrder);
            }
        }
    }


//    private void init() {
//        //给出当前时间
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date curDate = new Date(System.currentTimeMillis());
//        //将订单开始时间赋值为订单创建时系统时间
//        end_time = formatter.format(curDate);
//
//
//        //开始时间后面最好使用date数据类型，数据通过网络连接获取
//        start_time = new String[]{"2019-08-28 10:13:16","2019-08-28 10:13:16","2019-08-28 10:13:16","2019-08-28 10:13:16","2019-08-28 10:13:16","2019-08-28 10:13:16",};
//        address = new String[]{"狮山大学城阳光在线广场","和信广场","和信广场","狮山大学城阳光在线广场","和信广场","和信广场"};
//        boxtype = new String[]{"使用储物柜","使用储物柜","使用储物柜","使用储物柜","使用储物柜","使用储物柜"};
//        boxsize = new String[]{"大型","小型","小型","小型","小型","大型"};
//        coupon = new String[]{"五折卷","月卡七折卷","月卡七折卷"};
//        status = new int[]{0,0,1,2,2,2};
//    }

    //上拉刷新的动画
    private void initRefreshLayout() {
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(this);
    }


    //每次上拉加载该方法刷新列表
    private List<ItemOrder> getDatas(final int firstIndex, final int lastIndex) {
        List<ItemOrder> resList = new ArrayList<>();
        for (int i = firstIndex; i < lastIndex; i++) {
            if (i < mList2.size()) {
                resList.add(mList2.get(i));
            }
        }
        return resList;
    }

    private void updateRecyclerView(int fromIndex, int toIndex) {
        List<ItemOrder> newDatas = getDatas(fromIndex, toIndex);
        if (newDatas.size() > 0) {
            orderUnDoneAdapter.updateList(newDatas, true);
        } else {
            orderUnDoneAdapter.updateList(null, false);
        }
    }


    //刷新列表
    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        orderUnDoneAdapter.resetDatas();
        updateRecyclerView(0, PAGE_COUNT);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 500);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG,"onStart mList2.size()->"+mList2.size()+" onStart mList.size()->"+mList.size());
        if (mList.size() != 0 | mList2.size() != 0){
            Log.e(TAG,"onStart clearbefore->"+order_status);
            mList.clear();
            mList2.clear();
            location.clear();
            createtime.clear();
            storagesize.clear();
            order_status.clear();
            getHas_pay_fare.clear();
            storage_order_code.clear();
            has_overtime.clear();
            type.clear();
            orderAdapter.resetDatas();
            orderUnDoneAdapter.resetDatas();
        }
        Log.e(TAG,"onStart order_status->"+order_status);
        new Thread(postRun).start();
    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context,OrderActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
