package com.example.a10692.xidonglocker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;

public class FeedBackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        initHead();//设置头部
    }

    private void initHead() {
        StatusBarUtil.setTransparent(FeedBackActivity.this);//设置沉浸式状态栏
        StatusBarUtil.setLightMode(FeedBackActivity.this);//设置状态栏字体颜色

        //设置头部
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView textView = toolbar.findViewById(R.id.myTitle);
        textView.setText("意见反馈");
    }

    public void feedBackToast(View view) {
        Toast.makeText(FeedBackActivity.this,"此功能尚未完善",Toast.LENGTH_SHORT).show();
    }
    public static void actionStart(Context context){
        Intent intent = new Intent(context,FeedBackActivity.class);
        context.startActivity(intent);
    }
}
