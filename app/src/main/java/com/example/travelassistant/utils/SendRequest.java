package com.example.travelassistant.utils;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SendRequest {

    public static void makeAsyncRequest(String url,String[] strings, AsyncResponse asyncResponse)
    {
        OkHttpClient client = new OkHttpClient();//创建HTTP客户端
//        String ipv4 = new String();
//        try {
//            // 获取本机的IP地址
//            InetAddress ip = InetAddress.getLocalHost();
//
//            // 检查是否为IPv4地址
//            if (ip instanceof java.net.Inet4Address) {
//                ipv4 = ip.getHostAddress();
//
//                System.out.println("IPv4地址: " + ip.getHostAddress());
//            } else {
//                // 如果不是IPv4地址，可能是IPv6地址，你可以根据需要处理
//                System.out.println("这不是IPv4地址，可能是IPv6地址: " + ip.getHostAddress());
//            }
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
       // String dxl="http://192.168.194.177:8221"+url;
        String sendUrl="http://47.238.239.250:8221"+url;
        if(strings!=null) {
            for (int i = 0; i < strings.length; i++) {
                sendUrl = sendUrl + "/" + strings[i];
            }
        }

        Request request=new Request.Builder()
                .url(sendUrl)
                .build();//创建http请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                asyncResponse.processFailure();

                e.printStackTrace();

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseBody = response.body().string();
                // 在请求成功时调用回调函数
                asyncResponse.processFinish(responseBody);
            }
        });

    }
    public static void SendFile(String url, File file, AsyncResponse asyncResponse){
        OkHttpClient client = new OkHttpClient();
        String sendUrl="http://47.238.239.250:8222"+url;
        String dxl="http://192.168.194.177:8222"+url;
//        if(strings!=null) {
//            for (int i = 0; i < strings.length; i++) {
//                dxl = dxl + "/" + strings[i];
//            }
//        }
        MultipartBody.Builder requestBody=new MultipartBody.Builder().setType(MultipartBody.FORM);
        RequestBody fileBody=RequestBody.create(MediaType.parse("image/*"),file);
        requestBody.addFormDataPart("file", file.getName(),fileBody);

        Request request=new Request.Builder()
                .url(sendUrl)
                .post(requestBody.build())
                .build();//创建http请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {


                e.printStackTrace();

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseBody = response.body().string();
                // 在请求成功时调用回调函数
                asyncResponse.processFinish(responseBody);
                //在这里处理一下图片的Url
            }
        });

    }

    //将视频上传到阿里云Vod
    public static void sendVideo(String url, File file, AsyncResponse asyncResponse){

        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)  // 写入超时时间
                .readTimeout(30, TimeUnit.SECONDS)   // 读取超时时间
                .build();
        String sendUrl="http://47.238.239.250:8222"+url;
        MultipartBody.Builder requestBody=new MultipartBody.Builder().setType(MultipartBody.FORM);
        RequestBody fileBody=RequestBody.create(MediaType.parse("video/*"),file);
        requestBody.addFormDataPart("file", file.getName(),fileBody);
        Request request=new Request.Builder()
                .url(sendUrl)
                .post(requestBody.build())
                .build();//创建http请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {


                e.printStackTrace();

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseBody = response.body().string();
                // 在请求成功时调用回调函数
                asyncResponse.processFinish(responseBody);
                //在这里处理一下图片的Url
            }
        });


    }

}
