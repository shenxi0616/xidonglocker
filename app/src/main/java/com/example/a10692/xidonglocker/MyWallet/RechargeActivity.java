package com.example.a10692.xidonglocker.MyWallet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10692.xidonglocker.InfoChange.ChangeNameActivity;
import com.example.a10692.xidonglocker.MyWalletActivity;
import com.example.a10692.xidonglocker.R;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;

public class RechargeActivity extends AppCompatActivity {
    int last_btn = 0;
    RadioButton radioButton;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        StatusBarUtil.setTransparent(RechargeActivity.this);//设置沉浸式状态栏
        StatusBarUtil.setLightMode(RechargeActivity.this);//设置状态栏字体颜色
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView textView = toolbar.findViewById(R.id.myTitle);
        textView.setText("充值余额");
//        init();
    }

    private void init() {
//        Drawable drawable_weixin = getResources().getDrawable(R.drawable.weixin); //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
//        drawable_weixin.setBounds(0, 0, 100, 100);
//        radioButton =(RadioButton)findViewById(R.id.rbtn_wexin); //设置图片在文字的哪个方向
//        radioButton.setCompoundDrawables(drawable_weixin, null, null, null);
//        Drawable drawable_zhifubao = getResources().getDrawable(R.drawable.zhifubao); //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
//        drawable_zhifubao.setBounds(0, 0, 100, 100);
//        radioButton =(RadioButton)findViewById(R.id.rbtn_zhifubao); //设置图片在文字的哪个方向
//        radioButton.setCompoundDrawables(drawable_zhifubao, null, null, null);
    }


    public static void actionStart(Context context){
        Intent intent = new Intent(context, RechargeActivity.class);
        context.startActivity(intent);
    }

//    设置按钮点击变色事件
    @SuppressLint("ResourceAsColor")
    public void btn_recharge(View view) {
//        Toast.makeText(RechargeActivity.this,"点击了"+ view.getId()+"按钮"+last_btn,Toast.LENGTH_SHORT).show();
        if (view.getId() != last_btn){
            //setBackgroundColor无法使用自定义颜色
            view.setBackgroundResource(R.color.btnChecking);
            if (last_btn != 0){
//                Toast.makeText(RechargeActivity.this,"点击了"+findViewById(last_btn)+"按钮",Toast.LENGTH_SHORT).show();
                findViewById(last_btn).setBackgroundColor(Color.WHITE);
            }
            last_btn = view.getId();
        }else {

        }
    }
}
