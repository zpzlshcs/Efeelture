package com.example.windows8.newef.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.windows8.newef.R;
import com.example.windows8.newef.custom.getData;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddCriActivity extends AppCompatActivity {
    @BindView(R.id.add_edit_content)
    EditText content;
    @BindView(R.id.add_tt_save)
    TextView save;
    @BindView(R.id.add_tt_send)
    TextView send;
    @BindView(R.id.add_tt_textnum)
    TextView textnum;
    @BindView(R.id.iv_back)
    ImageView back;
    private SharedPreferences cricontent;
    private SharedPreferences.Editor editor;
    private String uid;
    private String uname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cri);
        ButterKnife.bind(this);
        content.addTextChangedListener(mTextWatcher);
        cricontent = getSharedPreferences("information", Context.MODE_PRIVATE);
        editor = cricontent.edit();
        content.setText(cricontent.getString("cricontent",""));
        uid = cricontent.getString("uid","");
        uname = cricontent.getString("uname","");
    }
    //输入框字数限制
    private static final int WEIBO_CONTENT_LENGTH_LIMITED = 150;
    //输入框监听
    private TextWatcher mTextWatcher = new TextWatcher() {
        private int editStart;
        private int editEnd;
        public void afterTextChanged(Editable s) {
            editStart = content.getSelectionStart();
            editEnd = content.getSelectionEnd();
            // 先去掉监听器，否则会出现栈溢出
            content.removeTextChangedListener(mTextWatcher);
            while (content.getText().toString().length() > WEIBO_CONTENT_LENGTH_LIMITED) {
                s.delete(editStart - 1, editEnd);
                editStart--;
                editEnd--;
            }
            content.setText(s);
            content.setSelection(editStart);
            content.addTextChangedListener(mTextWatcher);
            textnum.setText(content.getText().length()+"");
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    };
    @OnClick({R.id.iv_back,R.id.add_tt_send,R.id.add_tt_save})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.add_tt_save:
                editor.putString("cricontent", content.getText().toString());
                editor.commit();
                finish();
                break;
            case R.id.add_tt_send:
                if(content.getText().length()!=0){
                    try {
                        String sendcontent = content.getText().toString();
                        String zson2  = "uid:\"" + uid
                                + "\",content:\"" + URLEncoder.encode(sendcontent, "UTF-8")
                                + "\",mtype:\"" + 1
                                + "\",uname:\"" + uname + "\"";
                        new AsyncTask<String, Integer, JSONObject>() {
                            @Override
                            protected JSONObject doInBackground(String... strings) {
                                JSONObject result2 = new getData(strings[0],AddCriActivity.this,"1051").getResult();
                                return result2;
                            }
                            @Override
                            protected void onPostExecute(JSONObject result) {
                                super.onPostExecute(result);
                                if(result == null){
                                    Toast.makeText(AddCriActivity.this, "发表失败", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(AddCriActivity.this, "发表成功", Toast.LENGTH_SHORT).show();
                                    editor.putString("cricontent", "");
                                    editor.commit();
                                    AddCriActivity.this.finish();
                                }
                            }
                            @Override
                            protected void onPreExecute() {
                                super.onPreExecute();
                            }
                        }.execute(zson2);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
