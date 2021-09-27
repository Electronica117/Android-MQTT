package com.Electronica117_MQTT.electronica117_mqtt;

import android.app.Notification;

public class Notify {

    private static final NotificacionHandle notificacionHandle = new NotificacionHandle(MyContext.getMyContext());
    private static int notificacionId = 0;


    public static void Create(String titulo, String mensaje){
        android.app.Notification.Builder notificationBuilder = notificacionHandle.createNotification(titulo, mensaje, true);
        notificacionHandle.getManager().notify(notificacionId++, notificationBuilder.build());
    }

    public static void Create(String titulo, String mensaje, int icon){
        android.app.Notification.Builder notificationBuilder = notificacionHandle.createNotification(titulo, mensaje, true);
        notificationBuilder.setSmallIcon(icon);
        notificacionHandle.getManager().notify(notificacionId++, notificationBuilder.build());
    }

}
