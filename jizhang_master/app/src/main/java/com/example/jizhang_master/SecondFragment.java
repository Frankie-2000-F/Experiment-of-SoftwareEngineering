package com.example.jizhang_master;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.jizhang_master.utils.pubFun;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.jizhang_master.VaryActivity.budget;
import static com.example.jizhang_master.VaryActivity.expend;
import static com.example.jizhang_master.VaryActivity.income;


public class SecondFragment extends Fragment {

    List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>(); //存储数据的数组列表
    int[] image_expense = new int[]{R.drawable.icon_income, R.drawable.icon_outcome}; //存储图片
    private Button delete;
    private Handler handler;
    private Spinner month_spinner;
    private Spinner year_spinner;
    private ArrayAdapter<String> month_adapter;
    private ArrayAdapter<String> year_adapter;
    private AlertDialog alertDialog_Delete;
    private static final String[] monthList = { "01月", "02月", "03月", "04月", "05月", "06月", "07月", "08月", "09月", "10月", "11月", "12月" };
    private static final String[] yearList = { "2018年", "2019年", "2020年", "2021年", "2022年", "2023年","2024年","2025年","2026年","2027年"};


    public SecondFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_one, container, false);

        SharedPreferences tt = getActivity().getSharedPreferences("month", Activity.MODE_PRIVATE);
        String monthID =tt.getString("monthid", "");
        SharedPreferences year = getActivity().getSharedPreferences("year", Activity.MODE_PRIVATE);
        String yearID =year.getString("yearid", "");
        initSpinner( v);
        getData(monthID,yearID,v);
        resetpic(v);
        return v;
    }


    private void resetpic(final View v){
        handler=new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                SharedPreferences year = getActivity().getSharedPreferences("year", Activity.MODE_PRIVATE);
                String yearID =year.getString("yearid", "");
                SharedPreferences month = getActivity().getSharedPreferences("month", Activity.MODE_PRIVATE);
                String monthID =month.getString("monthid", "");

                if(msg.what==0){
                    getData(monthID,yearID,v);
                } else if(msg.what==1){
                    getData("7","2020",v);
                    SharedPreferences otherway = getActivity().getSharedPreferences("otherway", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor hello = otherway.edit();//实例化SharedPreferences.Editor对象
                    hello.putString("otherway", "YouShouldChange"); //用putString的方法保存数据
                    hello.commit();
                }


            }
        };
    }
    private void initSpinner(View v){
        month_spinner = (Spinner) v.findViewById(R.id.month_spinner);
        year_spinner = (Spinner) v.findViewById(R.id.year_spinner);
        //将可选内容与ArrayAdapter连接起来
        month_adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, monthList);
        year_adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, yearList);
        //设置下拉列表的风格
        month_adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year_adapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //将adapter添加到spinner中
        month_spinner.setAdapter(month_adapter);
        year_spinner.setAdapter(year_adapter);
        //添加事件Spinner事件监听
        month_spinner
                .setOnItemSelectedListener(new month_spinnerSelectedListener());
        year_spinner
                .setOnItemSelectedListener(new year_spinnerSelectedListener());
        //设置默认值
        month_spinner.setSelection(pubFun.getTime("M"), true);
        month_spinner.setVisibility(View.VISIBLE);
        year_spinner.setSelection(pubFun.getTime("Y")-2018, true);
        year_spinner.setVisibility(View.VISIBLE);

    }

    /**
     * 选择 年份 事件 监听器
     */
    class year_spinnerSelectedListener implements AdapterView.OnItemSelectedListener{
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            int pos = year_spinner.getSelectedItemPosition();
            System.out.println(pos);
            String yearID=Integer.toString(pos+2018);
            System.out.println(yearID);
            SharedPreferences year = getActivity().getSharedPreferences("year", Activity.MODE_PRIVATE);
            SharedPreferences.Editor hello = year.edit();//实例化SharedPreferences.Editor对象
            hello.putString("yearid", Integer.toString(pos+2018)); //用putString的方法保存数据
            hello.commit();

            handler.sendEmptyMessage(0);
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
    /**
     * 选择 月份 事件 监听器
     */
    class month_spinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            int pos = month_spinner.getSelectedItemPosition();
            System.out.println(pos);
            String monthID=Integer.toString(pos+1);
            //实例化SharedPreferences对象
            SharedPreferences month = getActivity().getSharedPreferences("month", Activity.MODE_PRIVATE);
            SharedPreferences.Editor hello = month.edit();//实例化SharedPreferences.Editor对象
            hello.putString("monthid", Integer.toString(pos+1)); //用putString的方法保存数据
            hello.commit(); //提交当前数据*/

            SharedPreferences year = getActivity().getSharedPreferences("year", Activity.MODE_PRIVATE);
            String yearID =year.getString("yearid", "");
            Message msgMessage = Message.obtain();
            handler.sendMessage(msgMessage);
            handler.sendEmptyMessage(0);
        }
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        }
    private void getData(String monthID,String yearID,View v){
            //先判断用户是否登录
            final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", Activity.MODE_PRIVATE);
            final String userID =sharedPreferences.getString("loginUserName", "");

            Log.i("info", "此次登录的用户是" + userID);


            //call DBOpenHelper
            DBOpenHelper helper = new DBOpenHelper(getActivity(),"qianbao.db",null,1);
            SQLiteDatabase db = helper.getWritableDatabase();
            Cursor c = db.query("basicCode_tb",null,"userID=?",new String[]{userID},null,null,"makeDate");
            c.moveToFirst();
            System.out.println("数据库长度是"+c.getCount());
            System.out.println(c);

            int iColCount = c.getColumnCount();
            int iNumber = 0;
            String strType = "";
            listitem.clear();
            while (iNumber < c.getCount()){
                Map<String, Object> map = new HashMap<String, Object>();
                strType = c.getString(c.getColumnIndex("Type"));
                String expense_date=c.getString(c.getColumnIndex("makeDate"));

                if (expense_date.contains(yearID) && expense_date.contains(monthID+"-")){
                    map.put("image_expense", image_expense[Integer.parseInt(strType)]);
                    map.put("expense_category", c.getString(c.getColumnIndex("category")));
                    if(strType.equals("0")){
                        map.put("expense_money", "+" + c.getString(c.getColumnIndex("cost")));
                    }else{
                        map.put("expense_money", "-" + c.getString(c.getColumnIndex("cost")));
                    }
                    map.put("date",c.getString(c.getColumnIndex("makeDate")));
                    map.put("note",c.getString(c.getColumnIndex("note")));
                    map.put("id",c.getInt(c.getColumnIndex("_id")));
                }
                c.moveToNext();
                System.out.println("这是第"+c.getColumnIndex("_id"));
                listitem.add(map);
                iNumber++;
                System.out.println(listitem);
            }
            //for(int i=0;i<c.getCount();++i){
                //if(listitem.get(i).size()==0){
                    //listitem.remove(i);
                //}
           // }
            c.close();
            db.close();

        int len=listitem.size();
        for(int i=0;i<len;){
            if(i==listitem.size()){
                break;
            }
            else if(listitem.get(i).size()==0) {
                listitem.remove(i);
                continue;
        }
        ++i;
        }
        System.out.println(listitem);
        SimpleAdapter adapter = new SimpleAdapter(getActivity()
                , listitem
                , R.layout.fragment_one_item
                , new String[]{"expense_category", "expense_money", "image_expense","note"}
                , new int[]{R.id.tv_expense_category, R.id.tv_expense_money, R.id.image_expense,R.id.tv_note});
        ListView listView = (ListView) v.findViewById(R.id.lv_expense);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//设置监听器
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);
                Toast.makeText(getActivity(), map.get("date").toString(), Toast.LENGTH_LONG).show();
            }

        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> map = (Map<String,Object>) parent.getItemAtPosition(position);
