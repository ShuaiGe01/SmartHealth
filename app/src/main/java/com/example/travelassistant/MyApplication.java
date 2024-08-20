package com.example.travelassistant;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.example.travelassistant.db.DBManager;
import com.example.travelassistant.message.model.Model;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseIM;

public class MyApplication extends Application {
    private  static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();

        SDKInitializer.setAgreePrivacy(this,true);

        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.

        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        //此处声明全局信息
        DBManager.initDB(this);

        //初始化环信EaseIM
        EMOptions options=new EMOptions();
        options.setAcceptInvitationAlways(false);//设置需要同意后才能接受邀请
        options.setAutoAcceptGroupInvitation(false);//设置需要同意后才能接受群邀请

        //EaseIM初始化
        //EaseIM.getInstance().init(this,options);

        //EaseIM初始化
        if(EaseIM.getInstance().init(this, options)){
            //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
            EMClient.getInstance().setDebugMode(true);
            //EaseIM初始化成功之后再去调用注册消息监听的代码 ...
        }

        //初始化数据模型层类
        Model.getInstance().init(this);

        //初始化全局上下文
        mContext=this;
    }
    public  static Context getGlobalApplication() {
        return mContext;
    }
}
