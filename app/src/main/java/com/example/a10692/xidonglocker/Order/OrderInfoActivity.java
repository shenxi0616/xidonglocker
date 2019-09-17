package com.example.a10692.xidonglocker.Order;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10692.xidonglocker.CreateOrderActivity;
import com.example.a10692.xidonglocker.OrderActivity;
import com.example.a10692.xidonglocker.R;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import adapter.ExPandableListViewAdapter;
import model.ChildrenData;
import model.FatherData;
import model.ItemOrder;
import model.Storage;
import model.StorageDetail;
import okhttp3.Call;
import okhttp3.Response;
import view.HttpRequest;
import view.HttpUtil;

public class OrderInfoActivity extends AppCompatActivity {
    private ExpandableListView myExpandableListView;
    private ExPandableListViewAdapter adapter;
    private ArrayList<FatherData> datas;
    private  String[][] str_info = new String[][]{{}};
    private String[] str_title = new String[]{};
    private String[] str_coupon = new String[]{};
    private String[] history_time;
    private String[] history;
    private String[] str_overtime;
    private String[] overtime;
    private TextView status,type,fee,use_time;
    private LinearLayout linear_pay,linear_item,linear_btn;
    private Button btn_pay;

    private RadioGroup gender;
    private RadioButton rbtn_wexin;
    private RadioButton rbtn_zhifubao;
    private RadioButton rbtn_quotas;
    private String TAG =OrderInfoActivity.class.getCanonicalName();
    private SharedPreferences sp ;
    private ItemOrder itemOrder;

    private String pre_end_time;
    private String start_time;
    private String getOver_time;
    private String over_fare;
    private String now_storage_code;
    private ArrayList<String> operation_time = new ArrayList<String>();
    private ArrayList<String> operation = new ArrayList<String>();
    private int pay_code = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);
        StatusBarUtil.setTransparent(OrderInfoActivity.this);//设置沉浸式状态栏
        StatusBarUtil.setLightMode(OrderInfoActivity.this);//设置状态栏字体颜色
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView textView = toolbar.findViewById(R.id.myTitle);
        textView.setText("订单详情");

        sp = getSharedPreferences("login_info", MODE_PRIVATE);
