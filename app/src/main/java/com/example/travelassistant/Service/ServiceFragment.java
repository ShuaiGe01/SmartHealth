package com.example.travelassistant.Service;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.example.travelassistant.MainActivity;
import com.example.travelassistant.R;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class ServiceFragment extends Fragment {
    public static int heart=-1;
    public static int button=-1;
    public static int slide=-1;
    public static int flag;

    private String CHANNEL_ID="ChannelID";
    int notificationId=1;
    int time=0;


    private MapView mMapView = null;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    float Li=0;
    float La=0;

    TextView tv_Lat;
    TextView tv_Lon;
    TextView tv_Add;
    TextView tv_heart;
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    public void sendmessage(String str){
        dosendmessage(str);

//        Timer timer=new Timer();
//        TimerTask task=new TimerTask() {
//            @Override
//            public void run() {
//
//            }
//
//        };
//        timer.schedule(task,0,8000);
    }
    public void sendNotification(String title, String body) {
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(getContext().NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 创建一个通知通道（仅适用于Android 8.0及以上）
            String channelId = "my_notification_channel";
            String channelName = "My Notification Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        // 创建一个NotificationCompat.Builder实例来构建通知
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "my_notification_channel")
                .setSmallIcon(R.drawable.mainicon) // 设置通知图标
                .setContentTitle("通知") // 设置通知标题
                .setContentText("----") // 设置通知内容
                .setPriority(NotificationCompat.PRIORITY_DEFAULT); // 设置优先级

        // 发送通知
        Notification notification = builder.build();
        notificationManager.notify(1, notification); // 1是通知ID，可以按需更改
    }

    private void dosendmessage(String str) {
        createNotificationChannel();
        // Create an Intent for the activity you want to start
        //TODO 下面发生了修修改
        Intent resultIntent = new Intent(getActivity(), MainActivity.class);
// Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getContext());
        stackBuilder.addNextIntentWithParentStack(resultIntent);
// Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);








        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.mainicon)
                .setContentTitle("通知")
                .setContentText("于"+str+"发现有人摔倒")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(resultPendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());


        notificationManager.notify(notificationId, builder.build());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_service, container, false);
        /*try {

           WebClientEnum.CLIENT.initClient(new MsgWebSocketClient(new URI("wss://www.bigiot.net:8484")));

        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        //获取地图控件引用
        tv_Add=view.findViewById(R.id.tv_Add);
        tv_Lon=view.findViewById(R.id.tv_Lon);
        tv_Lat=view.findViewById(R.id.tv_Lat);
        tv_heart=view.findViewById(R.id.tv_Heart);
        mMapView = (MapView) view.findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
//卫星地图
        //  mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);

        mBaiduMap.setMyLocationEnabled(true);



        LocationClient.setAgreePrivacy(true);//添加权限
//
//        Timer timer=new Timer();
//        TimerTask task=new TimerTask() {
//            @Override
//            public void run() {
        initLocation();
        return view;
    }
    private void initLocation(){
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else {
            try {
                mLocationClient = new LocationClient(getContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
            LocationClientOption option = new LocationClientOption();
            option.setAddrType("all");
            option.setOpenGps(true);
            option.setCoorType("bd09ll"); // 设置坐标类型
            option.setIsNeedAddress(true); //设置是否需要地址信息，默认不需要
            option.setIsNeedLocationDescribe(true);
//            option.setScanSpan(5000);
            mLocationClient.setLocOption(option);
            //设置监听器
            MyLocationListener myLocationListener = new MyLocationListener();
            mLocationClient.registerLocationListener(myLocationListener);
            //开启地图定位图层
            mLocationClient.start();

            MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
            MyLocationConfiguration mLocationConfiguration = new MyLocationConfiguration(mCurrentMode,true,null,0xAAFFFF88,0xAA00FF00);
            mBaiduMap.setMyLocationConfiguration(mLocationConfiguration);

        }









    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理

    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            System.out.println(location.getLocTypeDescription());

            if (location == null || mMapView == null){
                return;
            }


//            MyLocationData locData = new MyLocationData.Builder()
//                    .accuracy(10)
//                    // 此处设置开发者获取到的方向信息，顺时针0-360
//                    .direction(10).latitude(34.825508)
//                    .longitude(115.542328).build();
//            MainActivity.this.runOnUiThread(new Runnable() {
//                public void run() {
//
//                }
//            });
            Handler handler=new Handler();
            Timer timer=new Timer();
            TimerTask timerTask;
            timerTask= new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Random random = new Random();

                            // 设置随机数范围为60到100之间（包括边界值）
                            int minValue = 60;
                            int maxValue = 100;

                            // 生成随机数并输出结果
                            int randomNum = random.nextInt(maxValue - minValue + 1) + minValue;
                            time++;
                            heart=WebClientEnum.CLIENT.port1;
                            button=WebClientEnum.CLIENT.port2;
                            slide=WebClientEnum.CLIENT.port3;
                            flag=WebClientEnum.CLIENT.flag;
                            if(flag==1)
                            {
                                timer.cancel();
                                return;
                            }
                            System.out.println("线程1"+heart+button+slide);
                            if(time==30)
                            {
                                sendmessage(location.getAddrStr());
                                MyLocationData locData = new MyLocationData.Builder()
                                        .accuracy(location.getRadius())
//                    .direction(location.getDirection()).latitude(41.07)
//                    .longitude(121.09).build();
                                        // 此处设置开发者获取到的方向信息，顺时针0-360
                                        .direction(location.getDirection()).latitude(location.getLatitude())
                                        .longitude(location.getLongitude()).build();
                                tv_Add.setText(location.getAddrStr());
                                tv_Lat.setText(" " + location.getLatitude() + " ");
                                tv_Lon.setText(" " + location.getLongitude() + " ");
                                mBaiduMap.setMyLocationData(locData);
                                tv_heart.setText(" " + randomNum);





                            }
                            if(time>15){
                                tv_heart.setText(" " + randomNum);

                            }



//                            int num = (int) (Math.random() * 40 + 60);
//                            tv_heart.setText(" " + num);


                        }
                    });

                }
            };
            timer.schedule(timerTask,1000,1000);






















//            final Handler mTimerHandler = new Handler();
//            final Handler threadHandler = new Handler();
//            new Thread() {
//                @Override
//                public void run() {
//                    threadHandler.postDelayed(new Runnable() {
//                        public void run() {
//                            MyLocationData locData = new MyLocationData.Builder()
//                                    .accuracy(location.getRadius())
////                    .direction(location.getDirection()).latitude(41.07)
////                    .longitude(121.09).build();
//                                    // 此处设置开发者获取到的方向信息，顺时针0-360
//                                    .direction(location.getDirection()).latitude(location.getLatitude())
//                                    .longitude(location.getLongitude()).build();
//                            tv_Add.setText(location.getAddrStr());
//                            tv_Lat.setText(" " + location.getLatitude() + " ");
//                            tv_Lon.setText(" " + location.getLongitude() + " ");
//
//
////                System.out.println("----------------"+location.getLongitude()+"---"+location.getLatitude()+"----------"+location.getAddrStr()+location.getCity());
//
////                                Handler handler = new Handler();
////
////                                Runnable runnable = new Runnable() {
////
////                                    @Override
////                                    public void run() {
////
////                                        //要做的事情
////                                        System.out.println("执行次数："+(++n)+"\n");
////                                        handler.postDelayed(runnable, 5000);
////                                    }
////                                };
//
//
//
//
//
//                                sendmessage(location.getAddrStr());
//
//
//
//                            mBaiduMap.setMyLocationData(locData);
//
//                        }
//                    }, 15000);
//                }
//            }.start();

//
//            Handler handler = new Handler();
//            Runnable runnable = new Runnable() {
//                @Override
//                public void run() {
//
//                    //要做的事情
//                    System.out.println("执行次数：");
//                    handler.postDelayed(this, 5000);
//                }
//            };
//
//
//
//            Handler mHandler = new Handler();
//            Runnable heartBeatRunnable = new Runnable() {
//                @Override
//                public void run() {
//                    int num=(int)(Math.random()*100+1);
//                    tv_heart.setText(" "+num);
//                    System.out.println("-------------------心率"+num);
//                    //每隔一定的时间，对长连接进行一次心跳检测
//                    mHandler.postDelayed(this, 1000);
//                }
//            };




// your code here













        }
    }
}