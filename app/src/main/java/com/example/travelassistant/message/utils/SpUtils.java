package com.example.travelassistant.message.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.travelassistant.MyApplication;

/**
 * @Author cyh
 * @Date 2021/6/4 10:29
 */
//保存
//获取数据
public class SpUtils {
    public static final String IS_NEW_INVITE = "is_new_invite"; //新的邀请标记
    private static SpUtils instance = new SpUtils();
    private static SharedPreferences mSp;

    private SpUtils() {

    }

    //单例
    public static SpUtils getInstance() {

        if (mSp == null) {
            mSp = MyApplication.getGlobalApplication().getSharedPreferences("im", Context.MODE_PRIVATE);
        }

        return instance;
    }

    //保存
    public void save(String key, Object value) {
        if (value instanceof String) {
            mSp.edit().putString(key, (String) value).commit();
        } else if (value instanceof Boolean) {
            mSp.edit().putBoolean(key, (Boolean) value).commit();
        } else if (value instanceof Integer) {
            mSp.edit().putInt(key, (Integer) value).commit();
        }
    }

    //获取数据的方法
    public String getString(String key,String defValue) {
        return mSp.getString(key,defValue);
    }

    //获取boolean数据
    public boolean getBoolean(String key,boolean defValue) {
        return mSp.getBoolean(key,defValue);
    }

    //获取int类型数据
    public int getInt(String key, int defValue) {
        return mSp.getInt(key, defValue);
    }
}

