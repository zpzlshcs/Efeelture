package com.example.windows8.newef.util;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by windows8 on 2017/10/11.
 */

public class getData {
    Context con;
    String zson;
    String func;
    public getData(String zson,Context con,String func){
        this.con = con;
        this.func = func;
        this.zson = zson;
    }



    public JSONObject getResult() {
        try {
            String spec = "http://115.159.120.220:8080/efeelture/mobileAppServlet";
            URL url = new URL(spec);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(5000);
            String data = "func="+func + "&zson={"+zson+"}";
            Log.d("data",data);
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
                String code = dataJson.getString("resultCode");
                Log.d("resultcode",code);
                if (code.equals("999")) {
                    return dataJson;
                } else {

                }

            } else {
                Log.d("why","??");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
