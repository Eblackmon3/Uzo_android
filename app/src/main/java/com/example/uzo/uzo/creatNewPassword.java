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

import org.json.JSONObject;

public class creatNewPassword extends AppCompatActivity {
    EditText password;
    EditText repassword;
    Button submitNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_new_password);
        password=findViewById(R.id.forgotPasswordPassword);
        repassword=findViewById(R.id.forgotPasswordRePassword);
        submitNewPassword=findViewById(R.id.enteringNewPasswordSubmit);
        submitNewPassword.setOnClickListener(new View.OnClickListener() {
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
                    JSONObject newPassword = new JSONObject();
                    HTTPClientHandlerOnLoad handleInsert= new HTTPClientHandlerOnLoad();
                    newPassword.put("student_id",getIntent().getStringExtra("student_id"));
                    newPassword.put("password",password.getText().toString());
                    newPassword =new JSONObject(handleInsert.execute("postJSONRetObject",newPassword.toString(),"update_student_info").get());
                    Log.i("Result of update", newPassword.toString());
                    Log.i("Student ID Recev",getIntent().getStringExtra("student_id"));
                    if(validate()&&newPassword.getInt("affected_rows")>0 ){
                        AlertDialog.Builder builder = new AlertDialog.Builder(creatNewPassword.this,R.style.Theme_AppCompat_DayNight_DarkActionBar );
                        builder.setTitle(" Password changed would you like to continue to the login Page?");
                        builder.setMessage("Are you sure?");

                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent=new Intent(creatNewPassword.this, LoginActivity.class);
                                startActivity(intent);


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
                    }else{
                        Toast.makeText(creatNewPassword.this, "Please ensure that passwords are the same",
                                Toast.LENGTH_LONG).show();
                    }

                }catch(Exception e){
                    Toast.makeText(creatNewPassword.this, "Oops! Something went wrong",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    Log.e("Error in get password", e.toString());

                }

            }
        });

    }

    private boolean validate() {
        boolean temp=true;
        password=findViewById(R.id.forgotPasswordPassword);
        repassword=findViewById(R.id.forgotPasswordRePassword);
        if(!password.getText().toString().equals(repassword.getText().toString())){
            Toast.makeText(creatNewPassword.this,"Password Not matching", Toast.LENGTH_SHORT).show();
            temp=false;
        }
        return temp;
    }

}
