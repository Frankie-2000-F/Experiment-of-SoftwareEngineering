package com.example.jizhang_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import static com.example.jizhang_master.VaryActivity.budget;
import static com.example.jizhang_master.VaryActivity.expend;
import static com.example.jizhang_master.VaryActivity.income;

public class SplashActivity extends AppCompatActivity {

    private Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            //判断是否是第一次进入，第一次则打开导航界面，并提供登录页面入口
            SharedPreferences preferences = getSharedPreferences("share", MODE_PRIVATE);

            if (preferences.getBoolean("isNew", true)) {
                startActivity(new Intent(SplashActivity.this, NavigationActivity.class));
            }

            //判断是否已经登录，已经登录则跳过登录界面直接进入主页

            else if (getSharedPreferences("loginInfo", MODE_PRIVATE).getBoolean("isLogin",true)) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                getVary();
            }
            else if(getSharedPreferences("loginInfo", MODE_PRIVATE).getString("loginUserName","").equals(""))
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));

        }
    };


    public void getVary(){
        SharedPreferences sp=getSharedPreferences("loginInfo", 0);
        String userName = sp.getString("loginUserName","");
        SharedPreferences preferences = getSharedPreferences("vary", 0);
        budget = preferences.getString(userName+"budget","");
        expend = preferences.getString(userName+"expend","");
        income = preferences.getString(userName+"income","");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //延时2s，切换界面
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}

