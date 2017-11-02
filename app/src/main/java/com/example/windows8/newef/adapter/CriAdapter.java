package com.example.windows8.newef.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.windows8.newef.R;
import com.example.windows8.newef.activity.LikesActivity;
import com.example.windows8.newef.custom.getData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
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
    int[] imagehead = {R.drawable.image_head,R.drawable.image_head2,R.drawable.image_head5,R.drawable.image_head4,R.drawable.image_head1,R.drawable.image_head5};
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof ViewHolder){
            final HashMap<String,Object> map = mData.get(position);
            final String messageid = map.get("messageid").toString();
            final  List<HashMap<String,Object>> like = ( List<HashMap<String,Object>>)map.get("likeData");
            final  List<HashMap<String,Object>> reply = ( List<HashMap<String,Object>>)map.get("replyData");
            ((ViewHolder)holder).ttname.setText(map.get("secondid").toString());
            if(map.get("secondid").toString().length() == 0){
                ((ViewHolder)holder).ttname.setText("匿名侠");
            }
            ((ViewHolder)holder).tttime.setText(map.get("time").toString());
            ((ViewHolder)holder).head.setImageResource(imagehead[position%6]);
            ((ViewHolder)holder).ttlikenum.setText(map.get("likenumber").toString());
            ((ViewHolder)holder).ttcontent.setText(map.get("content").toString());
            ((ViewHolder)holder).llreply.removeAllViews();
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;     //布局比较复杂，再次设置高度自适应
            ((ViewHolder)holder).iblike.setEnabled(true);
            ((ViewHolder)holder).iblike.setTag(0);//点过赞设置为1，没有则设置为0
            if(map.get("likeid").equals("")) {
                ((ViewHolder)holder).iblike.setImageResource(R.drawable.ic_blackheart);
            }
            else {
                ((ViewHolder)holder).iblike.setImageResource(R.drawable.ic_redheart);
                ((ViewHolder)holder).iblike.setTag(1);
            }
            ((ViewHolder)holder).iblike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {  //点赞按钮设置监听
                    view.setClickable(false);
                    if(view.getTag().equals(0)){
                        new ConstactAsyncTask().execute(messageid,"0",position+"","");
                    }
                    else {
                        new ConstactAsyncTask().execute(messageid,"1",position+"",map.get("likeid").toString());
                    }
                }
            });
            for(HashMap<String,Object> replydata :reply){       //根据回复有多少条就添加多少条回复
                addChildView(((ViewHolder)holder).llreply,replydata);
            }
            ((ViewHolder)holder).lllikepeople.setOnClickListener(new View.OnClickListener() {  //点击点赞人数区域跳转到详情
                @Override
                public void onClick(View view) {
                    LikesActivity.likeData = like;
                    Intent likes = new Intent(mContext,LikesActivity.class);
                    mContext.startActivity(likes);
                }
            });
            ((ViewHolder)holder).pressreply.setOnClickListener(new View.OnClickListener() {    //点击回复弹窗
                @Override
                public void onClick(View view) {
                    final Dialog bottomDialog = new Dialog(mContext, R.style.BottomDialog);
                    View contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_edittext, null);
                    bottomDialog.setContentView(contentView);
                    ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
                    layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
                    contentView.setLayoutParams(layoutParams);
                    bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
                    bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
                    bottomDialog.setCanceledOnTouchOutside(true);
                    bottomDialog.show();
                    final EditText edit = (EditText)contentView.findViewById(R.id.dialog_edit);
                    edit.setFocusable(true);
                    InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    TextView send = (TextView)contentView.findViewById(R.id.dialog_send);
                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {               //点击回复
                            if(edit.getText().length()!=0){
                                try {
                                    new ConstactAsyncTask().execute(messageid,"2",position+"", URLEncoder.encode(edit.getText().toString(), "UTF-8"));
                                    bottomDialog.dismiss();
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            });
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
        @BindView(R.id.cri_item_head)
        ImageView head;
        @BindView(R.id.cri_rl)
        RelativeLayout crirl;
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
        String namestr = replydata.get("firstid").toString();
        if(replydata.get("firstid").toString().length()==0){
            namestr = "匿名侠";
        }
        name.setText(namestr+":");
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
        void refreshlikenum(String messageid,int position);
        void refreshreply(String messageid,int position);

    }
    private CriListener criListener;
    public void setCriListener(CriListener l){
        criListener = l;
    }
    private class ConstactAsyncTask extends AsyncTask<String, Integer, List<Object>> {   //异步加载数据

        @Override
        protected List<Object> doInBackground(String... arg0) {    //第一个数据为messageid第二个为进行的操作类型，第三个为操作的item，第四个为likeid或replyid
            String messageid = arg0[0];
            String type = arg0[1];
            int position = Integer.parseInt(arg0[2]);
            List<Object> getresult = new ArrayList<Object>();
            List<HashMap<String,Object>> getLike = new ArrayList<HashMap<String,Object>>();
            List<HashMap<String,Object>> getReply = new ArrayList<HashMap<String,Object>>();

            getresult.add(getLike);
            getresult.add(getReply);
            getresult.add(position);
            switch (type){
                case "0" : //点赞
                    String zson0 = "messageid:\"" + messageid
                            + "\",uid:\"" + uid + "\"";
                    JSONObject result0 = new getData(zson0,mContext,"1041").getResult();
                    if(result0 == null){
                        getresult.add("点赞失败");
                        return getresult;
                    }
                    break;
                case "1": //取消赞
                    String zson1 = "id:\"" + arg0[3] + "\"";
                    JSONObject result1 = new getData(zson1,mContext,"1042").getResult();
                    if(result1 == null){
                        getresult.add("取消失败");
                        return getresult;
                    }
                    break;
                case "2": //添加回复
                    String zson2 = "messageid:\"" + messageid
                            + "\",content:\"" + arg0[3]
                            + "\",secondid:\"" + uid
                            + "\",firstid:\"" + uid + "\"";
                    JSONObject result2 = new getData(zson2,mContext,"1061").getResult();
                    if(result2 == null){
                        getresult.add("回复失败");
                        return getresult;
                    }
                    break;
                case "3": //删除回复
                    break;
            }
            try{
                //获取点赞信息
                String zsonlike =  "messageid:\""+messageid+"\"";
                JSONObject resultlike = new getData(zsonlike,mContext,"1043").getResult();
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
                String zsonreply =  "messageid:\""+messageid+"\"";
                JSONObject resultreply = new getData(zsonreply,mContext,"1063").getResult();
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
                        if(replymap.get("firstid").equals(uid)) {
                            replymap.put("ismine","1");
                        }
                        else replymap.put("ismine","0");
                        getReply.add(replymap);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            getresult.add("");
            return getresult;
        }
        @Override
        protected void onPostExecute(List<Object> result) {
            super.onPostExecute(result);
            if(result.get(3).toString()!=""){
                Toast.makeText(mContext, result.get(3).toString(), Toast.LENGTH_SHORT).show();
                return;
            }
            int position = (int)result.get(2);
            List<HashMap<String,Object>> getLike = (List<HashMap<String,Object>>)result.get(0);
            List<HashMap<String,Object>> getReply = (List<HashMap<String,Object>>)result.get(1);
            mData.get(position).put("islike","0");
            mData.get(position).put("likeid","");
            for(HashMap<String,Object> map :getLike){
                if(map.get("uid").toString().equals(uid)){
                    mData.get(position).put("islike","1");
                    mData.get(position).put("likeid",map.get("likeid"));
                }
            }
            mData.get(position).put("likenumber",getLike.size());
            mData.get(position).put("likeData",getLike);
            mData.get(position).put("replyData",getReply);

            notifyItemChanged(position);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

    }
}
