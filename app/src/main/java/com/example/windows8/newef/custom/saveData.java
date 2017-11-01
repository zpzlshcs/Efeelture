package com.example.windows8.newef.custom;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.windows8.newef.bean.chatcontent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class saveData {  
    private SharedPreferences preferences;  
    private SharedPreferences.Editor editor;  
  
    public saveData(Context mContext, String preferenceName) {  
        preferences = mContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);  
        editor = preferences.edit();
    }  
  
    /** 
     * 保存List 
     * @param tag 
     * @param datalist 
     */  
    public void setDataList(String tag, ArrayList<chatcontent> datalist) {
        if (null == datalist || datalist.size() <= 0)  
            return;  
  
        Gson gson = new Gson();  
        //转换成json数据，再保存  
        String strJson = gson.toJson(datalist);  
        editor.clear();  
        editor.putString(tag, strJson);  
        editor.commit();  
  
    }  
  
    /** 
     * 获取List 
     * @param tag 
     * @return 
     */  
    public ArrayList<chatcontent> getDataList(String tag) {  
    	ArrayList<chatcontent> datalist=new ArrayList<chatcontent>();  
        String strJson = preferences.getString(tag, null);  
        if (null == strJson) {  
            return datalist;  
        }  
        Gson gson = new Gson();  
        datalist = gson.fromJson(strJson, new TypeToken<ArrayList<chatcontent>>() {  
        }.getType());  
        return datalist;  
  
    }  
} 
