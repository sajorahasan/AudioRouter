package com.sajorahasan.audiorouter;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class GenericUtility {
    private static String packageName = BuildConfig.APPLICATION_ID;

    public static boolean getBoolFromSharedPrefsForKey(String key, Context context) {
        return context.getSharedPreferences(packageName, 0).getBoolean(key, false);
    }

    public static boolean setBoolToSharedPrefsForKey(String key, boolean value, Context context) {
        Editor editor = context.getSharedPreferences(packageName, 0).edit();
        try {
            editor.putBoolean(key, value);
            editor.apply();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static int getIntFromSharedPrefsForKey(String key, Context context) {
        return context.getSharedPreferences(packageName, 0).getInt(key, -1);
    }

    public static boolean setIntToSharedPrefsForKey(String key, int value, Context context) {
        Editor editor = context.getSharedPreferences(packageName, 0).edit();
        try {
            editor.putInt(key, value);
            editor.apply();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getStringFromSharedPrefsForKey(String key, Context context) {
        String selectedValue = "";
        return context.getSharedPreferences(packageName, 0).getString(key, "");
    }

    public static boolean setStringToSharedPrefsForKey(String key, String value, Context context) {
        Editor editor = context.getSharedPreferences(packageName, 0).edit();
        try {
            editor.putString(key, value);
            editor.apply();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
