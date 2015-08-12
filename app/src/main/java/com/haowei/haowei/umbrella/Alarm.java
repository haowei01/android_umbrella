package com.haowei.haowei.umbrella;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.util.Log;

/**
 * Created by haowei on 8/2/15.
 */
public class Alarm {
    public static void setAlarm(Context context){
        Log.i("MyAlarm", "setAlarm called");
        AlarmManager alarMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarMgr.setInexactRepeating(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 10 * 1000,
                100 * 1000,
                getBroadCastActivityPendingIntent(context)
        );
        ComponentName bootReceiver = new ComponentName(context, MyReceiver.class);
        PackageManager packageManager = context.getPackageManager();
        packageManager.setComponentEnabledSetting(bootReceiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
        );
    }

    public static void cancelAlarm(Context context){
        AlarmManager alarMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarMgr.cancel(getBroadCastActivityPendingIntent(context));
        ComponentName bootReceiver = new ComponentName(context, MyReceiver.class);
        context.getPackageManager().setComponentEnabledSetting(
                bootReceiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
        );
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

    public static PendingIntent getBroadCastActivityPendingIntent(Context context) {
        Log.i("MyAlarm", "Get BroadCast Activity Pending Intent");
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }
}
