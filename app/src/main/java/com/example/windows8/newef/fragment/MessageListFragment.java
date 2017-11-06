package com.example.windows8.newef.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.menu.MenuBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.windows8.newef.R;
import com.example.windows8.newef.activity.ChatActivity;
import com.example.windows8.newef.adapter.chatlistadapter;
import com.example.windows8.newef.bean.chatcontent;
import com.example.windows8.newef.custom.VerticalSwipeRefreshLayout;
import com.example.windows8.newef.custom.saveData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.kareluo.ui.OptionMenu;
import me.kareluo.ui.OptionMenuView;
import me.kareluo.ui.PopupMenuView;
import me.kareluo.ui.PopupView;

/**
 * Created by windows8 on 2017/10/11.
 */

public class MessageListFragment extends Fragment {

    @BindView(R.id.chatlist_swipelistview)
    ListView list1;
    public static chatlistadapter simp_adapter = null;
    public static List<HashMap<String, Object>> Data = new ArrayList<HashMap<String, Object>>();
    private String[] talktolist;
    private String friendid = "";
    public static String talkto;
    private String uid;
    saveData chatmessages;
    @BindView(R.id.message_container)
    VerticalSwipeRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_messagelist,container,false);
        unbinder = ButterKnife.bind(this, view);
        chatmessages = new saveData(getActivity(), "chatmessages");
        SharedPreferences information = getActivity().getSharedPreferences("information", Context.MODE_PRIVATE);
        uid = information.getString("uid", "");
        Data.clear();
        SharedPreferences talklist = getActivity().getSharedPreferences("talklist", Context.MODE_PRIVATE);// 获取聊天人列表
        talkto = talklist.getString(uid, "");
        talktolist = talkto.split(",");
        if (talkto != "") {
            Data = stirngtolist(talkto);
        }
        if (Data.size() == 0) {
            Toast.makeText(getActivity(), "暂无聊天", Toast.LENGTH_SHORT).show();
        }
        simp_adapter = new chatlistadapter(getActivity(), Data, uid);
        list1.setAdapter(simp_adapter);
        simp_adapter.setMlistener(new chatlistadapter.listlistener() {   //点击事件和长点击事件监听
            @Override
            public void itemclick(int i) {
                friendid = Data.get(i).get("text").toString();
                ChatActivity.talkto = friendid;
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                startActivity(intent);
            }

            @Override
            public void itemlongclick(final int i,View v) {
                // 根据menu资源文件创建
                PopupMenuView menuView = new PopupMenuView(getActivity(), R.menu.messagefragment_longpress, new MenuBuilder(getContext()));

                // 设置点击监听事件
                menuView.setOnMenuClickListener(new OptionMenuView.OnOptionMenuClickListener() {
                    @Override
                    public boolean onOptionMenuClick(int position, OptionMenu menu) {
                        switch (position){
                            case 0:
                                chatmessages.setDataList(uid+Data.get(i),new ArrayList<chatcontent>());
                                Data.remove(i);
                                simp_adapter.notifyDataSetChanged();
                                saveData(Data);
                                break;
                            case 1:
                                break;
                        }
                        return true;
                    }
                });
                menuView.show(v);
                menuView.setSites(PopupView.SITE_BOTTOM, PopupView.SITE_LEFT, PopupView.SITE_TOP, PopupView.SITE_RIGHT);
            }
        });
        // 给下拉刷新设置监听
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            public void onRefresh() {
                // TODO Auto-generated method stub
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {// while pushing down need to do

                        // 下拉之后需要做的事情放在这里
                        refresh();
                        simp_adapter.notifyDataSetChanged();
                        // TODO Auto-generated method stub
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1500);
            }
        });

        return view;
    }
    public List<HashMap<String, Object>> stirngtolist(String s){
        List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < talktolist.length; i++) {
            if(talktolist[i].length()!=0){
                HashMap<String, Object> map1 = new HashMap<String, Object>();
                map1.put("image", R.drawable.side_nav_bar);
                map1.put("text", talktolist[i]);
                map1.put("text2", "在做什么？");
                result.add(map1);
            }
        }
        return result;
    }
    public void saveData(List<HashMap<String, Object>> l) {
        talkto = "";
        for(HashMap<String,Object> map :l){
            if(talkto!=""){
                talkto = talkto+","+map.get("text");
            }
            else talkto = ""+map.get("text");
        }
        SharedPreferences talklist = getActivity().getSharedPreferences("talklist", Context.MODE_PRIVATE);// 获取聊天人列表
        SharedPreferences.Editor editor = talklist.edit();
        editor.remove(uid);
        editor.putString(uid, talkto);
        editor.commit();


    }

    public void refresh() {
        Data.clear();
        SharedPreferences talklist = getActivity().getSharedPreferences("talklist", Context.MODE_PRIVATE);// 获取聊天人列表
        talkto = talklist.getString(uid, "");
        talktolist = talkto.split(",");
        if (talkto != "") {
            System.out.println(talkto);
            for (int i = 0; i < talktolist.length; i++) {
                HashMap<String, Object> map1 = new HashMap<String, Object>();
                map1.put("image", R.drawable.side_nav_bar);
                map1.put("text", talktolist[i]);
                map1.put("text2", "在？吗♂♂♂♂♂♂");
                if (talktolist[i].length() != 0) {
                    Data.add(map1);
                }
            }
        }
        simp_adapter.notifyDataSetChanged();
        if (talktolist.length == 0 || talkto == "") {
            Toast.makeText(getActivity(), "暂无消息，快去打招呼吧", Toast.LENGTH_SHORT).show();
        }
    }

    public void turntofriendmessage(String friendid) {
//        DynamicMainActivity.friendidformessage = talkto;
//        Intent intent = new Intent(getActivity(), DynamicMainActivity.class);
//        startActivity(intent);
    }
}
