package com.example.jizhang_master;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.util.Calendar;
import java.util.Date;

import static android.widget.Toast.LENGTH_SHORT;
import static com.example.jizhang_master.VaryActivity.budget;
import static com.example.jizhang_master.VaryActivity.expend;
import static com.example.jizhang_master.VaryActivity.income;
import static com.example.jizhang_master.VaryActivity.type;
import com.example.jizhang_master.utils.pubFun;


public class AddActivity extends AppCompatActivity {

    public static int ex_num=0;

    TextView tvex1;
    TextView tvex2;
    TextView tvex3;
    TextView tvex4;
    TextView tvex5;
    TextView tvex6;
    TextView tvex7;
    TextView tvex8;
    TextView tvex9;
    TextView tvex10;
    Button btndate;

    private Calendar calendar = Calendar.getInstance();
    private DatePickerDialog datePicker;
    private static String[] accountList = { "" };
    private TextView[] tvs;
    private EditText etmoney;
    private EditText etnote;
    private Spinner spsource;
    private String value="";  //金额
    private String txtAccount=null; //账户
    private String txtCategory=null; //类别
    private String txtNote =null;  //备注
    private Date chosenDate;
    private Date nowDate;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        initicon();

        btndate.setText(pubFun.format(calendar.getTime()));
        btndate.setText(pubFun.format(calendar.getTime()));
        btndate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                openDate();
            }
        });
        SharedPreferences preferences = getSharedPreferences("loginInfo", 0);
        userID = preferences.getString("loginUserName","");
    }

    private void initView() {
        type=1;//支出
        tvex1 = findViewById(R.id.tv_ex1);
        tvex2 = findViewById(R.id.tv_ex2);
        tvex3 = findViewById(R.id.tv_ex3);
        tvex4 = findViewById(R.id.tv_ex4);
        tvex5 = findViewById(R.id.tv_ex5);
        tvex6 = findViewById(R.id.tv_ex6);
        tvex7 = findViewById(R.id.tv_ex7);
        tvex8 = findViewById(R.id.tv_ex8);
        tvex9 = findViewById(R.id.tv_ex9);
        tvex10 = findViewById(R.id.tv_ex10);
        btndate=findViewById(R.id.btn_date);
        etmoney=findViewById(R.id.et_money);
        etnote=findViewById(R.id.et_note);
        spsource=findViewById(R.id.sp_source);
        spsource.setOnItemSelectedListener(new spinnerSelectedListener());
        tvs= new TextView[]{tvex1,tvex2,tvex3,tvex4,tvex5,tvex6,tvex7,tvex8,tvex9,tvex10};
        accountList = new String[] { "选择账户","银行卡", "支付宝","微信","校园卡"};


    }

    /**
     * 设置点击后颜色变化
     * @param i
     */
    private void set_color(int i){
        if(ex_num!=0){
            tvs[ex_num-1].setTextColor(Color.parseColor("#010C1F"));
        }
        ex_num=i;
        tvs[i-1].setTextColor(Color.parseColor("#D81B60"));
    }

    /**
     * 图标点击监听
     */
    private void initicon() {

        //点击“图标1
        findViewById(R.id.btn_ex1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_color(1);
            }
        });
        findViewById(R.id.btn_ex2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_color(2);
            }
        });

        findViewById(R.id.btn_ex3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_color(3);
            }
        });
        findViewById(R.id.btn_ex4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_color(4);
            }
        });
        findViewById(R.id.btn_ex5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_color(5);
            }
        });
        findViewById(R.id.btn_ex6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_color(6);
            }
        });
        findViewById(R.id.btn_ex7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_color(7);
            }
        });
        findViewById(R.id.btn_ex8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_color(8);
            }
        });
        findViewById(R.id.btn_ex9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_color(9);
            }
        });
        findViewById(R.id.btn_ex10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_color(10);
            }
        });
    }


    /**
     * 账户监听,获取账户选择结果
     */
    class spinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            txtAccount = accountList[arg2];
