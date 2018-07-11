package com.example.uzo.uzo;

import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyGigs extends AppCompatActivity {


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
                    return true;
                case R.id.navigation_dashboard:
                    Log.i("New Activity", "Switching to Open Gigs");
                    in=new Intent(getBaseContext(),OpenGigs.class);
                    startActivity(in);

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
        setContentView(R.layout.activity_my_gigs);

        session=new Session(MyGigs.this);;
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        HTTPClientHandlerOnLoad handler= new HTTPClientHandlerOnLoad();
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExpMyGigs);

        // preparing list data
        prepareListData();

        listAdapter = new MyGigsArrayAdapter(this, listDataHeader, listDataChild,job_ids);

        // setting list adapter
        expListView.setAdapter(listAdapter);


    }


    /*
     * Preparing the list data
     */
    private void prepareListData() {
        JSONArray opJobs= new JSONArray();
        JSONObject student_idObj= new JSONObject();
        try {
            student_idObj.put("student_id",session.getuserID());
            HTTPClientHandlerOnLoad handler = new HTTPClientHandlerOnLoad();
            opJobs = new JSONArray(handler.execute("postJSONRetArray", student_idObj.toString(), "get_students_jobs_by_id").get());
            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<String>>();
            job_ids= new ArrayList<>();
            ArrayList<String> jobHolder= new ArrayList<>();
            for (int i = 0; i < opJobs.length(); i++) {
                listDataHeader.add(new JSONObject(opJobs.get(i).toString()).get("job_title").toString().trim()+" at "+new JSONObject(opJobs.get(i).toString()).get("company_name").toString().trim());
                jobHolder.add("Clock-in");
                jobHolder.add("Clock-out");
                listDataChild.put(listDataHeader.get(i), jobHolder);
                job_ids.add(new JSONObject(opJobs.get(i).toString()).get("job_id").toString());
                jobHolder=new ArrayList<>();

            }

        }catch(Exception e){
            Log.e("Error", e.toString());
        }

    }

}
