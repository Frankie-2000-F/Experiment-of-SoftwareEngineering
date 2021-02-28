package com.example.jizhang_master;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jizhang_master.utils.MD5Util;

import static com.example.jizhang_master.VaryActivity.budget;
import static com.example.jizhang_master.VaryActivity.expend;
import static com.example.jizhang_master.VaryActivity.income;

public class LoginActivity extends AppCompatActivity {


    private TextView tv_register,tv_find_psw;//返回键,显示的注册，找回密码
    private Button btn_login;//登录按钮
    private String userName,psw,spPsw;//获取的用户名，密码，加密密码
    private EditText et_user_name,et_psw;//编辑框
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        initLoginStatus();

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

    private void init() {


        //从activity_login.xml中获取的
        tv_register=findViewById(R.id.tv_register);
        tv_find_psw=findViewById(R.id.tv_find_psw);
        btn_login=findViewById(R.id.btn_login);
        et_user_name=findViewById(R.id.et_user_name);
        et_psw=findViewById(R.id.et_psw);
        //返回键的点击事件
        ;
        //立即注册控件的点击事件
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //为了跳转到注册界面，并实现注册功能
            Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
            startActivityForResult(intent, 1);
            }
        });
        //找回密码控件的点击事件
        tv_find_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到找回密码界面
                Intent intent=new Intent(LoginActivity.this,ResetPswActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        //登录按钮的点击事件
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始登录，获取用户名和密码 getText().toString().trim();
                userName=et_user_name.getText().toString().trim();
                psw=et_psw.getText().toString().trim();
                //对当前用户输入的密码进行MD5加密再进行比对判断, MD5Utils.md5( ); psw 进行加密判断是否一致
                String md5Psw= MD5Util.md5(psw);
                // md5Psw ; spPsw 为 根据从database中用户名读取密码


                // TextUtils.isEmpty
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(psw)){
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                    // md5Psw.equals(); 判断，输入的密码加密后，是否与保存在SharedPreferences中一致
                }else if(checkData(userName,md5Psw) == 1){
                    Toast.makeText(LoginActivity.this, "此用户名不存在", Toast.LENGTH_SHORT).show();
                    return;
                } else if(checkData(userName,md5Psw) == 2){
                    Toast.makeText(LoginActivity.this, "输入的用户名和密码不一致", Toast.LENGTH_SHORT).show();
                    //清空密码输入栏
                    et_psw.setText("");
                    return;
                }else {
                    //一致登录成功
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    //保存登录状态，在界面保存登录的用户名 定义个方法 saveLoginStatus boolean 状态 , userName 用户名;
                    saveLoginStatus(true, userName);
                    getVary();
                    //登录成功后关闭此页面进入主页
                    Intent data=new Intent();
                    //datad.putExtra( ); name , value ;
                    data.putExtra("isLogin",true);
                    //RESULT_OK为Activity系统常量，状态码为-1
                    // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                    setResult(RESULT_OK,data);
                    //销毁登录界面
                    LoginActivity.this.finish();
                    //跳转到主界面，登录成功的状态传递到 MainActivity 中
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    return;
                }
            }
        });
    }
    /**
     * 判断用户名是否存在或密码是否正确，返回错误类型
     */
    private int checkData(String userName, String md5Psw){

        DBOpenHelper helper = new DBOpenHelper(this,"qianbao.db",null,1);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c1 = db.query("user_tb",null,"userID=?",new String[]{userName},null,null,null);
        Cursor c2 = db.query("user_tb",null,"userID=? and pwd=?",new String[]{userName,md5Psw},null,null,null);
        if(!(c1!=null && c1.getCount() >= 1)){
            c1.close();
            c2.close();
            db.close();
            return 1;
        }
        if(c2!=null && c2.getCount() >= 1) {
            String[] cols = c2.getColumnNames();
            while (c2.moveToNext()) {
                for (String ColumnName : cols) {
                    Log.i("info", ColumnName + ":" + c2.getString(c2.getColumnIndex(ColumnName)));
                }
            }
            return 0;
        }else
           return 2;


    }


    public void getVary(){
        SharedPreferences sp=getSharedPreferences("loginInfo", 0);
        String userName = sp.getString("loginUserName","");
        SharedPreferences preferences = getSharedPreferences("vary", 0);
        budget = preferences.getString(userName+"budget","");
        expend = preferences.getString(userName+"expend","");
        income = preferences.getString(userName+"income","");
    }
    /**
     *保存登录状态和登录用户名到SharedPreferences中
     */
    private void saveLoginStatus(boolean status,String userName){
        //saveLoginStatus(true, userName);
        //loginInfo表示文件名  SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor=sp.edit();
        //存入boolean类型的登录状态
        editor.putBoolean("isLogin", status);
        //存入登录状态时的用户名
        editor.putString("loginUserName", userName);
        //提交修改
        editor.commit();
    }
    /**
     * 注册成功的数据返回至此
     * @param requestCode 请求码
     * @param resultCode 结果码
     * @param data 数据
     */
    @Override
    //显示数据， onActivityResult
    //startActivityForResult(intent, 1); 从注册界面中获取数据
    //int requestCode , int resultCode , Intent data
    // LoginActivity -> startActivityForResult -> onActivityResult();
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            //是获取注册界面或修改密码界面回传过来的用户名
            // getExtra().getString("***");
            String userName=data.getStringExtra("userName");
            String passWord=data.getStringExtra("passWord");
            if(!TextUtils.isEmpty(userName)){
                //设置用户名到 et_user_name 控件
                et_user_name.setText(userName);
                //et_user_name控件的setSelection()方法来设置光标位置
                et_user_name.setSelection(userName.length());
            }
            if(!TextUtils.isEmpty(passWord)){
                //设置密码到 et_psw 控件
                et_user_name.setText(userName);
                //et_psw控件的setSelection()方法来设置光标位置
                et_user_name.setSelection(passWord.length());
            }
        }
    }
}
