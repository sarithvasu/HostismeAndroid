package com.effone.hostismeandroid.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sumanth.peddinti on 5/10/2017.
 */

public class SqliteConnection extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "OrderClient";
    public static final String TABLE_ORDER = "OrderPlace";
    public static final String BOOKING_HISTORY="booking_history";
    public static final String TABLE_PRICE = "PriceClient";

    public SqliteConnection(Context context) {
        super(context, DBConstant.DB_NAME, null,DBConstant.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBConstant.CREATE_ORDERITEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }
}
