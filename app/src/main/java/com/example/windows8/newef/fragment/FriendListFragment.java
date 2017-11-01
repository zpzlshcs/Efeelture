package com.example.windows8.newef.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.windows8.newef.R;
import com.example.windows8.newef.activity.ChatActivity;
import com.example.windows8.newef.adapter.SortAdapter;
import com.example.windows8.newef.clearedittext.CharacterParser;
import com.example.windows8.newef.clearedittext.ClearEditText;
import com.example.windows8.newef.clearedittext.PinyinComparator;
import com.example.windows8.newef.clearedittext.SideBar;
import com.example.windows8.newef.clearedittext.SortModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by windows8 on 2017/10/11.
 */

public class FriendListFragment extends LazyFragment {
    private View mBaseView;
    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
    private ClearEditText mClearEditText;

    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;

    private PinyinComparator pinyinComparator;
    private View view;
    private String uid = "";
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = getActivity().getLayoutInflater().inflate(R.layout.fragment_message_friendlist, null);
        SharedPreferences cys2 = getActivity().getSharedPreferences("information", Context.MODE_PRIVATE);
        uid = cys2.getString("uid", "");
        initView();
        initData();
        return view;
    }

    private void initView() {
        sideBar = (SideBar) view.findViewById(R.id.sidrbar);
        dialog = (TextView) view.findViewById(R.id.dialog);

        sortListView = (ListView) view.findViewById(R.id.sortlist);

    }

    private void initData() {
        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sideBar.setTextView(dialog);

        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @SuppressLint("NewApi")
            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }
            }
        });

        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 内部存储有关于聊天人的id
                String friendid = SourceDateList.get(position).getName().toString();
                Log.d("talkto", friendid);
                SharedPreferences talklist = getActivity().getSharedPreferences("talklist", Context.MODE_PRIVATE);// 获取聊天人列表
                String oldlist = talklist.getString(uid, "");// talklist中存放和uid拥有者聊天过的人的名单列表用“，”隔开
                Log.d("oldlist", oldlist);
                if (oldlist == "") {
                    SharedPreferences.Editor editor = talklist.edit();
                    editor.putString(uid, friendid);// 将新数据存进talklist中
                    editor.commit();
                    MessageListFragment.talkto = friendid;
                } else {
                    String oldlistcut[] = oldlist.split(",");
                    String talkto = "," + friendid;
                    for (int i = 0; i < oldlistcut.length; i++) { // 如果之前聊过天，内部存储过数据则不新增数据
                        if (friendid.equals(oldlistcut[i])) {
                            talkto = "";
                            break;

                        }
                    }
                    SharedPreferences.Editor editor = talklist.edit();
                    editor.putString(uid, "" + oldlist + talkto);// 将新数据存进talklist中
                    editor.commit();
                    MessageListFragment.talkto = "" + oldlist + talkto;
                    if (talkto != "") {
                        MessageListFragment.talkto = talklist.getString(uid, "");
                        HashMap<String, Object> map1 = new HashMap<String, Object>();
                        map1.put("image", R.drawable.side_nav_bar);
                        map1.put("text", friendid);
                        map1.put("text2", "在？吗♂♂♂♂♂♂");
                        MessageListFragment.Data.add(map1);
                        MessageListFragment.simp_adapter.notifyDataSetChanged();
                    }
                }
                ChatActivity.talkto = friendid;
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                startActivity(intent);
            }
        });
        sortListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                //showAlertDialog(position);
                return true;
            }
        });
        new ConstactAsyncTask().execute("");
    }
//    private void showAlertDialog(final int position) {    //长按弹窗
//        //添加聊天的弹窗
//        final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
//        //关联弹窗视图
//        dialog.setView(LayoutInflater.from(getActivity()).inflate(R.layout.deletealert, null));
//        dialog.show();
//        //绑定控件
//        dialog.getWindow().setContentView(R.layout.deletealert);
//        TextView delete = (TextView) dialog.findViewById(R.id.deletefriend);
//        delete.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                new Handler().post(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        // TODO Auto-generated method stub
//                        //deletefriend(SourceDateList.get(position).getConnectid(),position);
//                    }
//                });
//                dialog.dismiss();
//            }
//        });
//    }
    private class ConstactAsyncTask extends AsyncTask<String, Integer, List<HashMap<String,String>>> {

        @Override
        protected List<HashMap<String,String>> doInBackground(String... arg0) {
            List<HashMap<String,String>> getresult = new ArrayList<HashMap<String,String>>();
            try {
                String spec = "http://115.159.120.220:8080/efeelture/mobileAppServlet";
                URL url = new URL(spec);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setReadTimeout(5000);
                urlConnection.setConnectTimeout(5000);
                SharedPreferences information = getActivity().getSharedPreferences("information", Context.MODE_PRIVATE);
                String uid = information.getString("uid", "");
                String data = "func=1014" + "&zson={firstid:\"" + URLEncoder.encode(uid, "UTF-8") + "\",fstatus:\""
                        + URLEncoder.encode("1", "UTF-8") + "\"}";
                urlConnection.setRequestProperty("Connection", "keep-alive");
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("Content-Length", String.valueOf(data.getBytes().length));
                urlConnection.setRequestProperty("User-Agent",
                        "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");

                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                OutputStream os = urlConnection.getOutputStream();
                os.write(data.getBytes());
                os.flush();
                if (urlConnection.getResponseCode() == 200) {
                    InputStream is = urlConnection.getInputStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    int len = 0;
                    byte buffer[] = new byte[1024];
                    while ((len = is.read(buffer)) != -1) {
                        baos.write(buffer, 0, len);
                    }
                    is.close();
                    baos.close();
                    String result = new String(baos.toByteArray());

                    JSONObject dataJson = new JSONObject(result);
                    String code1 = dataJson.getString("resultCode");

                    if (code1.equals("999")) {

                        JSONArray resultJsonArray = dataJson.getJSONArray("friendList");
                        // System.out.println(resultJsonArray[0].get);
                        for (int i = 0; i < resultJsonArray.length(); i++) {
                            JSONObject jsonObject = resultJsonArray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("id", jsonObject.getString("secondid"));
                            map.put("connectid", jsonObject.getString("id"));
                            getresult.add(map);
                        }
                    } else {
                        Looper.prepare();
                        Toast.makeText(getActivity(), "您还没有添加好友，快去添加吧", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                } else {
                    Looper.prepare();
                    Toast.makeText(getActivity(), "请检查网络", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return getresult;
        }
        @Override
        protected void onPostExecute(List<HashMap<String,String>> result) {
            super.onPostExecute(result);
            if (result.size()!=0) {
                SourceDateList = filledData(result);

                // 根据a-z进行排序源数据
                Collections.sort(SourceDateList, pinyinComparator);
                adapter = new SortAdapter(getActivity(), SourceDateList);
                sortListView.setAdapter(adapter);

                mClearEditText = (ClearEditText) getActivity()
                        .findViewById(R.id.filter_edit);
                mClearEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                    @Override
                    public void onFocusChange(View arg0, boolean arg1) {
                        mClearEditText.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);

                    }
                });
                // 根据输入框输入值的改变来过滤搜索
                mClearEditText.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                        filterData(s.toString());
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

    }

    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private List<SortModel> filledData(List<HashMap<String,String>> date) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (int i = 0; i < date.size(); i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date.get(i).get("id"));
            sortModel.setConnectid(date.get(i).get("connectid"));
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(date.get(i).get("id"));
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1
                        || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }
}
