package com.example.windows8.newef.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.windows8.newef.R;
import com.example.windows8.newef.adapter.ChatAdapter;
import com.example.windows8.newef.bean.chatcontent;
import com.example.windows8.newef.util.SharedUtil;
import com.example.windows8.newef.util.saveData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity {
    public static String talkto = "";
    @BindView(R.id.recycle_chat)
    RecyclerView recyclerView;
    @BindView(R.id.text_send)
    TextView send;
    @BindView(R.id.edit_chat)
    EditText sendcontent;
    @BindView(R.id.chat_talkto)
    TextView chattalkto;
    @BindView(R.id.iv_back)
    ImageView back;
    String uid;
    saveData chatmessages;
    ChatAdapter adapter;
    ArrayList<chatcontent> list = new ArrayList<chatcontent>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        chattalkto.setText(talkto);
        uid = SharedUtil.getParam("uid","").toString();
        chatmessages = new saveData(ChatActivity.this, "chatmessages");
        list = chatmessages.getDataList(uid + talkto);
        adapter = new ChatAdapter(list,uid,ChatActivity.this);
        recyclerView.setAdapter(adapter);
        GridLayoutManager mLayoutManager=new GridLayoutManager(ChatActivity.this,1,GridLayoutManager.VERTICAL,false);//设置为一个1列的纵向网格布局
        recyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        chatmessages.setDataList(uid+talkto,list);
    }
    @OnClick({R.id.text_send,R.id.iv_back})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.text_send:
                if(sendcontent.getText().toString().length()!=0){
                    chatcontent newsend = new chatcontent();
                    newsend.setContent(sendcontent.getText().toString());
                    newsend.setFile(false);
                    newsend.setIspicture(false);
                    newsend.setToid(talkto);
                    newsend.setSendid(uid);
                    list.add(newsend);
                    adapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(adapter.getItemCount()-1);
                    sendcontent.setText("");
                }
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
