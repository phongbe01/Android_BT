package com.example.testlan1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DBName = "mydb.db";
    private static final int VERSION = 2;
    private static final String TABLE_NAME = "NhaHang";

    private static final String ID = "_id";
    private static final String NAME = "Ten";
    private static final String ADDR = "DiaChi";
    private static final String RATE = "DanhGia";

    private SQLiteDatabase myDB;

    public DBHelper (@Nullable Context context){
        super(context, DBName, null, VERSION);
    }


    public void openDB(){
        myDB = getWritableDatabase();
    }

    public void closeDB(){
        if(myDB!=null && myDB.isOpen()){
            myDB.close();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryTable = "CREATE TABLE " + TABLE_NAME + "( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                 NAME + " TEXT NOT NULL, " +
                ADDR + " TEXT NOT NULL, " +
                RATE + " DOUBLE NOT NULL " + ")";
        db.execSQL(queryTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long Insert(String Ten, String DiaChi, double DanhGia){
        ContentValues values = new ContentValues();
        values.put(NAME, Ten);
        values.put(ADDR, DiaChi);
        values.put(RATE, DanhGia);
        return myDB.insert(TABLE_NAME,null,values);
    }

    public Cursor getById(int id)
    {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE _id = " + id  + " ";
        return myDB.rawQuery(query, null);
    }

    public Cursor getAllRestaurent(){
        String query = "SELECT * FROM " + TABLE_NAME;
        return myDB.rawQuery(query,null);
    }

    public ArrayList<Restaurant> getAll(){
        ArrayList<Restaurant> ress = new ArrayList<>();
        Cursor cursor = getAllRestaurent();
        if(cursor!=null){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                ress.add(new Restaurant(cursor.getInt(0),cursor.getString(1),cursor.getString(2), cursor.getDouble(3)));
                cursor.moveToNext();
            }
        }
        return ress;
    }

    public long update(int id, String name, String addr, double rate)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(ADDR, addr);
        contentValues.put(RATE, rate);
        String where = ID +" = "+id;
        return myDB.update(TABLE_NAME,contentValues, where, null);
    }

   public long delete(int position)
   {
       String where = ID +" = "+ position;
       return myDB.delete(TABLE_NAME, where, null);
   }

}
