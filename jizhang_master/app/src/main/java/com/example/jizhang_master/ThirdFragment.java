package com.example.jizhang_master;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


import com.example.jizhang_master.utils.pubFun;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;


public class ThirdFragment extends Fragment {

    int[] colors = {0XFF2AE0C8,0XFFA2E1D4,0XFFACF6EF,0XFFCBF5FB,0XFFBDF3D4,0XFFE6E2C3,0XFFE3C887,0XFFFAD8BE,0XFFFBB8AC,0XFFFE6673};
    private TextView type;
    private TextView outcome;
    private LinearLayout ll_expense_piechart;
    private GraphicalView graphicalView;
    private Spinner month_spinner;
    private Spinner year_spinner;
    private ArrayAdapter<String> month_adapter;
    private ArrayAdapter<String> year_adapter;
    private Handler handler;
    private Button change;
    private static  int Type = 0;
    private ImageView ImageView;
    private static final String[] monthList = { "01月", "02月", "03月", "04月", "05月", "06月", "07月", "08月", "09月", "10月", "11月", "12月", "显示全年" };
    private static final String[] yearList = { "2018年", "2019年", "2020年", "2021年", "2022年", "2023年","2024年","2025年","2026年","2027年"};

    private void getData(double[] values,String monthID,String yearID){


            //先判断用户是否登录
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginInfo", Activity.MODE_PRIVATE);
            String userID =sharedPreferences.getString("loginUserName", "");

            Log.i("info", "此次登录的用户是" + userID);

            //call DBOpenHelper
            DBOpenHelper helper = new DBOpenHelper(getActivity(),"qianbao.db",null,1);
            SQLiteDatabase db = helper.getWritableDatabase();

            Cursor c = db.query("basicCode_tb",null,"userID=?",new String[]{userID},null,null,null);

            String strType = "";


            if(Type == 0) {
                while (c.moveToNext()) {
                    Log.i("weishenme", "dabukai");
                    String expense_category = c.getString(c.getColumnIndex("category"));
                    String expense_money = c.getString(c.getColumnIndex("cost"));
                    String expense_date = c.getString(c.getColumnIndex("makeDate"));
                    Log.i("weishenme", "dabukai");
                    Log.i("info", "cost:" + c.getString(c.getColumnIndex("cost")));
                    if (monthID.equals("13")) {
                        if (expense_date.contains(yearID) && (expense_category.equals("日用"))) {
                            values[0] = Double.parseDouble(expense_money) + values[0];
                            Log.i("nmd", String.valueOf(values[0]));
                        }
                        if (expense_date.contains(yearID) && (expense_category.equals("交通"))) {
                            values[1] = Double.parseDouble(expense_money) + values[1];
                            Log.i("nmd", String.valueOf(values[1]));
                        }
                        if (expense_date.contains(yearID) && (expense_category.equals("通讯"))) {
                            values[2] = Double.parseDouble(expense_money) + values[2];
                        }
                        if (expense_date.contains(yearID) && (expense_category.equals("娱乐"))) {
                            values[3] = Double.parseDouble(expense_money) + values[3];
                        }
                        if (expense_date.contains(yearID) && (expense_category.equals("旅行"))) {
                            values[4] = Double.parseDouble(expense_money) + values[4];
                        }
                        if (expense_date.contains(yearID) && (expense_category.equals("人情"))) {
                            values[5] = Double.parseDouble(expense_money) + values[5];
                        }
                        if (expense_date.contains(yearID) && (expense_category.equals("医疗"))) {
                            values[6] = Double.parseDouble(expense_money) + values[6];
                        }
                        if (expense_date.contains(yearID) && (expense_category.equals("其它"))) {
                            values[7] = Double.parseDouble(expense_money) + values[7];
                        }
                        if (expense_date.contains(yearID) && (expense_category.equals("购物"))) {
                            values[8] = Double.parseDouble(expense_money) + values[8];
                        }
                        if (expense_date.contains(yearID) && (expense_category.equals("餐饮"))) {
                            values[9] = Double.parseDouble(expense_money) + values[9];
                        }
                    } else {
                        if (expense_date.contains(yearID) && expense_date.contains(monthID + "-") && (expense_category.equals("日用"))) {
                            values[0] = Double.parseDouble(expense_money) + values[0];
                        }
                        if (expense_date.contains(yearID) && expense_date.contains(monthID + "-") && (expense_category.equals("交通"))) {
                            values[1] = Double.parseDouble(expense_money) + values[1];
                        }
                        if (expense_date.contains(yearID) && expense_date.contains(monthID + "-") && (expense_category.equals("通讯"))) {
                            values[2] = Double.parseDouble(expense_money) + values[2];
                        }
                        if (expense_date.contains(yearID) && expense_date.contains(monthID + "-") && (expense_category.equals("娱乐"))) {
                            values[3] = Double.parseDouble(expense_money) + values[3];
                        }
                        if (expense_date.contains(yearID) && expense_date.contains(monthID + "-") && (expense_category.equals("旅行"))) {
                            values[4] = Double.parseDouble(expense_money) + values[4];
                        }
                        if (expense_date.contains(yearID) && expense_date.contains(monthID + "-") && (expense_category.equals("人情"))) {
                            values[5] = Double.parseDouble(expense_money) + values[5];
                        }
                        if (expense_date.contains(yearID) && expense_date.contains(monthID + "-") && (expense_category.equals("医疗"))) {
                            values[6] = Double.parseDouble(expense_money) + values[6];
                        }
                        if (expense_date.contains(yearID) && expense_date.contains(monthID + "-") && (expense_category.equals("其它"))) {
                            values[7] = Double.parseDouble(expense_money) + values[7];
                        }
                        if (expense_date.contains(yearID) && expense_date.contains(monthID + "-") && (expense_category.equals("购物"))) {
                            values[8] =Double.parseDouble(expense_money) + values[8];
                        }
                        if (expense_date.contains(yearID) && expense_date.contains(monthID + "-") && (expense_category.equals("餐饮"))) {
                            values[9] = Double.parseDouble(expense_money) + values[9];
                        }
                    }
                }
            }else if(Type ==1){
                while (c.moveToNext()) {
                    String expense_category = c.getString(c.getColumnIndex("category"));
                    String expense_money = c.getString(c.getColumnIndex("cost"));
                    String expense_date = c.getString(c.getColumnIndex("makeDate"));
                    if (monthID.equals("13")) {
                        if (expense_date.contains(yearID) && (expense_category.equals("薪资"))) {
                            values[0] = Double.parseDouble(expense_money) + values[0];
                        }
                        if (expense_date.contains(yearID) && (expense_category.equals("奖金"))) {
                            values[1] = Double.parseDouble(expense_money) + values[1];
                        }
                        if (expense_date.contains(yearID) && (expense_category.equals("借款"))) {
                            values[2] = Double.parseDouble(expense_money) + values[2];
                        }
                        if (expense_date.contains(yearID) && (expense_category.equals("投资所得"))) {
                            values[3] = Double.parseDouble(expense_money) + values[3];
                        }
                        if (expense_date.contains(yearID) && (expense_category.equals("投资回收"))) {
                            values[4] = Double.parseDouble(expense_money) + values[4];
                        }
                        if (expense_date.contains(yearID) && (expense_category.equals("意外所得"))) {
                            values[5] = Double.parseDouble(expense_money) + values[5];
                        }
                        if (expense_date.contains(yearID) && (expense_category.equals("人情"))) {
                            values[6] = Double.parseDouble(expense_money) + values[6];
                        }
                        if (expense_date.contains(yearID) && (expense_category.equals("其它"))) {
                            values[7] = Double.parseDouble(expense_money) + values[7];
                        }

                    } else {
                        if (expense_date.contains(yearID) && expense_date.contains(monthID + "-") && (expense_category.equals("薪资"))) {
                            values[0] = Double.parseDouble(expense_money) + values[0];
                        }
                        if (expense_date.contains(yearID) && expense_date.contains(monthID + "-") && (expense_category.equals("奖金"))) {
                            values[1] =Double.parseDouble(expense_money) + values[1];
                        }
                        if (expense_date.contains(yearID) && expense_date.contains(monthID + "-") && (expense_category.equals("借款"))) {
                            values[2] = Double.parseDouble(expense_money) + values[2];
                        }
                        if (expense_date.contains(yearID) && expense_date.contains(monthID + "-") && (expense_category.equals("投资所得"))) {
                            values[3] = Double.parseDouble(expense_money) + values[3];
                        }
                        if (expense_date.contains(yearID) && expense_date.contains(monthID + "-") && (expense_category.equals("投资回收"))) {
                            values[4] = Double.parseDouble(expense_money) + values[4];
                        }
                        if (expense_date.contains(yearID) && expense_date.contains(monthID + "-") && (expense_category.equals("意外所得"))) {
                            values[5] = Double.parseDouble(expense_money) + values[5];
                        }
                        if (expense_date.contains(yearID) && expense_date.contains(monthID + "-") && (expense_category.equals("人情"))) {
                            values[6] = Double.parseDouble(expense_money) + values[6];
                        }
                        if (expense_date.contains(yearID) && expense_date.contains(monthID + "-") && (expense_category.equals("其它"))) {
                            values[7] = Double.parseDouble(expense_money) + values[7];
                        }
                    }
                }
            }
            c.close();
            db.close();


    }



