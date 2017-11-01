package com.example.windows8.newef.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.windows8.newef.R;
import com.example.windows8.newef.bean.chatcontent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by windows8 on 2017/10/22.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<chatcontent> mData;
    String uid;
    Context mContext;
    public ChatAdapter(List<chatcontent> mData,String uid,Context mContext){
        this.mData = mData;
        this.uid = uid;
        this.mContext = mContext;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 1){
            View right = LayoutInflater.from(mContext).inflate(R.layout.item_chat_text_right, parent, false);
            rightholder rh = new rightholder(right);
            return  rh;
        }
        else {
            View left = LayoutInflater.from(mContext).inflate(R.layout.item_chat_text_left, parent, false);
            rightholder lh = new rightholder(left);
            return  lh;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof rightholder){
            ((rightholder)holder).content.setText(mData.get(position).getContent());
        }
        else{
            ((leftholder)holder).content.setText(mData.get(position).getContent());
        }
    }
    class leftholder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_chatcontent)
        TextView content;
        public leftholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    class rightholder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_chatcontent)
        TextView content;
        public rightholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    @Override
    public int getItemViewType(int position) {
        if(mData.get(position).getSendid().equals(uid))return 1;
        return 0;
    }
}
