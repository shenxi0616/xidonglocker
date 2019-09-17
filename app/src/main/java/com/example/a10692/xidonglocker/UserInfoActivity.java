package com.example.a10692.xidonglocker;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import adapter.MyAdapter;
import model.ItemBean;

public class UserInfoActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<ItemBean> mList=new ArrayList<>();
    private MyAdapter adapter;
    private String title[];
    private String title_data[];
    private String uname;
    private String nickName;
    private String account;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        StatusBarUtil.setTransparent(UserInfoActivity.this);//设置沉浸式状态栏
        StatusBarUtil.setLightMode(UserInfoActivity.this);//设置状态栏字体颜色
        init();
        initRecyclerView();

        }

    @Override
    protected void onStart() {
        super.onStart();
        mList.clear();
        init();
        initRecyclerView();

    }

    private void init() {

        SharedPreferences sp = getSharedPreferences("login_info", MODE_PRIVATE);
        uname = sp.getString("account", null);
        nickName = sp.getString("nickname",null);
        account = sp.getString("account",null);
        TextView username = findViewById(R.id.user_name);
        username.setText(uname);
        title = new String[]{"昵称","手机号","微信","QQ","身份认证","退出账号"};
        title_data = new String[]{nickName,account,"立即绑定","立即绑定","未认证",""};
    }

    private void initRecyclerView() {
            initItemBean();//初始化item数据
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new MyAdapter(mList);
            recyclerView.setAdapter(adapter);
        }
    private void initItemBean() {
        ItemBean one;
        for(int i=0;i<5;i++){
            one = new ItemBean(R.drawable.next, title[i],title_data[i],0);
            mList.add(one);
        }
        one= new ItemBean(R.drawable.next, title[5],title_data[5],1);
        mList.add(one);
    }

    public void btn_back(View view) {
        finish();
    }
}
