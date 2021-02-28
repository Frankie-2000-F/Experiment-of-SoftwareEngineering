package com.example.jizhang_master;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


@SuppressLint("SetTextI18n")
public class ScanResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);
        TextView tv_result = findViewById(R.id.tv_result);
        // 获取扫码页面传来的结果字符串
        String result = getIntent().getStringExtra("result");
        tv_result.setText("扫码结果为：" + result);
        CheckWeb(result);
    }

    private void CheckWeb(String result){
        if (result.startsWith("http://")||result.startsWith("https://")) {
            JumpToWeb(result);
        }
    }

    public void JumpToWeb(String result) {
        Intent intent= new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(result);
        intent.setData(content_url);
        startActivity(intent);
    }
}