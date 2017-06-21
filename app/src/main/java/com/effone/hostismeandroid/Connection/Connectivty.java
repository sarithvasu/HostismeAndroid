package com.effone.hostismeandroid.Connection;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import com.effone.hostismeandroid.R;


/**
 * Created by sarith.vasu on 22-02-2017.
 */

public class Connectivty {
    private static final String TAG = "PermissionMessage";
    private Context m_context;

    public Connectivty(Context m_context) {
        this.m_context = m_context;
    }



    public static void showInternetDisabledAlertToUser(final Context context) {

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
                                System.exit(0);
                            }
                        }
                );
        AlertDialog alert = builder.create();
        alert.show();
    }



}
