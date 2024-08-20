package com.example.travelassistant.Service;

import org.java_websocket.WebSocket;

import java.util.Timer;
import java.util.TimerTask;

public enum WebClientEnum {


    CLIENT;
    int port1=-1;
    int port2=-1;
    int port3=-1;
    int flag;
    private static MsgWebSocketClient socketClient = null;
    public  void initClient(MsgWebSocketClient client)
    {
        try {
            socketClient = client;
            socketClient.connect();
            String msg="{\"M\":\"login\",\"ID\":\"19231\",\"K\":\"1a3bd6eb7c\"}\n";
            while (!socketClient.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
                System.out.println("连接中。。。");
                Thread.sleep(1000 * 5);
            }
            System.out.println("连接成功");
            socketClient.send(msg);
            final Timer t=new Timer();
            TimerTask timerTask=new TimerTask() {
                @Override
                public void run() {
                    port1=socketClient.heart;
                    port2= socketClient.acceleration;
                    port3= socketClient.button;
                    flag=socketClient.flag;
                    if(flag==1)
                    {
                        t.cancel();
                    }
                    //System.out.println("线程1"+port1+port2+port3);

                }
            };
            t.scheduleAtFixedRate(timerTask , 1000, 1000 );
            System.out.println("###");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
