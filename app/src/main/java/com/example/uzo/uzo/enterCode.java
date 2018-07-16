package com.example.uzo.uzo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uzo.DataHandler.HTTPClientHandlerOnLoad;
import com.example.uzo.DataHandler.Session;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class enterCode extends AppCompatActivity {
    EditText uuid;
    Button launchCorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_code);
        launchCorrect= findViewById(R.id.launchCorrect);
        uuid = findViewById(R.id.UUIDCode);
        launchCorrect.setOnClickListener(new View.OnClickListener() {
            /*
              {
            "email": "ericblackmon38@gmail.com",
            "first_name":"Eric",
            "last_name":"Blackmon",
            "uuid":"10d080de-5c08-43bb-8307-b2acf054a4d8"
           }
             */
            public void onClick(View v) {

                try {
                    HTTPClientHandlerOnLoad handleInsert = new HTTPClientHandlerOnLoad();
                    JSONObject getUUI = null;
                    getUUI=new JSONObject();
                    getUUI.put("uuid", uuid.getText().toString());
                    getUUI.put("email", getIntent().getStringExtra("email"));
                    getUUI.put("first_name", getIntent().getStringExtra("first_name"));
                    getUUI.put("last_name", getIntent().getStringExtra("last_name"));
                    Log.i("JSON Sending",getIntent().getStringExtra("first_name"));
                    getUUI = new JSONObject(handleInsert.execute("postJSONRetObject", getUUI.toString(), "allow_password_reset").get());
                    if (getUUI.getString("result").equals("correct")) {
                        Intent intent = new Intent(enterCode.this, creatNewPassword.class);
                        intent.putExtra("student_id", getUUI.getInt("student_id")+"");
                        Log.i("Student ID Passed:",getUUI.getInt("student_id")+"" );
                        startActivity(intent);

                    } else {
                        Toast.makeText(enterCode.this, "Information is incorrect",
                                Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Error", e.toString());
                    Toast.makeText(enterCode.this, "Information is incorrect",
                            Toast.LENGTH_LONG).show();
                }
            }
        });



    }

}
