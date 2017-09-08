package com.sajorahasan.audiorouter;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HeadsetService extends Service {
    private Context context;
    private boolean isRunning;
    private HeadsetIntentReceiverServ myReceiverServ;

    private class HeadsetIntentReceiverServ extends BroadcastReceiver {
        final /* synthetic */ boolean $assertionsDisabled = (!HeadsetService.class.desiredAssertionStatus());
        SharedPreferences setting;

        private HeadsetIntentReceiverServ() {
            setting = HeadsetService.this.getSharedPreferences("appstate", 0);
        }

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.HEADSET_PLUG")) {
                Class audioSystemClass = null;
                Method setForceUse = null;
                switch (intent.getIntExtra("state", -1)) {
                    case 0:
                        try {
                            audioSystemClass = Class.forName("android.media.AudioSystem");
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        if ($assertionsDisabled || audioSystemClass != null) {
                            try {
                                setForceUse = audioSystemClass.getMethod("setForceUse", new Class[]{Integer.TYPE, Integer.TYPE});
                            } catch (NoSuchMethodException e2) {
                                e2.printStackTrace();
                            }
                            if ($assertionsDisabled || setForceUse != null) {
                                try {
                                    setForceUse.invoke(null, new Object[]{Integer.valueOf(1), Integer.valueOf(0)});
                                    return;
                                } catch (IllegalAccessException e3) {
                                    e3.printStackTrace();
                                    return;
                                } catch (InvocationTargetException e4) {
                                    e4.printStackTrace();
                                    return;
                                }
                            }
                            throw new AssertionError();
                        }
                        throw new AssertionError();
                    case 1:
                        if (this.setting.getBoolean("Force", false)) {
                            try {
                                audioSystemClass = Class.forName("android.media.AudioSystem");
                            } catch (ClassNotFoundException e5) {
                                e5.printStackTrace();
                            }
                            if ($assertionsDisabled || audioSystemClass != null) {
                                try {
                                    setForceUse = audioSystemClass.getMethod("setForceUse", new Class[]{Integer.TYPE, Integer.TYPE});
                                } catch (NoSuchMethodException e22) {
                                    e22.printStackTrace();
                                }
                                if ($assertionsDisabled || setForceUse != null) {
                                    try {
                                        setForceUse.invoke(null, new Object[]{Integer.valueOf(1), Integer.valueOf(1)});
                                        return;
                                    } catch (IllegalAccessException e32) {
                                        e32.printStackTrace();
                                        return;
                                    } catch (InvocationTargetException e42) {
                                        e42.printStackTrace();
                                        return;
                                    }
                                }
                                throw new AssertionError();
                            }
                            throw new AssertionError();
                        }
                        try {
                            audioSystemClass = Class.forName("android.media.AudioSystem");
                        } catch (ClassNotFoundException e52) {
                            e52.printStackTrace();
                        }
                        if ($assertionsDisabled || audioSystemClass != null) {
                            try {
                                setForceUse = audioSystemClass.getMethod("setForceUse", new Class[]{Integer.TYPE, Integer.TYPE});
                            } catch (NoSuchMethodException e222) {
                                e222.printStackTrace();
                            }
                            if ($assertionsDisabled || setForceUse != null) {
                                try {
                                    setForceUse.invoke(null, new Object[]{Integer.valueOf(1), Integer.valueOf(0)});
                                    return;
                                } catch (IllegalAccessException e322) {
                                    e322.printStackTrace();
                                    return;
                                } catch (InvocationTargetException e422) {
                                    e422.printStackTrace();
                                    return;
                                }
                            }
                            throw new AssertionError();
                        }
                        throw new AssertionError();
                    default:
                        return;
                }
            }
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        context = this;
        isRunning = false;
        IntentFilter filterServ = new IntentFilter("android.intent.action.HEADSET_PLUG");
        myReceiverServ = new HeadsetIntentReceiverServ();
        registerReceiver(myReceiverServ, filterServ);
    }

    public void onDestroy() {
        isRunning = false;
        unregisterReceiver(myReceiverServ);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (isRunning) {
            isRunning = true;
        }
        return START_STICKY;
    }
}
