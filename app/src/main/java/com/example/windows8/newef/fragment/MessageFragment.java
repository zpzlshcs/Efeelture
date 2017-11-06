package com.example.windows8.newef.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.windows8.newef.R;
import com.example.windows8.newef.activity.AddFriendActivity;
import com.example.windows8.newef.activity.AddHardActivity;
import com.example.windows8.newef.adapter.CategoryPageAdapter;
import com.zaaach.toprightmenu.MenuItem;
import com.zaaach.toprightmenu.TopRightMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by windows8 on 2017/10/10.
 */

public class MessageFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.view_pager)ViewPager viewPager;
    @BindView(R.id.message_message)TextView message;
    @BindView(R.id.message_person)TextView friend;
    @BindView(R.id.iv_more)ImageView more;
    Unbinder unbinder;

    List<Fragment> fragList = new ArrayList<>();
    MessageListFragment cate1 = null;
    FriendListFragment cate2 = null;
    CategoryPageAdapter categoryPageAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message,container,false);
        unbinder = ButterKnife.bind(this, view);
        friend.setOnClickListener(this);
        message.setOnClickListener(this);
        cate1 = new MessageListFragment();
        fragList.add(cate1);
        categoryPageAdapter = new CategoryPageAdapter(getFragmentManager(),fragList);
        viewPager.setAdapter(categoryPageAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        message.setTextColor(Color.parseColor("#127C56"));
                        message.setBackgroundResource(R.drawable.bg_titlewhite);
                        friend.setTextColor(Color.parseColor("#ffffff"));
                        friend.setBackgroundResource(R.drawable.bg_titlegreen);
                        break;
                    case 1:
                        message.setTextColor(Color.parseColor("#ffffff"));
                        message.setBackgroundResource(R.drawable.bg_titlegreen);
                        friend.setTextColor(Color.parseColor("#127C56"));
                        friend.setBackgroundResource(R.drawable.bg_titlewhite);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.message_message:
                viewPager.setCurrentItem(0, true);
                break;
            case R.id.message_person:
                if(cate2 == null){
                    cate2 = new FriendListFragment();
                    fragList.add(cate2);
                    categoryPageAdapter.notifyDataSetChanged();
                }
                viewPager.setCurrentItem(1, true);
                break;
        }
    }
    @OnClick({R.id.iv_more})
    public void onClickl(View v){
        switch (v.getId()){
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
                        .showAsDropDown(v,-100,0);	//带偏移量
//      		.showAsDropDown(moreBtn)
                break;
        }
    }
}
