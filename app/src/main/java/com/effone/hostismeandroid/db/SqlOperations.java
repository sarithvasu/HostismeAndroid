package com.effone.hostismeandroid.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.effone.hostismeandroid.activity.Booking_History;
import com.effone.hostismeandroid.model.BookingHistoryItem;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Clase para manipular las operaciones simples con db local como son insert,delete,update,etc
 */
public class SqlOperations {

    //the next two variable it is only for debugging test.
    private String TAG = this.getClass().getSimpleName();
    private boolean LogDebug = true;

    // Database fields
    private SQLiteDatabase database;
    private SqliteConnection sqliteconnection;
    private static final String KEY_INDEX_CATEGORY = "index_category";
    private static final String KEY_INDEX_FOOD = "index_food";
    private static final String KEY_QTY = "quantity";
    private static final String KEY_FOOD_NAME = "food_name";
    private static final String KEY_PRICE = "price";
    private  static  final  String KEY_ORDER_ID="order_id";


    public SqlOperations(Context context) {
        sqliteconnection = new SqliteConnection(context);  //conexion y/o creacion de DB
    }

    public void open() throws SQLException {
        database = sqliteconnection.getWritableDatabase(); // avaliable to write in the db.
    }

    public void close() {
        sqliteconnection.close(); //close db
    }


    public void setEmptyOrder() {
        database.delete("OrderClient", null, null);
    }
    public void setEmptyTotal(){
        database.delete("PriceClient", null, null);
    }

    public List<HashMap<String, String>> getOrder(String order_id) {
           String name="1493958025";
            Cursor cursor;
            List<HashMap<String, String>> allElementsDictionary = new ArrayList<>();
            String select = "SELECT quantity,price,food_name,index_category,index_food,order_id from OrderClient where order_id = '"+order_id+"'";
            cursor = database.rawQuery(select, null);
            if (cursor.getCount() == 0) // if there are no elements do nothing
            {
                Log.d(TAG, "no elements");
            } else { //if there are elemnts
                Log.d(TAG, "there are elemnets");
                //get all the rows and pass the data to allElements dictionary.
                float totalByOrder = 0;

                while (cursor.moveToNext()) {
                    int qty = Integer.parseInt(cursor.getString(0));

                    if (qty > 0) {
                        float totalByFood = Float.parseFloat(cursor.getString(0)) * Float.parseFloat(cursor.getString(1));// qty * price
                        totalByOrder += totalByFood;
                        HashMap<String, String> map = new HashMap<>();

                        map.put("totalByFood", String.valueOf(totalByFood));
                        map.put(KEY_QTY, cursor.getString(0));
                        map.put(KEY_PRICE, cursor.getString(1));
                        map.put(KEY_FOOD_NAME, cursor.getString(2));
                        map.put(KEY_INDEX_CATEGORY, cursor.getString(3));
                        map.put(KEY_INDEX_FOOD, cursor.getString(4));
                        map.put("order_id", cursor.getString(5));
                        allElementsDictionary.add(map);
                        if (LogDebug) {
                            Log.d(TAG, "qty : " + cursor.getString(0) +
                                    "\n price :" + cursor.getString(1) +
                                    "\n foodname :" + cursor.getString(2) +
                                    "\n totalByFood :" + String.valueOf(totalByFood)
                            );
                        }
                    }
                    Log.d(TAG, "total is :" + totalByOrder);
                    //save the total in the TableTotal
                    setTotal(totalByOrder);
                }

            }
            cursor.close();//It is important close the cursor when you finish your process.


            return allElementsDictionary;

    }
    public List<HashMap<String, String>> getPlaceOrderItems(String order_id) {
        Cursor cursor;
        List<HashMap<String, String>> allElementsDictionary = new ArrayList<>();
        String select = "SELECT quantity,price,food_name,index_category,index_food,order_id from OrderClient where order_id = '"+ order_id+"'";
        cursor = database.rawQuery(select, null);
        if (cursor.getCount() == 0) // if there are no elements do nothing
        {
            Log.d(TAG, "no elements");
        } else { //if there are elemnts
            Log.d(TAG, "there are elemnets");
            //get all the rows and pass the data to allElements dictionary.
            float totalByOrder = 0;

            while (cursor.moveToNext()) {
                int qty = Integer.parseInt(cursor.getString(0));

                if (qty > 0) {
                    float totalByFood = Float.parseFloat(cursor.getString(0)) * Float.parseFloat(cursor.getString(1));// qty * price
                    totalByOrder += totalByFood;
                    HashMap<String, String> map = new HashMap<>();

                    map.put("totalByFood", String.valueOf(totalByFood));
                    map.put(KEY_QTY, cursor.getString(0));
                    map.put(KEY_PRICE, cursor.getString(1));
                    map.put(KEY_FOOD_NAME, cursor.getString(2));
                    map.put(KEY_INDEX_CATEGORY, cursor.getString(3));
                    map.put(KEY_INDEX_FOOD, cursor.getString(4));
                    map.put("order_id", cursor.getString(5));
                    allElementsDictionary.add(map);
                    if (LogDebug) {
                        Log.d(TAG, "qty : " + cursor.getString(0) +
                                "\n price :" + cursor.getString(1) +
                                "\n foodname :" + cursor.getString(2) +
                                "\n totalByFood :" + String.valueOf(totalByFood)
                        );
                    }
                }
                Log.d(TAG, "total is :" + totalByOrder);
                //save the total in the TableTotal
                setTotal(totalByOrder);
            }

        }
        cursor.close();//It is important close the cursor when you finish your process.


        return allElementsDictionary;

    }
    public void setTotal(Float total) {
        if(total>0) {
            Cursor cursorTotal;
            String select = "SELECT _id from PriceClient";
            cursorTotal = database.rawQuery(select, null);
            ContentValues row = new ContentValues();
            row.put("total", String.valueOf(total));
            if (cursorTotal.getCount() == 0) {
                //insert  the total price
                database.insert(SqliteConnection.TABLE_PRICE, null, row); //insert in DB the request
            } else {
                // update the value
                cursorTotal.moveToFirst();
                int idSum = Integer.parseInt(cursorTotal.getString(0));//get the id from database
                database.update(SqliteConnection.TABLE_PRICE, row, "_id=" + idSum, null); //update qty DB the request
            }
            cursorTotal.close();//It is important close the cursor when you finish your process.

        }

    }
    