    public ThirdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        double[] values1 = {0,0,0,0,0,0,0,0,0,0};

        double[] values2 = {0,0,0,0,0,0,0,0};
        int[] x={1};
        View v = inflater.inflate(R.layout.fragment_two, container, false);
        type = v.findViewById(R.id.textView3);
        outcome=(TextView) v.findViewById(R.id.outcome);
        change = v.findViewById(R.id.btn_change);
        change.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                if(Type ==0){
                    type.setText("收入");
                    Type =1;
                }else if(Type ==1){
                    type.setText("支出");
                    Type =0;
                }
                handler.sendEmptyMessage(0);

            }
        });
        SharedPreferences tt = getActivity().getSharedPreferences("month", Activity.MODE_PRIVATE);
        String monthID =tt.getString("monthid", "");
        SharedPreferences year = getActivity().getSharedPreferences("year", Activity.MODE_PRIVATE);
        String yearID =year.getString("yearid", "");
        initSpinner(v);
        //initSpinner2(v);
        int monthid=Integer.parseInt(monthID)+1;
        monthID=Integer.toString(monthid);
        System.out.println(monthID+"verynice!");
        if (Type == 0) {
            getData(values1, monthID, yearID);
            initPieChart(v, values1);
            resetpic(v);
        }else if(Type == 1){
            getData(values2, monthID, yearID);
            initPieChart(v, values2);
            resetpic(v);
        }
        return v;
    }


    /**
     * 生成饼图
     */
    private void resetpic(final View v){
        handler=new Handler(Looper.myLooper()){
            @SuppressLint("ResourceAsColor")
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                SharedPreferences year = getActivity().getSharedPreferences("year", Activity.MODE_PRIVATE);
                String yearID =year.getString("yearid", "");
                SharedPreferences month = getActivity().getSharedPreferences("month", Activity.MODE_PRIVATE);
                String monthID =month.getString("monthid", "");
                double[] values1 = {0,0,0,0,0,0,0,0,0,0};
                double[] values2 = {0,0,0,0,0,0,0,0};
                //getData(values,Integer.toString(msg.what),yearID);
                if(msg.what==0){
                    double sum = 0;
                    if(Type == 0) {
                        getData(values1, monthID, yearID);
                        initPieChart(v, values1);
                        for (int i = 0; i < 10; ++i) {
                            sum += values1[i];
                        }
                    }else if(Type == 1) {
                        getData(values2, monthID, yearID);
                        initPieChart(v, values2);
                        for (int i = 0; i < 8; ++i) {
                            sum += values2[i];
                        }
                    }
                    outcome.setText(Double.toString(sum));
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
            String monthID=Integer.toString(pos+1);
            //实例化SharedPreferences对象
            SharedPreferences month = getActivity().getSharedPreferences("month", Activity.MODE_PRIVATE);
            SharedPreferences.Editor hello = month.edit();//实例化SharedPreferences.Editor对象
            hello.putString("monthid", Integer.toString(pos+1)); //用putString的方法保存数据
            hello.commit(); //提交当前数据*/

            handler.sendEmptyMessage(0);
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
    private void initPieChart(View v,double[] values){
        CategorySeries dataset = buildCategoryDataset("图文报表", values);
        int count =0;
        if(Type ==0) {
            for (int i = 0; i < 10; i++) {
                if (values[i] != 0)
                    count++;
            }
        }else {
            for (int i = 0; i < 8; i++) {
            if (values[i] != 0)
                count++;
        }
        }
        int[] new_colors=new int[count];
        for (int i =0;i<count;i++){
            new_colors[i]=colors[i];
        }
        DefaultRenderer renderer = buildCategoryRenderer(new_colors);

        ll_expense_piechart = (LinearLayout) v.findViewById(R.id.ll_expense_piechart);
        ll_expense_piechart.removeAllViews();
        double sum=0;

        if(Type == 0) {
            for (int i = 0; i < 10; ++i) {
                sum += values[i];
            }
        }else{
            for (int i = 0; i < 8; ++i) {
                sum += values[i];
            }
        }
        if(sum!=0){
            graphicalView = ChartFactory.getPieChartView(getContext()
                    ,dataset, renderer);//饼状图
            graphicalView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

            ll_expense_piechart.addView(graphicalView);
        }
        else{

            ImageView = v.findViewById(R.id.textView);
            ImageView.setBackgroundResource(R.drawable.lazy1);

        }

        /**graphicalView = ChartFactory.getPieChartView(getContext()
                ,dataset, renderer);//饼状图
        graphicalView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        ll_expense_piechart.addView(graphicalView); */
    }

    /**
     * 构建数据集
     * @param title
     * @param values
     * @return
     */


    protected CategorySeries buildCategoryDataset(String title, double[] values){
        CategorySeries series = new CategorySeries(title);
        if(Type == 0) {
            double sumVal = values[0] + values[1] + values[2] + values[3] + values[4] + values[5] + values[6] + values[7] + values[8] + values[9];
            if (values[0] != 0)
                series.add("日用:" + values[0], values[0] / sumVal);
            if (values[1] != 0)
                series.add("交通:" + values[1], values[1] / sumVal);
            if (values[2] != 0)
                series.add("通讯:" + values[2], values[2] / sumVal);
            if (values[3] != 0)
                series.add("娱乐:" + values[3], values[3] / sumVal);
            if (values[4] != 0)
                series.add("旅行:" + values[4], values[4] / sumVal);
            if (values[5] != 0)
                series.add("人情:" + values[5], values[5] / sumVal);
            if (values[6] != 0)
                series.add("医疗:" + values[6], values[6] / sumVal);
            if (values[7] != 0)
                series.add("其它:" + values[7], values[7] / sumVal);
            if (values[8] != 0)
                series.add("购物:" + values[8], values[8] / sumVal);
            if (values[9] != 0)
                series.add("餐饮:" + values[9], values[9] / sumVal);
        }
        else{
            double sumVal = values[0] + values[1] + values[2] + values[3] + values[4] + values[5] + values[6] + values[7] ;
            if (values[0] != 0)
                series.add("薪资:" + values[0], values[0] / sumVal);
            if (values[1] != 0)
                series.add("奖金:" + values[1], values[1] / sumVal);
            if (values[2] != 0)
                series.add("借款:" + values[2], values[2] / sumVal);
            if (values[3] != 0)
                series.add("投资所得:" + values[3], values[3] / sumVal);
            if (values[4] != 0)
                series.add("投资回收:" + values[4], values[4] / sumVal);
            if (values[5] != 0)
                series.add("意外所得:" + values[5], values[5] / sumVal);
            if (values[6] != 0)
                series.add("人情:" + values[6], values[6] / sumVal);
            if (values[7] != 0)
                series.add("其它:" + values[7], values[7] / sumVal);
        }
        return series;
    }



    /**
     * 构建渲染器
     * @param colors
     * @return
     */
    protected DefaultRenderer buildCategoryRenderer(int[] colors){
        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setLabelsTextSize(50);//饼图上标记文字的字体大小
        renderer.setLabelsColor(Color.BLACK);//饼图上标记文字的颜色
        renderer.setPanEnabled(false);
        renderer.setShowLegend(false);
        //renderer.setDisplayValues(true);//显示数据


        for(int color : colors){
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(color);
            //设置百分比
            //r.setChartValuesFormat(NumberFormat.getPercentInstance());
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }
}

