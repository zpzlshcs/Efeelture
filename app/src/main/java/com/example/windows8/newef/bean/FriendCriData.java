package com.example.windows8.newef.bean;

import java.util.ArrayList;

/**
 * Created by windows8 on 2017/11/22.
 */

public class FriendCriData {
    String messageid;
    String sendid;
    String content;
    ArrayList<replyData> replyDatas;
    ArrayList<likeData> likeDatas;
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getSendid() {
        return sendid;
    }

    public void setSendid(String sendid) {
        this.sendid = sendid;
    }
    public String getMessageid() {
        return messageid;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    public ArrayList<replyData> getReplyDatas() {
        return replyDatas;
    }

    public void setReplyDatas(ArrayList<replyData> replyDatas) {
        this.replyDatas = replyDatas;
    }

    public ArrayList<likeData> getLikeDatas() {
        return likeDatas;
    }

    public void setLikeDatas(ArrayList<likeData> likeDatas) {
        this.likeDatas = likeDatas;
    }
    public void remove(int index,int type){
        if(type == 1){
            replyDatas.remove(index);
        }else{
            likeDatas.remove(index);
        }
    }
    public void add(Object o){
        if(o instanceof replyData){
            replyDatas.add((replyData) o);
        }
        if(o instanceof likeData){
            likeDatas.add((likeData) o);
        }
    }
}
