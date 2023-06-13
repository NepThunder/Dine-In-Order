package com.dineInOrder.application.Activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    final static String DBName="mydatabase.db";
    final static int version=2;

    public DBHelper(@Nullable Context context) {
        super(context, DBName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query="create table orders(id integer primary key autoincrement,name text,phone text,price int,image int,quantity int,description text,foodName text,userId text)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query="Drop table if exists orders";
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }
    public boolean insertOrder(String name,String phone,int price,int image,String description,String foodName,String userId,int quantity){
    SQLiteDatabase database=getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",name);
        values.put("phone",phone);
        values.put("price",price);
        values.put("image",image);
        values.put("description",description);
        values.put("foodName",foodName);
        values.put("userId",userId);
        values.put("quantity",quantity);
        long id=database.insert("orders",null,values);
        return id > 0;
    }
}
