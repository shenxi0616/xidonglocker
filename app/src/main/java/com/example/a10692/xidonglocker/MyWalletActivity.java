package com.example.a10692.xidonglocker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10692.xidonglocker.MyWallet.ConPaymentActivity;
import com.example.a10692.xidonglocker.MyWallet.DetailsInfoActivity;
import com.example.a10692.xidonglocker.MyWallet.RechargeActivity;
import com.example.a10692.xidonglocker.MyWallet.depositActivity;
import com.jaeger.library.StatusBarUtil;

public class MyWalletActivity extends AppCompatActivity {

    Button btn_return;
    TextView text_details;
    RelativeLayout payment,rel_deposit;
    Button btn_recharge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        StatusBarUtil.setTransparent(MyWalletActivity.this);//设置沉浸式状态栏
        StatusBarUtil.setLightMode(MyWalletActivity.this);//设置状态栏字体颜色
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        btn_return = findViewById(R.id.back);
        text_details = findViewById(R.id.details);
        payment = findViewById(R.id.confidential_payment);
        rel_deposit = findViewById(R.id.rel_deposit);
        btn_recharge = findViewById(R.id.btn_recharge);
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void listen_deposit(View view) {
        Toast.makeText(MyWalletActivity.this,"点击了缴纳押金页面",Toast.LENGTH_SHORT).show();
        depositActivity.actionStart(MyWalletActivity.this);
    }

    public void listen_payment(View view) {
        Toast.makeText(MyWalletActivity.this,"点击了免密支付页面",Toast.LENGTH_SHORT).show();
        ConPaymentActivity.actionStart(MyWalletActivity.this);
    }

    public void listen_recharge(View view) {
        Toast.makeText(MyWalletActivity.this,"点击了充值界面",Toast.LENGTH_SHORT).show();
        RechargeActivity.actionStart(MyWalletActivity.this);
    }

    public void listen_details(View view) {
        Toast.makeText(MyWalletActivity.this,"点击了交易明细页面",Toast.LENGTH_SHORT).show();
        DetailsInfoActivity.actionStart(MyWalletActivity.this);
    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context,MyWalletActivity.class);
        context.startActivity(intent);
    }
}