    public String getTotal(){
        String total="0.00";
        Cursor cursorTotal;
        String select = "SELECT total from PriceClient";
        cursorTotal = database.rawQuery(select, null);
        if (cursorTotal.getCount() > 0)
        {
            cursorTotal.moveToFirst();
           total = cursorTotal.getString(0);//get the total price
        }
        cursorTotal.close();//It is important close the cursor when you finish your process.

        return total;
    }


    public int AddOrSubstractProduct(int categoryIndex, int foodIndex, String food, float price, int kindOperation) {
        /* kind Operation = add or Subtract
         1= add
         2= substract

         */  int values = 0;
        String name="SUMANTH";
        if(kindOperation == 0) {
            values=0;
        }else {
     /*NOTE when you close the Session we have to delete this data to create a new order*/

            List<HashMap<String, String>> allElementsDictionary = new ArrayList<>();

            Cursor cursor;
            String select = "SELECT quantity,_id FROM OrderClient where " + KEY_INDEX_CATEGORY + "=" + categoryIndex +
                    " and " + KEY_INDEX_FOOD + "=" + foodIndex +" and " + KEY_ORDER_ID +" = '" +name +"'" ;
            cursor = database.rawQuery(select, null);
            if (cursor.getCount() == 0 && kindOperation == 1) // if there are no elements and the operation is ADD , set QTY in  1
            {
                ContentValues row = new ContentValues();
                row.put(KEY_INDEX_CATEGORY, categoryIndex);
                row.put(KEY_INDEX_FOOD, foodIndex);
                row.put(KEY_QTY, 1);
                row.put(KEY_FOOD_NAME, food);
                row.put(KEY_PRICE, String.valueOf(price));
                row.put(KEY_ORDER_ID,name);
                database.insert(SqliteConnection.TABLE_NAME, null, row); //insert in DB the request
                values = 1;
                //add this food with qty 1
            } else if (cursor.getCount() > 0) {
                //this means the product exist with some qty
                cursor.moveToFirst();
                int oldQty = Integer.parseInt(cursor.getString(0));//get the qty from Database
                int idSum = Integer.parseInt(cursor.getString(1));//get the id from database
                ContentValues row = new ContentValues();

                switch (kindOperation) {
                    case 1:
                        values = oldQty + 1;
                        //its a sum, update the qty in 1
                        row.put(KEY_QTY, oldQty + 1);//add  1

                        database.update(SqliteConnection.TABLE_NAME, row, "_id=" + idSum, null); //update qty in DB the request
                        break;

                    case 2:
                        if (oldQty > 0) {
                            row.put(KEY_QTY, oldQty - 1);//substract -1 if the qty is greater than 0,
                            values = oldQty - 1;
                        }
                        database.update(SqliteConnection.TABLE_NAME, row, "_id=" + idSum, null); //update qty DB the request
                        break;
                    default:
                        break;
                }
            }
            cursor.close();//It is important close the cursor when you finish your process.
        }
        return  values;
    }


