package com.effone.hostismeandroid.common;

public class URL {

    /*urls effone local*/

    public static final String BASE_URL="http://192.168.11.23/hostisme/admin/api";
    public static final String GET_RESTAURANT_LIST=BASE_URL+"/restaurant/get-restaurant?cityid=1";
    public static final String POST_ORDER=BASE_URL+"/orders/get-orderdetails";
    public static final String GET_BILL=BASE_URL+"/viewandpaybill/get-billdetails";
    public static final String APPLY_PROMOCODE=BASE_URL+"/viewandpaybill/get-promocode";
    public static final String GET_BOOKING_HISTORY=BASE_URL+"/bookinghistory/get-bookedhistory?device_id=";
    public static final String GET_MENU=BASE_URL+"/menu/get-menudetails?restaurant_id=";
    public static final String REQUEST_SERVICE=BASE_URL+"/servicerequest/get-servicerequestdetails";
    //?restaurant_id=1&promocode=O75ZRGKEOH&device_id=14558295348432157



    /*dummy urls*/

    public static final String menu_url="http://192.168.2.44/android_web_api/Sample.json";
    public static final String place_order_url="http://192.168.2.44/android_web_api/include/PlaceOrder.php";
    public static final String get_placed_order="http://192.168.2.44/android_web_api/include/getPlacedOrder.php?order_id=";
    public static final String bill_url="http://192.168.2.44/android_web_api/bill.json";
    public static final String tables_url="http://192.168.2.44/android_web_api/table_nos.json";
    public static final String dish_images_url = "http://192.168.2.44/android_web_api/images.json";
    public static final String restaurant_list_url = "http://192.168.2.44/android_web_api/include/restraunt.php";
    public static final String booking_hisory_url="http://192.168.2.44/android_web_api/booking_history.json";
    public static final String book_a_table_url="http://192.168.2.44/android_web_api/book_a_table";
    public static final String payment_confirmation_url="http://192.168.2.44/webservice_sample/PaymentConf.json";

}