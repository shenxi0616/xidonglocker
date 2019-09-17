package com.example.a10692.xidonglocker.MyWallet;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.a10692.xidonglocker.R;
import com.example.a10692.xidonglocker.UserInfoActivity;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import adapter.DetailsAdapter;
import adapter.MyAdapter;
import model.ItemBean;
import model.ItemDetails;

public class DetailsInfoActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<ItemDetails> mList=new ArrayList<>();
    private DetailsAdapter adapter;
    private String useway[];
    private String date[];
    private String time[];
    private String str_balance[];
    private String str_expenses[];
    private double expenses[];
    private double balance[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_info);
        StatusBarUtil.setTransparent(DetailsInfoActivity.this);//设置沉浸式状态栏
        StatusBarUtil.setLightMode(DetailsInfoActivity.this);//设置状态栏字体颜色
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView textView = toolbar.findViewById(R.id.myTitle);

        init();
        initRecyclerView();
        textView.setText("交易明细");
    }

    private void initRecyclerView() {
        initItemBean();//初始化item数据
        recyclerView = (RecyclerView) findViewById(R.id.recycle_info);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DetailsAdapter(mList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));//添加分割线
    }
    private void initItemBean() {
        for (int i = 0;i < 2;i++){
            ItemDetails one = new ItemDetails(useway[i],date[i],time[i],expenses[i],balance[i]);
            mList.add(one);
        }

    }
    private void init() {
        useway = new String[]{"使用储物柜","充值"};
        date = new String[]{"2017-07-05","2017-07-10"};
        time = new String[]{"14:44","10:02"};
        expenses = new double[]{-41.0,20.0};
        balance = new double[]{102.0,102.0};


    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context,DetailsInfoActivity.class);
        context.startActivity(intent);
    }
}
