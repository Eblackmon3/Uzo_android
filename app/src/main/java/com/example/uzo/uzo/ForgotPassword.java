package com.example.uzo.uzo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uzo.DataHandler.HTTPClientHandlerOnLoad;

import org.json.JSONObject;

public class ForgotPassword extends AppCompatActivity {
    private EditText first_name;
    private EditText last_name;
    private EditText email;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        submit= (Button) findViewById(R.id.forgotPasswordSubmit);
        email= (EditText) findViewById(R.id.forgotPasswordEmail);
        last_name=(EditText) findViewById(R.id.forgotPasswordLastName);
        first_name=(EditText) findViewById(R.id.forgotPasswordFirstName);
        submit.setOnClickListener(new View.OnClickListener() {
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
                    JSONObject getUUI = new JSONObject();
                    getUUI.put("email", email.getText().toString().toLowerCase());
                    Log.i("Email:",email.getText().toString());
                    getUUI.put("first_name", first_name.getText().toString().toLowerCase());
                    getUUI.put("last_name", last_name.getText().toString().toLowerCase());
                    HTTPClientHandlerOnLoad handleInsert= new HTTPClientHandlerOnLoad();
                    getUUI =new JSONObject(handleInsert.execute("postJSONRetObject",getUUI.toString(),"lost_password_request").get());
                    if(getUUI.getInt("affected_rows")>0){
                        Intent intent=new Intent(ForgotPassword.this, enterCode.class);
                        intent.putExtra("email", email.getText().toString().toLowerCase());
                        intent.putExtra("first_name", first_name.getText().toString().toLowerCase());
                        intent.putExtra("last_name", last_name.getText().toString().toLowerCase());
                        startActivity(intent);
                        //send to next page and send text
                    }else{
                        Toast.makeText(ForgotPassword.this, "Information is incorrect",
                                Toast.LENGTH_LONG).show();
                    }

                }catch(Exception e){
                    e.printStackTrace();
                    Log.e("Error in get password", e.toString());
                }

            }
        });

    }
}
