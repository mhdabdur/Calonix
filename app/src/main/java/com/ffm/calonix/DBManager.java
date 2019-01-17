package com.ffm.calonix;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
    private Context context;
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public DBManager(Context c) {
        this.context = c;
    }

    public DBManager open() throws SQLException {
        this.dbHelper = new SQLiteHelper(this.context);
        this.database = this.dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        this.dbHelper.close();
    }

    public void insert(String weight, String age, String height) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(SQLiteHelper.weight, weight);
        contentValue.put(SQLiteHelper.height, height);
        contentValue.put(SQLiteHelper.age, age);
        this.database.insert(SQLiteHelper.table_name, null, contentValue);
    }


    public Cursor fetch(int a) {
        if (a==1){
        Cursor cursor = this.database.query(SQLiteHelper.table_name, new String[]{SQLiteHelper._ID, SQLiteHelper.weight, SQLiteHelper.age,SQLiteHelper.height}, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
        }
        else {
            Cursor cursor = this.database.query(SQLiteHelper.table_name1, new String[]{SQLiteHelper._ID, SQLiteHelper.weight, SQLiteHelper.time}, null, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
            }
            return cursor;
        }
    }

    public int update(long _id, String weight, String age, String height) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteHelper.weight, weight);
        contentValues.put(SQLiteHelper.age, age);
        contentValues.put(SQLiteHelper.height, height);
        return this.database.update(SQLiteHelper.table_name, contentValues, "_id = " + _id, null);
    }

    public void delete(long _id) {
        this.database.delete(SQLiteHelper.table_name, "_id=" + _id, null);
    }
}

