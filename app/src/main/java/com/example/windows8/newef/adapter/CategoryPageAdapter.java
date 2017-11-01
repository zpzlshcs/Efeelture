package com.example.windows8.newef.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangsheng
 * on 2017/9/27.
 */

public class CategoryPageAdapter extends FragmentPagerAdapter {

    List<Fragment> fragList = new ArrayList<>();

    public CategoryPageAdapter(FragmentManager fm , List<Fragment> fragList) {
        super(fm);
        this.fragList = fragList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragList.get(position);
    }

    @Override
    public int getCount() {
        return fragList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

}
