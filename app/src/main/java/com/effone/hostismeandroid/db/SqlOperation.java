package com.effone.hostismeandroid.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.effone.hostismeandroid.model.CartItems;
import com.effone.hostismeandroid.model.Order_Items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.effone.hostismeandroid.db.DBConstant.COLUMN_NAME_CAT_NAME;
import static com.effone.hostismeandroid.db.DBConstant.COLUMN_NAME_FLAG;
import static com.effone.hostismeandroid.db.DBConstant.COLUMN_NAME_IS_SPECIAL;
import static com.effone.hostismeandroid.db.DBConstant.COLUMN_NAME_ITEM_INGRE;
import static com.effone.hostismeandroid.db.DBConstant.COLUMN_NAME_ITEM_NAME;
import static com.effone.hostismeandroid.db.DBConstant.COLUMN_NAME_ITEM_PRICE;
import static com.effone.hostismeandroid.db.DBConstant.COLUMN_NAME_ITEM_QTY;
import static com.effone.hostismeandroid.db.DBConstant.COLUMN_NAME_MENU_ITEM_ID;
import static com.effone.hostismeandroid.db.DBConstant.COLUMN_NAME_MENU_TYPE;
import static com.effone.hostismeandroid.db.DBConstant.COLUMN_NAME_SUB_CAT;
import static com.effone.hostismeandroid.db.DBConstant.TABLE_NAME_ORDERITEM;

/**
 * Created by sumanth.peddinti on 5/10/2017.
 */

public class SqlOperation {
    private static final String TAG = "SqlOperation" ;
    private  SqliteConnection sqliteconnection;
    private SQLiteDatabase database;

    public SqlOperation(Context context) {
        sqliteconnection = new SqliteConnection(context);
    }

    public void open() throws SQLException {
        database = sqliteconnection.getWritableDatabase();
    }

    public void close() {
        sqliteconnection.close();
    }


