package com.example.windows8.newef.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.windows8.newef.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePasswordActivity extends AppCompatActivity {
    @BindView(R.id.iv_back)
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.iv_back})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
