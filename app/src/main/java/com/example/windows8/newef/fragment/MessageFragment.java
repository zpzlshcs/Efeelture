package com.example.windows8.newef.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.windows8.newef.R;
import com.example.windows8.newef.adapter.CategoryPageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by windows8 on 2017/10/10.
 */

public class MessageFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.view_pager)ViewPager viewPager;
    @BindView(R.id.message_message)TextView message;
    @BindView(R.id.message_person)TextView friend;
    Unbinder unbinder;
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
        List<Fragment> fragList = new ArrayList<>();
        MessageListFragment cate1 = new MessageListFragment();
        FriendListFragment cate2 = new FriendListFragment();
        fragList.add(cate1);
        fragList.add(cate2);
        viewPager.setAdapter(new CategoryPageAdapter(getFragmentManager(),fragList));
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
                viewPager.setCurrentItem(1, true);
                break;
        }
    }
}
