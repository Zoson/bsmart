package com.koalitech.service.Database;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zoson on 6/26/15.
 */
public class SharedPreference {
    Context context;
    SharedPreferences mySharedPreferences;
    public SharedPreference(Context context) {
        this.context = context;
        mySharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public void set(String key, String value){
        if (value.equals("")||value.equals(" ")||value.equals("null")){
            return;
        }
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String get(String key,String defaultString) {
        String value =mySharedPreferences.getString(key, defaultString);
        return value;
    }

    public boolean get(String key,boolean defaultValue){
        boolean value = mySharedPreferences.getBoolean(key,defaultValue);
        return value;
    }

    public void set(String key,boolean value){
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

}
