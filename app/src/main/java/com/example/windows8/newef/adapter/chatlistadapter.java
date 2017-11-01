package com.example.windows8.newef.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.windows8.newef.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class chatlistadapter extends BaseAdapter {

	private Context context;
	private LayoutInflater mInflater;
	private List<HashMap<String, Object>> mData;
	private String friendid = "";
	private String uid = "";
	private String list = "";

	public chatlistadapter(Context con, List<HashMap<String, Object>> data,String uid) {
		mInflater = LayoutInflater.from(con);
		context = con;
		mData = data;
	}

	public int getCount() {
		return mData.size();
	}

	public Object getItem(int position) {
		return mData.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	static class ViewHolder {
		TextView name;
		TextView chat;
		LinearLayout main;
		LinearLayout head;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final Map thisfriend = mData.get(position);
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_friend, null);
			holder.name = (TextView) convertView.findViewById(R.id.friend_textView3);
			holder.main = (LinearLayout) convertView.findViewById(R.id.friend_front);
			holder.head = (LinearLayout) convertView.findViewById(R.id.friendhead);
			holder.chat = (TextView) convertView.findViewById(R.id.friend_textView4);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.head.setVisibility(View.GONE);
		holder.chat.setText(thisfriend.get("text2").toString());
		holder.name.setText(thisfriend.get("text").toString());
		final View main = convertView;
		holder.main.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				mlistener.itemlongclick(position,main);
				return false;
			}
		});
		holder.main.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mlistener.itemclick(position);
			}
		});
		return convertView;
	}
	private listlistener mlistener;
	public interface listlistener{
		void itemclick(int i);
		void itemlongclick(int i,View v);
	}
	public void setMlistener(listlistener l){
		mlistener = l;
	}
}