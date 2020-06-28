package com.example.studentmgr;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class ConnectionChangeReceiver extends BroadcastReceiver {


    private static final String TAG = ConnectionChangeReceiver.class.getSimpleName();


    @Override

    public void onReceive(Context context, Intent intent) {



        boolean success = false;



        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // State state = connManager.getActiveNetworkInfo().getState();


        NetworkInfo gprs = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);


        if (!gprs.isConnected() && !wifi.isConnected()) {

            Toast.makeText(context, "lose connect", Toast.LENGTH_LONG).show();

        }

    }
}
