package com.example.uzo.uzo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.uzo.DataHandler.HTTPClientHandlerOnLoad;
import com.example.uzo.DataHandler.Session;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OpenGigs extends AppCompatActivity {

    private TextView mTextMessage;
    private Session session;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    List<String> job_ids;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent in;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Log.i("New Activity", "Switching to Open Gigs");
                    in=new Intent(getBaseContext(),MyGigs.class);
                    startActivity(in);

                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_notifications:

                    Log.i("New Activity", "Switching to Open Gigs");
                    in=new Intent(getBaseContext(),LoginActivity.class);
                    session.setuserID(null);
                    startActivity(in);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_gigs);

        session=new Session(OpenGigs.this);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_dashboard);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        HTTPClientHandlerOnLoad handler= new HTTPClientHandlerOnLoad();
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();
        Log.i("makes it here ", "makes it here ");

        listAdapter = new OpenGigsArrayAdapter(this, listDataHeader, listDataChild,job_ids);

        // setting list adapter
        expListView.setAdapter(listAdapter);


    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {

        SharedPreferences sharedPref = OpenGigs.this.getPreferences(Context.MODE_PRIVATE);
        int lastJob = getResources().getInteger(R.integer.last_job_id);
        lastJob= sharedPref.getInt(getResources().getResourceName(R.integer.last_job_id), lastJob);

        JSONObject interestedStudent= new JSONObject();
        JSONArray opJobs= new JSONArray();
        try {
            HTTPClientHandlerOnLoad handler = new HTTPClientHandlerOnLoad();
            opJobs = new JSONArray(handler.execute("getJSONArray", handler.toString(), "get_open_jobs").get());
            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<String>>();
            job_ids= new ArrayList<>();
            ArrayList<String> jobHolder= new ArrayList<>();
            for (int i = 0; i < opJobs.length(); i++) {
                listDataHeader.add(new JSONObject(opJobs.get(i).toString()).get("job_title").toString().trim()+" at "+new JSONObject(opJobs.get(i).toString()).get("company_name").toString().trim());
                jobHolder.add("Company Name: "+new JSONObject(opJobs.get(i).toString()).get("company_name").toString().trim());
                jobHolder.add("Description: "+new JSONObject(opJobs.get(i).toString()).get("description").toString().trim());
                jobHolder.add("Pay Rate: " +new JSONObject(opJobs.get(i).toString()).get("rate").toString());
                jobHolder.add("Start Date: " +new JSONObject(opJobs.get(i).toString()).get("date").toString()+" "+
                        new JSONObject(opJobs.get(i).toString()).get("start_time").toString());
                jobHolder.add("End Date: "+new JSONObject(opJobs.get(i).toString()).get("date").toString()+" "+
                        new JSONObject(opJobs.get(i).toString()).get("end_time").toString());
                jobHolder.add("Address: "+new JSONObject(opJobs.get(i).toString()).get("street").toString()+" "+
                        new JSONObject(opJobs.get(i).toString()).get("city").toString()+" "+
                        new JSONObject(opJobs.get(i).toString()).get("state").toString());
                listDataChild.put(new JSONObject(opJobs.get(i).toString()).get("job_title").toString().trim()+" at "+new JSONObject(opJobs.get(i).toString()).get("company_name").toString().trim(), jobHolder);
                job_ids.add(new JSONObject(opJobs.get(i).toString()).get("job_id").toString());

                if(Integer.parseInt(new JSONObject(opJobs.get(i).toString()).get("job_id").toString())>lastJob){
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt(getResources().getResourceName(R.integer.last_job_id), Integer.parseInt(new JSONObject(opJobs.get(i).toString()).get("job_id").toString()));
                    editor.apply();
                }


                jobHolder.add("Select This Job");
                jobHolder=new ArrayList<>();



            }

        }catch(Exception e){
            Log.e("Error", e.toString());
            e.printStackTrace();
        }



    }



}
