package com.dineInOrder.application.Activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.dineInOrder.application.Models.cartModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    FirebaseAuth auth;

    final static String DBName="mydatabase.db";
    final static int version=1;

    public DBHelper(@Nullable Context context) {
        super(context, DBName, null, version);
        auth=FirebaseAuth.getInstance();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query="create table orders(id integer primary key autoincrement,name text,phone text,price int,image int,quantity int,description text,foodName text,userId text,payment text)";
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
//        values.put("Payment",payment);
        long id=database.insert("orders",null,values);
        return id > 0;
    }
    public ArrayList<cartModel> getOrders(){
        FirebaseUser firebaseUser=auth.getCurrentUser();
        assert firebaseUser != null;
        String userID=firebaseUser.getUid();
        ArrayList<cartModel>orders=new ArrayList<>();
        SQLiteDatabase database=this.getWritableDatabase();
        String query = "SELECT id, foodName, image, price FROM orders WHERE userId = ?";
        Cursor cursor = database.rawQuery(query, new String[] {userID});

        if(cursor.moveToFirst()){
            do {
                cartModel model = new cartModel();
                model.setOrderId(cursor.getInt(0) + "");
                model.setOrderName(cursor.getString(1));
                model.setOrderImage(cursor.getInt(2));
                model.setPrice(cursor.getInt(3) + "");
                orders.add(model);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return orders;
    }
    public Cursor getOrderById(int id){

        SQLiteDatabase database=this.getWritableDatabase();
        Cursor cursor =database.rawQuery("Select * from orders where id="+id,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        return cursor;
    }
    public boolean updateOrder(String name,String phone,int price,int image,String description,String foodName,String userId,int quantity,int id){
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
//        values.put("Payment",payment);
//        long row=database.update("orders",values,"id"+id,null);
        long row = database.update("orders", values, "id=?", new String[]{String.valueOf(id)});
        return row > 0;
    }
    public int deleteOrder(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete("orders","id=?",new String[]{String.valueOf(id)});
    }
}
