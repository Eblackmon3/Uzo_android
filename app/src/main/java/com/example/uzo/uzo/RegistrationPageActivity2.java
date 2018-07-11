package com.example.uzo.uzo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.uzo.DataHandler.HTTPClientHandlerOnLoad;
import com.example.uzo.DataHandler.Session;

import org.json.JSONObject;

public class RegistrationPageActivity2 extends AppCompatActivity {
    private String student_id;
    private Session session;
    private EditText uzoReason;
    private CheckBox bar, cashier,cleaning,data,deskAs,driver,security,setup,service,moving, bike,bus,car;
    private RadioButton yes;
    private JSONObject workAbility;
    private JSONObject studentPreferences;
    private Button next;
    private Button back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);
        session=new Session(RegistrationPageActivity2.this);
        student_id=session.getuserID();
        Log.i("Student Id",student_id);
        //get the refences to everything
        uzoReason = (EditText) findViewById(R.id.uzoReason);
        bar = (CheckBox) findViewById(R.id.Bar);
        cashier = (CheckBox) findViewById(R.id.cashier);
        cleaning = (CheckBox) findViewById(R.id.Cleaning);
        data = (CheckBox) findViewById(R.id.Data);
        deskAs = (CheckBox) findViewById(R.id.DeskAss);
        driver = (CheckBox) findViewById(R.id.Driver);
        security = (CheckBox) findViewById(R.id.EventSecurity);
        data = (CheckBox) findViewById(R.id.Data);
        setup = (CheckBox) findViewById(R.id.EventSetup);
        service = (CheckBox) findViewById(R.id.FoodServ);
        moving = (CheckBox) findViewById(R.id.Moving);
        bike = (CheckBox) findViewById(R.id.BikeMove);
        bus = (CheckBox) findViewById(R.id.BusMove);
        car = (CheckBox) findViewById(R.id.CarMove);
        yes=(RadioButton) findViewById(R.id.LiftingYes);
        next= (Button) findViewById(R.id.nextReg2);
        back= (Button) findViewById(R.id.backReg2);
        workAbility=new JSONObject();
        studentPreferences=new JSONObject();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*

                      var registrationInfo = {
					 first_name: fname,
					 last_name: lname,
					 phone_number: number,
                     university: university,
					 date_of_birth: dob,
					 email: email,
					 major:major,
					 year:grade,
                     password: password,
                     description: student_id,
                     state:state,
                     city:city,
                     street: street,
                     apt:apt,
                     zipcode:zipcode

				 }
                       */
                //call the http method and if successful then jump to the next page
                try {
                    workAbility.put("student_id",student_id);
                    workAbility.put("bar",bar.isChecked());
                    workAbility.put("cashier",cashier.isChecked());
                    workAbility.put("cleaning",cleaning.isChecked());
                    workAbility.put("data_entry",data.isChecked());
                    workAbility.put("desk_assistant",deskAs.isChecked());
                    workAbility.put("driving_delivery",driver.isChecked());
                    workAbility.put("event_security",security.isChecked());
                    workAbility.put("setup_breakdown",setup.isChecked());
                    workAbility.put("food_service",service.isChecked());
                    workAbility.put("moving",moving.isChecked());


                    studentPreferences.put("student_id",student_id);
                    studentPreferences.put("uzo_reason",uzoReason.getText().toString());
                    studentPreferences.put("lift_ability",yes.isChecked());
                    studentPreferences.put("bike",bike.isChecked());
                    studentPreferences.put("car",car.isChecked());
                    studentPreferences.put("bus",bus.isChecked());

                    HTTPClientHandlerOnLoad handleInsert= new HTTPClientHandlerOnLoad();
                    if(validate()){
                        workAbility=new JSONObject(handleInsert.execute("postJSONRetObject",workAbility.toString(),"insert_student_work_ability").get());
                        handleInsert=new HTTPClientHandlerOnLoad();
                        studentPreferences=new JSONObject(handleInsert.execute("postJSONRetObject",studentPreferences.toString(),"insert_student_preferences").get());
                        Log.i("workAbility", workAbility.toString());
                        Log.i("studentPreferences",studentPreferences.toString());
                        startActivity(new Intent(RegistrationPageActivity2.this, RegistrationPageActivity3.class));
                    }

                    Log.i("Result",workAbility.toString()
                    );
                    Log.i("Result2",studentPreferences.toString()
                    );
                }catch(Exception e ){
                    Log.e("Error in RP2Create", e.toString());
                }
            }
        });







    }

    private boolean validate() {
        boolean temp=true;
         if(uzoReason.getText().toString().equals("")){
            Toast.makeText(RegistrationPageActivity2.this,"Please Fill in Reason for UZO", Toast.LENGTH_SHORT).show();
            temp=false;
        }
        return temp;
    }
}