//                DeleteData(Integer.parseInt(map.get("id").toString()));
                DBOpenHelper helper = new DBOpenHelper(getActivity(),"qianbao.db",null,1);
                SQLiteDatabase db = helper.getWritableDatabase();
                Cursor c = db.query("basicCode_tb",null,"_id=?",new String[]{map.get("id").toString()},null,null,null);
                String cost="";
                int Type=0;
                String date ="";
                String account ="";
                if(c!=null && c.getCount() >= 1){
                    c.moveToFirst();
                    cost = c.getString(c.getColumnIndex("cost"));
                    Type = c.getInt(c.getColumnIndex("Type"));
                    date = c.getString(c.getColumnIndex("makeDate"));
                    account = c.getString(c.getColumnIndex("way"));
                }
                db.execSQL("Delete from basicCode_tb where _id=?",new Object[]{Integer.parseInt(map.get("id").toString())});
                Toast.makeText(getActivity(), "成功删除记录", Toast.LENGTH_LONG).show();
                String name = "";
                if(account.equals("微信"))
                    name = "weixin";
                else if (account.equals("支付宝"))
                    name = "alipay";
                else if (account.equals("银行卡"))
                    name = "bankCard";
                else if (account.equals("校园卡"))
                    name = "campusCard";
                DBOpenHelper helper2 = new DBOpenHelper(getActivity(),"qianbao.db",null,1);
                SQLiteDatabase db2 = helper2.getWritableDatabase();
                Cursor cursor = db2.query("user_tb", new String[]{name},"userID=?", new String[]{userID},null,null,null);
                String money=null;

                if(cursor!=null && cursor.getCount()>0){
                    cursor.moveToFirst();
                    money = cursor.getString(cursor.getColumnIndex(name));
                }else
                    return false;
                if(money!=null&&!money.equals("")) {

                    if (Type == 0) {
                        double i = Double.parseDouble(money) - Double.parseDouble(cost);
                        String result = "" + i;
                        if (account.equals("微信"))
                            db.execSQL("update user_tb set weixin=? where userID=?", new Object[]{result, userID});
                        else if (account.equals("支付宝"))
                            db.execSQL("update user_tb set alipay=? where userID=?", new Object[]{result, userID});
                        else if (account.equals("银行卡"))
                            db.execSQL("update user_tb set bankCard=? where userID=?", new Object[]{result, userID});
                        else if (account.equals("校园卡"))
                            db.execSQL("update user_tb set campusCard=? where userID=?", new Object[]{result, userID});
                    } else if (Type == 1) {
                        double i = Double.parseDouble(money) + Double.parseDouble(cost);
                        String result = "" + i;
                        if (account.equals("微信"))
                            db.execSQL("update user_tb set weixin=? where userID=?", new Object[]{result, userID});
                        else if (account.equals("支付宝"))
                            db.execSQL("update user_tb set alipay=? where userID=?", new Object[]{result, userID});
                        else if (account.equals("银行卡"))
                            db.execSQL("update user_tb set bankCard=? where userID=?", new Object[]{result, userID});
                        else if (account.equals("校园卡"))
                            db.execSQL("update user_tb set campusCard=? where userID=?", new Object[]{result, userID});
                    }
                }
                if (date.contains((Calendar.getInstance().get(Calendar.MONTH) + 1) +"-")) {

                    if (Type == 1) {
                        expend=expend+0;

                        double j = Double.parseDouble(expend) - Double.parseDouble(cost);

                        expend = "" + j;
                        if(!budget.equals("")) {
                        budget=budget+0;
                        double i = Double.parseDouble(budget) + Double.parseDouble(cost);
                        budget = "" + i;
                        }
                    } else {
                        double j =0;
                        j = Double.parseDouble(income) - Double.parseDouble(cost);
                        income = "" + j;
                    }

                    commitVary();
                }

                c.close();
                db.close();
                handler.sendEmptyMessage(0);
                return true;
            }
        });
    }


    public void commitVary(){
        SharedPreferences sp=getActivity().getSharedPreferences("loginInfo", 0);
        String userName = sp.getString("loginUserName","");
        SharedPreferences preferences = getActivity().getSharedPreferences("vary", 0);
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

