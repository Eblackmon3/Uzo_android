package com.example.uzo.uzo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.uzo.DataHandler.HTTPClientHandlerOnLoad;
import com.example.uzo.DataHandler.Session;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MyGigsArrayAdapter  extends BaseExpandableListAdapter implements ListAdapter {
    private Context _context;
    Session session;
    private List<String> job_id;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    public MyGigsArrayAdapter(Context context, List<String> listDataHeader,
                                HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    public  MyGigsArrayAdapter(Context context, List<String> listDataHeader,
                                HashMap<String, List<String>> listChildData, List<String> job_id) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.job_id =job_id;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }
    public Object getJobId(int groupPosition) {
        return this.job_id.get(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.my_gigs_item, null);
        }

        Button clockIn= convertView.findViewById(R.id.Clockin);
        Button clockOut= convertView.findViewById(R.id.ClockOut);

        if(isLastChild){
            clockOut.setVisibility(clockOut.VISIBLE);
            clockIn.setVisibility(clockIn.GONE);
            Log.e("Clock-out", "clock-out");
            clockOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(_context,R.style.Theme_AppCompat_DayNight_DarkActionBar );

                    builder.setTitle("Confirm");
                    builder.setMessage("Are you sure?");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            session= new Session(_context);
                            int student= Integer.parseInt(session.getuserID());
                            String df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(Calendar.getInstance().getTime());
                            JSONObject sendInterest= new JSONObject();
                            try {
                                sendInterest.put("student_id", student);
                                sendInterest.put("job_id", Integer.parseInt(getJobId(groupPosition).toString()));
                                sendInterest.put("clock_out", df);

                                HTTPClientHandlerOnLoad handler= new HTTPClientHandlerOnLoad();
                                Log.i("Inserted Student",handler.execute(handler.execute("postJSONRetObject",sendInterest.toString(),"clockout_student").get()).get());

                            }catch(Exception e){
                                Log.e("Error in adapter", e.toString());
                                e.printStackTrace();
                            }
                            // Do nothing but close the dialog

                            dialog.dismiss();
                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // Do nothing
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        }else{
            clockOut.setVisibility(clockOut.GONE);
            Log.e("Clock-in", "clock-in");
            clockIn.setVisibility(clockIn.VISIBLE);
            clockIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(_context,R.style.Theme_AppCompat_DayNight_DarkActionBar );

                    builder.setTitle("Confirm");
                    builder.setMessage("Are you sure?");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            session= new Session(_context);
                            int student= Integer.parseInt(session.getuserID());
                            String df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(Calendar.getInstance().getTime());
                            JSONObject sendInterest= new JSONObject();
                            try {
                                sendInterest.put("student_id", student);
                                sendInterest.put("job_id", Integer.parseInt(getJobId(groupPosition).toString()));
                                sendInterest.put("clock_in", df);
                                HTTPClientHandlerOnLoad handler= new HTTPClientHandlerOnLoad();
                                Log.i("Inserted Student",handler.execute(handler.execute("postJSONRetObject",sendInterest.toString(),"clockin_student").get()).get());

                            }catch(Exception e){
                                Log.e("Error in adapter", e.toString());
                                e.printStackTrace();
                            }
                            // Do nothing but close the dialog

                            dialog.dismiss();
                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // Do nothing
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.my_gigs_header, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeaderMyGigs);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getCount() {
        return 0;
    }


}
