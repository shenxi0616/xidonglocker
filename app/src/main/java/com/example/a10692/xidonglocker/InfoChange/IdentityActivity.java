package com.example.a10692.xidonglocker.InfoChange;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a10692.xidonglocker.R;
import com.jaeger.library.StatusBarUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import view.ClearEditText;

import static com.baidu.mapapi.BMapManager.getContext;

public class IdentityActivity extends AppCompatActivity {
    public static final int POSITIVE_PHOTO = 1;
    public static final int NEGATIVE_PHOTO = 2;
    private ClearEditText user_real_name,id_card;
    private Button btn_positive,btn_negative;
    private ImageView img_positive,img_negative;
    private Uri positiveUri,negativeUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity);
        StatusBarUtil.setTransparent(IdentityActivity.this);//设置沉浸式状态栏
        StatusBarUtil.setLightMode(IdentityActivity.this);//设置状态栏字体颜色
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView textView = toolbar.findViewById(R.id.myTitle);
        textView.setText("身份认证");
        //修改输入框提示语
        user_real_name = findViewById(R.id.user_real_name);
        id_card = findViewById(R.id.id_card);
        user_real_name.getmEdittext().setHint("持证人姓名");
        id_card.getmEdittext().setHint("身份证号码");
        init();
    }

    private void init() {
//        btn_negative = findViewById(R.id.negative_card);
//        btn_positive = findViewById(R.id.positive_card);
        img_negative = findViewById(R.id.img_negative);
        img_positive = findViewById(R.id.img_positive);
        img_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File positiveImage = new File(getExternalCacheDir(),"positiveImage.jpg");
                try {
                    if(positiveImage.exists()){
                        positiveImage.exists();
                    }
                    positiveImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 24){
                    positiveUri = FileProvider.getUriForFile(IdentityActivity.this,
                            "com.example.a10692.xidonglocker.InfoChange",positiveImage);
                }else {
                    positiveUri = Uri.fromFile(positiveImage);
                }
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,positiveUri);
                startActivityForResult(intent,POSITIVE_PHOTO);
            }
        });
        img_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File negativeImage = new File(getExternalCacheDir(),"negativeImage.jpg");
                try {
                    if(negativeImage.exists()){
                        negativeImage.exists();
                    }
                    negativeImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= 24){
                    negativeUri = FileProvider.getUriForFile(IdentityActivity.this,
                            "com.example.a10692.xidonglocker.InfoChange",negativeImage);
                }else {
                    negativeUri = Uri.fromFile(negativeImage);
                }
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,negativeUri);
                startActivityForResult(intent,NEGATIVE_PHOTO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case POSITIVE_PHOTO:
                if (resultCode == RESULT_OK){
                    try {
                        //显示拍照的照片
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(positiveUri));
                        img_positive.setImageBitmap(bitmap);
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            case NEGATIVE_PHOTO:
                if (resultCode == RESULT_OK){
                    try {
                        //显示拍照的照片
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(negativeUri));
                        img_negative.setImageBitmap(bitmap);
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context, IdentityActivity.class);
        context.startActivity(intent);
    }
}
