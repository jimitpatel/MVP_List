package com.wellthy.www.utils;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jimitpatel on 22/03/17.
 */

public class SqliteHelper extends SQLiteOpenHelper {

    public static final String TABLE_USER_LIST = "users";
    public static final String COLUMN_USER = "name";
    public static final String COLUMN_MOBILE = "mobileNumber";

    private static final String DB_NAME = "wellthy.db";
    private static final int DB_VERSION = 1;

    private static final String CREATE_TABLE = "CREATE TABLE " +
            TABLE_USER_LIST +
            " (" +
            COLUMN_USER + " TEXT NOT NULL PRIMARY KEY, " +
            COLUMN_MOBILE +  " TEXT NOT NULL" +
            ");";

    public SqliteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public SqliteHelper(Context context, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        super(context, DB_NAME, factory, DB_VERSION, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
