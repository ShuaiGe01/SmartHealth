package com.example.travelassistant.message.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.travelassistant.message.model.dao.ContactTable;
import com.example.travelassistant.message.model.dao.InviteTable;


/**
 * @Author cyh
 * @Date 2021/6/3 15:23
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context,String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建联系人的表
        db.execSQL(ContactTable.CREATE_TAB);

        //创建邀请信息的表
        db.execSQL(InviteTable.CREATE_TAB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
