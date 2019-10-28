package com.example.a10692.xidonglocker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10692.xidonglocker.Order.OrderInfoActivity;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import adapter.ExPandableListViewAdapter;
import model.ChildrenData;
import model.FatherData;
import model.ItemOrder;
import model.Order;
import Gson.CreateOrder;
import Gson.CreateOrderList;
import okhttp3.Call;
import okhttp3.Response;
import view.HttpRequest;
import view.HttpUtil;

public class CreateOrderActivity extends AppCompatActivity {
    private ExpandableListView myExpandableListView;
    private ExPandableListViewAdapter adapter;
    private ArrayList<FatherData> datas;
    private String TAG =CreateOrderActivity.class.getCanonicalName();
    private TextView status,type,fee,use_time;
    private String result;
    private LinearLayout linear_pay,linear_item,linear_btn;

    private RadioGroup gender;
    private RadioButton rbtn_wexin;
    private RadioButton rbtn_zhifubao;
    private RadioButton rbtn_quotas;

    private Button btn_pay;
    private  String[][] str_info = new String[][]{{}};
    private String[] str_title,info = new String[]{};
    private SharedPreferences sp;
//    private Intent in = getIntent();
    private String payway,storage_oredr_code,getHas_pay_fare,now_storage_code,pre_end_time;
    private int pay_code = 0;//判断订单是否成功扣款，没有付款即pay_code = 0就退出界面，则调用删除订单接口
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);
        StatusBarUtil.setTransparent(CreateOrderActivity.this);//设置沉浸式状态栏
        StatusBarUtil.setLightMode(CreateOrderActivity.this);//设置状态栏字体颜色
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView textView = toolbar.findViewById(R.id.myTitle);
        textView.setText("生成订单");
        init();
        inithttp();
