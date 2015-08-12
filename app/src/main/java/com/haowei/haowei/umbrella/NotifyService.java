package com.haowei.haowei.umbrella;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class NotifyService extends IntentService {

    public NotifyService() {
        super("NotifyService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("NotifyService", "onHandleIntent, receive Intent, handle it");
        Notifier.createNotify(this);
        AlarmBroadcastReceiver.completeWakefulIntent(intent);
    }

}
