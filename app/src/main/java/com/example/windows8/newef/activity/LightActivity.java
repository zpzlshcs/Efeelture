package com.example.windows8.newef.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.windows8.newef.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LightActivity extends AppCompatActivity {

    private TextView xuanxiang;
    private ImageView shoudong;
    private ImageView zidong, back;
    private TextView text1;
    private TextView text2;
    private TextView text3;
    private TextView text4;
    private TextView text5;
    private TextView kt;
    private TextView wsh;
    private TextView chufang;
    private TextView yyuan;
    private TextView xiuxi;
    private TextView paidui;
    private TextView gongzuo;
    private TextView mingxiang;
    private TextView zdsd;
    boolean isChanged = false;
    @BindView(R.id.imageview)
    ImageView ivMyBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ButterKnife.bind(this);
        Glide.with(this)
                .load(R.drawable.bg_light_head)
                .into(ivMyBg);
        xuanxiang = (TextView) findViewById(R.id.lg_textView_xuanxiang);
        shoudong = (ImageView) findViewById(R.id.lgn_imageView_off1);
        zidong = (ImageView) findViewById(R.id.lgn_imageView_off2);
        text1 = (TextView) findViewById(R.id.lgn_textView1_1);
        text2 = (TextView) findViewById(R.id.lgn_textView1_2);
        text3 = (TextView) findViewById(R.id.lgn_textView1_3);
        text4 = (TextView) findViewById(R.id.lgn_textView1_4);
        text5 = (TextView) findViewById(R.id.lgn_textView1_5);
        kt = (TextView) findViewById(R.id.lgn_textView1_kt);
        wsh = (TextView) findViewById(R.id.lgn_textView1_ws);
        chufang = (TextView) findViewById(R.id.lgn_textView1_cf);
        yyuan = (TextView) findViewById(R.id.lgn_textView1_yy);
        xiuxi = (TextView) findViewById(R.id.lgn_textView1_xx);
        paidui = (TextView) findViewById(R.id.lgn_textView1_pd);
        gongzuo = (TextView) findViewById(R.id.lgn_textView1_gz);
        mingxiang = (TextView) findViewById(R.id.lgn_textView1_mx);
        zdsd = (TextView) findViewById(R.id.lg_textView3_zidong);
        back = (ImageView) findViewById(R.id.imageView_bg);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuanxiang.setText("档位1");
                zdsd.setText("手动");

                zidong.setImageResource(R.drawable.image_light_off);
                shoudong.setImageResource(R.drawable.image_light_on);
                addxiahuaxian(text1);
            }
        });

        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuanxiang.setText("档位2");
                zdsd.setText("手动");
                zidong.setImageResource(R.drawable.image_light_off);
                shoudong.setImageResource(R.drawable.image_light_on);
                addxiahuaxian(text2);
            }
        });

        text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuanxiang.setText("档位3");
                zdsd.setText("手动");
                zidong.setImageResource(R.drawable.image_light_off);
                shoudong.setImageResource(R.drawable.image_light_on);
                addxiahuaxian(text3);
            }
        });

        text4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuanxiang.setText("档位4");
                zdsd.setText("手动");
                zidong.setImageResource(R.drawable.image_light_off);
                shoudong.setImageResource(R.drawable.image_light_on);
                addxiahuaxian(text4);
            }
        });

        text5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuanxiang.setText("档位5");
                zdsd.setText("手动");
                zidong.setImageResource(R.drawable.image_light_off);
                shoudong.setImageResource(R.drawable.image_light_on);
                addxiahuaxian(text5);
            }
        });

        kt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuanxiang.setText("客厅");
                zdsd.setText("手动");
                zidong.setImageResource(R.drawable.image_light_off);
                shoudong.setImageResource(R.drawable.image_light_on);
                addxiahuaxian(kt);
            }
        });

        wsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuanxiang.setText("卧室");
                zdsd.setText("手动");
                zidong.setImageResource(R.drawable.image_light_off);
                shoudong.setImageResource(R.drawable.image_light_on);
                addxiahuaxian(wsh);
            }
        });

        chufang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuanxiang.setText("厨房");
                zdsd.setText("手动");
                zidong.setImageResource(R.drawable.image_light_off);
                shoudong.setImageResource(R.drawable.image_light_on);
                addxiahuaxian(chufang);
            }
        });

        yyuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuanxiang.setText("影院");
                zdsd.setText("手动");
                zidong.setImageResource(R.drawable.image_light_off);
                shoudong.setImageResource(R.drawable.image_light_on);
                addxiahuaxian(yyuan);
            }
        });

        xiuxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuanxiang.setText("休息");
                zdsd.setText("手动");
                zidong.setImageResource(R.drawable.image_light_off);
                shoudong.setImageResource(R.drawable.image_light_on);
                addxiahuaxian(xiuxi);
            }
        });

        paidui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuanxiang.setText("派对");
                zdsd.setText("手动");
                zidong.setImageResource(R.drawable.image_light_off);
                shoudong.setImageResource(R.drawable.image_light_on);
                addxiahuaxian(paidui);
            }
        });

        gongzuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuanxiang.setText("工作");
                zdsd.setText("手动");
                zidong.setImageResource(R.drawable.image_light_off);
                shoudong.setImageResource(R.drawable.image_light_on);
                addxiahuaxian(gongzuo);
            }
        });

        mingxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuanxiang.setText("冥想");
                zdsd.setText("手动");
                zidong.setImageResource(R.drawable.image_light_off);
                shoudong.setImageResource(R.drawable.image_light_on);
                addxiahuaxian(mingxiang);
            }
        });

        shoudong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuanxiang.setText("手动");
                zdsd.setText("自动");
                zidong.setImageResource(R.drawable.image_light_off);
                if (v == shoudong) {
                    if (isChanged) {
                        shoudong.setImageDrawable(getResources().getDrawable(R.drawable.image_light_on));
                    } else {
                        shoudong.setImageDrawable(getResources().getDrawable(R.drawable.image_light_off));
                    }
                    isChanged = !isChanged;

                }

            }
        });

        zidong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuanxiang.setText("自动");
                zdsd.setText("手动");
                shoudong.setImageResource(R.drawable.image_light_off);
                if (v == zidong) {
                    if (isChanged) {
                        zidong.setImageDrawable(getResources().getDrawable(R.drawable.image_light_off));
                    } else {
                        zidong.setImageDrawable(getResources().getDrawable(R.drawable.image_light_on));
                    }
                    isChanged = !isChanged;

                }
            }
        });

    }
    public void addxiahuaxian(TextView t) {
        text1.setBackgroundColor(Color.parseColor("#ffffff"));
        text2.setBackgroundColor(Color.parseColor("#ffffff"));
        text3.setBackgroundColor(Color.parseColor("#ffffff"));
        text4.setBackgroundColor(Color.parseColor("#ffffff"));
        text5.setBackgroundColor(Color.parseColor("#ffffff"));
        kt.setBackgroundColor(Color.parseColor("#ffffff"));
        wsh.setBackgroundColor(Color.parseColor("#ffffff"));
        chufang.setBackgroundColor(Color.parseColor("#ffffff"));
        yyuan.setBackgroundColor(Color.parseColor("#ffffff"));
        xiuxi.setBackgroundColor(Color.parseColor("#ffffff"));
        paidui.setBackgroundColor(Color.parseColor("#ffffff"));
        gongzuo.setBackgroundColor(Color.parseColor("#ffffff"));
        mingxiang.setBackgroundColor(Color.parseColor("#ffffff"));

        t.setBackgroundResource(R.drawable.bg_textviewbottom);
    }
}
