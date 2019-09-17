package com.example.a10692.xidonglocker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.a10692.xidonglocker.MyCard.GetCouponActivity;
import com.example.a10692.xidonglocker.MyCard.PayCardActivity;
import com.example.a10692.xidonglocker.MyWallet.DetailsInfoActivity;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import adapter.CardAdapter;
import model.ItemCard;

public class MyCardActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private int[] title,title2;
    private List<ItemCard> mList=new ArrayList<>();
    private List<ItemCard> mList2=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_card);
        StatusBarUtil.setTransparent(MyCardActivity.this);//设置沉浸式状态栏
        StatusBarUtil.setLightMode(MyCardActivity.this);//设置状态栏字体颜色
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView textView = toolbar.findViewById(R.id.myTitle);
        textView.setText("我的卡卷");
        init();
        initRecyclerView();
    }
    private void init() {
        title = new int[]{R.drawable.pay_card_backgroud,R.drawable.pay_card_backgroud,R.drawable.pay_card_backgroud,R.drawable.pay_card_backgroud};
        title2 = new int[]{R.drawable.coupon_backgroud,R.drawable.coupon_backgroud,R.drawable.coupon_backgroud,R.drawable.coupon_backgroud};

    }

    private void initRecyclerView() {
        initItemBean();//初始化item数据
        for (int i = 0;i<2;i++){
            if ( i == 0 ){
                recyclerView = (RecyclerView) findViewById(R.id.recycle_card);
                cardAdapter = new CardAdapter(mList);
            }else {
                recyclerView = (RecyclerView) findViewById(R.id.recycle_coupon);
                cardAdapter = new CardAdapter(mList2);
            }
            //设置禁止recycleview滑动
            LinearLayoutManager layoutManager = new LinearLayoutManager(this){
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setAdapter(cardAdapter);
        }

    }
    private void initItemBean() {
        for (int i = 0; i < title.length; i++) {
            ItemCard one = new ItemCard(title[i]);
            mList.add(one);
        }
        for (int i = 0; i < title2.length; i++) {
            ItemCard one = new ItemCard(title2[i]);
            mList2.add(one);
        }
    }
    public static void actionStart(Context context){
        Intent intent = new Intent(context,MyCardActivity.class);
        context.startActivity(intent);
    }

    public void listen_pay_card(View view) {
        PayCardActivity.actionStart(MyCardActivity.this);
    }

    public void listen_get_coupon(View view) {
        GetCouponActivity.actionStart(MyCardActivity.this);
    }
}
