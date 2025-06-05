package com.google.firebase.quickstart.fcm.java;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class HangUpBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (context != null) {
            Intent serviceIntent = new Intent(context, CallingService.class);
            context.stopService(serviceIntent);
        }
    }
}
