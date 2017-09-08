package com.sajorahasan.audiorouter;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

public class ToggleService extends Service {
    private HeadsetIntentReceiverService myReceiverService;

    private class HeadsetIntentReceiverService extends BroadcastReceiver {
        SharedPreferences setting;
        ToggleAudioManager toggleAudioManager;

        private HeadsetIntentReceiverService() {
            setting = ToggleService.this.getSharedPreferences(getString(R.string.save_file), 0);
            toggleAudioManager = new ToggleAudioManager();
        }

        public void onReceive(Context context, Intent intent) {
            if (toggleAudioManager.State()) {
                Log.d("toggleAudioManager", toggleAudioManager.GetUsage());
            }
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        IntentFilter filterService = new IntentFilter("android.intent.action.HEADSET_PLUG");
        myReceiverService = new HeadsetIntentReceiverService();
        registerReceiver(myReceiverService, filterService);
    }

    public void onDestroy() {
        unregisterReceiver(this.myReceiverService);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
}
