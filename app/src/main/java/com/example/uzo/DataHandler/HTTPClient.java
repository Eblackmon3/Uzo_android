package com.example.uzo.DataHandler;

import android.os.AsyncTask;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class HTTPClient{
    private static final String WEB_APP_URL= "https://uzo-web-app.herokuapp.com/";

    public static String getWebAppUrl() {
        return WEB_APP_URL;
    }

    public static JSONArray getJSONArray(String jsonBody, String param) {
        URL url = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder;
        JSONArray retJSON= new JSONArray();

        try {
            url = new URL(WEB_APP_URL + param);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setReadTimeout(15*1000);
            con.connect();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                stringBuilder.append(line + "\n");
            }
            retJSON= new JSONArray(stringBuilder.toString());
            con.disconnect();

        }catch(Exception e){
            Log.e("Error in getJSONArray", e.toString());

        }finally{

        }
        return retJSON;

    }


    public static JSONObject getJSONObject(String jsonBody, String param) {
        URL url = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder;
        JSONObject retObject= new JSONObject();

        try {
            url = new URL(WEB_APP_URL + param);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setReadTimeout(15*1000);
            con.connect();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                stringBuilder.append(line + "\n");
            }
            retObject= new JSONObject(stringBuilder.toString());
            con.disconnect();

        }catch(Exception e){
            Log.e("Error in getJSONArray", e.toString());

        }finally{

        }
        return retObject;

    }

    public static JSONArray postJSONRetArray(String jsonBody, String param) {
        URL url = null;
        BufferedReader reader = null;
        JSONArray retArray= new JSONArray();

        try {
            url = new URL(WEB_APP_URL + param);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setReadTimeout(15*1000);
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            StringBuilder sb = new StringBuilder();
            wr.write(jsonBody);
            wr.flush();int HttpResult = con.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                System.out.println("" + sb.toString());
            } else {
                System.out.println(con.getResponseMessage());
            }
            con.disconnect();
            retArray= new JSONArray(sb.toString());

        }catch(Exception e){
            Log.e("Error in getJSONArray", e.toString());

        }finally{

        }
        return retArray;

    }


    public static JSONObject postJSONRetObject(String jsonBody, String param) {
        URL url = null;
        BufferedReader reader = null;
        JSONObject retObject= new JSONObject();

        try {
            url = new URL(WEB_APP_URL + param);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setReadTimeout(15*1000);
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            StringBuilder sb = new StringBuilder();
            wr.write(jsonBody);
            wr.flush();int HttpResult = con.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                System.out.println("" + sb.toString());
            } else {
                System.out.println(con.getResponseMessage());
            }
            con.disconnect();
            retObject= new JSONObject(sb.toString());

        }catch(Exception e){
            Log.e("Error in getJSONArray", e.toString());

        }finally{

        }
        return retObject;

    }


}
