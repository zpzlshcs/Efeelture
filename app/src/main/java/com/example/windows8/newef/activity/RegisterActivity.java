package com.example.windows8.newef.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.windows8.newef.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.register_bu_getcode)
    Button getcode;
    @BindView(R.id.register_button_next)
    Button next;
    @BindView(R.id.register_edit_code)
    EditText code;
    @BindView(R.id.register_edit_phone)
    EditText phone;
    @BindView(R.id.iv_back)
    ImageView back;
    EventHandler eventHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        // 创建EventHandler对象
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable)data;
                    String msg = throwable.getMessage();
                } else {
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        // 处理你自己的逻辑
                    }
                }
            }
        };

        // 注册监听器
        SMSSDK.registerEventHandler(eventHandler);
    }
    @OnClick({R.id.register_bu_getcode,R.id.register_button_next,R.id.iv_back})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.register_bu_getcode:
                T = 60;
                String phonenum = phone.getText().toString();
                SMSSDK.getVerificationCode("86",phonenum);
                Toast.makeText(RegisterActivity.this, "注意查收"+phonenum, Toast.LENGTH_SHORT).show();
                new Thread(new MyCountDownTimer()).start();//开始执行
                break;
            case R.id.register_button_next:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String phonenum = phone.getText().toString();
                        String codecontent = code.getText().toString();
                        String params = "appkey=21669efdee742;phone=" + phonenum + ";zone=86;code=" + codecontent;
                        String result = requestData("https://webapi.sms.mob.com/sms/verify",
                                params);
                        //输出结果
                        Log.d("result", result);
                        try {
                            JSONObject dataJson = new JSONObject(result);
                            String resultcode = dataJson.getString("status");
                            switch (resultcode) {
                                case "200":
                                    Looper.prepare();
                                    RegisterNextActivity.phone = phonenum;
                                    Intent intent = new Intent(RegisterActivity.this, RegisterNextActivity.class);
                                    startActivity(intent);
                                    RegisterActivity.this.finish();
                                    Looper.loop();
                                    break;
                                default:
                                    Looper.prepare();
                                    Toast.makeText(RegisterActivity.this, "验证码错误"+result, Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
                break;
            case R.id.iv_back:
                finish();
                break;
        }

    }
    public int T =60; //倒计时时长
    private Handler mHandler = new Handler();
    /**
     * 自定义倒计时类，实现Runnable接口
     */
    class MyCountDownTimer implements Runnable{
        @Override
        public void run() {

            //倒计时开始，循环
            while (T > 0) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        getcode.setClickable(false);
                        getcode.setText(T + "秒后重新发送");
                    }
                });
                try {
                    Thread.sleep(1000); //强制线程休眠1秒，就是设置倒计时的间隔时间为1秒。
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                T--;
            }

            //倒计时结束，也就是循环结束
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    getcode.setClickable(true);
                    getcode.setText("发送验证码");
                }
            });
        }
    }
    /**
     * @param address post地址
     * @param params  参数
     * @return 结果
     * 发送验证码
     */
    public static String requestData(String address, String params) {

        HttpURLConnection conn = null;
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};
            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());

            //ip host verify
            HostnameVerifier hv = new HostnameVerifier() {
                public boolean verify(String urlHostName, SSLSession session) {
                    return urlHostName.equals(session.getPeerHost());
                }
            };

            //set ip host verify
            HttpsURLConnection.setDefaultHostnameVerifier(hv);

            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            URL url = new URL(address);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");// POST
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            // set params ;post params
            if (params != null) {
                conn.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                out.write(params.getBytes(Charset.forName("UTF-8")));
                out.flush();
                out.close();
            }
            conn.connect();
            //get result
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = conn.getInputStream();
                StringBuffer out = new StringBuffer();
                byte[] b = new byte[4096];
                for (int n; (n = in.read(b)) != -1; ) {
                    out.append(new String(b, 0, n));
                }
                return out.toString();
            } else {
                System.out.println(conn.getResponseCode() + " " + conn.getResponseMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                conn.disconnect();
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
