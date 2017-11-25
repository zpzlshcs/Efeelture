package com.example.windows8.newef.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.windows8.newef.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class LikesActivity extends AppCompatActivity {
    @BindView(R.id.iv_back)
    ImageView back;
    @BindView(R.id.likes_recy)
    RecyclerView recyclerView;
    public static List<HashMap<String,Object>> likeData = new ArrayList<HashMap<String,Object>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likes);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1,GridLayoutManager.VERTICAL,false));
        final int[] imagehead = {R.drawable.image_head,R.drawable.image_head2,R.drawable.image_head5,R.drawable.image_head4,R.drawable.image_head1,R.drawable.image_head5};
        recyclerView.setAdapter(new CommonAdapter<HashMap<String,Object>>(this,R.layout.item_recy_like,likeData) {
            @Override
            protected void convert(ViewHolder holder, HashMap<String,Object> o, final int position) {
                ((TextView)holder.getView(R.id.like_tt_name)).setText(o.get("uid").toString());
                ((CircleImageView)holder.getView(R.id.like_cimage_head)).setImageResource(imagehead[position%6]);
                if(o.get("uid").toString().equals("")){
                    ((TextView)holder.getView(R.id.like_tt_name)).setText("匿名侠");
                }
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
}
