package com.haowei.haowei.umbrella;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // an Intent broadcast.
        if (intent.getAction() == "android.intent.action.BOOT_COMPLETED") {
            Log.i("Receiver", "Receive Boot Complete");
            Alarm.setAlarm(context);
        }
    }
}
