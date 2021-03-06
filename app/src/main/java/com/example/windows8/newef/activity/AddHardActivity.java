package com.example.windows8.newef.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.windows8.newef.R;
import com.example.windows8.newef.util.SharedUtil;
import com.example.windows8.newef.util.getData;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddHardActivity extends AppCompatActivity {
    @BindView(R.id.iv_back)
    ImageView back;
    @BindView(R.id.addfri_recy)
    RecyclerView recyclerView;
    @BindView(R.id.addfri_refresh)
    SwipeRefreshLayout refreshLayout;
    List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
    CommonAdapter adapter;
    private long clickTime = 0;
    int[] imagehead = {R.drawable.image_icebox,R.drawable.image_tv,R.drawable.image_wind,R.drawable.image_light};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hard);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1,GridLayoutManager.VERTICAL,false));
        adapter = new CommonAdapter<HashMap<String,Object>>(this,R.layout.item_recy_hard,list) {
            @Override
            protected void convert(ViewHolder holder, final HashMap<String,Object> o, final int position) {
                ((TextView)holder.getView(R.id.hard_tt_id)).setText(o.get("id").toString());
                ((ImageView)holder.getView(R.id.hard_image)).setImageResource(imagehead[position%4]);
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SystemClock.uptimeMillis() - clickTime <= 1500) {
                            String zson2  = "uid:\"" + SharedUtil.getParam("uid","")
                                    + "\",hardwareid:\"" + o.get("id")+"\"";
                            new AsyncTask<String, Integer, JSONObject>() {
                                @Override
                                protected JSONObject doInBackground(String... strings) {
                                    JSONObject result2 = new getData(strings[0],AddHardActivity.this,"1021").getResult();
                                    return result2;
                                }
                                @Override
                                protected void onPostExecute(JSONObject result) {
                                    super.onPostExecute(result);
                                    if(result == null){
                                        Toast.makeText(AddHardActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(AddHardActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                protected void onPreExecute() {
                                    super.onPreExecute();
                                }
                            }.execute(zson2);
                            clickTime = 0;

                        } else {
                            // 当前系统时间的毫秒值
                            clickTime = SystemClock.uptimeMillis();
                            Toast.makeText(AddHardActivity.this, "再次点击添加", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
        new asyncTask().execute("utype:\"" + 0+"\"");
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new asyncTask().execute("utype:\"" + 0+"\"");
            }
        });
    }
    @OnClick({R.id.iv_back})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
    //获取当前设备列表
    private class asyncTask extends AsyncTask<String, Integer, JSONObject>{
        @Override
        protected JSONObject doInBackground(String... strings) {
            JSONObject result2 = new getData(strings[0],AddHardActivity.this,"1099").getResult();
            return result2;
        }
        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            if(result == null){
                Toast.makeText(AddHardActivity.this, "刷新失败", Toast.LENGTH_SHORT).show();
            }
            else{
                list.clear();
                try {
                    JSONArray jsonArray = result.getJSONArray("userList");
                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject likeobj = jsonArray.getJSONObject(j);
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("id", likeobj.getString("id"));
                        map.put("hname", likeobj.getString("uname"));
                        map.put("hipaddress", likeobj.getString("ipaddress"));
                        list.add(map);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
            }
            refreshLayout.setRefreshing(false);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    };
}
