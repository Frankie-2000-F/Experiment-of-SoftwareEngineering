package com.example.jizhang_master;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;




public class FifthFragment extends Fragment {
    private Button btn_calculator;
    private Button btn_exit;
    private Button btn_scan;
    private Button btn_mortgage;
    private Button btn_weixin;
    private Button btn_alipay;
    private Button btn_bankCard;
    private Button btn_campusCard;
    private TextView tv_userID;
    private String userID;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fifth, null);
        btn_calculator = view.findViewById(R.id.btn_calculator);
        btn_scan = view.findViewById(R.id.btn_scan);
        btn_exit = view.findViewById(R.id.btn_exit);
        btn_mortgage = view.findViewById(R.id.btn_mortgage);
        btn_weixin = view.findViewById(R.id.btn_weixin);
        btn_alipay = view.findViewById(R.id.btn_alipay);
        btn_bankCard = view.findViewById(R.id.btn_bankCard);
        btn_campusCard = view.findViewById(R.id.btn_campusCard);
        tv_userID = view.findViewById(R.id.tv_userID);
        SharedPreferences preferences = getActivity().getSharedPreferences("loginInfo", 0);
        userID = preferences.getString("loginUserName","");
        tv_userID.setText(userID);
        initAccount();
        return view;
    }

    public void initAccount(){
        DBOpenHelper helper = new DBOpenHelper(getActivity(),"qianbao.db",null,1);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query("user_tb", new String[]{"weixin","alipay","bankCard","campusCard"},"userID=?", new String[]{userID},null,null,null);
        String weixin_money=null;
        String alipay_money=null;
        String bankCard_money=null;
        String campusCard_money=null;
        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            weixin_money = cursor.getString(cursor.getColumnIndex("weixin"));
            alipay_money = cursor.getString(cursor.getColumnIndex("alipay"));
            bankCard_money = cursor.getString(cursor.getColumnIndex("bankCard"));
            campusCard_money = cursor.getString(cursor.getColumnIndex("campusCard"));
        }
        cursor.close();
        db.close();
        if(weixin_money!=null && !weixin_money.equals(""))
            btn_weixin.setText("微信:"+weixin_money+"元");
        if(alipay_money!=null && !alipay_money.equals(""))
            btn_alipay.setText("支付宝:"+alipay_money+"元");
        if(bankCard_money!=null && !bankCard_money.equals(""))
            btn_bankCard.setText("银行卡:"+bankCard_money+"元");
        if(campusCard_money!=null && !campusCard_money.equals(""))
            btn_campusCard.setText("校园卡:"+campusCard_money+"元");

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FindScanActivity.class));
            }
        });
        btn_calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CalculatorActivity.class));
            }
        });
        btn_mortgage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MortgageActivity.class));
            }
        });
        btn_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert_edit(btn_weixin,"微信");
            }
        });
        btn_alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert_edit(btn_alipay,"支付宝");
            }
        });
        btn_bankCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert_edit(btn_bankCard,"银行卡");
            }
        });
        btn_campusCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert_edit(btn_campusCard,"校园卡");
            }
        });
    }

    /**
     * 账户余额设置弹框
     *
     */
    public void alert_edit(final Button btn, final String account) {
        final EditText et = new EditText(getActivity());
        new AlertDialog.Builder(getActivity()).setTitle("请设置"+account+"余额")
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //按下确定键后的事件
                        btn.setText(account+":"+et.getText().toString()+"元");
                        saveAccount(et.getText().toString(),account);

                    }
                }).setNegativeButton("取消",null).show();
    }

    public void saveAccount(String money,String account){
        DBOpenHelper helper = new DBOpenHelper(getActivity(),"qianbao.db",null,1);
        SQLiteDatabase db = helper.getWritableDatabase();
        if(account.equals("微信"))
            db.execSQL("update user_tb set weixin=? where userID=?",new Object[]{money,userID});
        else if (account.equals("支付宝"))
            db.execSQL("update user_tb set alipay=? where userID=?",new Object[]{money,userID});
        else if (account.equals("银行卡"))
            db.execSQL("update user_tb set bankCard=? where userID=?",new Object[]{money,userID});
        else if (account.equals("校园卡"))
            db.execSQL("update user_tb set campusCard=? where userID=?",new Object[]{money,userID});

        db.close();
    }




}