    public void AddOrSubstractProduct(String item_cat, String sub_item_cat, int menu_item_id, String name, ArrayList<String> checkedCountries, String ingredients, String specail, float price, int quantity, int flag, int kindOfOperation) {
        int values = 0;
        String listString="";
        for (int i=0;i<checkedCountries.size();i++)
        {
            if(i == 0)
            listString += checkedCountries.get(i) ;
            else
                listString += ","+checkedCountries.get(i);
        }
        if(kindOfOperation == 0) {
            values=0;
        }else {
     /*NOTE when you close the Session we have to delete this data to create a new order*/

            List<HashMap<String, String>> allElementsDictionary = new ArrayList<>();

            Cursor cursor;
            String select = "SELECT item_qty FROM "+TABLE_NAME_ORDERITEM +" where " + COLUMN_NAME_CAT_NAME + "= '" + item_cat +
                    "' and " + COLUMN_NAME_MENU_ITEM_ID + "=" + menu_item_id +" and " + COLUMN_NAME_FLAG +" = " +flag +" and "+COLUMN_NAME_MENU_TYPE +" = '"+listString+"' and "+COLUMN_NAME_IS_SPECIAL +" = '"+specail+"'" ;
            cursor = database.rawQuery(select, null);
            if (cursor.getCount() == 0 && kindOfOperation == 1) // if there are no elements and the operation is ADD , set QTY in  1
            {
                ContentValues row = new ContentValues();
                row.put(COLUMN_NAME_CAT_NAME, item_cat);
                row.put(COLUMN_NAME_SUB_CAT, sub_item_cat);
                row.put(COLUMN_NAME_MENU_ITEM_ID, menu_item_id);
                row.put(COLUMN_NAME_ITEM_NAME, name);
                row.put(COLUMN_NAME_ITEM_INGRE, ingredients);
                row.put(COLUMN_NAME_ITEM_PRICE,price);
                row.put(COLUMN_NAME_ITEM_QTY, quantity);
                row.put(COLUMN_NAME_IS_SPECIAL,specail);
                row.put(COLUMN_NAME_MENU_TYPE,listString);
                row.put(COLUMN_NAME_FLAG, flag);

                database.insert(TABLE_NAME_ORDERITEM , null, row); //insert in DB the request
                values = 1;
                //add this food with qty 1
            } else if (cursor.getCount() > 0) {
                //this means the product exist with some qty
                cursor.moveToFirst();
                int oldQty = Integer.parseInt(cursor.getString(0));//get the qty from Database
//                int idSum = Integer.parseInt(cursor.getString(1));//get the id from database
                ContentValues row = new ContentValues();

                switch (kindOfOperation) {
                    case 1:
                        //its a sum, update the qty in 1
                        row.put(COLUMN_NAME_ITEM_QTY, oldQty + 1);//add  1

                        database.update(TABLE_NAME_ORDERITEM, row, COLUMN_NAME_MENU_ITEM_ID+" = "  + menu_item_id, null); //update qty in DB the request
                        break;

                    case 2:
                        if (oldQty > 0) {
                            row.put(COLUMN_NAME_ITEM_QTY, oldQty - 1);//substract -1 if the qty is greater than 0,
                            database.update(TABLE_NAME_ORDERITEM, row, COLUMN_NAME_MENU_ITEM_ID+" = " + menu_item_id, null); //update qty DB the request
                            if(oldQty == 1) {
                                database.delete(TABLE_NAME_ORDERITEM, COLUMN_NAME_MENU_ITEM_ID + "= " + menu_item_id, null);//delete the complete row if the qunatity is 0
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
            cursor.close();//It is important close the cursor when you finish your process.
        }

    }

    public ArrayList<CartItems> getCartItems(int flags) {
        ArrayList<CartItems> cartItemses=new ArrayList<>();
        Cursor cursor;
        String select = "SELECT * from " + TABLE_NAME_ORDERITEM + " Where " + COLUMN_NAME_FLAG + " = " + flags;
        cursor = database.rawQuery(select, null);
        if (cursor.getCount() == 0) // if there are no elements do nothing
        {
            Log.d(TAG, "no elements");
        } else { //if there are elemnts
            Log.d(TAG, "there are elemnets");

            float orderTotal = 0;
            while (cursor.moveToNext()) {
                CartItems cartItems=new CartItems();
                cartItems.setItemCatagerie(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CAT_NAME)));
                cartItems.setItemSubCat(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_SUB_CAT)));
                cartItems.setItemMenuCatId(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_MENU_ITEM_ID)));
                cartItems.setItemName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ITEM_NAME)));
                cartItems.setItemIngred(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ITEM_INGRE)));
                cartItems.setItemPrice(cursor.getFloat(cursor.getColumnIndex(COLUMN_NAME_ITEM_PRICE)));
                cartItems.setMenuType(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_MENU_TYPE)));
                cartItems.setItemQuantity(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ITEM_QTY)));
                cartItems.setSpecial(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_IS_SPECIAL)));
                cartItemses.add(cartItems);
            }
        }
        cursor.close();
        return cartItemses;
    }

    public int getItemCountInCart() {
        Cursor cursor;
        String select = "SELECT * from " + TABLE_NAME_ORDERITEM + " Where " + COLUMN_NAME_FLAG + " = 1" ;
        cursor = database.rawQuery(select, null);
        return  cursor.getCount();
    }

    public boolean cartItemDelete(int itemMenuCatId, String itemName, String menuType) {



        return database.delete(TABLE_NAME_ORDERITEM,
                COLUMN_NAME_MENU_ITEM_ID + "="+itemMenuCatId+" AND " + COLUMN_NAME_ITEM_NAME + " = '"+itemName+"' AND " +
                        COLUMN_NAME_MENU_TYPE + "= '"+menuType+"'",
                null) > 0;
    }

    public void setFlagaUpdate() {
        Cursor cursor;
        String select = "SELECT * FROM "+TABLE_NAME_ORDERITEM +" where "+ COLUMN_NAME_FLAG + " = 1"   ;
        cursor = database.rawQuery(select, null);
        if(cursor.getCount()>0) {
            ContentValues row = new ContentValues();
            row.put(COLUMN_NAME_FLAG, 2);//substract -1 if the qty is greater than 0,
            database.update(TABLE_NAME_ORDERITEM, row, COLUMN_NAME_FLAG+" = 1" , null); //update qty DB the request
        }
    }
    public boolean delete() {
/*      this is what your database delete method should look like
        this method deletes by id, the first column in your database*/
        return database.delete(TABLE_NAME_ORDERITEM, null, null) > 0;
    }


    public ArrayList<Order_Items> getItemName(String[] values, String order_id) {
        String whereClouse;
        int[] numbers = new int[values.length];
        for(int i = 0;i < values.length;i++)
        {
            // Note that this is assuming valid input
            // If you want to check then add a try/catch
            // and another index for the numbers if to continue adding the others
            numbers[i] = Integer.parseInt(values[i]);
        }

        ArrayList<Order_Items> cartItemses = new ArrayList<>();
        Cursor cursor;
        StringBuilder s=new StringBuilder();
        for(int i = 0;i < numbers.length;i++) {
            if (i == 0) {
                s.append("(" + numbers[i]);
            } else {
                s.append("," + numbers[i]);
            }
            if (i == numbers.length - 1) {
                s.append(")");
            }
        }
        whereClouse=s.toString();



        String select = "SELECT * from " + TABLE_NAME_ORDERITEM + " Where " + COLUMN_NAME_MENU_ITEM_ID + " IN " +whereClouse + " and " + COLUMN_NAME_FLAG + " = " + Integer.parseInt(order_id);
        try{
        cursor = database.rawQuery(select, null);
        if (cursor.getCount() == 0) // if there are no elements do nothing
        {
            Log.d(TAG, "no elements");
        } else { //if there are elemnts
            while (cursor.moveToNext()) {
            Order_Items order_items=new Order_Items();



            order_items.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ITEM_NAME)));
            order_items.setPrice(cursor.getFloat(cursor.getColumnIndex(COLUMN_NAME_ITEM_PRICE)));
            order_items.setQuantity(cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ITEM_QTY)));
            cartItemses.add(order_items);
            }
        }
        } catch (Exception e){
            Log.e("SqlOperation",e.getMessage());
        }
        return cartItemses;
    }


    public void setOrderFlagsUpdate(int order_id) {
        Cursor cursor;
        String select = "SELECT * FROM "+TABLE_NAME_ORDERITEM +" where "+ COLUMN_NAME_FLAG + " = 2"   ;
        cursor = database.rawQuery(select, null);
        if(cursor.getCount()>0) {
            ContentValues row = new ContentValues();
            row.put(COLUMN_NAME_FLAG, order_id);//substract -1 if the qty is greater than 0,
            database.update(TABLE_NAME_ORDERITEM, row, COLUMN_NAME_FLAG+" = 2" , null); //update qty DB the request
        }
    }

}
