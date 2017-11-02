package com.example.windows8.newef.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.windows8.newef.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutmeActivity extends AppCompatActivity {
    @BindView(R.id.toolbar_back)
    ImageView back;
    @BindView(R.id.btn_edit_my_info)
    Button myinfo;
    private SharedPreferences information;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutme);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ButterKnife.bind(this);
        information = getSharedPreferences("information", Context.MODE_PRIVATE);
    }
    @OnClick({R.id.toolbar_back})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.toolbar_back:
                finish();
                break;
        }
    }
}
