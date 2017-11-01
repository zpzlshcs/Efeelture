package com.example.windows8.newef.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.windows8.newef.R;
import com.example.windows8.newef.custom.PickerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WindActivity extends AppCompatActivity {
    @BindView(R.id.imageview)
    ImageView ivMyBg;
    PickerView minute_pv;
    private boolean isVisible = true;
    private LinearLayout layout_1;
    private ImageView zilengonoff,zhireonoff,choushionoff,shoudongfxonoff,zidongfxonoff,shoudngfsonoff,
            zidongfsonoff,windup,winddown,windleft,windright,windlevel,back;
    private TextView zhileng_re;
    boolean isChanged = false;
    boolean iswind = false;
    boolean iswind2 = false;
    boolean iswind3 = false;
    int numb = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wind);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        ButterKnife.bind(this);

        Glide.with(this)
                .load(R.drawable.bg_wind_head)
                .into(ivMyBg);
        zilengonoff = (ImageView)findViewById(R.id.imageView_zloff1);
        zhireonoff = (ImageView)findViewById(R.id.imageView_zroff1);
        choushionoff = (ImageView)findViewById(R.id.imageView_csoff2);
        shoudongfxonoff = (ImageView)findViewById(R.id.imageView_off1);
        zidongfxonoff = (ImageView)findViewById(R.id.imageView_off2);
        shoudngfsonoff = (ImageView)findViewById(R.id.imageView_off3);
        zidongfsonoff = (ImageView)findViewById(R.id.imageView_off4);
        windup = (ImageView)findViewById(R.id.imageView_1);
        winddown = (ImageView)findViewById(R.id.imageView_2);
        windleft = (ImageView)findViewById(R.id.imageView_3);
        windright = (ImageView)findViewById(R.id.imageView_4);
        windlevel = (ImageView)findViewById(R.id.imageView_level);
        zhileng_re = (TextView)findViewById(R.id.textView_xuanxiang);
        minute_pv = (PickerView) findViewById(R.id.minute_pv);
        back = (ImageView)findViewById(R.id.imageView_bg);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindActivity.this.finish();
            }
        });


        zilengonoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                zhireonoff.setImageResource(R.drawable.ic_airoff);
                if(v == zilengonoff)
                {
                    if(isChanged){
                        zilengonoff.setImageDrawable(getResources().getDrawable(R.drawable.ic_airoff));
                    }else
                    {
                        zilengonoff.setImageDrawable(getResources().getDrawable(R.drawable.ic_airon));
                        zhileng_re.setText("制冷");
                    }
                    isChanged = !isChanged;

                }
            }
        });




        zhireonoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                zilengonoff.setImageResource(R.drawable.ic_airoff);
                if(v == zhireonoff)
                {
                    if(!isChanged){
                        zhireonoff.setImageDrawable(getResources().getDrawable(R.drawable.ic_airoff));
                    }else
                    {
                        zhireonoff.setImageDrawable(getResources().getDrawable(R.drawable.ic_airon));
                        zhileng_re.setText("制热");
                    }
                    isChanged = !isChanged;

                }
            }
        });




        shoudongfxonoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                zidongfxonoff.setImageResource(R.drawable.ic_airoff);
                if(v == shoudongfxonoff)
                {
                    if(iswind){
                        shoudongfxonoff.setImageDrawable(getResources().getDrawable(R.drawable.ic_airoff));
                    }else
                    {
                        shoudongfxonoff.setImageDrawable(getResources().getDrawable(R.drawable.ic_airon));

                    }
                    iswind = !iswind;

                }
            }
        });




        zidongfxonoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                windup.setImageResource(R.drawable.ic_windup1);
                winddown.setImageResource(R.drawable.ic_winddown1);
                windleft.setImageResource(R.drawable.ic_windleft1);
                windright.setImageResource(R.drawable.ic_windright1);
                shoudongfxonoff.setImageResource(R.drawable.ic_airoff);
                if(v == zidongfxonoff)
                {
                    if(!iswind){
                        zidongfxonoff.setImageDrawable(getResources().getDrawable(R.drawable.ic_airoff));
                    }else
                    {
                        zidongfxonoff.setImageDrawable(getResources().getDrawable(R.drawable.ic_airon));

                    }
                    iswind = !iswind;

                }
            }
        });




        shoudngfsonoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                zidongfsonoff.setImageResource(R.drawable.ic_airoff);
                if(v == shoudngfsonoff)
                {
                    if(iswind2){
                        shoudngfsonoff.setImageDrawable(getResources().getDrawable(R.drawable.ic_airoff));
                    }else
                    {
                        shoudngfsonoff.setImageDrawable(getResources().getDrawable(R.drawable.ic_airon));

                    }
                    iswind2 = !iswind2;

                }
            }
        });




        zidongfsonoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                shoudngfsonoff.setImageResource(R.drawable.ic_airoff);
                if(v == zidongfsonoff)
                {
                    if(!iswind2){
                        zidongfsonoff.setImageDrawable(getResources().getDrawable(R.drawable.ic_airoff));
                    }else
                    {
                        zidongfsonoff.setImageDrawable(getResources().getDrawable(R.drawable.ic_airon));

                    }
                    iswind2 = !iswind2;

                }
            }
        });



        windup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                windup.setImageResource(R.drawable.ic_windup);
                winddown.setImageResource(R.drawable.ic_winddown1);
                windleft.setImageResource(R.drawable.ic_windleft1);
                windright.setImageResource(R.drawable.ic_windright1);
                shoudongfxonoff.setImageResource(R.drawable.ic_airon);
                zidongfxonoff.setImageResource(R.drawable.ic_airoff);

            }
        });
        winddown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                windup.setImageResource(R.drawable.ic_windup1);
                winddown.setImageResource(R.drawable.ic_winddown);
                windleft.setImageResource(R.drawable.ic_windleft1);
                windright.setImageResource(R.drawable.ic_windright1);
                shoudongfxonoff.setImageResource(R.drawable.ic_airon);
                zidongfxonoff.setImageResource(R.drawable.ic_airoff);

            }
        });
        windleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                windup.setImageResource(R.drawable.ic_windup1);
                winddown.setImageResource(R.drawable.ic_winddown1);
                windleft.setImageResource(R.drawable.ic_windleft);
                windright.setImageResource(R.drawable.ic_windright1);
                shoudongfxonoff.setImageResource(R.drawable.ic_airon);
                zidongfxonoff.setImageResource(R.drawable.ic_airoff);

            }
        });
        windright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                windup.setImageResource(R.drawable.ic_windup1);
                winddown.setImageResource(R.drawable.ic_winddown1);
                windleft.setImageResource(R.drawable.ic_windleft1);
                windright.setImageResource(R.drawable.ic_windright);
                shoudongfxonoff.setImageResource(R.drawable.ic_airon);
                zidongfxonoff.setImageResource(R.drawable.ic_airoff);
            }
        });


        windlevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                zidongfsonoff.setImageResource(R.drawable.ic_airoff);
                shoudngfsonoff.setImageResource(R.drawable.ic_airon);

                switch (numb) {
                    case 0:
                        windlevel.setImageResource(R.drawable.ic_windlevel1);
                        numb++;
                        break;
                    case 1:
                        windlevel.setImageResource(R.drawable.ic_windlevel2);
                        numb++;
                        break;
                    case 2:
                        windlevel.setImageResource(R.drawable.ic_windlevel3);
                        numb = 0;
                        break;

                }
            }
        });



        choushionoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(v == choushionoff)
                {

                    if(iswind3){
                        choushionoff.setImageDrawable(getResources().getDrawable(R.drawable.ic_airoff));
                    }else
                    {
                        choushionoff.setImageDrawable(getResources().getDrawable(R.drawable.ic_airon));

                    }
                    iswind3 = !iswind3;

                }
            }
        });



        List<String> data = new ArrayList<String>();
        layout_1 = (LinearLayout) findViewById(R.id.linearLayout_id);
        layout_1.setVisibility(View.GONE);//这一句即隐藏布局LinearLayout区域

        //点击触发的图标
        ImageView more = (ImageView) findViewById(R.id.imageView_xiala);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVisible) {
                    isVisible = false;
                    layout_1.setVisibility(View.VISIBLE);//这一句显示布局LinearLayout区域
                } else {
                    layout_1.setVisibility(View.GONE);//这一句即隐藏布局LinearLayout区域
                    isVisible = true;
                }
            }
        });

        for (int i = 16; i < 31; i++)
        {
            data.add("" + i);//添加da
        }
        minute_pv.setData(data);
        minute_pv.setOnSelectListener(new PickerView.onSelectListener()
        {

            @Override
            public void onSelect(String text)
            {
                Toast.makeText(WindActivity.this, "选择了 " + text + " ℃",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
