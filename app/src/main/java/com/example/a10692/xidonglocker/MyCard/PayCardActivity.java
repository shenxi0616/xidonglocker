package com.example.a10692.xidonglocker.MyCard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.a10692.xidonglocker.MyCardActivity;
import com.example.a10692.xidonglocker.MyWallet.DetailsInfoActivity;
import com.example.a10692.xidonglocker.R;
import com.jaeger.library.StatusBarUtil;

public class PayCardActivity extends AppCompatActivity {
    RadioButton btn_week,btn_mouth;
    Button btn_payment;
    TextView coupon,coupon_data,textView;
    public final int COUPONNUM = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_card);
        StatusBarUtil.setTransparent(PayCardActivity.this);//设置沉浸式状态栏
        StatusBarUtil.setLightMode(PayCardActivity.this);//设置状态栏字体颜色
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        textView  = toolbar.findViewById(R.id.myTitle);
        textView.setText("购买存放卡");

        btn_week = findViewById(R.id.week_card);
        btn_mouth = findViewById(R.id.mouth_card);

        btn_payment = findViewById(R.id.btn_exit);
        init();
    }

    private void init() {
        coupon = findViewById(R.id.coupon);
        coupon_data = findViewById(R.id.coupon_data);
        if (COUPONNUM != 0){
            coupon.setText("已使用优惠卷");
            coupon_data.setText("立减");
        }else {
            coupon.setText("不使用优惠卷");
            coupon_data.setText("立即使用");
        }
        btn_payment.setText("确认支付");
    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context,PayCardActivity.class);
        context.startActivity(intent);
    }

    public void listen_scrollview(View view) {
        if (view.getId() == R.id.week_card){
            btn_week.setTextSize(25);
            btn_mouth.setTextSize(17);
            findViewById(R.id.week_scrollView).setVisibility(View.VISIBLE);//显示周卡列表
            findViewById(R.id.mouth_scrollView).setVisibility(View.GONE);//隐藏月卡列表
        }else if (view.getId() == R.id.mouth_card){
            btn_week.setTextSize(17);
            btn_mouth.setTextSize(25);
            findViewById(R.id.mouth_scrollView).setVisibility(View.VISIBLE);//显示月卡列表
            findViewById(R.id.week_scrollView).setVisibility(View.GONE);//隐藏周卡列表
        }
    }
}
