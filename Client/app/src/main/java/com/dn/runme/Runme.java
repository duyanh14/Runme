package com.dn.runme;

import android.content.Context;
import android.content.SharedPreferences;

public class Runme {
    public static Context context;

    public static SharedPreferences sharedPref;

    public static void Init(Context context) {
        Runme.context = context;
        Runme.sharedPref = context.getSharedPreferences("Everything", Context.MODE_PRIVATE);
    }
}
