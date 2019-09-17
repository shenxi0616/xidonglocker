package com.example.a10692.xidonglocker.MyCard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.a10692.xidonglocker.R;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import adapter.CardAdapter;
import model.ItemCard;

public class GetCouponActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private List<ItemCard> mList=new ArrayList<>();
    private TextView textView;
    private int[] title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_coupon);
        StatusBarUtil.setTransparent(GetCouponActivity.this);//设置沉浸式状态栏
        StatusBarUtil.setLightMode(GetCouponActivity.this);//设置状态栏字体颜色
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        textView  = toolbar.findViewById(R.id.myTitle);
        textView.setText("领取优惠卷");
        init();
        initRecyclerView();
    }
    private void init() {
        title = new int[]{R.drawable.coupon_backgroud,R.drawable.coupon_backgroud,R.drawable.coupon_backgroud,R.drawable.coupon_backgroud};
    }
    private void initRecyclerView() {
        initItemBean();//初始化item数据
        recyclerView = (RecyclerView) findViewById(R.id.recycler_coupon);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        cardAdapter = new CardAdapter(mList);
        recyclerView.setAdapter(cardAdapter);


    }
    private void initItemBean() {
        for (int i = 0; i < title.length; i++) {
            ItemCard one = new ItemCard(title[i]);
            mList.add(one);
        }
    }
    public static void actionStart(Context context){
        Intent intent = new Intent(context,GetCouponActivity.class);
        context.startActivity(intent);

    }
}
