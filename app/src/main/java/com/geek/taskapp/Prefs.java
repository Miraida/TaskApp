package com.geek.taskapp;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    private final SharedPreferences preferences;
    public Prefs(Context context){
        preferences = context.getSharedPreferences("settings",Context.MODE_PRIVATE);
    }
    public void saveBoardState(){
        preferences.edit().putBoolean("isShown",true).apply();
    }

    public boolean isBoardShown(){
        return preferences.getBoolean("isShown",false);
    }

    public void saveProfileText(String text){
        preferences.edit().putString("profileText",text).apply();
    }
    public String getProfileText(){
        return preferences.getString("profileText","");
    }
}
