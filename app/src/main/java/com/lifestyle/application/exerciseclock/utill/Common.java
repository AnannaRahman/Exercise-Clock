package com.lifestyle.application.exerciseclock.utill;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ananna on 11/10/2017.
 */

public class Common {

    public static String CreateUUID() {

        return UUID.randomUUID().toString().replace("-", "");
    }

    public static void SaveOnSharedPreference(Context ctx,String sharedPrefName, String key, String value) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE).edit();
        editor.remove(key);
        editor.putString(key, value);
        editor.apply();
    }

    public static SharedPreferences GetSharedPreference(Context ctx,String sharedPrefName){
        SharedPreferences prefs = ctx.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        return prefs;
    }

    public static void RemoveAllDataFromSharedPref(Context ctx,String sharedPrefName){
        ctx.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE).edit().clear().apply();
    }

    public static String fotmattedTime(long millis){


        String formatedTime = String.format("%02d : %02d : %02d ",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
        Log.d("time",formatedTime);

        return  formatedTime;
    }
}
