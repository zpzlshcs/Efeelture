package com.example.windows8.newef.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.windows8.newef.R;
import com.example.windows8.newef.custom.CodeUtils;
import com.example.windows8.newef.custom.getData;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.edittext_phone)EditText phone;
    @BindView(R.id.edittext_password)EditText password;
    @BindView(R.id.edittext_code)EditText code;
    @BindView(R.id.button_login)Button login;
    @BindView(R.id.button_register)Button register;
    @BindView(R.id.button_forget)Button forget;
    @BindView(R.id.button_anonymous)Button anonymous;
    @BindView(R.id.image_code)ImageView codeimage;
    private String strphone = "";
    private String strpassword = "";
    private String strcode = "";
    private CodeUtils codeUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ButterKnife.bind(this);
        SharedPreferences user = getSharedPreferences("information", Context.MODE_PRIVATE);
        strpassword = user.getString("password", "");
        strphone = user.getString("phone", "");
        phone.setText(strphone);
        password.setText(strpassword);
        codeUtils = CodeUtils.getInstance();
        Bitmap bitmap = codeUtils.createBitmap();
        codeimage.setImageBitmap(bitmap);
        codeimage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO 自动生成的方法存根
                codeUtils = CodeUtils.getInstance();
                Bitmap bitmap = codeUtils.createBitmap();
                codeimage.setImageBitmap(bitmap);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO 自动生成的方法存根
                strcode = code.getText().toString().trim();
                Log.e("strcode", strcode);
                if (null == strcode || TextUtils.isEmpty(strcode)) {

                    Toast.makeText(LoginActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();

                    return;
                }
                String code = codeUtils.getCode();
                Log.e("code", code);
                if (code.equalsIgnoreCase(strcode)) {
                    new Thread() {
                        public void run() {
                            strphone = phone.getText().toString();
                            strpassword = password.getText().toString();
                            Map<String,String> map = new HashMap<String, String>();
                            map.put("phone",strphone);
                            map.put("upassword",strpassword);
                            try {
                                String zson = "phone:\"" + URLEncoder.encode(strphone, "UTF-8")
                                        + "\",upassword:\"" + URLEncoder.encode(strpassword, "UTF-8") + "\"";
                                JSONObject result = new getData(zson,LoginActivity.this,"1093").getResult();
                                if(result == null){
                                    Looper.prepare();
                                    Toast.makeText(LoginActivity.this, "用户名或密码不正确", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                                else{
                                    SharedPreferences islogin = getSharedPreferences("islogin", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor2 = islogin.edit();
                                    editor2.putString("islogin", "1");
                                    editor2.commit();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    LoginActivity.this.finish();
                                }
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }

                        };
                    }.start();

                } else {

                    Toast.makeText(LoginActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();

                }
            }
        });
        anonymous.setOnClickListener(new View.OnClickListener() {// 匿名登录
            @Override
            public void onClick(View v) {
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

                Intent intent2 = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent2);
                LoginActivity.this.finish();

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent2);

            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        SharedPreferences islogin = getSharedPreferences("islogin", Context.MODE_PRIVATE);
        String isalreadlylogin = islogin.getString("islogin", "");
        if(isalreadlylogin.equals("1")){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();
        }
    }
    // 给返回键安排操作
    private long clickTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (SystemClock.uptimeMillis() - clickTime <= 1500) {

                // 如果两次的时间差＜1s，就不执行操作

            } else {
                // 当前系统时间的毫秒值
                clickTime = SystemClock.uptimeMillis();
                Toast.makeText(LoginActivity.this, "再次点击退出", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
