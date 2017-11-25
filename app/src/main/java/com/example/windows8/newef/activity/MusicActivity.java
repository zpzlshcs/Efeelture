package com.example.windows8.newef.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.windows8.newef.R;
import com.example.windows8.newef.service.MusicService;
import com.example.windows8.newef.adapter.MyAadapter;
import com.example.windows8.newef.bean.LrcModel;
import com.example.windows8.newef.bean.Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MusicActivity extends AppCompatActivity implements View.OnClickListener{


    int img[] = { R.drawable.pic_11, R.drawable.pic_11, R.drawable.pic_11, R.drawable.pic_11,
            R.drawable.pic_11, R.drawable.pic_11 };


    ListView list;

    MyAadapter adapter;

    Saomiao saomiao;
    ArrayList<Model> alist;
    //
    MediaPlayer media;
    // seekbar
    SeekBar se;
    ImageView nextm,lastm,playm,style,b_back;


    Boolean isplay=false;

    Boolean isgeci=false;

    int min = 0;
    int sec = 0;
    int min1 = 0;
    int sec1 = 0;
    int check=0;
    Handler hander = new Handler();
    //
    TextView time;
    TextView time2;
    String star;
    String all;
    Intent mintent;
    Mybroad myBD;

    TextView geci,showgeci,nowmusic;
    //	ImageView
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        requestReadExternalPermission();
        list=(ListView) findViewById(R.id.list);
        saomiao=new Saomiao();

        alist=new ArrayList<Model>();
        alist=saomiao.query(this);
        adapter=new MyAadapter(alist, this);
        list.setAdapter(adapter);


        se=(SeekBar) findViewById(R.id.se);
        media=new MediaPlayer();
        myBD=new Mybroad();

        time=(TextView) findViewById(R.id.sec);
        time2=(TextView) findViewById(R.id.min);
        nextm=(ImageView) findViewById(R.id.xia);
        lastm=(ImageView) findViewById(R.id.shang);
        playm=(ImageView) findViewById(R.id.play);
        style=(ImageView) findViewById(R.id.line);
        geci=(TextView) findViewById(R.id.geci);
        showgeci=(TextView) findViewById(R.id.showgeci);
        nowmusic=(TextView) findViewById(R.id.nowmusic);


        nextm.setOnClickListener(this);
        lastm.setOnClickListener(this);
        playm.setOnClickListener(this);
        style.setOnClickListener(this);
        geci.setOnClickListener(this);

        showgeci.setVisibility(View.GONE);
        b_back = (ImageButton) findViewById(R.id.img_1);
        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicActivity.this.finish();

            }
        });
        //启动服务�?
        Intent mintent=new Intent();
        mintent.setClass(MusicActivity.this, MusicService.class);
        startService(mintent);

        zhuce();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub

                isplay=true;

                playm.setImageResource(R.drawable.dianji_20);

                showgeci.setText("歌词");


                Intent intent=new Intent();
                intent.setAction("ACTION_INDEX");
                intent.putExtra("index", arg2);
                sendBroadcast(intent);



            }
        });
