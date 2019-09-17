package com.example.a10692.xidonglocker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jaeger.library.StatusBarUtil;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Data;
import model.User;
import okhttp3.Call;
import okhttp3.Response;
import view.ClearEditText;
import view.HttpRequest;
import view.HttpUtil;

public class LoginActivity extends AppCompatActivity {
    private String phonenum;
    private ClearEditText editText;
    Button btn_next;
    String TAG = MainActivity.class.getCanonicalName();
    private HashMap<String, String> stringHashMap;

    // 声明SharedPreferences对象
    private SharedPreferences sp;
    // 声明SharedPreferences编辑器对象
    private SharedPreferences.Editor editor;
    // 声明token
    private String token;
    private String token_telphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBarUtil.setTransparent(LoginActivity.this);//设置沉浸式状态栏
        StatusBarUtil.setLightMode(LoginActivity.this);//设置状态栏字体颜色
        editText = findViewById(R.id.login_phone);
        btn_next = findViewById(R.id.btn_next);
        stringHashMap = new HashMap<>();
        setOnFocusChangeErrMsg(editText.getmEdittext(), "phone", "手机号格式不正确");

    }


//    当输入账号FocusChange时，校验账号是否是中国大陆手机号

    private void setOnFocusChangeErrMsg(final EditText editText, final String inputType, final String errMsg) {
        editText.setOnFocusChangeListener(
                new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        String inputStr = editText.getText().toString();
                        if (!hasFocus) {
                            if (inputType == "phone") {
                                if (isTelphoneValid(inputStr)) {
                                    editText.setError(null);
                                } else {
                                    editText.setError(errMsg);
                                }
                            }
                        }
                    }
                }
        );
    }

    // 校验账号不能为空且必须是中国大陆手机号（宽松模式匹配）
    private boolean isTelphoneValid(String account) {
        if (account == null) {
            return false;
        }
        // 首位为1, 第二位为3-9, 剩下九位为 0-9, 共11位数字
        String pattern = "^[1]([3-9])[0-9]{9}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(account);
        return m.matches();
    }

    public void loginPOST(View view) {
        stringHashMap.put("account", editText.getmEdittext().getText().toString());
    // 让密码输入框失去焦点,触发setOnFocusChangeErrMsg方法
        editText.clearFocus();
        String account = editText.getmEdittext().getText().toString();
    // 发送URL请求之前,先进行校验
        if (isTelphoneValid(account)) {
            new Thread(postRun).start();
        }
    }
    /**
     * post请求线程
     */
    Runnable postRun = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            requestPost(stringHashMap);

        }
    };
    /**
     * post提交数据
     *
     * @param paramsMap
     */
    private void requestPost(HashMap<String, String> paramsMap) {
        try {
            String baseUrl3 = "http://172.20.10.9:8080/locker/creatInfoCode/";
            String baseUrl = "http://192.168.43.6:8080/locker/creatInfoCode/";
            String baseUrl2 = "http://10.4.123.236:8080/locker/creatInfoCode/";
            baseUrl = new HttpRequest().baseUrlLogin;
            //合成参数
            StringBuilder tempParams = new StringBuilder();
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos >0) {
                    tempParams.append("&");
                }
                phonenum = URLEncoder.encode(paramsMap.get(key),"utf-8");
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                pos++;
            }
            final String params = tempParams.toString();
            Log.e(TAG,"params--post-->>"+params);
            Log.e(TAG,"params--post-->>"+phonenum);

            //使用okhttp3与数据库进行连接
            HttpUtil.sendOkHttpRequest(baseUrl,phonenum,new okhttp3.Callback(){
                @Override
                public void onFailure(Call call, IOException e) {
                    showToastInThread(LoginActivity.this,"连接失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseResult = response.body().string();
                    Log.e(TAG,"responseResult-->>"+responseResult);

                    if (parseJSONWITHJSONObject(responseResult).equals("用户被禁用")){
                        showToastInThread(LoginActivity.this,"用户被禁用");
                    }else {
                        Intent intent = new Intent(LoginActivity.this, CheckingActivity.class);
                        intent.putExtra("tel", phonenum);
                        startActivity(intent);
                        // 登录成功后，登录界面就没必要占据资源了
                        finish();
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
//对返回的数据进行处理
    private String parseJSONWITHJSONObject(String jsondata) {
//        jsondata = "{\"code\":0,\"msg\":\"查找成功\",\"data\":[{\"user_code\":49,\"username\":\"5\",\"nickname\":\"1\",\"account\":\"666\",\"password\":\"123\",\"identify\":\"131\",\"identification\":0,\"strorage_number\":0,\"coupun_number\":0,\"register_time\":\"2019-07-27 11:04:37.0\",\"binding\":\"8\",\"email\":\"336\",\"profile_photo_url\":\"10\",\"balance\":0.0}]}";
        String code = null;

        try {
            JSONObject jsonObject = new JSONObject(jsondata);
            code = jsonObject.getString("msg");
            Log.e(TAG,"code-->>"+code);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }


    public void btn_finish(View view) {
        finish();
    }
}
