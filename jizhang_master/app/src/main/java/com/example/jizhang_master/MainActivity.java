package com.example.jizhang_master;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;

import static com.example.jizhang_master.VaryActivity.budget;
import static com.example.jizhang_master.VaryActivity.expend;
import static com.example.jizhang_master.VaryActivity.income;

public class MainActivity extends AppCompatActivity {
    //未选中的Tab图片
    private int[] unSelectTabRes = new int[]{R.drawable.icon_first_1
            , R.drawable.icon_second_1, R.drawable.icon_third_1, R.drawable.icon_fourth_1, R.drawable.icon_fifth_1};
    //选中的Tab图片
    private int[] selectTabRes = new int[]{R.drawable.icon_first_2, R.drawable.icon_second_2
            , R.drawable.icon_third_2, R.drawable.icon_fourth_2, R.drawable.icon_fifth_2};
    //Tab标题
    private String[] title = new String[]{"首页", "账单", "报表", "新闻", "我的"};
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//隐藏掉整个ActionBar
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
        initVary();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void initVary(){
        Calendar calendar = Calendar.getInstance();
        //年
        int year = calendar.get(Calendar.YEAR);
        //月
        int month = calendar.get(Calendar.MONTH)+1;


        SharedPreferences preferences = getSharedPreferences("share", MODE_PRIVATE);
        SharedPreferences sp = getSharedPreferences("vary", MODE_PRIVATE);
        int buff_year = preferences.getInt("year",2000);
        int buff_month = preferences.getInt("month",1);
        if(!(buff_month == month && buff_year == year)){
            SharedPreferences.Editor editor=preferences.edit();
            //存入boolean类型的登录状态
            editor.putInt("year", year);
            //存入登录状态时的用户名
            editor.putInt("month", month);
            //提交修改
            editor.commit();
            SharedPreferences.Editor editor1=sp.edit();
            editor1.clear();
            editor1.commit();
            budget="";
            expend="";
            income="";
        }
    }



    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager_content_view);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout_view);

        //使用适配器将ViewPager与Fragment绑定在一起
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
        //将TabLayout与ViewPager绑定
        tabLayout.setupWithViewPager(viewPager);


        for (int i = 0; i < title.length; i++) {
            if (i == 0) {
                tabLayout.getTabAt(0).setIcon(selectTabRes[0]);
            } else {
                tabLayout.getTabAt(i).setIcon(unSelectTabRes[i]);
            }
        }




    }
    private void initData() {
    }
    private void initListener() {
        //TabLayout切换时导航栏图片处理
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {//选中图片操作
                for (int i = 0; i < title.length; i++) {
                    if (tab == tabLayout.getTabAt(i)) {
                        tabLayout.getTabAt(i).setIcon(selectTabRes[i]);
                        viewPager.setCurrentItem(i,false);
                    }
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {//未选中图片操作
                for (int i = 0; i < title.length; i++) {
                    if (tab == tabLayout.getTabAt(i)) {
                        tabLayout.getTabAt(i).setIcon(unSelectTabRes[i]);
                    }
                }
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
    //自定义适配器
    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            if (position == 1) {
                return new SecondFragment();//账单
            } else if (position == 2) {
                return new ThirdFragment();//报表
            } else if (position == 3) {
                return new FourthFragment();//新闻
            }else if (position == 4){
                return new FifthFragment();//我的
            }
            return new FirstFragment();//首页
        }
        @Override
        public int getCount() {
            return title.length;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }
}
