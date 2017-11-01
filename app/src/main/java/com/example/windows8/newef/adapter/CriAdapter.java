package com.example.windows8.newef.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.windows8.newef.R;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.windows8.newef.R.id.likepeople;

/**
 * Created by windows8 on 2017/10/30.
 */

public class CriAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<HashMap<String,Object>> mData;
    Context mContext;
    String uid;
    public CriAdapter(Context mContext,List<HashMap<String,Object>> mData,String uid){
        this.mContext = mContext;
        this.mData = mData;
        this.uid = uid;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 1){
            View v = LayoutInflater.from(mContext).inflate(R.layout.item_recy_cri, parent, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }
        else {
            View v = LayoutInflater.from(mContext).inflate(R.layout.item_recy_foot, parent, false);
            footHolder viewHolder = new footHolder(v);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof ViewHolder){
            final HashMap<String,Object> map = mData.get(position);
            final String messageid = map.get("messageid").toString();
            final  List<HashMap<String,Object>> like = ( List<HashMap<String,Object>>)map.get("likeData");
            final  List<HashMap<String,Object>> reply = ( List<HashMap<String,Object>>)map.get("replyData");
            ((ViewHolder)holder).ttname.setText(map.get("secondid").toString());
            ((ViewHolder)holder).tttime.setText(map.get("time").toString());
            ((ViewHolder)holder).ttlikenum.setText(map.get("likenumber").toString());
            ((ViewHolder)holder).ttcontent.setText(map.get("content").toString());
            ((ViewHolder)holder).llreply.removeAllViews();
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            ((ViewHolder)holder).iblike.setTag(0);//点过赞设置为1，没有则设置为0
            if(map.get("likeid").equals("")) ((ViewHolder)holder).iblike.setImageResource(R.drawable.ic_blackheart);
            else ((ViewHolder)holder).iblike.setImageResource(R.drawable.ic_redheart);
            ((ViewHolder)holder).iblike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setClickable(false);
                    if(view.getTag().equals(0)){
                        criListener.addLike(messageid,view);
                        criListener.refreshlikenum(messageid,((ViewHolder)holder).ttlikenum);
                    }
                    else {
                        criListener.deleteLike(map.get("likeid").toString(),view);
                        criListener.refreshlikenum(messageid,((ViewHolder)holder).ttlikenum);
                    }
                }
            });
            for(HashMap<String,Object> replydata :reply){
                addChildView(((ViewHolder)holder).llreply,replydata);
            }
        }
        else{

        }
    }

    @Override
    public int getItemCount() {
        return mData.size()+1;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.cri_item_reply)
        Button pressreply;
        @BindView(R.id.replyroom)
        LinearLayout llreply;
        @BindView(R.id.cri_item_textView_content)
        TextView ttcontent;
        @BindView(R.id.cri_item_textview_name)
        TextView ttname;
        @BindView(R.id.cri_item_textView1)
        TextView ttlikenum;
        @BindView(R.id.cri_item_likeButton1)
        ImageView iblike;
        @BindView(R.id.cri_item_textview_time)
        TextView tttime;
        @BindView(likepeople)
        LinearLayout lllikepeople;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    class footHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.foot_ll)
        LinearLayout footll;
        @BindView(R.id.foot_text)
        TextView foottext;
        public footHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public void addChildView(LinearLayout v,HashMap<String,Object> replydata){
        View view = LayoutInflater.from(v.getContext()).inflate(R.layout.item_ll_reply,v,false);
        v.addView(view);
        TextView name = (TextView)view.findViewById(R.id.reply_text_name);
        TextView content = (TextView)view.findViewById(R.id.reply_text_content);
        name.setText(replydata.get("firstid").toString());
        content.setText(replydata.get("content").toString());
    }
    @Override
    public int getItemViewType(int position) {
        if(position == mData.size()) return 0;
        return 1;
    }
    public interface CriListener{
        void addLike(String messageid,View v);
        void deleteLike(String likeid,View v);
        void addreply(String messageid,String content,View v);
        void deletereply(String replyid,View v);
        void refreshlikenum(String messageid,TextView v);

    }
    private CriListener criListener;
    public void setCriListener(CriListener l){
        criListener = l;
    }
}
