package com.example.windows8.newef.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.windows8.newef.R;
import com.example.windows8.newef.bean.Model;

import java.util.ArrayList;

public class MyAadapter extends BaseAdapter{

	ArrayList<Model> alist;
	Context mContext;
	
	public  MyAadapter(ArrayList<Model> alist,Context mContext) {
		// TODO Auto-generated constructor stub
		this.alist=alist;
		this.mContext=mContext;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return alist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return alist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		viewhodler viewhodler;
		if(convertView==null)
		{
			viewhodler=new viewhodler();
			convertView=LayoutInflater.from(mContext).inflate(R.layout.item_music, null);
			
			viewhodler.text_gq=(TextView) convertView.findViewById(R.id.text_gq);
			viewhodler.text_gs=(TextView) convertView.findViewById(R.id.text_gs);
			viewhodler.img=(ImageView) convertView.findViewById(R.id.img11);
			
			convertView.setTag(viewhodler);
		}else{
			viewhodler=(viewhodler) convertView.getTag();
		}


		viewhodler.text_gq.setText(""+alist.get(position).music_name);
		viewhodler.text_gs.setText(""+alist.get(position).singer);
		viewhodler.img.setImageResource(R.drawable.pic_11);
	
	
		
		return convertView;
	}
	static class viewhodler{
		TextView text_gq,text_gs;
		ImageView img;
	}


}
