package com.geek.taskapp;

import android.app.Application;

import androidx.room.Room;

import com.geek.taskapp.room.AppDataBase;

public class App extends Application {
    public static AppDataBase appDataBase;
    @Override
    public void onCreate() {
        super.onCreate();
        appDataBase = Room.databaseBuilder(this,AppDataBase.class,"database").allowMainThreadQueries().build();
    }
}
