package com.example.jizhang_master;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class NavigationActivity extends AppCompatActivity {

    private Button btn;
    private ImageView image;
    private int index = 0;
    private int[] images = {R.drawable.image1,R.drawable.image2,R.drawable.image3};

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    break;
                case 200:
                    index = (index+1) % 3;
                    image.setImageResource(images[index]);
                    handler.sendEmptyMessageDelayed(200, 1500);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        initLoginStatus();
        btn = findViewById(R.id.dummy_button);
        image = findViewById(R.id.imageView);
        SharedPreferences preferences = getSharedPreferences("share", MODE_PRIVATE);
        preferences.edit().putBoolean("isNew", false).apply();
    }

    @Override
    protected void onResume() {
        super.onResume();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacksAndMessages(null);
                startActivity(new Intent(NavigationActivity.this,LoginActivity.class));
            }
        });

        handler.sendEmptyMessageDelayed(200, 1500);

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    private void initLoginStatus() {
        SharedPreferences sp= getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor=sp.edit();
        //存入boolean类型的登录状态
        editor.putBoolean("isLogin", false);
        //存入登录状态时的用户名
        editor.putString("loginUserName", "");
        //提交修改
        editor.commit();
    }
}


