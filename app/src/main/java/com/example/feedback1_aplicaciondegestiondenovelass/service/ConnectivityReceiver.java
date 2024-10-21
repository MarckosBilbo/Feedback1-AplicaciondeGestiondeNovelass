package com.example.feedback1_aplicaciondegestiondenovelass.service;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.feedback1_aplicaciondegestiondenovelass.modelo.NovelRepository;

public class ConnectivityReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            boolean isWiFi = isConnected && activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;

            if (isWiFi) {
                Log.d("ConnectivityReceiver", "Wi-Fi connected, triggering data sync");
                // Trigger data synchronization
                NovelRepository repository = new NovelRepository((Application) context.getApplicationContext());
                repository.fetchAllNovels();
            }
        }
    }
}