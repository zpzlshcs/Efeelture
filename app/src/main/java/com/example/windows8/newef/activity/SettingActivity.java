package com.example.windows8.newef.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.windows8.newef.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {
    @BindView(R.id.btn_exit)
    Button exit;
    @BindView(R.id.iv_back)
    ImageView back;
    @BindView(R.id.setting_tt_changepw)
    TextView changepw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.btn_exit,R.id.iv_back,R.id.setting_tt_changepw})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_exit:
                SharedPreferences user = getSharedPreferences("information", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = user.edit();
                editor.putString("uid", "");
                editor.putString("uname", "");
                editor.putString("phone", "");
                editor.putString("password", "");
                editor.commit();
                SharedPreferences islogin = getSharedPreferences("islogin", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = islogin.edit();
                editor2.putString("islogin", "0");
                editor2.commit();
                Intent login = new Intent(SettingActivity.this,LoginActivity.class);
                startActivity(login);
                finish();
                MainActivity.mainActivity.finish();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.setting_tt_changepw:
                Intent changepassword = new Intent(this,ChangePasswordActivity.class);
                startActivity(changepassword);
        }
    }
}
