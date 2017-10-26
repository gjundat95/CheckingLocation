package com.jundat95.tinhngo.sharelocation.until;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by tinhngo on 12/27/16.
 */

public class TiSharedPreferences {
    static final String MY_SHARED = "MY_SHARED";

    public static void saveSharedPreferences(Context context, String tag, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(tag,value);
        editor.commit();
    }

    public static String  getSharedPreferences(Context context, String tag){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED,Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(tag,"");
        if(value.equalsIgnoreCase(""))
            return null;
        return value;
    }

    public static void removeSharedPreferences(Context context, String tag){
       SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED,Context.MODE_PRIVATE);
       sharedPreferences.edit().remove(tag).commit();
    }
}
