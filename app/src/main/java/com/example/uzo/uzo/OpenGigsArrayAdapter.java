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
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uzo.DataHandler.HTTPClientHandlerOnLoad;
import com.example.uzo.DataHandler.Session;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class OpenGigsArrayAdapter  extends BaseExpandableListAdapter implements ListAdapter {
    private Context _context;
    Session session;
    private List<String> job_id;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    public OpenGigsArrayAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    public OpenGigsArrayAdapter(Context context, List<String> listDataHeader,
                                HashMap<String, List<String>> listChildData, List<String> job_id) {
        Log.i("makes it here ", "makes it here ");
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
            convertView = infalInflater.inflate(R.layout.open_gigs_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.Item);
        Button button= convertView.findViewById(R.id.SelectJob);

        txtListChild.setText(childText);
        if(isLastChild){
            button.setVisibility(button.VISIBLE);
            Log.e("Last Child", "Child Text: " +childText+" Child Position"+childPosition+ " header"+this._listDataHeader.get(groupPosition)+" txtview "+txtListChild.getText());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(_context,R.style.Theme_AppCompat_DayNight_DarkActionBar );

                    builder.setTitle("Confirm");
                    builder.setMessage("Are you sure?");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            session= new Session(_context);
                            int student= Integer.parseInt(session.getuserID());
                            JSONObject sendInterest= new JSONObject();
                            try {
                                sendInterest.put("student_id", student);
                                sendInterest.put("job_id", Integer.parseInt(getJobId(groupPosition).toString()));
                                HTTPClientHandlerOnLoad handler= new HTTPClientHandlerOnLoad();
                                JSONObject studentResult=new JSONObject(handler.execute("postJSONRetObject",sendInterest.toString(),"insert_interested_student").get());
                                Log.i("Result of insert",studentResult.toString());
                                if(studentResult.get("result").equals("student not accepted")){
                                    Toast.makeText(_context, "You have not been accepted by HKA, please wait to select jobs",
                                            Toast.LENGTH_LONG).show();
                                }


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
            button.setVisibility(button.GONE);

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
            convertView = infalInflater.inflate(R.layout.open_gigs_header, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);

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
