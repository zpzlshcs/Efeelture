package com.example.windows8.newef.util;

import android.util.Log;
import android.widget.Toast;

import com.example.windows8.newef.MyApplication;


/**
 * Created by Administrator on 2017/5/8.
 */

public class ShowUtil {
    private static final String TAG = "mytag";
    public static void toast(String text){
        Toast.makeText(MyApplication.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public static void print(String text){
        if(text != null) {
            Log.d(TAG, text);
        }
    }

    public static void print(String key, String text){
        if(text != null && key != null) {
            Log.d(TAG, key + ":------ " + text);
        }
    }

    public static void print(String key, int text){
        if(key != null) {
            Log.d(TAG, key + ":------ " + text);
        }
    }

}
