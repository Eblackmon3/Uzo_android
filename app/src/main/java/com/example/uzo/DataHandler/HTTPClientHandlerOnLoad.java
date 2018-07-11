package com.example.uzo.DataHandler;

import android.os.AsyncTask;
import android.util.Log;

public class HTTPClientHandlerOnLoad extends AsyncTask<String,String,String> {



    //first param is the method, then json, then link
    public String doInBackground(String... params) {
        String method= params[0];
        String json= params[1];
        String link= params[2];
        String ret;
        if(method.equals("postJSONRetObject")) {
             ret = HTTPClient.postJSONRetObject(json, link).toString();
        }else if(method.equals("postJSONRetArray")) {
            ret = HTTPClient.postJSONRetArray(json, link).toString();

        }else if(method.equals("getJSONObject")) {
            ret = HTTPClient.getJSONObject(json, link).toString();
        }else {
            ret = HTTPClient.getJSONArray(json, link).toString();
        }
        return ret;

    }

}
