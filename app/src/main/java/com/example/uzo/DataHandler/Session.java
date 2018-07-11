package com.example.uzo.DataHandler;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    private SharedPreferences prefs;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setuserID(String id) {
        prefs.edit().putString("student_id", id).commit();
    }

    public String getuserID() {
        String usename = prefs.getString("student_id","");
        return usename;
    }
}
