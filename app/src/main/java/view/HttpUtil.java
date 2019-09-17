package view;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {
    public static void sendOkHttpRequest(String address,String params,okhttp3.Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("tel", params);
        Request request = new Request.Builder()
                .url(address)
                .post(formBody.build())
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }
    public static void sendOkHttpRequestChecking(String address,String  params,String code,okhttp3.Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("tel",params);
        formBody.add("code",code);
        Request request = new Request.Builder()
                .url(address)
                .post(formBody.build())
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }
    public static void sendOkHttpRequestPosition(String address,String longitude,String latitude,okhttp3.Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("longitude",longitude);
        formBody.add("latitude",latitude);
        Request request = new Request.Builder()
                .url(address)
                .post(formBody.build())
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }
    public static void sendOkHttpRequestScanResult(String address,String result,String token,okhttp3.Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("token",token);
        formBody.add("now_storage_code","12345");
        Request request = new Request.Builder()
                .url(address)
                .post(formBody.build())
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }
    public static void sendOkHttpRequestOrder(String address,String token,okhttp3.Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("token",token);
        Request request = new Request.Builder()
                .url(address)
                .post(formBody.build())
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }
    //获取储物柜状态
    public static void sendOkHttpRequestOrderStart(String address,String now_storage_code,String token,okhttp3.Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("token",token);
        formBody.add("now_storage_code",now_storage_code);
        Request request = new Request.Builder()
                .url(address)
                .post(formBody.build())
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }
    //修改昵称
    public static void sendOkHttpRequestChangeName(String address,String token,String nickname,String account,okhttp3.Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("token",token);
        formBody.add("nickname",nickname);
        formBody.add("account",account);
        Request request = new Request.Builder()
                .url(address)
                .post(formBody.build())
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    //创建订单
    public static void sendOkHttpRequestOrderCreate(String address,String boxid,String pre_end_time,String token,okhttp3.Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("token",token);
        formBody.add("now_storage_code",boxid);
        formBody.add("pre_end_time",pre_end_time);
        Request request = new Request.Builder()
                .url(address)
                .post(formBody.build())
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    //订单详情
    public static void sendOkHttpRequestOrderinfo(String address,String storage_order_code,String token,okhttp3.Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("token",token);
        formBody.add("storageCode",storage_order_code);
        Request request = new Request.Builder()
                .url(address)
                .post(formBody.build())
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void sendOkHttpRequestOrderFinish(String address,String storage_order_code,String now_storage_code,String token,okhttp3.Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("token",token);
        formBody.add("storage_order_code",storage_order_code);
        formBody.add("now_storage_code",now_storage_code);
        Request request = new Request.Builder()
                .url(address)
                .post(formBody.build())
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }
    //支付接口payStoreLockerOrder
    //code成功返回0，失败返回3
    public static void sendOkHttpReponsePayOrder(String address,String storage_order_code,String payway,String has_pay_money,String token,okhttp3.Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("token",token);
        formBody.add("storage_order_code",storage_order_code);
        formBody.add("pay_method","1");
        formBody.add("has_pay_fare",has_pay_money);
        Request request = new Request.Builder()
                .url(address)
                .post(formBody.build())
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    //删除订单
    public static void sendOkHttpRequestOrderDelete(String address,String storage_order_code,String token,okhttp3.Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("token",token);
        formBody.add("storage_order_code",storage_order_code);
        Request request = new Request.Builder()
                .url(address)
                .post(formBody.build())
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    //打开储物柜
    public static void sendOkHttpRequestOpen(String address,String storage_order_code,int type,String token,okhttp3.Callback callback){
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("token",token);
        formBody.add("storage_order_code",storage_order_code);
        formBody.add("type",type+"");
        Request request = new Request.Builder()
                .url(address)
                .post(formBody.build())
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }
}