    public int getCount(int categoryIndex, int foodIndex, String food, float price, int kindOperation) {
        Cursor cursosr;
        String select = "SELECT quantity,_id FROM OrderClient where " + KEY_INDEX_CATEGORY + "=" + categoryIndex +
                " and " + KEY_INDEX_FOOD + "=" + foodIndex;
        cursosr = database.rawQuery(select, null);
        int oldQty;
        if (cursosr.getCount() > 0) {
            //this means the product exist with some qty
            cursosr.moveToFirst();
            oldQty  = Integer.parseInt(cursosr.getString(0));
        }else{
            oldQty=0;
        }
        cursosr.close();
        return  oldQty;
    }

    public void  itemDelete(int item_cat, int index_food) {
        database.execSQL("delete from OrderClient where "+KEY_INDEX_CATEGORY+"= '"+item_cat+"' and "+KEY_INDEX_FOOD+" = " +index_food);
    }
    public int getCartItem(){
        Cursor cursor;
        String select = "SELECT * FROM OrderClient where order_id = 'SUMANTH'";
        cursor = database.rawQuery(select, null);
       return  cursor.getCount();
    }

    public int getOrderCount(){
        Cursor cursor;
        String select = "SELECT * FROM OrderPlace where status =" +"'BOOKED'";
        cursor = database.rawQuery(select, null);
        return  cursor.getCount();
    }

    public long updateOrderId(String order_id){
        ContentValues row = new ContentValues();
        row.put(KEY_ORDER_ID, order_id );//add  1

      return database.update(SqliteConnection.TABLE_NAME, row, KEY_ORDER_ID+"=" + "'SUMANTH'", null);
    }


    public long placeOrder(String ts, int table_no, String rest_name, String currentDateTimeString,
                           String booked,String description,double totalPrice,int qutity) {

        ContentValues row=new ContentValues();
        row.put("_id", ts);
        row.put("table_no", table_no);
        row.put("rest_name", rest_name);
        row.put("time_stamp", currentDateTimeString);
        row.put("status", String.valueOf(booked));
        row.put("description",description);
        row.put("totalPrice",totalPrice);
        row.put("quantity",qutity);


      return  database.insert(SqliteConnection.TABLE_ORDER, null, row); //insert in DB the request
    }

    public List<String> getPayItems(){
        List<String> hs = new ArrayList<>();
        Cursor cursor;
        String select = "SELECT _id from "+SqliteConnection.TABLE_ORDER+ " Where status = 'BOOKED'";
        cursor = database.rawQuery(select, null);
        if(cursor.getCount() == 0 ){
            Log.d(TAG, "no elements");
        }else {
            while (cursor.moveToNext())
                hs.add(cursor.getString(0));
        }
        cursor.close();
        return hs;
    }

