package com.example.a10692.xidonglocker.MyWallet;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.a10692.xidonglocker.AboutActivity;
import com.example.a10692.xidonglocker.R;
import com.jaeger.library.StatusBarUtil;

public class depositActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        StatusBarUtil.setTransparent(depositActivity.this);//设置沉浸式状态栏
        StatusBarUtil.setLightMode(depositActivity.this);//设置状态栏字体颜色

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView textView = toolbar.findViewById(R.id.myTitle);
        textView.setText("缴纳押金");
    }
    public static void actionStart(Context context){
        Intent intent = new Intent(context, depositActivity.class);
        context.startActivity(intent);
    }
}