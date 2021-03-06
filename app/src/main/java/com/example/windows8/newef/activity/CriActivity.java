package com.example.windows8.newef.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.windows8.newef.R;
import com.example.windows8.newef.adapter.CriAdapter;
import com.example.windows8.newef.util.SharedUtil;
import com.example.windows8.newef.util.getData;
import com.example.windows8.newef.view.RecyScroll;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CriActivity extends AppCompatActivity {
    @BindView(R.id.recy_cri)
    RecyclerView recyclerView;
    @BindView(R.id.cri_swipeRefreshLayout)
    SwipeRefreshLayout criswipeRefreshLayout;
    @BindView(R.id.imageView_bg)
    ImageView back;
    @BindView(R.id.imageView_add)
    ImageView add;
    List<HashMap<String,Object>> list = new ArrayList<HashMap<String, Object>>();
    List<HashMap<String,Object>> allData = new ArrayList<HashMap<String, Object>>();
    CriAdapter adapter;
    private String uid;
    private int crinum = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cri);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ButterKnife.bind(this);
        uid = SharedUtil.getParam("uid", "").toString();
        GridLayoutManager mLayoutManager=new GridLayoutManager(CriActivity.this,1,GridLayoutManager.VERTICAL,false);//设置为一个1列的纵向网格布局
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new CriAdapter(this,list,uid);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyScroll(mLayoutManager) {  //如果有则加载更多数据
            @Override
            public void onLoadMore(int currentPage) {
                if(allData.size()>0){
                    list.add(allData.get(0));
                    allData.remove(0);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        criswipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {  //下拉加载更多
            @Override
            public void onRefresh() {
                new ConstactAsyncTask().execute("");
            }
        });
        new ConstactAsyncTask().execute("");
    }
    //获取朋友圈，及点赞、回复信息
    private class ConstactAsyncTask extends AsyncTask<String, Integer, List<HashMap<String,Object>>> {   //异步加载数据

        @Override
        protected List<HashMap<String,Object>> doInBackground(String... arg0) {
            List<HashMap<String,Object>> getresult = new ArrayList<HashMap<String,Object>>();
            List<HashMap<String,Object>> getLike = new ArrayList<HashMap<String,Object>>();
            List<HashMap<String,Object>> getReply = new ArrayList<HashMap<String,Object>>();
            try {
                //获取点赞信息
                String zsonlike =  "messageid:\"\"";
                JSONObject resultlike = new getData(zsonlike,CriActivity.this,"1043").getResult();
                if(resultlike != null){
                    JSONArray likeJsonArray = resultlike.getJSONArray("likeList");
                    for (int j = 0; j < likeJsonArray.length(); j++) {
                        JSONObject likeobj = likeJsonArray.getJSONObject(j);
                        HashMap<String, Object> likemap = new HashMap<String, Object>();
                        likemap.put("messageid", likeobj.getString("messageid"));
                        likemap.put("likeid", likeobj.getString("id"));
                        likemap.put("uid", likeobj.getString("uid"));
                        getLike.add(likemap);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                //获取回复信息
                String zsonreply =  "messageid:\"\"";
                JSONObject resultreply = new getData(zsonreply,CriActivity.this,"1063").getResult();
                if(resultreply != null){
                    JSONArray replyJSONArry = resultreply.getJSONArray("replyList");
                    for (int j = 0; j < replyJSONArry.length(); j++) {
                        JSONObject replyobj = replyJSONArry.getJSONObject(j);
                        HashMap<String, Object> replymap = new HashMap<String, Object>();
                        replymap.put("messageid", replyobj.getString("messageid"));
                        replymap.put("replyid", replyobj.getString("id"));
                        replymap.put("firstid", replyobj.getString("firstid"));
                        replymap.put("secondid", replyobj.getString("secondid"));
                        replymap.put("content", replyobj.getString("content"));
                        getReply.add(replymap);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                String zson = "";
                JSONObject result = new getData(zson,CriActivity.this,"1054").getResult();
                if(result != null){
                    JSONArray resultJsonArray = result.getJSONArray("messageList");
                    for (int i = 0; i < resultJsonArray.length(); i++) {
                        JSONObject jsonObject = resultJsonArray.getJSONObject(i);
                        HashMap<String, Object> hashMap = new HashMap<String, Object>();
                        hashMap.put("messageid", jsonObject.getString("id"));
                        hashMap.put("content", jsonObject.getString("content"));
                        hashMap.put("likenumber", jsonObject.getString("likenumber"));
                        hashMap.put("secondid", jsonObject.getString("uid"));
                        hashMap.put("time", jsonObject.getString("mtime"));
                        hashMap.put("islike","0");
                        hashMap.put("likeid","");
                        //获取点赞信息
                        List<HashMap<String,Object>> likeData = new ArrayList<HashMap<String,Object>>();
                        for(HashMap<String,Object> likemap:getLike){
                            if(likemap.get("messageid").equals(jsonObject.getString("id"))){
                                likeData.add(likemap);
                                if(likemap.get("uid").equals(uid)) {
                                    hashMap.put("islike","1");
                                    hashMap.put("likeid",likemap.get("likeid"));
                                }
                            }
                        }
                        hashMap.put("likenumber",likeData.size());
                        hashMap.put("likeData",likeData);
                        //获取回复信息
                        List<HashMap<String,Object>> replyData = new ArrayList<HashMap<String,Object>>();
                        for(HashMap<String,Object> replymap:getReply){
                            if(replymap.get("messageid").equals(jsonObject.getString("id"))){
                                replyData.add(replymap);
                                if(replymap.get("firstid").equals(uid)) {
                                    replymap.put("ismine","1");
                                }
                                else replymap.put("ismine","0");
                            }
                        }
                        hashMap.put("replyData",replyData);
                        getresult.add(hashMap);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return getresult;
        }
        @Override
        protected void onPostExecute(List<HashMap<String,Object>> result) {
            super.onPostExecute(result);
            if (result.size()!=0) {
                allData = result;
                list.clear();
                for(int i = 0; i <result.size() ; i++){

                    if(i==10)break;
                    list.add(result.get(i));
                    allData.remove(i);
                }
                adapter.notifyDataSetChanged();
            }
            else{
                Toast.makeText(CriActivity.this, "获取动态失败", Toast.LENGTH_SHORT).show();
            }
            criswipeRefreshLayout.setRefreshing(false);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

    }
    private Handler refreshlike = new Handler(){
        @Override
        public void handleMessage(Message msg){
            adapter.notifyDataSetChanged();
        }
    };
    @OnClick({R.id.imageView_bg,R.id.imageView_add})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.imageView_bg:
                finish();
                break;
            case R.id.imageView_add:
                Intent intent = new Intent(this,AddCriActivity.class);
                startActivity(intent);
                break;
        }
    }
}
