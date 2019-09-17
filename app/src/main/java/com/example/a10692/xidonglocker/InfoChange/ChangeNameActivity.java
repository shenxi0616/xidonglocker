package com.example.a10692.xidonglocker.InfoChange;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10692.xidonglocker.CheckingActivity;
import com.example.a10692.xidonglocker.LoginActivity;
import com.example.a10692.xidonglocker.OrderActivity;
import com.example.a10692.xidonglocker.R;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;

import java.io.IOException;

import model.Order;
import okhttp3.Call;
import okhttp3.Response;
import view.ClearEditText;
import view.HttpRequest;
import view.HttpUtil;

import static com.baidu.mapapi.BMapManager.getContext;

public class ChangeNameActivity extends AppCompatActivity {
    private ClearEditText clearEditText;
    private String TAG = ChangeNameActivity.class.getCanonicalName();
    private SharedPreferences sp;
    // 声明SharedPreferences编辑器对象
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);
        StatusBarUtil.setTransparent(ChangeNameActivity.this);//设置沉浸式状态栏
        StatusBarUtil.setLightMode(ChangeNameActivity.this);//设置状态栏字体颜色
        TextView tv_save = findViewById(R.id.tv_save);
        Toolbar toolbar = findViewById(R.id.user_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        sp = getSharedPreferences("login_info", MODE_PRIVATE);
        TextView textView = toolbar.findViewById(R.id.myTitle);
        textView.setText("昵称");
        //修改输入框提示语
        clearEditText = findViewById(R.id.user_name);
        clearEditText.getmEdittext().setHint("输入姓名");
        //设置右上角的点击事件保存昵称
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(postRun).start();
            }
        });
    }
    Runnable postRun = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            requestPostChangeName(ChangeNameActivity.this,sp.getString("token",null),clearEditText.getmEdittext().getText().toString());
        }
    };
    public void requestPostChangeName(final Context context,String token,String nickname) {
        try {
//            10.4.123.236

            String baseUrl = new HttpRequest().baseUrlUpdateUser;

            //使用okhttp3与数据库进行连接
            HttpUtil.sendOkHttpRequestChangeName(baseUrl,token,nickname,sp.getString("account",null),new okhttp3.Callback(){
                @Override
                public void onFailure(Call call, IOException e) {
                    Toast.makeText(context,"连接失败",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseResult = response.body().string();
                    Log.e(TAG,"responseResult-->"+responseResult);
                    if (parseJSONWITHJSONObject(responseResult).equals("用户修改成功")){
                        sp = getSharedPreferences("login_info", MODE_PRIVATE);
                        editor = sp.edit();
                        editor.putString("nickname",clearEditText.getmEdittext().getText().toString());
                        editor.commit();
                        showToastInThread(ChangeNameActivity.this,"昵称修改成功");
                    }

                }
            });

        } catch (Exception e) {
        }
    }

    private String parseJSONWITHJSONObject(String responseResult) {
        String msg = null;
        try {
            Gson gson = new Gson();
            Order order =gson.fromJson(responseResult, Order.class);
            msg = order.getMsg();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }

    public static void actionStart(Context context, String data){
        Intent intent = new Intent(context, ChangeNameActivity.class);
        intent.putExtra("name",data);
        context.startActivity(intent);
    }
    public static void actionStart(Context context){
        Intent intent = new Intent(context, ChangeNameActivity.class);
        context.startActivity(intent);
    }
    // 实现在子线程中显示Toast
    private void showToastInThread(final Context context, final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            }
        });
    }
}
