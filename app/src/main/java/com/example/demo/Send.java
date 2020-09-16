package com.example.demo;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Send {
    //将扫描的结果以json的形式发送给服务器
    public static String checkStatus(String urlStr, String seat, String time) {
        String params = "{\"num\":\"" + seat + "\",\"time\":\"" + time + "\"}";
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            connect.setRequestMethod("POST");
            //设置文件类型
            connect.setRequestProperty("Content-Type", "application/json:charset=UTF-8");
            //往服务器发送数据
            OutputStream outputStream = connect.getOutputStream();
            outputStream.write(params.getBytes());
            //处理返回结果
            int response = connect.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                System.out.println(response);
                //获取返回结果
                InputStream input=connect.getInputStream();
                return "ok";
            }
            else{
                System.out.println(response);
                return "error";
            }
        } catch (IOException e) {
            Log.e("e:",String.valueOf(e));
            return e.toString();
        }
    }

    public static String cherStatus(String s, String seat, String time) {

    }
}
