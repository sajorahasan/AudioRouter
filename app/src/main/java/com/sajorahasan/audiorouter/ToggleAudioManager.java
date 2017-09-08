package com.sajorahasan.audiorouter;

import java.lang.reflect.Method;

public class ToggleAudioManager {
    private Method getForceUse = null;
    private Method setForceUse = null;
    private boolean status = false;

    public ToggleAudioManager() {
        Class<?> audioSystemClass = null;
        try {
            audioSystemClass = Class.forName("android.media.AudioSystem");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (audioSystemClass != null) {
            try {
                setForceUse = audioSystemClass.getMethod("setForceUse", new Class[]{Integer.TYPE, Integer.TYPE});
                getForceUse = audioSystemClass.getMethod("getForceUse", new Class[]{Integer.TYPE});
            } catch (NoSuchMethodException e2) {
                e2.printStackTrace();
            }
            if (setForceUse != null) {
                status = true;
            }
        }
    }

    public boolean State() {
        return status;
    }

    public void ForMedia(int forceUseNumber) {
        try {
            setForceUse.invoke(null, new Object[]{Integer.valueOf(1), Integer.valueOf(forceUseNumber)});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String GetUsage() {
        String usage = "";
        try {
            usage = (((((usage + "FOR_COMMUNICATION: " + getForceUse.invoke(null, new Object[]{Integer.valueOf(0)}) + ", ") + "FOR_MEDIA: " + this.getForceUse.invoke(null, new Object[]{Integer.valueOf(1)}) + ", ") + "FOR_RECORD: " + this.getForceUse.invoke(null, new Object[]{Integer.valueOf(2)}) + ", ") + "FOR_DOCK: " + this.getForceUse.invoke(null, new Object[]{Integer.valueOf(3)}) + ", ") + "FOR_SYSTEM: " + this.getForceUse.invoke(null, new Object[]{Integer.valueOf(4)}) + ", ") + "FOR_HDMI_SYSTEM_AUDIO: " + this.getForceUse.invoke(null, new Object[]{Integer.valueOf(5)}) + ", ";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usage;
    }
}
