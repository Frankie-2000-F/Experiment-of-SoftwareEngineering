package com.example.jizhang_master;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.jizhang_master.utils.MD5Util;

public class ResetPswActivity extends AppCompatActivity {

    private Button btn_respsw;//重置按钮
    //用户名，密码，再次输入的密码的控件
    private EditText et_user_name, et_psw, et_psw_again, et_phoneNum;
    //用户名，密码，再次输入的密码的控件的获取值
    private String userName, psw, pswAgain, phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respsw);
        init();
    }

    private void init() {
        //从activity_register.xml 页面中获取对应的UI控件
        btn_respsw = findViewById(R.id.btn_respsw);
        et_user_name = findViewById(R.id.et_user_name_res);
        et_psw = findViewById(R.id.et_psw_res);
        et_psw_again = findViewById(R.id.et_psw_again_res);
        et_phoneNum = findViewById(R.id.et_phoneNum_res);

        //注册按钮
        //注册按钮
        btn_respsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入在相应控件中的字符串
                getEditString();
                //判断输入框内容
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(ResetPswActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(psw)) {
                    Toast.makeText(ResetPswActivity.this, "请输入新密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(pswAgain)) {
                    Toast.makeText(ResetPswActivity.this, "请再次输入新密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(ResetPswActivity.this, "请输入密保手机号", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!isExistUserName(userName)) {
                    Toast.makeText(ResetPswActivity.this, "此用户名不存在", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!psw.equals(pswAgain)) {
                    Toast.makeText(ResetPswActivity.this, "输入两次的密码不一样", Toast.LENGTH_SHORT).show();
                    //清空密码输入栏
                    et_psw.setText("");
                    et_psw_again.setText("");
                    return;
                }else if (matchPhone(userName,phoneNumber)){
                    /**
                     * 在database中修改对应密码
                     */
                    changeInfo(userName, psw);
                    Toast.makeText(ResetPswActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    //注册成功后把账号传递到LoginActivity.java中
                    // 返回值到loginActivity显示
                    Intent data = new Intent();
                    data.putExtra("userName", userName);
                    data.putExtra("passWord",psw);
                    setResult(RESULT_OK, data);
                    //RESULT_OK为Activity系统常量，状态码为-1，
                    // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                    ResetPswActivity.this.finish();
                }
                else {
                    Toast.makeText(ResetPswActivity.this, "密保手机号不正确", Toast.LENGTH_SHORT).show();



                }
            }
        });
        }
    /**
     * 获取控件中的字符串
     */
    private void getEditString() {
        userName = et_user_name.getText().toString().trim();
        psw = et_psw.getText().toString().trim();
        pswAgain = et_psw_again.getText().toString().trim();
        phoneNumber = et_phoneNum.getText().toString().trim();
    }

    private boolean isExistUserName(String userName) {
        boolean has_userName = false;

        DBOpenHelper helper = new DBOpenHelper(this,"qianbao.db",null,1);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.query("user_tb",null,"userID=?",new String[]{userName},null,null,null);
        if(c!=null && c.getCount() >= 1){
            has_userName = true;
        }
        c.close();
        db.close();

        return has_userName;
    }

    private Boolean matchPhone(String userName, String phoneNumber){

        Boolean isMatched = false;

        DBOpenHelper helper = new DBOpenHelper(this,"qianbao.db",null,1);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.query("user_tb",null,"userID=? and phoneNumber=?",new String[]{userName,phoneNumber},null,null,null);
        if(c!=null && c.getCount() >= 1){
            String[] cols = c.getColumnNames();
            while(c.moveToNext()){
                for(String ColumnName:cols){
                    Log.i("info",ColumnName+":"+c.getString(c.getColumnIndex(ColumnName)));
                }
            }
            c.close();
            db.close();
            isMatched = true;
        }
        return isMatched;
    }

    private void changeInfo(String userName, String psw) {
        String md5Psw = MD5Util.md5(psw);//把密码用MD5加密

        DBOpenHelper helper = new DBOpenHelper(this,"qianbao.db",null,1);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values= new ContentValues();

        values.put("pwd",md5Psw);

        long rowid = db.update("user_tb",values,"userID=?", new String[]{userName});
        db.close();

    }
}
