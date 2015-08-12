package com.haowei.haowei.umbrella;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

/**
 * Created by haowei on 8/3/15.
 */
public class Notifier {
    public static void createNotify(Context context){
        NotificationCompat.Builder notifyBuild = new NotificationCompat.Builder(context);
        notifyBuild.setContentTitle("Umbrella Notification");
        notifyBuild.setContentText("Notification Content");
        notifyBuild.setSmallIcon(R.mipmap.ic_launcher);
        notifyBuild.setContentIntent(getPendingIntentMainActivity(context));

        notifyBuild.setAutoCancel(true);
        NotificationManager manager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        manager.notify(1000, notifyBuild.build());
    }

    public static PendingIntent getPendingIntentMainActivity(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(
                context,
                1234,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }
}
