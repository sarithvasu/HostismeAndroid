package com.effone.hostismeandroid.db;

/**
 * Created by sumanth.peddinti on 5/10/2017.
 */

public class DBConstant {
    public static final int DATABASE_VERSION = 1;
    public static final String DB_NAME = "HostIsMe";
    public static final String TABLE_NAME_ORDERITEM = "orderItem";
    public static final String COLUMN_NAME_CAT_NAME = "cat_name";
    public static final String COLUMN_NAME_SUB_CAT = "sub_cat";
    public static final String COLUMN_NAME_MENU_ITEM_ID = "menu_item_id";
    public static final String COLUMN_NAME_ITEM_NAME = "item_name";
    public static final String COLUMN_NAME_ITEM_INGRE = "item_ingre";
    public static final String COLUMN_NAME_ITEM_PRICE = "item_price";
    public static final String COLUMN_NAME_MENU_TYPE="menu_type";
    public static final String COLUMN_NAME_IS_SPECIAL="is_special";
    public static final String COLUMN_NAME_ITEM_QTY = "item_qty";
    public static final String COLUMN_NAME_FLAG = "flag";
    public static final String COLUMN_NAME_ID = "_id";
    public static final double serviceTax = 06.00;
    public static final double vatTax = 12.00;
    public static final double ser = 05.00;


    public static final String CREATE_ORDERITEM = "CREATE TABLE " + TABLE_NAME_ORDERITEM
            + " (" + COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME_CAT_NAME + " TEXT, "
            + COLUMN_NAME_SUB_CAT + " TEXT, " +
            COLUMN_NAME_MENU_ITEM_ID + " INTEGER, " + COLUMN_NAME_ITEM_NAME + " TEXT, " + COLUMN_NAME_ITEM_INGRE + " TEXT," +
            COLUMN_NAME_ITEM_PRICE + " REAL, " + COLUMN_NAME_ITEM_QTY + " INTEGER," +COLUMN_NAME_MENU_TYPE +" TEXT,"+COLUMN_NAME_IS_SPECIAL+" TEXT,"+ COLUMN_NAME_FLAG + " INTEGER)";


}
