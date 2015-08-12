package com.haowei.haowei.umbrella;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by haowei on 8/3/15.
 */
public class AlarmBroadcastReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("AlarmBroadcastReceiver", "onReceive, received Intent");
        Intent service = new Intent(context, NotifyService.class);
        startWakefulService(context, service);
    }
}
