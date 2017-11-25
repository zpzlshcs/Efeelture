package com.example.windows8.newef.bean;

public class SortModel {

	private String name;   //显示的数�?
	private String sortLetters;  //显示数据拼音的首字母
	private String connectid;
	public String getConnectid() {
		return connectid;
	}
	public void setConnectid(String connectid) {
		this.connectid = connectid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
}
