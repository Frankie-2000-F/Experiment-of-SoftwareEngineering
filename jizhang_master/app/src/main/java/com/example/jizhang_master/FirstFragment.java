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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.jizhang_master.VaryActivity.budget;
import static com.example.jizhang_master.VaryActivity.expend;
import static com.example.jizhang_master.VaryActivity.income;

import com.example.jizhang_master.utils.pubFun;

public class FirstFragment extends Fragment {
    private TextView tvMexpend;
    private Button btnBudget;
    private TextView tvMincome;
    private TextView tvDayaccount;
    private Handler handler;

    List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>(); //存储数据的数组列表
    int[] image_expense = new int[]{R.drawable.icon_income, R.drawable.icon_outcome}; //存储图片
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, null);
        initView(view);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     *初始化预算
     */


    /**
     * 初始化控件
     */
    private void initView(View v) {
        tvMexpend = v.findViewById(R.id.tv_month_expend);
        tvMincome = v.findViewById(R.id.tv_month_income);
        btnBudget = v.findViewById(R.id.btn_budget);
        tvDayaccount = v.findViewById(R.id.tv_day_account);
        btnBudget.setText(budget);
        tvMexpend.setText(expend);
        tvMincome.setText(income);
        v.findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddActivity.class));
            }
        });
        btnBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert_edit();
            }
        });
        getData(v);
//        Adapter adapter=new Adapter(MainActivity.this,R.layout.list_item,accountList);
//        ListView listview = findViewById(R.id.list_view);
//        listview.setAdapter(adapter);

    }


    /**
     * listview
     */
//
//    public class Account{
//        private int imageId;
//        private String msg;
//        public Account(String msg,int imageId){
//            this.imageId=imageId;
//            this.msg=msg;
//        }
//        public int getImageId(){
//            return imageId;
//        }
//        public String getMsg(){
//            return msg;
//        }
//    }
//
//    /**
//     * adapter
//     */
//    public class Adapter extends ArrayAdapter<Account> {
//        private int resourceId;
//        public Adapter(Context context, int textViewResourceId, List<Account> objects){
//            super(context,textViewResourceId,objects);
//            resourceId=textViewResourceId;
//        }
//        @Override
//        public View getView(int position,View convertView,ViewGroup parent){
//            Account account=getItem(position);   //获取当前项的实例
//            View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
//            ImageView imImage=(ImageView)view.findViewById(R.id.im_list_category);
//            TextView tvMsg=(TextView) view.findViewById(R.id.tv_msg);
//            imImage.setImageResource(account.getImageId());
//            tvMsg.setText(account.getMsg());
//            return view;
//        }
//    }
//
//    /**
//     * 设置listview的内容
//     */
//    private void initMessage() {
//
//        if (ex_num != 0) {
//            switch (ex_num) {
//                case 1: {
//                    Account a = new Account(message, R.drawable.expend1);
//                    accountList.add(a);
//                }
//                case 2: {
//                    Account a = new Account(message, R.drawable.expend2);
//                    accountList.add(a);
//                }
//            }
//        }
//    }





    /**
     * 预算设置弹框
     *
     */
    public void alert_edit() {
        final EditText et = new EditText(getActivity());
        new AlertDialog.Builder(getActivity()).setTitle("请设置预算")
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //按下确定键后的事件
                        btnBudget.setText(et.getText().toString());
                        budget=et.getText().toString();
                        commitVary();
                    }
                }).setNegativeButton("取消",null).show();
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


    private void getData(View v){
        //先判断用户是否登录
        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", Activity.MODE_PRIVATE);
        String userID =sharedPreferences.getString("loginUserName", "");

        Log.i("info", "此次登录的用户是" + userID);


        //call DBOpenHelper
        DBOpenHelper helper = new DBOpenHelper(getActivity(),"qianbao.db",null,1);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.query("basicCode_tb",null,"userID=?",new String[]{userID},null,null,null);
        c.moveToLast();
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
            if (expense_date.equals(pubFun.format(Calendar.getInstance().getTime()))){
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
            c.moveToPrevious();
            System.out.println("这是第"+c.getColumnIndex("_id"));
            listitem.add(map);
            iNumber++;
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
        ListView listView = (ListView) v.findViewById(R.id.list_view);
        listView.setAdapter(adapter);



    }



}
