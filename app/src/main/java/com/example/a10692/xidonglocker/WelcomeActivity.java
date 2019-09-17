package com.example.a10692.xidonglocker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jaeger.library.StatusBarUtil;

public class WelcomeActivity extends AppCompatActivity {
    private SharedPreferences sp;
    private String telphone;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        StatusBarUtil.setTransparent(WelcomeActivity.this);//设置沉浸式状态栏
        StatusBarUtil.setLightMode(WelcomeActivity.this);//设置状态栏字体颜色
        initData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (token != null){
                    Intent in = new Intent(WelcomeActivity.this,MainActivity.class);
//                    in.putExtra("telphone",telphone);
                    startActivity(in);
                }else {
                    Intent in = new Intent(WelcomeActivity.this,LoginActivity.class);
                    startActivity(in);
                }
                finish();
            }
        }, 2000);
    }
    private void initData() {
        sp = getSharedPreferences("login_info", MODE_PRIVATE);
        // 获取token
        token = sp.getString("token", null);
        telphone = sp.getString("telphone", null);

    }
}