//        initView();
//        setAdapter();
//        parseJSONWITHJSONObject("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pay_code != 1){
            requestPostDeleteOrder(CreateOrderActivity.this,sp.getString("token",null),storage_oredr_code);
        }

    }

    public static void actionStart(Context context, String now_storage_code, String location, String type, String pre_end_time){
        Intent intent = new Intent(context, CreateOrderActivity.class);
        intent.putExtra("now_storage_code",now_storage_code);
        intent.putExtra("location",location);
        intent.putExtra("type",type);
        intent.putExtra("pre_end_time",pre_end_time);
        context.startActivity(intent);
    }
    public static void actionStart(Context context, String responseResult){
        Intent intent = new Intent(context, CreateOrderActivity.class);
        intent.putExtra("responseResult",responseResult);
        context.startActivity(intent);
    }
    private void inithttp() {
        Intent intent = getIntent();
        now_storage_code =intent.getStringExtra("now_storage_code");
        pre_end_time = intent.getStringExtra("pre_end_time");

        new Thread(postRun).start();
    }
    Runnable postRun = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            requestPostCreateOrder(CreateOrderActivity.this,sp.getString("token",null),now_storage_code,pre_end_time);

        }
    };



    private void setAdapter() {
        if (adapter == null) {
            adapter = new ExPandableListViewAdapter(this, datas);
            myExpandableListView.setAdapter(adapter);
        } else {
            adapter.flashData(datas);
        }
    }
    private void init() {
        status = findViewById(R.id.status);
        type = findViewById(R.id.type);
        fee = findViewById(R.id.fee);
        use_time = findViewById(R.id.use_time);
        btn_pay = findViewById(R.id.btn_exit);
        myExpandableListView = (ExpandableListView) findViewById(R.id.alarm_clock_expandablelist);
        sp = getSharedPreferences("login_info", MODE_PRIVATE);

    }

    private void initView() {
        status.setText("待支付");
        type.setText("使用储物柜");
        use_time.setText(pre_end_time+"小时");
        fee.setText(getHas_pay_fare);
        result = getIntent().getStringExtra("result");
//        parseJSONWITHJSONObject(result);

        //设置支付选项和按钮
        linear_pay = findViewById(R.id.linear_pay);
        linear_pay.setVisibility(View.VISIBLE);
        linear_btn = findViewById(R.id.linear_btn);
        linear_btn.setVisibility(View.VISIBLE);
        //支付按钮
        Button button = findViewById(R.id.btn_exit);
        button.setText("确认支付");
        //设置选择支付方式
        gender = findViewById(R.id.radioGroup_gender);
        rbtn_zhifubao = findViewById(R.id.rbtn_zhifubao);
        rbtn_wexin = findViewById(R.id.rbtn_wexin);
        rbtn_quotas = findViewById(R.id.rbtn_quotas);
        linear_item = findViewById(R.id.item);
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用支付接口
                requestPostPayOrder(CreateOrderActivity.this,sp.getString("token",null),storage_oredr_code,payway,getHas_pay_fare);
                Log.e(TAG,"调用接口");
            }
        });

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                switch (checkedId) {
                    case R.id.rbtn_quotas:
                        //点击执行逻辑
                        payway = "0";
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
        if (datas == null) {
            datas = new ArrayList<>();
        }

        str_title = new String[]{"订单详情"};
        str_info = new String[][]{{"创建时间","租用地址","预结时间","已付金额","箱柜类型","订单编号"}};
//        info = new String[]{"1","1","1","1","1","1"};

        // 一级列表中的数据
        for (int i = 0; i < str_title.length; i++) {
            FatherData fatherData = new FatherData();
            fatherData.setTitle(str_title[i]);
            // 二级列表中的数据
            ArrayList<ChildrenData> itemList = new ArrayList<>();
            for (int j = 0; j < str_info[i].length; j++) {
//                Log.e(TAG,"str_info[i].length-->"+info[j]);
                ChildrenData childrenData = new ChildrenData();

                childrenData.setTitle(str_info[i][j]);
                childrenData.setDesc(info[j]);
                itemList.add(childrenData);
            }
            fatherData.setList(itemList);
            datas.add(fatherData);
        }
    }


    //处理订单表的返回结果
    private void parseJSONWITHJSONObject(String jsondata) {
//        jsondata = "{\"code\":0,\"msg\":\"查找成功\",\"data\":[{\"user_code\":49,\"username\":\"5\",\"nickname\":\"1\",\"account\":\"666\",\"password\":\"123\",\"identify\":\"131\",\"identification\":0,\"strorage_number\":0,\"coupun_number\":0,\"register_time\":\"2019-07-27 11:04:37.0\",\"binding\":\"8\",\"email\":\"336\",\"profile_photo_url\":\"10\",\"balance\":0.0}]}";
//        jsondata = "{\"code\":0,\"msg\":\"生成储物柜订单\",\"data\":{\"storage_order_code\":\"024461035335\",\"now_storage_code\":\"12345\",\"start_time\":null,\"end_time\":null,\"pre_end_time\":\"2019-08-30 12:35:28.0\",\"create_time\":\"2019-08-30 10:35:28.0\",\"order_status\":0,\"has_overtime\":null,\"account\":\"17765602446\",\"has_pay_fare\":3.0}}";
        int status = 3;
        Intent in = getIntent();
        try {
            Gson gson = new Gson();
            CreateOrder createOrder =gson.fromJson(jsondata, CreateOrder.class);
            Log.e(TAG,"getStorage_order_code-->>"+createOrder.getData().getStorage_order_code());
            Log.e(TAG,"getNow_storage_code-->>"+createOrder.getData().getNow_storage_code());
            Log.e(TAG,"getStart_time-->>"+createOrder.getData().getStart_time());
            Log.e(TAG,"getEnd_time-->>"+createOrder.getData().getEnd_time());
            Log.e(TAG,"getPre_end_time-->>"+createOrder.getData().getPre_end_time());
            Log.e(TAG,"getCreate_time-->>"+createOrder.getData().getCreate_time());
            Log.e(TAG,"getOrder_status-->>"+createOrder.getData().getOrder_status());
            Log.e(TAG,"getHas_overtime-->>"+createOrder.getData().getHas_overtime());
            Log.e(TAG,"getAccount-->>"+createOrder.getData().getAccount());
            Log.e(TAG,"getHas_pay_fare-->>"+createOrder.getData().getHas_pay_fare());
//            fee.setText(createOrder.getData().getHas_pay_fare()+"");
            Log.e(TAG,"token-->"+sp.getString("token",null));
            storage_oredr_code = createOrder.getData().getStorage_order_code();
            getHas_pay_fare = createOrder.getData().getHas_pay_fare()+"";
//            String str = HttpRequest.requestPostOrderinfo(CreateOrderActivity.this,sp.getString("token",null),createOrder.getData().getStorage_order_code());
//            Log.e(TAG,"infobefore");
            info = new String[]{createOrder.getData().getCreate_time(),in.getStringExtra("location"),createOrder.getData().getPre_end_time(),createOrder.getData().getHas_pay_fare()+"元",in.getStringExtra("type"),createOrder.getData().getStorage_order_code()};
//            info = new String[]{createOrder.getData().getCreate_time(),"location",createOrder.getData().getPre_end_time(),createOrder.getData().getHas_pay_fare()+"","type",createOrder.getData().getStorage_order_code()};

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //创建订单
        public void requestPostCreateOrder(final Context context,String token,String storage_oredr_code,String pre_end_time) {
        try {
//            10.4.123.236

            String baseUrl = new HttpRequest().baseUrlCreateStoreLockerOrder;
            //使用okhttp3与数据库进行连接
            HttpUtil.sendOkHttpRequestOrderCreate(baseUrl,storage_oredr_code,pre_end_time,token,new okhttp3.Callback(){
                @Override
                public void onFailure(Call call, IOException e) {
                    Toast.makeText(context,"连接失败",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseResult = response.body().string();
                    Log.e(TAG,"responseResult-->"+responseResult);
                    parseJSONWITHJSONObject(responseResult);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initView();
                            setAdapter();
                        }
                    });

//                    CreateOrderActivity.actionStart(context,responseResult);
//                  responseResult = "{"code":0,"msg":"生成储物柜订单","data":{"storage_order_code":"024461509320","now_storage_code":"12345","start_time":null,"end_time":null,"pre_end_time":"2019-08-31 17:09:34.0","create_time":"2019-08-31 15:09:34.0","order_status":0,"has_overtime":null,"account":"17765602446","has_pay_fare":3.0}}"
                }
            });

        } catch (Exception e) {
        }
    }
    //调用支付订单接口
    public void requestPostPayOrder(final Context context, final String token, final String storage_oredr_code, final String payway,final String getHas_pay_fare) {
        try {
//            10.4.123.236


            String baseUrl = new HttpRequest().baseUrlPayStoreLockerOrder;

            //使用okhttp3与数据库进行连接
            HttpUtil.sendOkHttpReponsePayOrder(baseUrl,storage_oredr_code,payway,getHas_pay_fare,token,new okhttp3.Callback(){
                @Override
                public void onFailure(Call call, IOException e) {
                    Toast.makeText(context,"连接失败",Toast.LENGTH_SHORT).show();
                    Log.e(TAG,"连接失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseResult = response.body().string();
                    Log.e(TAG,"responseResult-->"+storage_oredr_code+","+payway+","+token);
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
                        showToastInThread(CreateOrderActivity.this,"扣款成功");
                        pay_code = 1;
                        finish();
                    }else {
                        showToastInThread(CreateOrderActivity.this,"扣款失败");
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

    //删除订单接口
    public void requestPostDeleteOrder(final Context context,String token,String storage_oredr_code) {
        try {
//            10.4.123.236

            String baseUrl = new HttpRequest().baseUrlDeleteStoreLockerOrder;

            //使用okhttp3与数据库进行连接
            HttpUtil.sendOkHttpRequestOrderDelete(baseUrl,storage_oredr_code,token,new okhttp3.Callback(){
                @Override
                public void onFailure(Call call, IOException e) {
                    Toast.makeText(context,"连接失败",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseResult = response.body().string();
                    Log.e(TAG,"responseResult-->"+responseResult);
//                    parseJSONWITHJSONObject(responseResult);
                    //删除接口返回的处理进行处理
                    String code = null;
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(responseResult);
                        code = jsonObject.getString("code");
                        if (code.equals("0")){
                            Log.e(TAG,"删除订单成功-->");
                            showToastInThread(CreateOrderActivity.this,"删除订单成功");
                            finish();
                        }else{
                            Log.e(TAG,"删除订单失败-->");
                            showToastInThread(CreateOrderActivity.this,"删除订单失败");
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.e(TAG,"code-->>"+code);
                }
            });

        } catch (Exception e) {
        }
    }
    private void showToastInThread(final Context context, final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            }
        });
    }
}