//

        se.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                if(fromUser==true)
                {
                    Intent intent=new Intent();
                    intent.setAction("ACTION_SEEKBAR");
                    intent.putExtra("seekbar", progress);
                    sendBroadcast(intent);
                }



            }
        });


    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.shang:

                nextmusic(0);

                lastm.setImageResource(R.drawable.dianji_22);

                break;
            case R.id.xia:

                nextmusic(1);

                nextm.setImageResource(R.drawable.dianji_25);

                break;
            case R.id.geci:
                isgeci=!isgeci;
                if(isgeci==true){
                    geci.setTextColor(Color.rgb(255, 255, 0));
                    showgeci.setVisibility(View.VISIBLE);
                }else{
                    geci.setTextColor(Color.rgb(255, 255, 255));
                    showgeci.setVisibility(View.GONE);
                }


                break;
            case R.id.line:

                check++;
                if(check>3)
                    check=1;
                if(check==1){
                    style.setImageResource(R.drawable.dianji_28);
                    stylemusic(1);
                }
                if(check==2){
                    style.setImageResource(R.drawable.dianji1_28);
                    stylemusic(2);
                }
                if(check==3){
                    style.setImageResource(R.drawable.pic2_28);
                    stylemusic(3);
                }


                break;
            case R.id.play:

                isplay=!isplay;
                if(isplay==true)
                {
                    playm.setImageResource(R.drawable.dianji_20);
                }else{
                    playm.setImageResource(R.drawable.dianji_19);
                }
                musicplay(isplay);

                break;

            default:
                break;
        }

    }
    //发�?�，播放，暂停广�?
    public void musicplay(boolean isplay){
        Intent intent=new Intent();
        intent.setAction("ACTION_ISPLAY");
        intent.putExtra("isplay",isplay);
        sendBroadcast(intent);
    }

    //上一曲，下一曲，0上一曲，1下一�?
    public void nextmusic(int num){
        showgeci.setText("歌词");

        Intent intent=new Intent();
        intent.setAction("ACTION_NEXT");
        intent.putExtra("next",num);
        sendBroadcast(intent);
    }

    public void stylemusic(int num){
        Intent intent=new Intent();
        intent.setAction("ACTION_STYLE");
        intent.putExtra("check",num);
        sendBroadcast(intent);
    }



    class Mybroad extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if(intent.getAction().equals("ACTION_MAXTIME")){
                int maxtime=intent.getIntExtra("maxtime", 0);

                se.setMax(maxtime);
                time2.setText(""+settime(maxtime));
            }
            if(intent.getAction().equals("ACTION_NOWTIME")){
                int nowtime=intent.getIntExtra("nowtime", 0);

                se.setProgress(nowtime);
                time.setText(""+settime(nowtime));
            }
            if(intent.getAction().equals("ACTION_LRC")){
                String lrc=intent.getStringExtra("geci");
                //Toast.makeText(MainActivity.this, "剧本�?�?"+lrc, Toast.LENGTH_LONG).show();

                showgeci.setText(""+lrc);


            }
            if(intent.getAction().equals("ACTION_NOWMUSIC")){
                String nowmusic1=intent.getStringExtra("nowmusic");
                //Toast.makeText(MainActivity.this, "剧本�?�?"+lrc, Toast.LENGTH_LONG).show();

                nowmusic.setText("正在播放�?"+nowmusic1);


            }

        }

    }
    public void zhuce() {
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction("ACTION_MAXTIME");
        mFilter.addAction("ACTION_NOWTIME");
        mFilter.addAction("ACTION_NOWMUSIC");
        mFilter.addAction("ACTION_LRC");
        registerReceiver(myBD, mFilter);
    }
    public String settime(int time){
        int fen=time/60000;
        int miao=time/1000%60;
        return fen+":"+miao;
    }
    class Saomiao {
        public ArrayList<Model> query(Context mcontext) {

            ArrayList<Model> arraylist = null;
            //扫描->游标
            Cursor c = mcontext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);

            if (c != null) {
                //
                arraylist = new ArrayList<Model>();

                //

                while (c.moveToNext()) {


                    Model model;
                    model = new Model();

                    String music_name = c.getString(c.getColumnIndex(MediaStore.Audio.Media.TITLE));

                    String singer = c.getString(c.getColumnIndex(MediaStore.Audio.Media.ARTIST));

                    String path = c.getString(c.getColumnIndex(MediaStore.Audio.Media.DATA));

                    //System.out.println(""+music_name);
                    //System.out.println(""+singer);
                    //System.out.println(""+path);

                    model.setMusic_name(music_name);
                    model.setSinger(singer);
                    model.setPath(path);

                    arraylist.add(model);

                }


            }
            return arraylist;

        }

        public ArrayList<LrcModel> redlrc(String path) {

            ArrayList<LrcModel> alist = new ArrayList<LrcModel>();
            File f = new File(path.replace(".mp3", ".lrc"));

            try {
                FileInputStream fs = new FileInputStream(f);

                InputStreamReader inputstreamreader = new InputStreamReader(fs, "utf-8");

                BufferedReader br = new BufferedReader(inputstreamreader);
                String s = "";

                while (null != (s = br.readLine())) {

                    if (!TextUtils.isEmpty(s)) {
                        LrcModel lrcmodel = new LrcModel();

                        String lylrc = s.replace("[", "");

                        String data_ly[] = lylrc.split("]");
                        if (data_ly.length > 1) {

                            String time = data_ly[0];
                            lrcmodel.setTime(lrcdata(time));

                            String lrc = data_ly[1];
                            lrcmodel.setIrc(lrc);
                            //System.out.println("..............."+lrcmodel.getIrc());


                            alist.add(lrcmodel);
                            //System.out.println(".................."+alist.get(0).getIrc());

                        }
                    }

                }


            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return alist;

        }

        public int lrcdata(String time) {
            time = time.replace(":", "#");
            time = time.replace(".", "#");

            String mTime[] = time.split("#");

            int mtime = Integer.parseInt(mTime[0]);
            int stime = Integer.parseInt(mTime[1]);
            int mitime = Integer.parseInt(mTime[2]);

            int ctime = (mtime * 60 + stime) * 1000 + mitime * 10;

            return ctime;


        }
    }
    private static String TAG = "MusicActivity";
    @SuppressLint("NewApi")
    private void requestReadExternalPermission() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "READ permission IS NOT granted...");

            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Log.d(TAG, "11111111111111");
            } else {
                // 0 是自己定义的请求coude
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                Log.d(TAG, "222222222222");
            }
        } else {
            Log.d(TAG, "READ permission is granted...");
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.d(TAG, "requestCode=" + requestCode + "; --->" + permissions.toString()
                + "; grantResult=" + grantResults.toString());
        switch (requestCode) {
            case 0: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted
                    // request successfully, handle you transactions

                } else {

                    // permission denied
                    // request failed
                }

                return;
            }
            default:
                break;

        }
    }
    @Override
    protected void onDestroy (){
        super.onDestroy();
        if(myBD!=null){
            unregisterReceiver(myBD);
            myBD = null;
        }
    }

}
