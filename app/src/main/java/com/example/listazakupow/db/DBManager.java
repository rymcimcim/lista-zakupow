package com.example.listazakupow.db;

/**
 * Created by jedrzej on 16.06.17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String name, Float price, Integer quantity, Float currency, Boolean isBought) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.NAME, name);
        contentValue.put(DatabaseHelper.PRICE, price);
        contentValue.put(DatabaseHelper.QUANTITY, quantity);
        contentValue.put(DatabaseHelper.CURRENCY, currency);
        contentValue.put(DatabaseHelper.IS_BOUGHT, isBought);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] {
                DatabaseHelper.ID,
                DatabaseHelper.NAME,
                DatabaseHelper.PRICE,
                DatabaseHelper.QUANTITY,
                DatabaseHelper.CURRENCY,
                DatabaseHelper.IS_BOUGHT
        };
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long id, String name, Float price, Integer quantity, Float currency, Boolean isBought) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME, name);
        contentValues.put(DatabaseHelper.PRICE, price);
        contentValues.put(DatabaseHelper.QUANTITY, quantity);
        contentValues.put(DatabaseHelper.CURRENCY, currency);
        contentValues.put(DatabaseHelper.IS_BOUGHT, isBought);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper.ID + " = " + id, null);
        return i;
    }

    public void delete(long id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.ID + "=" + id, null);
    }

}
