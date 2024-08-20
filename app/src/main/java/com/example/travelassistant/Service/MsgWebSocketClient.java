package com.example.travelassistant.Service;

import com.alibaba.fastjson.JSONObject;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Iterator;

public class MsgWebSocketClient extends WebSocketClient {
    public int heart=-1;
    public int acceleration=-1;
    public int button=-1;
    public int flag=0;
    public MsgWebSocketClient(URI serverUri) {
        super(serverUri);
    }
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("握手...");
        for(Iterator<String> it = handshakedata.iterateHttpFields(); it.hasNext();) {
            String key = it.next();
            System.out.println(key+":"+handshakedata.getFieldValue(key));
        }

    }
    public void onMessage (String message){

        System.out.println("接收到消息：" + message);
        JSONObject object = JSONObject.parseObject(message);
        try {
            String str = (String) object.getJSONObject("V").get("22720");
            String str1=(String)object.getJSONObject("V").get("22724");//加速度
            String str2=(String)object.getJSONObject("V").get("22725");//按钮

            heart = Integer.parseInt(str);
            acceleration=Integer.parseInt(str1);
            button=Integer.parseInt(str2);
        } catch (NullPointerException e) {
        }
    }





    public void onClose(int code, String reason, boolean remote) {
        System.out.println("关闭..."+reason);
        flag=1;

    }

    public void onError(Exception ex) {
        System.out.println("异常"+ex);


    }
}