    public List<HashMap<String, String>> getPlaceOrder(String order_id) {

        Cursor cursor;
        List<HashMap<String, String>> allElementsDictionarys = new ArrayList<>();
        String select = "SELECT * from "+SqliteConnection.TABLE_ORDER+ " Where _id = '"+ order_id+"'";
        cursor = database.rawQuery(select, null);
        if (cursor.getCount() == 0) // if there are no elements do nothing
        {
            Log.d(TAG, "no elements");
        } else { //if there are elemnts
            Log.d(TAG, "there are elemnets");
            //get all the rows and pass the data to allElements dictionary.
            float totalByOrder = 0;

            while (cursor.moveToNext()) {



                    HashMap<String, String> map = new HashMap<>();

                    map.put("_id", cursor.getString(0));
                    map.put("table_no", cursor.getString(1));
                    map.put("rest_name", cursor.getString(2));
                    map.put("time_stamp", cursor.getString(3));
                    map.put("status",cursor.getString(4));
                    map.put("description", cursor.getString(5));
                    map.put("totalPrice",cursor.getString(6));
                    map.put("quantity",cursor.getString(7));

                    allElementsDictionarys.add(map);
                    if (LogDebug) {
                        Log.d(TAG, "id : " + cursor.getString(0) +
                                "\n table_no :" + cursor.getString(1) +
                                "\n rest_name :" + cursor.getString(2) +
                                "\n time_stamp :" + cursor.getString(3)+
                                "\n status :" + cursor.getString(4)+
                                "\n description :" + cursor.getString(5) +
                                "\n totalPrice :" + cursor.getString(6)+
                                "\n quantity :" + cursor.getString(7)
                        );
                    }
                }

            }


        cursor.close();//It is important close the cursor when you finish your process.


        return allElementsDictionarys;
    }


    public void updatePlaceOrderStatus(String order_id){
        if(database.isOpen()){
        ContentValues row = new ContentValues();
        row.put("status", "Received" );//add  1
        database.update(SqliteConnection.TABLE_ORDER, row, "_id"+" = '" + order_id + "'", null);
        }else {
        }
    }

    public long pymentStatment(int id, String date, String rest_name, String order_id,
                           int  table_no,String description,double bill_no,double bill_ammount,String status) {

        ContentValues row=new ContentValues();
        row.put("_id", id);
        row.put("date", date);
        row.put("rest_name", rest_name);
        row.put("order_id", order_id);
        row.put("table_no", table_no);
        row.put("description",description);
        row.put("bill_no",bill_no);
        row.put("bill_ammount",bill_ammount);
        row.put("status",status);

        return  database.insert(SqliteConnection.BOOKING_HISTORY, null, row); //insert in DB the request
    }





    public List<BookingHistoryItem> getBookedHistory(Long value) {

        Cursor cursor;
        List<BookingHistoryItem> allElementsDictionarys = new ArrayList<>();
        String select;
        if(value == 000000) {
            select = "SELECT * from " + SqliteConnection.BOOKING_HISTORY;
        }else{
            select = "SELECT * from " + SqliteConnection.BOOKING_HISTORY +" where bill_no = '"+ value +"'";
        }
        cursor = database.rawQuery(select, null);
        if (cursor.getCount() == 0) // if there are no elements do nothing
        {
            Log.d(TAG, "no elements");
        } else { //if there are elemnts
            Log.d(TAG, "there are elemnets");
            //get all the rows and pass the data to allElements dictionary.
            float totalByOrder = 0;

            while (cursor.moveToNext()) {


                BookingHistoryItem bookingHistoryItem= new BookingHistoryItem();

                bookingHistoryItem.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                bookingHistoryItem.setOrder_id( cursor.getString(cursor.getColumnIndex("order_id")));
                bookingHistoryItem.setDate( cursor.getString(cursor.getColumnIndex("date")));
                bookingHistoryItem.setRest_name( cursor.getString(cursor.getColumnIndex("rest_name")));
                bookingHistoryItem.setDescription( cursor.getString(cursor.getColumnIndex("description")));
                bookingHistoryItem.setTable_no( cursor.getInt(cursor.getColumnIndex("table_no")));
                bookingHistoryItem.setBill_no(cursor.getInt(cursor.getColumnIndex("bill_no")));
                bookingHistoryItem.setBill_ammount(cursor.getDouble(cursor.getColumnIndex("bill_ammount")));
                bookingHistoryItem.setStatus(cursor.getString(cursor.getColumnIndex("status")));

                allElementsDictionarys.add(bookingHistoryItem);
                if (LogDebug) {
                    Log.d(TAG, "id : " + cursor.getString(0) +
                            "\n table_no :" + cursor.getString(1) +
                            "\n rest_name :" + cursor.getString(2) +
                            "\n time_stamp :" + cursor.getString(3)+
                            "\n status :" + cursor.getString(4)+
                            "\n description :" + cursor.getString(5) +
                            "\n totalPrice :" + cursor.getString(6)+
                            "\n quantity :" + cursor.getString(7)
                    );
                }
            }

        }


        cursor.close();//It is important close the cursor when you finish your process.


        return allElementsDictionarys;
    }

}
