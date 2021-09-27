package com.Electronica117_MQTT.electronica117_mqtt;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;

public class NotificacionHandle extends ContextWrapper {

    private NotificationManager manager;

    public static final String CHANNEL_HIGH_ID = "1";
    private final String CHANNEL_HIGH_NAME = "HIGH CHANNEL";
    public static final String CHANNEL_LOW_ID = "2";
    private final String CHANNEL_LOW_NAME = "LOW CHANNEL";

    public NotificacionHandle(Context base) {
        super(base);
        createChannels();
    }

    public NotificationManager getManager(){
        if (manager == null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    private void createChannels(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel highChannel = new NotificationChannel(CHANNEL_HIGH_ID,
                    CHANNEL_HIGH_NAME, NotificationManager.IMPORTANCE_HIGH);

            highChannel.enableLights(true);
            highChannel.enableVibration(true);
            highChannel.setShowBadge(true);
            highChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            NotificationChannel lowChannel = new NotificationChannel(CHANNEL_LOW_ID,
                    CHANNEL_LOW_NAME, NotificationManager.IMPORTANCE_LOW);

            getManager().createNotificationChannel(highChannel);
            getManager().createNotificationChannel(lowChannel);
        }
    }

    public Notification.Builder createNotification(String titulo, String texto, boolean importancia){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            if(importancia){
                return this.createNotificationWithChannel(titulo, texto, CHANNEL_HIGH_ID);
            }
            return this.createNotificationWithChannel(titulo, texto, CHANNEL_LOW_ID);
        }
        return this.createNotificationWithoutChannel(titulo, texto);
    }

    private Notification.Builder createNotificationWithChannel(String titulo, String message, String channelId){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            return new Notification.Builder(MyContext.getMyContext(), channelId)
                    .setContentTitle(titulo)
                    .setContentText(message)
                    .setSmallIcon(R.mipmap.electronica117icon)
                    .setAutoCancel(true);
        }
        return null;
    }

    private Notification.Builder createNotificationWithoutChannel(String titulo, String message){
        return new Notification.Builder(MyContext.getMyContext())
                .setContentTitle(titulo)
                .setContentText(message)
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setAutoCancel(true);
    }
}
