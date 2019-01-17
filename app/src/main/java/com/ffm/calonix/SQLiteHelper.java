package com.ffm.calonix;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String CREATE_TABLE = "create table STUDENTS ( id INTEGER PRIMARY KEY AUTOINCREMENT, weight int, age int, height int );";
    private static final String DB_NAME = "Profile";
    private static final int DB_VERSION = 1;
    public static final String _ID = "id";
    public static final String weight = "weight";
    public static final String age = "age";
    public static final String table_name = "currentprofile";
    public static final String table_name1 = "targetprofile";
    public static final String height = "height";
    public static final String time = "time";

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        Log.e("SQLITE", "table reated");
        db.execSQL(CREATE_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS profile");
        onCreate(db);
    }

}

