package com.sajorahasan.audiorouter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

public class HeadsetIntentReceiver extends BroadcastReceiver {
    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onReceive(Context context, Intent intent) {
        int i;
        TextView txtState = (TextView) ((Activity) context).findViewById(R.id.txtState);
        if (intent.getAction().equals("android.intent.action.HEADSET_PLUG")) {
            switch (intent.getIntExtra("state", -1)) {
                case 0:
                    txtState.setText(context.getResources().getString(R.string.unplugged));
                    txtState.setTextColor(Color.parseColor("#FF0000"));
                    break;
                case 1:
                    txtState.setText(context.getResources().getString(R.string.plugged));
                    txtState.setTextColor(Color.parseColor("#0090FF"));
                    break;
                default:
                    txtState.setText(context.getResources().getString(R.string.cannot_detect));
                    txtState.setTextColor(Color.parseColor("#FF8C00"));
                    break;
            }
        }
        String action = intent.getAction();
        switch (action.hashCode()) {
            case -301431627:
                if (action.equals("android.bluetooth.device.action.ACL_CONNECTED")) {
                    i = 0;
                    break;
                }
            case 1821585647:
                if (action.equals("android.bluetooth.device.action.ACL_DISCONNECTED")) {
                    i = 1;
                    break;
                }
            default:
                i = -1;
                break;
        }
        switch (i) {
            case 0:
                Log.d("BluetoothDevice", "Connected");
                return;
            case 1:
                Log.d("BluetoothDevice", "Disconnected");
                return;
            default:
                return;
        }
    }
}
