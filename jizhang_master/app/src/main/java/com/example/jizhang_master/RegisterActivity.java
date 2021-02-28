package com.example.jizhang_master;


import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jizhang_master.utils.MD5Util;

public class RegisterActivity extends AppCompatActivity {

    private Button btn_register;//注册按钮
    //用户名，密码，再次输入的密码的控件
    private EditText et_user_name, et_psw, et_psw_again, et_phoneNum;
    //用户名，密码，再次输入的密码的控件的获取值
    private String userName, psw, pswAgain, phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置页面布局 ,注册界面
        setContentView(R.layout.activity_register);

        init();
    }

    private void init() {


        //从activity_register.xml 页面中获取对应的UI控件
        btn_register = findViewById(R.id.btn_register);
        et_user_name = findViewById(R.id.et_user_name);
        et_psw = findViewById(R.id.et_psw);
        et_psw_again = findViewById(R.id.et_psw_again);
        et_phoneNum = findViewById(R.id.et_phoneNum);

        //注册按钮
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入在相应控件中的字符串
                getEditString();
                //判断输入框内容
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(psw)) {
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(pswAgain)) {
                    Toast.makeText(RegisterActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(RegisterActivity.this, "请输入密保手机号", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!psw.equals(pswAgain)) {
                    Toast.makeText(RegisterActivity.this, "两次输入的密码不一样", Toast.LENGTH_SHORT).show();
                    //清空密码输入栏
                    et_psw.setText("");
                    et_psw_again.setText("");
                    return;
                    /**
                     *从database中读取输入的用户名，判断database中是否有此用户名
                     */
                } else if (isExistUserName(userName)) {
                    Toast.makeText(RegisterActivity.this, "此账户名已经存在", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    //把账号、密码和账号标识保存到sp里面
                    /**
                     * 保存账号和密码到database中
                     */
                    saveRegisterInfo(userName, psw, phoneNumber);
                    //注册成功后把账号传递到LoginActivity.java中
                    // 返回值到loginActivity显示
                    Intent data = new Intent();
                    data.putExtra("userName", userName);
                    setResult(RESULT_OK, data);
                    //RESULT_OK为Activity系统常量，状态码为-1，
                    // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                    RegisterActivity.this.finish();
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

    /**
     * 从database中读取输入的用户名，判断database中是否有此用户名
     */
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

    /**
     * 保存账号和密码到database
     */
    private void saveRegisterInfo(String userName, String psw, String phoneNumber) {
        String md5Psw = MD5Util.md5(psw);//把密码用MD5加密

        DBOpenHelper helper = new DBOpenHelper(this,"qianbao.db",null,1);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put("userID",userName);
        values.put("pwd",md5Psw);
        values.put("phoneNumber",phoneNumber);
        long rowid = db.insert("user_tb",null,values);

        db.close();

//        //loginInfo表示文件名, mode_private SharedPreferences sp = getSharedPreferences( );
//        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
//        //获取编辑器， SharedPreferences.Editor  editor -> sp.edit();
//        SharedPreferences.Editor editor = sp.edit();
//        //以用户名为key，密码为value保存在SharedPreferences中
//        //key,value,如键值对，editor.putString(用户名，密码）;
//        editor.putString(userName, md5Psw);
//        //提交修改 editor.commit();
//        editor.commit();
    }
}
