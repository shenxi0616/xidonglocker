package com.example.a10692.xidonglocker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        StatusBarUtil.setTransparent(AboutActivity.this);//设置沉浸式状态栏
        StatusBarUtil.setLightMode(AboutActivity.this);//设置状态栏字体颜色

        //设置头部
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView textView = toolbar.findViewById(R.id.myTitle);
        textView.setText("关于我们");


    }
    public static void actionStart(Context context){
        Intent intent = new Intent(context,AboutActivity.class);
        context.startActivity(intent);
    }
}
