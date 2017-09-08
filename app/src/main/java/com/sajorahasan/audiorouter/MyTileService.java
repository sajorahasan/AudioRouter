package com.sajorahasan.audiorouter;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by Sajora on 14-08-2017.
 */

@RequiresApi(api = Build.VERSION_CODES.N)
public class MyTileService extends TileService {
    private static final String TAG = "MyTileService";
    private static final String SERVICE_STATUS_FLAG = "serviceStatus";
    private static final String PREFERENCES_KEY = "com.sajorahasan.audio_router";

    private ToggleAudioManager toggleAudioManager;
    private boolean isActive;

    @Override
    public void onCreate() {
        super.onCreate();
        toggleAudioManager = new ToggleAudioManager();
    }


    @Override
    public void onStartListening() {
        super.onStartListening();
        isActive = getServiceStatus();
        if (GenericUtility.getBoolFromSharedPrefsForKey("Force", getApplicationContext()) != isActive) {
            updateTileState(Tile.STATE_ACTIVE);
        } else {
            updateTileState(Tile.STATE_INACTIVE);
        }
    }

    private void updateTileState(int state) {
        Tile tile = getQsTile();
        if (tile != null) {
            tile.setState(state);
            Icon icon = tile.getIcon();
            switch (state) {
                case Tile.STATE_ACTIVE:
                    icon.setTint(Color.WHITE);
                    break;
                case Tile.STATE_INACTIVE:
                case Tile.STATE_UNAVAILABLE:
                default:
                    icon.setTint(Color.GRAY);
                    break;
            }
            tile.updateTile();
        }
    }

    @Override
    public void onClick() {
        super.onClick();

        int i = 0;
        boolean isChecked = GenericUtility.getBoolFromSharedPrefsForKey("Force", this);
        ToggleAudioManager toggleAudioManager = new ToggleAudioManager();
        if (toggleAudioManager.State()) {
            boolean z;
            String str = "Force";
            if (isChecked) {
                z = false;
            } else {
                z = true;
            }
            GenericUtility.setBoolToSharedPrefsForKey(str, z, this);
            if (isChecked) {
                i = 1;
            }
            toggleAudioManager.ForMedia(i);
        }

        Tile tile = getQsTile();
        switch (tile.getState()) {
            case Tile.STATE_INACTIVE:
                toggleAudioManager.ForMedia(isActive ? 0 : 1);
                GenericUtility.setBoolToSharedPrefsForKey("Force", isActive, getApplicationContext());
                updateTileState(Tile.STATE_ACTIVE);
                break;
            case Tile.STATE_ACTIVE:
                toggleAudioManager.ForMedia(isActive ? 0 : 1);
                GenericUtility.setBoolToSharedPrefsForKey("Force", isActive, getApplicationContext());
            default:
                updateTileState(Tile.STATE_INACTIVE);
                break;
        }
    }

    @Override
    public void onDestroy() {
        toggleAudioManager = null;
        super.onDestroy();
    }

    /**
     * Called when this tile moves out of the listening state.
     */
    @Override
    public void onStopListening() {
        Log.d("QS", "Stop Listening");
    }

    /**
     * Called when the user removes this tile from Quick Settings.
     */
    @Override
    public void onTileRemoved() {
        Log.d("QS", "Tile removed");
    }

    // Changes the appearance of the tile.
    private void updateTile() {

        Tile tile = this.getQsTile();
        boolean isActive = getServiceStatus();

        Icon newIcon;
        String newLabel;
        int newState;

        // Change the tile to match the service status.
        if (isActive) {

            newLabel = String.format(Locale.US,
                    "%s %s",
                    getString(R.string.tile_label),
                    getString(R.string.service_active));

            newIcon = Icon.createWithResource(getApplicationContext(), R.drawable.ic_speaker);

            newState = Tile.STATE_ACTIVE;

        } else {
            newLabel = String.format(Locale.US,
                    "%s %s",
                    getString(R.string.tile_label),
                    getString(R.string.service_inactive));

            newIcon =
                    Icon.createWithResource(getApplicationContext(),
                            android.R.drawable.ic_dialog_alert);

            newState = Tile.STATE_INACTIVE;
        }

        // Change the UI of the tile.
        tile.setLabel(newLabel);
        tile.setIcon(newIcon);
        tile.setState(newState);

        // Need to call updateTile for the tile to pick up changes.
        tile.updateTile();
    }

    // Access storage to see how many times the tile has been tapped.
    private boolean getServiceStatus() {

        SharedPreferences prefs =
                getApplicationContext()
                        .getSharedPreferences(PREFERENCES_KEY, MODE_PRIVATE);

        boolean isActive = prefs.getBoolean(SERVICE_STATUS_FLAG, false);
        isActive = !isActive;

        prefs.edit().putBoolean(SERVICE_STATUS_FLAG, isActive).apply();

        return isActive;
    }

    private void showDialog() {
        Toast.makeText(this, "Hi ", Toast.LENGTH_SHORT).show();
    }
}