//        initView();
//        setData();
//        setAdapter();
        requestPost();
    }

    public static void actionStart(Context context, ItemOrder itemOrder){
        Intent intent = new Intent(context, OrderInfoActivity.class);
        intent.putExtra("itemOrder",itemOrder);
        context.startActivity(intent);
    }
    private void requestPost() {
        try {
            String baseUrl = new HttpRequest().baseUrlGetStorageDetail;


            //使用okhttp3与数据库进行连接
            Log.e(TAG,"token-->"+sp.getString("token",null));
            itemOrder = (ItemOrder) getIntent().getSerializableExtra("itemOrder");//获取上个界面传过来的对象类型
            HttpUtil.sendOkHttpRequestOrderinfo(baseUrl,itemOrder.getId(),sp.getString("token",null),new okhttp3.Callback(){
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(OrderInfoActivity.this,"连接失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String responseResult = response.body().string();
                    Log.e(TAG,"responseResult-->>"+responseResult);
                    parseJSONWITHJSONObject(responseResult);
                    Log.e(TAG,"pre_end_time1-->>"+pre_end_time);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initView();
                            setData();
                            setAdapter();
                        }
                    });
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
    //处理发送储物柜码后的返回结果
    private void parseJSONWITHJSONObject(String jsondata) {
//        jsondata = "{\"code\":0,\"msg\":\"查找成功\",\"data\":[{\"user_code\":49,\"username\":\"5\",\"nickname\":\"1\",\"account\":\"666\",\"password\":\"123\",\"identify\":\"131\",\"identification\":0,\"strorage_number\":0,\"coupun_number\":0,\"register_time\":\"2019-07-27 11:04:37.0\",\"binding\":\"8\",\"email\":\"336\",\"profile_photo_url\":\"10\",\"balance\":0.0}]}";
//        jsondata = "{\"code\":0,\"msg\":\"查找成功\",\"data\":[{\"now_storage_code\":\"12345\",\"storage_code\":\"321122\",\"son_storage_code\":\"1222\",\"location\":\"佛山\",\"status\":0}]}";
        StorageDetail storageDetail = null;
        try {
            Gson gson = new Gson();
            storageDetail =gson.fromJson(jsondata,StorageDetail.class);
            Log.e(TAG,"pre_end_time json-->>"+storageDetail.getData().getOrderDetail().getPre_end_time());
            Log.e(TAG,"start_time  json-->>"+storageDetail.getData().getOrderDetail().getStart_time());
            pre_end_time = storageDetail.getData().getOrderDetail().getPre_end_time();
            if (storageDetail.getData().getOrderDetail().getStart_time()!=null){
                start_time = storageDetail.getData().getOrderDetail().getStart_time();
            }
            if (storageDetail.getData().getOverDetail() != null){
                getOver_time = storageDetail.getData().getOverDetail().getOver_time()+"";
                over_fare = storageDetail.getData().getOverDetail().getOver_fare()+"";
            }
            for (int i = 0; i<storageDetail.getData().getPutRecord().size();i++){
                Log.e(TAG,"getOperation()-->>"+storageDetail.getData().getPutRecord().get(i).getOperation());
                Log.e(TAG,"getOperation_time()-->>"+storageDetail.getData().getPutRecord().get(i).getOperation_time());
                operation_time.add(storageDetail.getData().getPutRecord().get(i).getOperation_time());
                operation.add(storageDetail.getData().getPutRecord().get(i).getOperation());
            }
            now_storage_code = storageDetail.getData().getOrderDetail().getNow_storage_code();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // 初始化控件
    private void initView() {
        myExpandableListView = (ExpandableListView) findViewById(R.id.alarm_clock_expandablelist);
        // 设置ExpandableListView的监听事件
        // 设置一级item点击的监听器
        myExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
//                Toast.makeText(OrderInfoActivity.this, datas.get(arg2).getTitle(), Toast.LENGTH_LONG).show();
                return false;
            }
        });

        // 设置二级item点击的监听器，同时在Adapter中设置isChildSelectable返回值true，同时二级列表布局中控件不能设置点击效果
        myExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView arg0, View arg1, int arg2, int arg3, long arg4) {
                // TODO Auto-generated method stub
                Toast.makeText(OrderInfoActivity.this, datas.get(arg2).getList().get(arg3).getTitle(), Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    /**
     * 自定义setAdapter
     */
    private void setAdapter() {
        if (adapter == null) {
            adapter = new ExPandableListViewAdapter(this, datas);
            myExpandableListView.setAdapter(adapter);
        } else {
            adapter.flashData(datas);
        }
    }

    //将String类型的时间转化为date型，精确到ms
    public static Date parseServerTime(String serverTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        Date date = null;
        try {
            date = sdf.parse(serverTime);
        } catch (Exception e) {
        }
        return date;
    }


    // 定义数据
    private void setData() {
//        requestPost();
        String[] info = new String[]{};
        status = findViewById(R.id.status);
        type = findViewById(R.id.type);
        fee = findViewById(R.id.fee);
        use_time = findViewById(R.id.use_time);
        fee.setText(itemOrder.getPaid());
        float time = 0;
        //计算持续时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//将获取手机当前时间
        long difftime = curDate.getTime();
        if (itemOrder.getStart_time() != null){
            Log.e(TAG,"curDate.getTime()-->"+curDate.getTime());
            difftime = curDate.getTime() - parseServerTime(itemOrder.getStart_time()).getTime();
        }

        if (itemOrder.getStatus() == 1){
            try {
                time = (Float.parseFloat(itemOrder.getPaid()) * 2 / 3 );
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            use_time.setText(time+"小时");
            status.setText("进行中");
            linear_btn = findViewById(R.id.linear_btn);
            linear_btn.setVisibility(View.VISIBLE);
            btn_pay = findViewById(R.id.btn_exit);
            btn_pay.setText("结束订单");
            btn_pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToastInThread(OrderInfoActivity.this,"结束订单");
                    requestPostOrderFinish();
                }
            });
        }else if (itemOrder.getStatus() == 0){
            //修改状态为待支付
            status.setText("待支付");
            fee.setText(over_fare+"");
            //将支付选项显示出来
            btn_pay = findViewById(R.id.btn_exit);
            linear_pay = findViewById(R.id.linear_pay);
            linear_pay.setVisibility(View.VISIBLE);
            linear_btn = findViewById(R.id.linear_btn);
            linear_btn.setVisibility(View.VISIBLE);
            Button button = findViewById(R.id.btn_exit);
            button.setText("确认支付");

            gender = findViewById(R.id.radioGroup_gender);
            rbtn_zhifubao = findViewById(R.id.rbtn_zhifubao);
            rbtn_wexin = findViewById(R.id.rbtn_wexin);
            rbtn_quotas = findViewById(R.id.rbtn_quotas);
            linear_item = findViewById(R.id.item);
            try {
                time = Float.parseFloat(getOver_time)+(Float.parseFloat(itemOrder.getPaid()) * 2 / 3 );
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            use_time.setText(time+"小时");
            gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                    switch (checkedId) {
                        case R.id.rbtn_quotas:
                            //点击执行逻辑
                            pay_code = 1;
                            linear_item.setVisibility(View.GONE);
                            break;
                        case R.id.rbtn_wexin:
                            //点击执行逻辑
                            linear_item.setVisibility(View.VISIBLE);
                            break;
                        case R.id.rbtn_zhifubao:
                            //点击执行逻辑
                            linear_item.setVisibility(View.VISIBLE);
                            break;
                        default:
                            break;
                    }
                }
            });

            btn_pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //调用支付接口
                    requestPostPayOrder(OrderInfoActivity.this,sp.getString("token",null),itemOrder.getId(),"",over_fare);
                    Log.e(TAG,"调用接口");
                }
            });

        }else {
            status.setText("已完成");
//            long diff_time = parseServerTime(itemOrder.getEnd_time()).getTime() - parseServerTime(itemOrder.getStart_time()).getTime();
            try {
                if ( getOver_time != null){
                    time = Float.parseFloat(getOver_time)+(Float.parseFloat(itemOrder.getPaid()) * 2 / 3 );
                    fee.setText((Float.parseFloat(over_fare)+Float.parseFloat(itemOrder.getPaid()))+"");
                }else {
                    time =(Float.parseFloat(itemOrder.getPaid()) * 2 / 3 );
                }
                use_time.setText(time+"小时");
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        //设置下拉框数据
        //判断是否超时
        if (itemOrder.getOvertime() != 1) {
            str_title = new String[]{"订单详情","存取记录"};
            history = new String[]{"开柜使用","关闭柜门","中途开柜","关闭柜门","支付费用","使用结束"};
            history_time = new String[]{"2019-08-03 10:13:16","2019-08-03 10:13:16","2019-08-03 10:13:16","2019-08-03 10:13:16","2019-08-03 10:13:16","2019-08-03 10:13:16"};
            str_info = new String[][]{{"创建时间", "租用地址","起始时间","预结时间", "箱柜类型","已付金额","订单编号"},history_time};
            type.setText("使用储物柜");
            info = new String[]{itemOrder.getCreate_time(),itemOrder.getAddress(),start_time,pre_end_time,itemOrder.getBoxsize(),itemOrder.getPaid(),itemOrder.getId()+""};
            Log.e(TAG,"pre_end_time-->>"+pre_end_time);
        }else{
            Log.e(TAG,"getOver_time-->>"+getOver_time+"over_fare-->>"+over_fare);
            str_title = new String[]{"订单详情","存取记录","超时详情"};
            history = new String[]{"开柜使用","关闭柜门","中途开柜","关闭柜门","支付费用","使用结束"};
            str_overtime = new String[]{"超出时长","超出费用"};
            overtime = new String[]{getOver_time,over_fare}; //超时的数据,在该页面请求网络连接
            history_time = new String[]{"2019-08-03 10:13:16","2019-08-03 10:13:16","2019-08-03 10:13:16","2019-08-03 10:13:16","2019-08-03 10:13:16","2019-08-03 10:13:16"};
            str_info = new String[][]{{"创建时间", "租用地址","起始时间","预结时间", "箱柜类型","已付金额","订单编号"} ,history_time,str_overtime};
            type.setText("充值");
            info = new String[]{itemOrder.getCreate_time(),itemOrder.getAddress(),start_time,pre_end_time,itemOrder.getBoxsize(),itemOrder.getPaid(),itemOrder.getId()+""};
        }
        if (datas == null) {
            datas = new ArrayList<>();
        }


//        数据填充逻辑有问题!!!!!!!!!!!!!!!!!!!!!
        // 一级列表中的数据

        for (int i = 0; i < str_title.length; i++) {
            FatherData fatherData = new FatherData();
            fatherData.setTitle(str_title[i]);
            // 二级列表中的数据
            ArrayList<ChildrenData> itemList = new ArrayList<>();
//            for (int j = 0; j < str_info[i].length; j++) {
//                    ChildrenData childrenData = new ChildrenData();
//                    if (itemOrder.getStatus() == 1 && str_title[i].equals("超时详情")){//判断是否可以选择优惠卷，更改子项布局类型
//                        childrenData.setTitle(str_info[i][j]);
//                        childrenData.setDesc(overtime[j]);
//                    }else {
//                        childrenData.setType(0);
//                        childrenData.setTitle(str_info[i][j]);
//                        if(i == 0){
//                            childrenData.setDesc(info[j]);
//                        }else if (i == 1&operation_time.size()!= 0){
//                            childrenData.setTitle(operation_time.get(j));
//                            childrenData.setDesc(operation.get(j));
//                        }
//
//                    }
//                    itemList.add(childrenData);
//            }
//            ChildrenData childrenData = new ChildrenData();
            if (i == 0){
                for (int j = 0; j < str_info[0].length; j++) {
                    ChildrenData childrenData = new ChildrenData();
                    Log.e(TAG,"str_info[0]["+j+"]-->>"+str_info[0][j]);
                    childrenData.setTitle(str_info[0][j]);
                    childrenData.setDesc(info[j]);
                    itemList.add(childrenData);
                }
            }else if (i == 1 & operation_time.size()!= 0){
                for (int j = 0; j < operation.size(); j++){
                    ChildrenData childrenData = new ChildrenData();
                    childrenData.setTitle(operation_time.get(j));
                    childrenData.setDesc(operation.get(j));
                    itemList.add(childrenData);
                }
            }else if (i == 2){
                for (int j = 0; j < overtime.length; j++){
                    Log.e(TAG,"start_time3-->>"+str_overtime[j]+"overtime[j]->"+overtime[j]);
                    ChildrenData childrenData = new ChildrenData();
                    childrenData.setTitle(str_overtime[j]);
                    childrenData.setDesc(overtime[j]);
                    itemList.add(childrenData);
                }
            }
            fatherData.setList(itemList);
            datas.add(fatherData);
        }
    }

    //结束订单
    private void requestPostOrderFinish() {
        try {
//            10.4.123.236
            String baseUrl = new HttpRequest().baseUrlEndUseStorage;

            //使用okhttp3与数据库进行连接
            Log.e(TAG,"token-->"+sp.getString("token",null));
            HttpUtil.sendOkHttpRequestOrderFinish(baseUrl,itemOrder.getId(),now_storage_code,sp.getString("token",null),new okhttp3.Callback(){
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(OrderInfoActivity.this,"连接失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String responseResult = response.body().string();
                    Log.e(TAG,"responseResult-->>"+responseResult);
                    parseJSONWITHJSONObjectFinish(responseResult);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    private void parseJSONWITHJSONObjectFinish(String responseResult) {
        StorageDetail storageDetail = null;
        try {
            Gson gson = new Gson();
            storageDetail =gson.fromJson(responseResult,StorageDetail.class);
            if (storageDetail.getMsg().equals("箱子已打开")){
                showToastInThread(OrderInfoActivity.this,"结束订单");
                finish();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //调用支付订单接口
    public void requestPostPayOrder(final Context context, final String token, final String storage_order_code, final String payway,final String getHas_pay_fare) {
        try {
//            10.4.123.236
            String baseUrl = new HttpRequest().basePayStoreLockerOrder;
            //使用okhttp3与数据库进行连接
            HttpUtil.sendOkHttpReponsePayOrder(baseUrl,storage_order_code,payway,getHas_pay_fare,token,new okhttp3.Callback(){
                @Override
                public void onFailure(Call call, IOException e) {
                    Toast.makeText(context,"连接失败",Toast.LENGTH_SHORT).show();
                    Log.e(TAG,"连接失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseResult = response.body().string();
                    Log.e(TAG,"responseResult  -->"+storage_order_code+","+payway+","+token);
                    Log.e(TAG,"responseResult-->"+responseResult);
                    //支付接口返回的处理进行处理
                    String code = null;
                    try {
                        JSONObject jsonObject = new JSONObject(responseResult);
                        code = jsonObject.getString("code");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (code.equals("0")){
                        showToastInThread(OrderInfoActivity.this,"扣款成功");
                        pay_code = 1;
                        finish();
                    }else {
                        showToastInThread(OrderInfoActivity.this,"扣款失败");
//                        requestPostDeleteOrder(CreateOrderActivity.this,token,storage_oredr_code);
                    }
                    Log.e(TAG,"code-->>"+code);
//                    parseJSONWITHJSONObject(responseResult);
//                    CreateOrderActivity.actionStart(context,responseResult);
//                  responseResult = "{"code":0,"msg":"生成储物柜订单","data":{"storage_order_code":"024461509320","now_storage_code":"12345","start_time":null,"end_time":null,"pre_end_time":"2019-08-31 17:09:34.0","create_time":"2019-08-31 15:09:34.0","order_status":0,"has_overtime":null,"account":"17765602446","has_pay_fare":3.0}}"
                }
            });

        } catch (Exception e) {
        }
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
}

