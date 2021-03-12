package com.lyhoangvinh.app.utils;

import android.text.Html;

public class Utils {
    public static String format(String s) {
        String str = "";
        try {
            str = new String(s.getBytes("ISO-8859-1"), "UTF-8");
        } catch (Exception e) {
            str = s;
        }
        return Html.fromHtml(str).toString();
    }

    public static void putStateNighMode(boolean bool) {
        SharedPrefs.getInstance().put("NIGHT_MODE", bool);
    }

    public static boolean getStateNightMode() {
        return SharedPrefs.getInstance().get("NIGHT_MODE", Boolean.class);
    }
}
