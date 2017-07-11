package com.effone.hostismeandroid.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.effone.hostismeandroid.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * Created by sarith.vasu on 20-01-2017.
 */

public final class Util {
    public static void createNetErrorDialog(final Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.internet_connection)
                .setTitle(R.string.titleMsg)
                .setCancelable(false)
                .setPositiveButton(R.string.settings,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Intent i = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                                context.startActivity(i);
                            }
                        }
                )
                .setNegativeButton(R.string.button,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }
                );
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static final class Operations {
        private Operations() throws InstantiationException {
            throw new InstantiationException("This class is not for instantiation");
        }
        /**
         * Checks to see if the device is online before carrying out any operations.
         *
         * @return
         */
        public static boolean isOnline(Context context) {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            }
            return false;
        }
    }
    private Util() throws InstantiationException {
        throw new InstantiationException("This class is not for instantiation");
    }
    public static void createOKAlert(Context context,String title,String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(context,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        if(!title.equals("")) {
            builder.setTitle(title);

        }
        builder.setMessage(msg)
                .setCancelable(false)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert = builder.show();
        alert.getWindow().getAttributes().gravity = Gravity.CENTER;
        TextView messageText = (TextView)alert.findViewById(android.R.id.message);
        int titleId =context.getResources().getIdentifier("alertTitle", "id", "android");
        TextView titleText = (TextView)alert.findViewById(titleId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {

            titleText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            titleText.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
        }
        titleText.setGravity(Gravity.CENTER);
        messageText.setGravity(Gravity.CENTER);
        alert.show();
    }
    public static void createErrorAlert(Context context,String title,String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(context,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        if(!title.equals("")) {
            builder.setTitle(title);

        }
        builder.setMessage(msg)
                .setCancelable(false)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert = builder.show();
        alert.getWindow().getAttributes().gravity = Gravity.CENTER;

        int titleId =context.getResources().getIdentifier("alertTitle", "id", "android");
        TextView titleText = (TextView)alert.findViewById(titleId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {

            titleText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            titleText.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
        }
        titleText.setGravity(Gravity.CENTER);
        alert.show();
    }

    public static void createOKAlert(Context context,String title,String msg ,DialogInterface.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        if(!title.equals("")) {
            builder.setTitle(title);

        }
        builder.setMessage(msg)
                .setCancelable(false)
                .setNeutralButton("OK", listener);

        AlertDialog alert = builder.show();
        alert.getWindow().getAttributes().gravity = Gravity.CENTER;
        TextView messageText = (TextView)alert.findViewById(android.R.id.message);
        int titleId =context.getResources().getIdentifier("alertTitle", "id", "android");
        TextView titleText = (TextView)alert.findViewById(titleId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {

            titleText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            titleText.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
        }
        titleText.setGravity(Gravity.CENTER);
        messageText.setGravity(Gravity.CENTER);
        alert.show();
    }
    public static boolean isInternetOn(Context context) {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

        // Check for network connections

        if (connec != null) {
            NetworkInfo nInfo = connec.getActiveNetworkInfo();

            if (nInfo != null && nInfo.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;



    }
    public static void createOkCancelDialog(AppCompatActivity context, String text, DialogInterface.OnClickListener listner) {
        Dialog dialog = null;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(
                text)
                .setCancelable(false)
                .setPositiveButton("Ok",listner )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {

                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        dialog = alert;
        dialog.show();


    }
}
