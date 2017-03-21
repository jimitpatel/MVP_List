package com.wellthy.www.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wellthy.www.PojoSqliteClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jimitpatel on 22/03/17.
 */

public class DBHelper {

    private SQLiteDatabase database;
    private SqliteHelper dbHelper;

    public DBHelper(Context context) {
        dbHelper = new SqliteHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public int addUsers(List<PojoSqliteClass> list) {
        int count = 0;
        open();
        database.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            for (PojoSqliteClass data : list) {
                contentValues.put(SqliteHelper.COLUMN_USER, data.userId);
                contentValues.put(SqliteHelper.COLUMN_MOBILE, data.mobile);
                database.insert(SqliteHelper.TABLE_USER_LIST, null, contentValues);
                count++;
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
            close();
        }
        return count;
    }

    public List<PojoSqliteClass> getUserList() {
        open();
        Cursor cursor = database.rawQuery("SELECT * FROM " + SqliteHelper.TABLE_USER_LIST, null);
        List<PojoSqliteClass> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                PojoSqliteClass data = new PojoSqliteClass();
                data.userId = cursor.getString(cursor.getColumnIndex(SqliteHelper.COLUMN_USER));
                data.mobile = cursor.getString(cursor.getColumnIndex(SqliteHelper.COLUMN_MOBILE));
                list.add(data);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public int deleteAllRecords() {
        open();
        int count = database.delete(SqliteHelper.TABLE_USER_LIST, null, null);
        close();
        return count;
    }
}
