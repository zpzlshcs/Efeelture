package com.example.windows8.newef.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.windows8.newef.R;
import com.example.windows8.newef.activity.CriActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by windows8 on 2017/10/10.
 */

public class MomentFragment extends LazyFragment {
    @BindView(R.id.ll_cri)
    LinearLayout llcri;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moment,container,false);
        ButterKnife.bind(this,view);
        return view;
    }
    @OnClick({R.id.ll_cri})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.ll_cri:
                Intent cri = new Intent(getActivity(),CriActivity.class);
                startActivity(cri);
                break;
        }
    }
}