//            Log.i("tag",txtAccount);
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    /**
     * 打开日历，获取日期
     */
    private void openDate() {
        datePicker = new DatePickerDialog(this, mDateSetListenerSatrt,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.show();
    }

    /**
     * 设置日期
     */
    private DatePickerDialog.OnDateSetListener mDateSetListenerSatrt = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.YEAR, year);
            btndate.setText(pubFun.format(calendar.getTime()));
        }
    };

    /**
     *判断选择的日期是否晚于当前日期
     */
    private Boolean afterNow(){
        chosenDate = calendar.getTime();
        nowDate = Calendar.getInstance().getTime();
        if(nowDate.before(chosenDate))
            return true;
        else
            return false;
    }
    /**
     * 获取数据至字符串
     */
    private void transdata(){
        //金额
        value=etmoney.getText().toString();
        Log.i("mon",value);
        //类别
        if(ex_num!=0){
            //选择后
            txtCategory=tvs[(ex_num-1)].getText().toString();
            Log.i("tag",txtCategory);
        }
        //备注
        txtNote=etnote.getText().toString();
        Log.i("tag",txtNote);


    }

    public Boolean changeAccount(String account){

        if (account ==null || account.equals("选择账户"))
            return true;
        String name = "";
        if(account.equals("微信"))
            name = "weixin";
        else if (account.equals("支付宝"))
            name = "alipay";
        else if (account.equals("银行卡"))
            name = "bankCard";
        else if (account.equals("校园卡"))
           name = "campusCard";
        DBOpenHelper helper = new DBOpenHelper(this,"qianbao.db",null,1);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query("user_tb", new String[]{name},"userID=?", new String[]{userID},null,null,null);
        String money=null;

        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            money = cursor.getString(cursor.getColumnIndex(name));
        }else
            return false;

        if(money!=null&&!money.equals("")) {
            double i = Double.parseDouble(money) -  Double.parseDouble(value);
            String result = "" + i;
            if(account.equals("微信"))
                db.execSQL("update user_tb set weixin=? where userID=?",new Object[]{result,userID});
            else if (account.equals("支付宝"))
                db.execSQL("update user_tb set alipay=? where userID=?",new Object[]{result,userID});
            else if (account.equals("银行卡"))
                db.execSQL("update user_tb set bankCard=? where userID=?",new Object[]{result,userID});
            else if (account.equals("校园卡"))
                db.execSQL("update user_tb set campusCard=? where userID=?",new Object[]{result,userID});
        }
        else {

            return false;
        }
        cursor.close();
        db.close();
        return true;
    }

    private void changeVary(){
        int chosenMonth = calendar.get(Calendar.MONTH);
        int nowMonth = Calendar.getInstance().get(Calendar.MONTH);

        //支出
        if(!value.equals("") && chosenMonth == nowMonth) {
            expend=expend+0;
            double j = Double.parseDouble(expend) + Double.parseDouble(value);
            expend = "" + j;
        }
        //预算
        if(!budget.equals("")&&!value.equals("") && chosenMonth == nowMonth){
            double i= Double.parseDouble(budget)- Double.parseDouble(value);
            budget=""+i;
        }

    }
    /**
     * 保存输入数据至数据库
     */
    private void saveInfo() {

        //调用DBOpenHelper
        DBOpenHelper helper = new DBOpenHelper(this,"qianbao.db",null,1);
        SQLiteDatabase db = helper.getWritableDatabase();
        //插入数据
        ContentValues values= new ContentValues();
        values.put("userID",userID);
        values.put("Type",type);
        values.put("way",txtAccount);
        values.put("category",txtCategory);
        values.put("cost", value);
        values.put("note", txtNote);
        values.put("makeDate",pubFun.format(calendar.getTime()));
        db.insert("basicCode_tb",null,values);
        Toast.makeText(this, "记录成功", LENGTH_SHORT).show();

        Cursor c = db.query("basicCode_tb",null,"userID=?",new String[]{userID},null,null,null);
        if(c!=null && c.getCount() >= 1){
            String[] cols = c.getColumnNames();
            while(c.moveToNext()){
                for(String ColumnName:cols){
                    Log.i("info",ColumnName+":"+c.getString(c.getColumnIndex(ColumnName)));
                }
            }
            c.close();
        }
        db.close();
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //点击左上角x回到主页面
        findViewById(R.id.btn_return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(AddActivity.this, MainActivity.class));
            }
        });
        //点击收入进入收入页面
        findViewById(R.id.btn_income).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type=0;
                finish();
                startActivity(new Intent(AddActivity.this, IncomeActivity.class));
            }
        });

        //点击确定
        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transdata();
                //非空检测
                if(value.equals("")||value==null|| Float. parseFloat(value) <=0){
                    Toast.makeText(AddActivity.this, "请输入正确金额", LENGTH_SHORT).show();
                }
                else if(txtCategory==null||txtCategory.equals("")){
                    Toast.makeText(AddActivity.this, "请选择类别", LENGTH_SHORT).show();
                }
                else if (afterNow())
                    Toast.makeText(AddActivity.this, "不能预知未来哟，请修改日期", LENGTH_SHORT).show();
                else if (!changeAccount(txtAccount))
                    Toast.makeText(AddActivity.this, "尚未创建此账户，请前往个人信息界面设置", LENGTH_SHORT).show();
                else{
                    changeVary();
                    saveInfo();
                    commitVary();
//                    message="支出："+txtCategory+"￥"+value+"\n"+txtNote;
                    startActivity(new Intent(AddActivity.this, MainActivity.class));
                }
            }
        });

        //点击“再记一笔”
        findViewById(R.id.btn_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                transdata();
                //非空检测
                if(value.equals("")||value==null|| Float. parseFloat(value) <=0){
                    Toast.makeText(AddActivity.this, "请输入正确金额", LENGTH_SHORT).show();
                }
                else if(txtCategory==null||txtCategory.equals("")){
                    Toast.makeText(AddActivity.this, "请选择类别", LENGTH_SHORT).show();
                }
                else if (afterNow())
                    Toast.makeText(AddActivity.this, "不能预知未来哟，请修改日期", LENGTH_SHORT).show();
                else if (!changeAccount(txtAccount))
                    Toast.makeText(AddActivity.this, "尚未创建此账户，请前往个人信息界面设置", LENGTH_SHORT).show();
                else{
                    changeVary();
                    saveInfo();
                    commitVary();
                    startActivity(new Intent(AddActivity.this, AddActivity.class));
                }
            }
        });
    }

    public void commitVary(){
        SharedPreferences sp=getSharedPreferences("loginInfo", 0);
        String userName = sp.getString("loginUserName","");
        SharedPreferences preferences = getSharedPreferences("vary", 0);
        SharedPreferences.Editor editor=preferences.edit();
        //存入当月预算
        editor.putString(userName+"budget", budget);
        //存入当月支出
        editor.putString(userName+"expend", expend);
        //存入当月收入
        editor.putString(userName+"income", income);
        //提交修改
        editor.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
