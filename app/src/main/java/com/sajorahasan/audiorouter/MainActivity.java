package com.sajorahasan.audiorouter;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private HeadsetIntentReceiver myReceiver;
    private ToggleAudioManager toggleAudioManager;
    private ToggleButton toggleButton;
    private TextView txtState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        txtState = (TextView) findViewById(R.id.txtState);

        toggleAudioManager = new ToggleAudioManager();

        myReceiver = new HeadsetIntentReceiver();
        startService(new Intent(this, ToggleService.class));

        toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!toggleAudioManager.State()) {
                    txtState.setText(getString(R.string.cannot_support));
                    txtState.setTextColor(Color.parseColor("#FF0000"));
                } else if (GenericUtility.getBoolFromSharedPrefsForKey("Force", getApplicationContext()) != isChecked) {
                    toggleAudioManager.ForMedia(isChecked ? 0 : 1);
                    GenericUtility.setBoolToSharedPrefsForKey("Force", isChecked, getApplicationContext());
                    AppWidgetManager man = AppWidgetManager.getInstance(getApplicationContext());
                    new ToggleWidget().onUpdate(getApplicationContext(), man, man.getAppWidgetIds(new ComponentName(MainActivity.this.getApplicationContext(), ToggleWidget.class)));
                }
            }
        });
    }

    public void onResume() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.HEADSET_PLUG");
        registerReceiver(myReceiver, filter);
        if (GenericUtility.getBoolFromSharedPrefsForKey("Force", getApplicationContext())) {
            toggleButton.setChecked(true);
        } else {
            toggleButton.setChecked(false);
        }
        super.onResume();
    }

    public void onPause() {
        unregisterReceiver(myReceiver);
        super.onPause();
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
