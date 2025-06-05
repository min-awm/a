package com.google.firebase.quickstart.fcm.java;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.firebase.quickstart.fcm.R;

public class CallingService extends Service {

    public static final String CHANNEL_ID = "fcm_default_channel";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showIncomingCallPopup();
        return START_STICKY;
    }

    @SuppressLint("ForegroundServiceType")
    private void showIncomingCallPopup() {
        Intent hangupIntent = new Intent(getApplicationContext(), HangUpBroadcast.class);
        PendingIntent hangupPendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(),
                0,
                hangupIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ? PendingIntent.FLAG_MUTABLE : 0)
        );

        Intent incomingCallIntent = new Intent(getApplicationContext(), IncomingCallActivity.class);
        PendingIntent incomingCallPendingIntent = PendingIntent.getActivity(
                getApplicationContext(),
                0,
                incomingCallIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ? PendingIntent.FLAG_MUTABLE : 0)
        );

        Intent answerIntent = new Intent(getApplicationContext(), MainActivity.class);
        answerIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent answerPendingIntent = PendingIntent.getActivity(
                getApplicationContext(),
                0,
                answerIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ? PendingIntent.FLAG_MUTABLE : 0)
        );

        RemoteViews customView = new RemoteViews(getPackageName(), R.layout.incoming_call_popup);
        customView.setOnClickPendingIntent(R.id.btnAcceptCall, answerPendingIntent);
        customView.setOnClickPendingIntent(R.id.btnRejectCall, hangupPendingIntent);

        createNotificationChannel();

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_ic_notification)
                .setCustomContentView(customView)
                .setFullScreenIntent(incomingCallPendingIntent, true)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setVibrate(new long[]{0, 500, 1000})
                .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.ring_stone))
                .setAutoCancel(true);

        startForeground(1024, notificationBuilder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();

            String name = "Incoming call";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.ring_stone), audioAttributes);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}