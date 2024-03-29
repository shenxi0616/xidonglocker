package com.example.a10692.xidonglocker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.User;
import okhttp3.Call;
import okhttp3.Response;
import view.HttpRequest;
import view.HttpUtil;

public class CheckingActivity extends AppCompatActivity {
    private EditText first, second, third, fourth;
    private List<EditText> mEdits = new ArrayList<EditText>();
    private Button btn_login;
    private TextView textView;
    private String code,tel;
    private EditText editText1,editText2,editText3,editText4;
    private String TAG = CheckingActivity.class.getCanonicalName();
    private HashMap<String, String> stringHashMap;
    // 声明SharedPreferences对象
    private SharedPreferences sp;
    // 声明SharedPreferences编辑器对象
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checking);
        StatusBarUtil.setTransparent(CheckingActivity.this);//设置沉浸式状态栏
        StatusBarUtil.setLightMode(CheckingActivity.this);//设置状态栏字体颜色
        Intent in = getIntent();
        tel = in.getStringExtra("tel");//取出上个页面传过来的手机号码
        stringHashMap = new HashMap<>();
        textView = findViewById(R.id.phone_get);
        textView.setText(tel);
        btn_login = findViewById(R.id.btn_login);
        init();

        //点击登陆按钮事件监听
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent in = new Intent(CheckingActivity.this,MainActivity.class);
//                startActivity(in);
                initView();//在initview获取输入的验证码和手机号
                new Thread(postRun).start();
            }
        });
    }



    Runnable postRun = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            requestPost(tel,code);
            Log.e(TAG,"tel,code-->>"+tel+","+code);

        }
    };

    private void requestPost(final String tel, String code) {
        try {
            String baseUrl = new HttpRequest().baseUrlLoginByCode;
            //使用okhttp3与数据库进行连接
            HttpUtil.sendOkHttpRequestChecking(baseUrl,tel,code,new okhttp3.Callback(){
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CheckingActivity.this,"连接失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseResult = response.body().string();
                    Log.e(TAG,"responseResult-->>"+responseResult);

                    //用gson解析返回的数据，登陆成功即跳转到主界面
                    Log.e(TAG,"Gson-->>"+parseJSONWITHJSONObject(responseResult));
                    if (parseJSONWITHJSONObject(responseResult) == 0){
                        /*
                             更新token，下次自动登录
                             真实的token值应该是一个加密字符串
                             我为了让token不为null，就随便传了一个字符串
                             这里的telphone和password每次都要重写的
                             否则无法实现修改密码
                            */
                        sp = getSharedPreferences("login_info", MODE_PRIVATE);
                        editor = sp.edit();
                        editor.putString("telphone",tel);
                        if (editor.commit()) {
                            Intent in = new Intent(CheckingActivity.this,MainActivity.class);
                            startActivity(in);

                            // 登录成功后，登录界面就没必要占据资源了
                            finish();
                        } else {
                            showToastInThread(CheckingActivity.this, "token保存失败，请重新登录");
                        }
                    }else {
                        showToastInThread(CheckingActivity.this, "登陆失败");
                    }

                }
            });

        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
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
    private int parseJSONWITHJSONObject(String jsondata) {
//        jsondata = "{\"code\":0,\"msg\":\"查找成功\",\"data\":[{\"user_code\":49,\"username\":\"5\",\"nickname\":\"1\",\"account\":\"666\",\"password\":\"123\",\"identify\":\"131\",\"identification\":0,\"strorage_number\":0,\"coupun_number\":0,\"register_time\":\"2019-07-27 11:04:37.0\",\"binding\":\"8\",\"email\":\"336\",\"profile_photo_url\":\"10\",\"balance\":0.0}]}";
//        jsondata = "{\"code\":0,\"msg\":\"新用户新增成功\",\"data\":[{\"user_code\":57,\"nickname\":\"18023175651\",\"account\":\"18023175651\",\"identification_is_identify\":0,\"register_time\":\"2019-08-27 23:58:48.0\",\"email\":null,\"profile_photo_url\":null,\"balance\":0.0,\"token\":\"72907013ac924e268b45f8983f64d02c\"}]}";

        int ucode = 3;

        try {
//            JSONArray对有大量重复类型的json object的解析时使用
//            JSONArray jsonArray = new JSONArray(jsondata);
//            for (int i = 0;i<jsonArray.length();i++){
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                String code = jsonObject.getString("code");
//                Log.e(TAG,"code-->>"+code);
//            }


//            JSONObject jsonObject = new JSONObject(jsondata);
//            code = jsonObject.getString("code");
//            Log.e(TAG,"code-->>"+code);

            Gson gson = new Gson();
            User user =gson.fromJson(jsondata,User.class);
//            code = user.getCode();
            sp = getSharedPreferences("login_info", MODE_PRIVATE);
            editor = sp.edit();
            editor.putString("token",  user.getData().get(0).getToken());
            editor.putString("account", user.getData().get(0).getAccount());
            editor.putString("nickname", user.getData().get(0).getNickname());
//            editor.putString("account",user.getData().get(0).getAccount());
            editor.commit();
            ucode = user.getCode();
            Log.e(TAG,"Account-->>"+user.getData().get(0).getAccount());
//            code = user.getUser_code();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ucode;
    }


    private void init() {
        editText1 = findViewById(R.id.edit_one);
        editText2 = findViewById(R.id.edit_two);
        editText3 = findViewById(R.id.edit_three);
        editText4 = findViewById(R.id.edit_four);
        Log.e(TAG,"onTextChanged");

        //输入框中输入数字时，让光标跳转到下一个输入框，直到最后一个


        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString().trim())){
                    editText1.clearFocus();
                    editText1.setFocusable(false);
                    editText2.setFocusable(true);
                    editText2.setFocusableInTouchMode(true);
                    editText2.requestFocus();
                }else{
                    editText1.clearFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.e(TAG,"beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e(TAG,"onTextChanged");
                if (!TextUtils.isEmpty(s.toString().trim())){
                    editText2.clearFocus();
                    editText2.setFocusable(false);
                    editText3.setFocusable(true);
                    editText3.setFocusableInTouchMode(true);
                    editText3.requestFocus();
                }else{
                    editText2.clearFocus();
                    editText1.setFocusable(true);
                    editText1.setFocusableInTouchMode(true);
                    editText1.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e(TAG,"afterTextChanged");
            }
        });
        editText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.e(TAG,"beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e(TAG,"onTextChanged");
                if (!TextUtils.isEmpty(s.toString().trim())){
                    editText3.clearFocus();
                    editText3.setFocusable(false);
                    editText4.setFocusable(true);
                    editText4.setFocusableInTouchMode(true);
                    editText4.requestFocus();
                }else{
                    editText3.clearFocus();
                    editText2.setFocusable(true);
                    editText2.setFocusableInTouchMode(true);
                    editText2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e(TAG,"afterTextChanged");
            }
        });
//        editText4.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (!TextUtils.isEmpty(s.toString().trim())){
//                    editText4.clearFocus();
//                    editText4.setFocusable(false);
//                }else{
//                    editText4.clearFocus();
//                    editText3.setFocusable(true);
//                    editText3.setFocusableInTouchMode(true);
//                    editText3.requestFocus();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });


        //结束


    }
    private void initView() {
        code = editText1.getText().toString()+""+editText2.getText().toString()+""+editText3.getText().toString()+""+editText4.getText().toString();
        Log.d(" CheckingActivity",code);
        stringHashMap.put("tel", tel);
        stringHashMap.put("code", code);
    }

}
