package com.example.windows8.newef.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.windows8.newef.R;
import com.example.windows8.newef.custom.getData;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterNextActivity extends AppCompatActivity {
    @BindView(R.id.register_edit_name)
    EditText edname;
    @BindView(R.id.register_edit_password)
    EditText edpassword;
    @BindView(R.id.register_edit_repassword)
    EditText edrepassword;
    @BindView(R.id.register_button_next)
    Button next;
    @BindView(R.id.iv_back)
    ImageView back;
    public static String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_next);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.iv_back,R.id.register_button_next})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.register_button_next:
                if(edname.getText().length()<3){
                    Toast.makeText(this,"用户名必须大于6位",Toast.LENGTH_SHORT).show();
                }
                else if(!edpassword.getText().toString().equals(edrepassword.getText().toString()) ){
                    Toast.makeText(this,"两次输入密码不一致",Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        String zson2 = "phone:\"" + URLEncoder.encode(phone, "UTF-8")
                                + "\",upassword:\"" + URLEncoder.encode(edpassword.getText().toString(), "UTF-8")
                                + "\",uname:\"" + URLEncoder.encode(edname.getText().toString(), "UTF-8") + "\"";
                        new AsyncTask<String, Integer, JSONObject>() {
                            @Override
                            protected JSONObject doInBackground(String... strings) {
                                JSONObject result2 = new getData(strings[0], RegisterNextActivity.this, "1096").getResult();
                                return result2;
                            }

                            @Override
                            protected void onPostExecute(JSONObject result) {
                                super.onPostExecute(result);
                                if (result == null) {
                                    Toast.makeText(RegisterNextActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(RegisterNextActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                    RegisterNextActivity.this.finish();
                                }
                            }

                            @Override
                            protected void onPreExecute() {
                                super.onPreExecute();
                            }
                        }.execute(zson2);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
