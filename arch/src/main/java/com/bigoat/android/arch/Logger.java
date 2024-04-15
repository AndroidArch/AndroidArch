package com.bigoat.android.arch;

import android.util.Log;

interface Logger {
    String tag = null;

    default void log(String msg, Object... args) {
        log(tag, Log.DEBUG, msg, args);
    }

    default void loge(String msg, Object... args) {
        log(tag, Log.ERROR, msg, args);
    }

    default void log(String tag, int level, String msg, Object... args) {
        if (msg == null) { return; }

        if (tag == null) {
            tag = this.getClass().getSimpleName();
        }
        String log = String.format(msg, args);
        switch (level) {
            case Log.DEBUG:
                Log.d(tag, log);
                break;
            case Log.INFO:
                Log.i(tag, log);
                break;
            case Log.WARN:
                Log.w(tag, log);
                break;
            case Log.ERROR:
                Log.e(tag, log);
                break;
            case Log.ASSERT:
                Log.wtf(tag, log);
                break;
            default:
                Log.v(tag, log);
                break;
        }
    }
}
