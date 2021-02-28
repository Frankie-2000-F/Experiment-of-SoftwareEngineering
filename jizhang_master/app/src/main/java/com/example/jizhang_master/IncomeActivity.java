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

public class IncomeActivity extends AppCompatActivity {

    private int in_num=0;

    TextView tvin1;
    TextView tvin2;
    TextView tvin3;
    TextView tvin4;
    TextView tvin5;
    TextView tvin6;
    TextView tvin7;
    TextView tvin8;
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

        setContentView(R.layout.activity_income);
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
        type=0;//收入
        tvin1= findViewById(R.id.tv_in1);
        tvin2 = findViewById(R.id.tv_in2);
        tvin3 = findViewById(R.id.tv_in3);
        tvin4 = findViewById(R.id.tv_in4);
        tvin5 = findViewById(R.id.tv_in5);
        tvin6 = findViewById(R.id.tv_in6);
        tvin7 = findViewById(R.id.tv_in7);
        tvin8 = findViewById(R.id.tv_in8);
        btndate=findViewById(R.id.btn_date);
        etmoney=findViewById(R.id.et_money);
        etnote=findViewById(R.id.et_note);
        spsource=findViewById(R.id.sp_source);
        spsource.setOnItemSelectedListener(new IncomeActivity.spinnerSelectedListener());
        tvs= new TextView[]{tvin1,tvin2,tvin3,tvin4,tvin5,tvin6,tvin7,tvin8};
        accountList = new String[] { "选择账户","银行卡", "支付宝","微信","校园卡"};
    }
    /**
     * 设置点击后颜色变化
     * @param i
     */
    private void set_color(int i){
        if(in_num!=0){
            tvs[in_num-1].setTextColor(Color.parseColor("#010C1F"));
        }
        in_num=i;
        tvs[i-1].setTextColor(Color.parseColor("#008577"));
    }

    /**
     * 图标点击监听
     */
    private void initicon() {

        //点击“图标1
        findViewById(R.id.btn_in1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_color(1);
            }
        });
        findViewById(R.id.btn_in2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_color(2);
            }
        });

        findViewById(R.id.btn_in3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_color(3);
            }
        });
        findViewById(R.id.btn_in4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_color(4);
            }
        });
        findViewById(R.id.btn_in5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_color(5);
            }
        });
        findViewById(R.id.btn_in6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_color(6);
            }
        });
        findViewById(R.id.btn_in7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_color(7);
            }
        });
        findViewById(R.id.btn_in8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_color(8);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
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
     * 获取数据至字符串
     */
    private void transdata(){
        //金额
        value=etmoney.getText().toString();
        Log.i("mon",value);
        //类别
        if(in_num!=0){
            //选择后
            txtCategory=tvs[(in_num-1)].getText().toString();
            Log.i("tag",txtCategory);
        }
        //备注
        txtNote=etnote.getText().toString();
        Log.i("tag",txtNote);




    }

    /**
     * 更改资产账户信息
     * @param account
     * @return
     */
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
            double i = Double.parseDouble(money) +  Double.parseDouble(value);
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

        //收入
        if(!value.equals("") && chosenMonth == nowMonth){
            income=income+0;
            double i= Double.parseDouble(value)+ Double.parseDouble(income);
            income=""+i;
        }

    }

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
     * 保存输入数据至数据库
     */
    private void saveInfo() {
        //用户ID
        SharedPreferences sharedPreferences= getSharedPreferences("loginInfo", Activity.MODE_PRIVATE);
        String userID =sharedPreferences.getString("loginUserName", "");
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
                type = 1;
                startActivity(new Intent(IncomeActivity.this, MainActivity.class));
            }
        });
        //点击支出回到支出界面
        findViewById(R.id.btn_expend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 1;
                startActivity(new Intent(IncomeActivity.this, AddActivity.class));
            }
        });

        //点击确定
        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transdata();
                //非空检测
                if(value.equals("")||value==null|| Float. parseFloat(value) <=0){
                    Toast.makeText(IncomeActivity.this, "请输入正确金额", LENGTH_SHORT).show();
                }
                else if(txtCategory==null||txtCategory.equals("")){
                    Toast.makeText(IncomeActivity.this, "请选择类别", LENGTH_SHORT).show();
                }
                else if (afterNow())
                        Toast.makeText(IncomeActivity.this, "不能预知未来哟，请修改日期", LENGTH_SHORT).show();
                else if (!changeAccount(txtAccount))
                    Toast.makeText(IncomeActivity.this, "尚未创建此账户，请前往个人信息界面设置", LENGTH_SHORT).show();
                else{
                    changeVary();
                    saveInfo();
                    commitVary();
                    startActivity(new Intent(IncomeActivity.this, MainActivity.class));
                }
            }
        });

        //点击“再记一笔”
        findViewById(R.id.btn_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type =1;
                transdata();
                //非空检测
                if(value.equals("")||value==null|| Float. parseFloat(value) <=0){
                    Toast.makeText(IncomeActivity.this, "请输入正确金额", LENGTH_SHORT).show();
                }
                else if(txtCategory==null||txtCategory.equals("")){
                    Toast.makeText(IncomeActivity.this, "请选择类别", LENGTH_SHORT).show();
                }
                else if (afterNow())
                    Toast.makeText(IncomeActivity.this, "不能预知未来哟，请修改日期", LENGTH_SHORT).show();
                else if (!changeAccount(txtAccount))
                    Toast.makeText(IncomeActivity.this, "尚未创建此账户，请前往个人信息界面设置", LENGTH_SHORT).show();
                else{
                    changeVary();
                    saveInfo();
                    commitVary();
                    startActivity(new Intent(IncomeActivity.this, AddActivity.class));
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
}
