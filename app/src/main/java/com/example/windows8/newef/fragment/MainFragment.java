package com.example.windows8.newef.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.windows8.newef.R;
import com.example.windows8.newef.activity.AddFriendActivity;
import com.example.windows8.newef.activity.AddHardActivity;
import com.example.windows8.newef.activity.LightActivity;
import com.example.windows8.newef.activity.MainActivity;
import com.example.windows8.newef.activity.MusicActivity;
import com.example.windows8.newef.activity.WindActivity;
import com.zaaach.toprightmenu.MenuItem;
import com.zaaach.toprightmenu.TopRightMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by windows8 on 2017/10/10.
 */

public class MainFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.image_light)ImageView light;
    @BindView(R.id.image_wind)ImageView wind;
    @BindView(R.id.ciriv_user_icon)CircleImageView usericon;
    @BindView(R.id.iv_more)
    ImageView ivmore;
    @BindView(R.id.image_sport)
    ImageView sport;
    @BindView(R.id.image_music)
    ImageView music;
    Unbinder unbinder;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.image_wind,R.id.ciriv_user_icon,R.id.iv_more,R.id.image_light,R.id.image_music,R.id.image_sport})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.image_music:
                Intent music = new Intent(getActivity(),MusicActivity.class);
                startActivity(music);
                break;
            case R.id.image_light:
                Intent light = new Intent(getActivity(),LightActivity.class);
                startActivity(light);
                break;
            case R.id.image_wind:
                Intent intent = new Intent(getActivity(), WindActivity.class);
                startActivity(intent);
                break;
            case R.id.ciriv_user_icon:
                MainActivity.drawer.openDrawer(Gravity.LEFT,true);
                break;
            case R.id.iv_more:
                TopRightMenu mTopRightMenu = new TopRightMenu(getActivity());
                List<MenuItem> menuItems = new ArrayList<>();
//                menuItems.add(new MenuItem(R.mipmap.multichat, "发起多人聊天"));
//                menuItems.add(new MenuItem(R.mipmap.addmember, "加好友"));
//                menuItems.add(new MenuItem(R.mipmap.qr_scan, "扫一扫"));

                mTopRightMenu
                        .setHeight(240)     //默认高度480
                        //.setWidth(200)      //默认宽度wrap_content
                        .showIcon(false)     //显示菜单图标，默认为true
                        .dimBackground(true)        //背景变暗，默认为true
                        .needAnimationStyle(true)   //显示动画，默认为true
                        .setAnimationStyle(R.style.TRM_ANIM_STYLE)
                        .addMenuList(menuItems)
                        .addMenuItem(new MenuItem( "添加好友"))
                        .addMenuItem(new MenuItem( "添加硬件"))
                        .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                            @Override
                            public void onMenuItemClick(int position) {
                                switch (position){
                                    case 0:
                                        Intent addf = new Intent(getActivity(), AddFriendActivity.class);
                                        startActivity(addf);
                                        break;
                                    case 1:
                                        Intent addh = new Intent(getActivity(), AddHardActivity.class);
                                        startActivity(addh);
                                        break;
                                }
                            }
                        })
                        .showAsDropDown(ivmore,-100,0);	//带偏移量
//      		.showAsDropDown(moreBtn)
                break;
            case R.id.image_sport:
//                Intent sport = new Intent(getActivity(), SportActivity.class);
//                startActivity(sport);
        }
    }
}
