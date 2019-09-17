package com.example.a10692.xidonglocker.InfoChange;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10692.xidonglocker.CheckingActivity;
import com.example.a10692.xidonglocker.R;
import com.jaeger.library.StatusBarUtil;

import view.ClearEditText;

import static com.baidu.mapapi.BMapManager.getContext;

public class ChangePhoneActivity extends AppCompatActivity {

    private ClearEditText mphone,ve_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone);
        StatusBarUtil.setTransparent(ChangePhoneActivity.this);//设置沉浸式状态栏
        StatusBarUtil.setLightMode(ChangePhoneActivity.this);//设置状态栏字体颜色
        TextView tv_save = findViewById(R.id.tv_save);
        Toolbar toolbar = findViewById(R.id.user_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView textView = toolbar.findViewById(R.id.myTitle);
        textView.setText("修改手机号");
        //修改输入框提示语
        mphone = findViewById(R.id.mphone);
        ve_code = findViewById(R.id.ve_code);
        mphone.getmEdittext().setHint("输入姓名");
        ve_code.getmEdittext().setHint("输入验证码");
        //设置右上角的点击事件保存昵称
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"this"+mphone.getmEdittext().getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });

    }
    public static void actionStart(Context context,String data){
        Intent intent = new Intent(context, ChangePhoneActivity.class);
        intent.putExtra("phone",data);
        context.startActivity(intent);
    }
    public static void actionStart(Context context){
        Intent intent = new Intent(context, ChangePhoneActivity.class);
        context.startActivity(intent);
    }
}
