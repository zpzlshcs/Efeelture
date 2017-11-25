package com.example.windows8.newef.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
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
import de.hdodenhof.circleimageview.CircleImageView;
import me.kareluo.ui.OptionMenu;
import me.kareluo.ui.OptionMenuView;
import me.kareluo.ui.PopupMenuView;
import me.kareluo.ui.PopupView;

public class AddFriendActivity extends AppCompatActivity {
    @BindView(R.id.iv_back)
    ImageView back;
    @BindView(R.id.addfri_recy)
    RecyclerView recyclerView;
    @BindView(R.id.addfri_refresh)
    SwipeRefreshLayout refreshLayout;
    List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
    CommonAdapter adapter;
    int[] imagehead = {R.drawable.image_head,R.drawable.image_head2,R.drawable.image_head5,R.drawable.image_head4,R.drawable.image_head1,R.drawable.image_head5};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1,GridLayoutManager.VERTICAL,false));
        adapter = new CommonAdapter<HashMap<String,Object>>(this,R.layout.item_recy_like,list) {
            @Override
            protected void convert(ViewHolder holder, final HashMap<String,Object> o, final int position) {
                ((TextView)holder.getView(R.id.like_tt_name)).setText(o.get("uid").toString());
                ((CircleImageView)holder.getView(R.id.like_cimage_head)).setImageResource(imagehead[position%6]);
                if(o.get("uid").toString().equals("")){
                    ((TextView)holder.getView(R.id.like_tt_name)).setText("匿名侠");
                }
                holder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        // 根据menu资源文件创建
                        PopupMenuView menuView = new PopupMenuView(AddFriendActivity.this, R.menu.activity_addfri_longpress, new MenuBuilder(AddFriendActivity.this.getApplicationContext()));

                        // 设置点击监听事件
                        menuView.setOnMenuClickListener(new OptionMenuView.OnOptionMenuClickListener() {
                            @Override
                            public boolean onOptionMenuClick(int position, OptionMenu menu) {
                                switch (position){
                                    case 0:
                                        String zson2  = "firstid:\"" + SharedUtil.getParam("uid","")
                                                + "\",secondid:\"" + o.get("uid")+"\"";
                                        new AsyncTask<String, Integer, JSONObject>() {
                                            @Override
                                            protected JSONObject doInBackground(String... strings) {
                                                JSONObject result2 = new getData(strings[0],AddFriendActivity.this,"1011").getResult();
                                                return result2;
                                            }
                                            @Override
                                            protected void onPostExecute(JSONObject result) {
                                                super.onPostExecute(result);
                                                if(result == null){
                                                    Toast.makeText(AddFriendActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                                                }
                                                else{
                                                    Toast.makeText(AddFriendActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                                    AddFriendActivity.this.finish();
                                                }
                                            }
                                            @Override
                                            protected void onPreExecute() {
                                                super.onPreExecute();
                                            }
                                        }.execute(zson2);
                                        break;
                                    case 1:
                                        break;
                                }
                                return true;
                            }
                        });
                        menuView.show(view);
                        menuView.setSites(PopupView.SITE_BOTTOM, PopupView.SITE_LEFT, PopupView.SITE_TOP, PopupView.SITE_RIGHT);
                        return false;
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
        new asyncTask().execute("id:\"" + SharedUtil.getParam("uid","")+"\"");
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new asyncTask().execute("id:\"" + SharedUtil.getParam("uid","")+"\"");
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
    //获取好友列表
    private class asyncTask extends AsyncTask<String, Integer, JSONObject>{
        @Override
        protected JSONObject doInBackground(String... strings) {
            JSONObject result2 = new getData(strings[0],AddFriendActivity.this,"1015").getResult();
            return result2;
        }
        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            if(result == null){
                Toast.makeText(AddFriendActivity.this, "刷新失败", Toast.LENGTH_SHORT).show();
            }
            else{
                list.clear();
                try {
                    JSONArray jsonArray = result.getJSONArray("noFriendList");
                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject likeobj = jsonArray.getJSONObject(j);
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("uname", likeobj.getString("uname"));
                        map.put("phone", likeobj.getString("phone"));
                        map.put("uid", likeobj.getString("id"));
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
