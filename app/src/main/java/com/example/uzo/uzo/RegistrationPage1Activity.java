package com.example.uzo.uzo;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uzo.DataHandler.HTTPClientHandlerOnLoad;
import com.example.uzo.DataHandler.Session;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegistrationPage1Activity extends AppCompatActivity {
    // UI references.
    private AutoCompleteTextView mEmailView;
    private Session session;//global variable
    private EditText firstName;
    private EditText lastName;
    private EditText phoneNumber;
    private EditText state;
    private EditText street;
    private EditText aptNum;
    private EditText city;
    private EditText zipcode;
    private EditText email;
    private EditText password;
    private EditText rpassword;
    private EditText major;
    private EditText studentID;
    private EditText dob;
    private EditText year;
    private Button submit;
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Session(RegistrationPage1Activity.this); //in oncreate
        final String [] availableMajors = {
        "Accounting",
                "Accounting / International Business",
                "Actuarial Science Certificate",
                "Advertising",
                "Aerospace Studies (Air Force ROTC)",
                "African American Studies",
                "African American Studies Certificate",
                "African Languages and Literatures MALL",
                "African Studies Certificate",
                "Agribusiness",
                "Agribusiness Law Certificate",
                "Agricultural & Applied Economics",
                "Agricultural Communication",
                "Agricultural Education",
                "Agricultural Engineering",
                "Agriscience & Environmental Systems",
                "Animal Health",
                "Animal Science",
                "Anthropology",
                "Applied Biotechnology",
                "Applied Data Science",
                "Arabic",
                "Archaeological Sciences Certificate",
                "Art - Art X: Expanded Forms",
                "Art - Ceramics",
                "Art - Drawing",
                "Art - Fabric Design",
                "Art - Graphic Design",
                "Art - Jewelry & Metalwork",
                "Art - Painting",
                "Art - Photography",
                "Art - Printmaking & Book Art",
                "Art - Scientific Illustration",
                "Art - Sculpture",
                "Art and Design Art Education",
                "Art History",
                "Asian Language & Literature",
                "Asian Studies Certificate",
                "Athletic Training",
                "Atmospheric Sciences Avian Biology",
                "Biochemical Engineering",
                "Biochemistry & Molecular Biology",
                "Biological Engineering",
                "Biological ScienceBiology",
                "Biology / Science Education",
                "British & Irish Studies Certificate",
                "Business & Political German Certificate",
                "Cellular Biology",
                "Chemistry (B.S.)",
                "Chemistry (B.S.Chem.)",
                "Civil Engineering",
                "Classical Culture",
                "Classical Languages - Latin",
                "Coastal & Oceanographic Eng. Cert.",
                "Cognitive Science",
                "Communication Sciences & Disorders",
                "Communication Studies",
                "Community Forestry Certificate",
                "Comparative Literature",
                "Computer Science",
                "Computer Systems Engineering",
                "Computing Certificate",
                "Consumer Economics",
                "Consumer Foods",
                "Consumer Journalism",
                "Criminal Justice",
                "Dairy ScienceDance (A.B.)",
                "Dance (B.F.A.)",
                "Dietetics",
                "Digital Humanities Certificate Disability Studies Certificate",
                "Disaster Management Certificate",
                "Early Childhood Education",
                "Ecology (A.B.)",
                "Ecology (B.S.)",
                "Economics (A.B.)",
                "Economics (B.B.A.)",
                "Economics / International Business",
                "Electrical and Electronics Engineering",
                "Engineering Physics Certificate",
                "Engineering Science Certificate",
                "English",
                "English / English Education",
                "English Education",
                "Entertainment and Media Studies",
                "Entomology",
                "Entrepreneurship Certificate",
                "Environmental Economics & Management",
                "Environmental Education Certificate",
                "Environmental Engineering",
                "Environmental Ethics Certificate",
                "Environmental Health Science Environmental Resource Science",
                "Exercise & Sport Science",
                "Family & Consumer Sciences Education",
                "Fashion Merchandising",
                "Film Studies",
                "FinanceFinance / International Business",
                "Financial Planning",
                "Fisheries & Wildlife",
                "Food Industry Marketing & Administration",
                "Food Science",
                "Forestry",
                "French",
                "Furnishings & Interiors",
                "General Business (Griffin)",
                "General Business (Online)",
                "Genetics",
                "Geographic Information Science Cert.",
                "Geography (A.B.)",
                "Geography (B.S.)",
                "Geology (A.B.)",
                "Geology (B.S.)",
                "German",
                "Germanic and Slavic Studies",
                "Global Health Certificate",
                "Global Studies Certificate",
                "Greek",
                "Health and Physical Education",
                "Health Promotion",
                "Historic Preservation Certificate",
                "History",
                "History / Social Studies Education",
                "Honors Interdisc. Studies (A.B.)",
                "Honors Interdisc. Studies (B.S.)",
                "Honors Interdisc. Studies (B.S.A.)",
                "Honors Interdisc. Studies (B.S.F.C.S.)",
                "Horticulture",
                "Housing Management and Policy",
                "Human Development and Family Science",
                "Informatics Certificate",
                "Interdisciplinary Studies (A.B.)",
                "Interdisciplinary Studies (A.B.)",
                "Interdisciplinary Studies (B.F.A.)",
                "Interdisciplinary Studies (B.S.)",
                "Interdisciplinary Writing Certificate",
                "Interior Design",
                "International Affairs",
                "International Agriculture Certificate",
                "International Business",
                "Italian",
                "Journalism",
                "Landscape Architecture",
                "Latin American & Caribbean Studies",
                "Leadership & Service Certificate",
                "Legal Studies Certificate",
                "Linguistics",
                "Local Food Systems Certificate",
                "Management",
                "Management / International Business",
                "Management Info Systems / Int'l Business",
                "Management Information Systems",
                "MarketingMarketing / International Business",
                "Mathematics",
                "Mathematics / Mathematics Education",
                "Mathematics Education",
                "Mechanical Engineering",
                "Medieval Studies Certificate",
                "Microbiology",
                "Middle School Education",
                "Music",
                "Music Business Certificate",
                "Music Composition",
                "Music Education",
                "Music Performance",
                "Music Theory",
                "Music Therapy",
                "Native American Studies Certificate",
                "Natural Resource Mgmt and Sustainability",
                "Natural Resource Recreation & Tourism",
                "New Media Certificate",
                "Nutritional Sciences",
                "Organic Agriculture Certificate",
                "Personal & Org. Leadership Cert.",
                "Pharmaceutical Sciences",
                "Pharmacy",
                "Pharmacy Entrepreneurship Certificate",
                "Philosophy",
                "Physics",
                "Physics & Astronomy",
                "Plant Biology",
                "Plant Health Management Certificate",
                "Political Science",
                "Poultry Science",
                "Pre-Business",
                "Pre-Dentistry",
                "Pre-Forest Resources",
                "Pre-Journalism",
                "Pre-Law",
                "Pre-Medicine",
                "Pre-Optometry",
                "Pre-Pharmacy",
                "Pre-Theology",
                "Pre-Veterinary Medicine (B.S.)",
                "Pre-Veterinary Medicine (B.S.A.)",
                "Pre-Veterinary Medicine (B.S.F.R.)",
                "Psychology",
                "Public Affairs Professional Certificate",
                "Public Relations",
                "Real Estate",
                "Real Estate / International Business",
                "Religion",
                "Risk Management & Insurance",
                "Risk Mgmt & Insurance / Int'l Business",
                "Romance Languages",
                "Russian",
                "Science Education",
                "Social Studies Education",
                "Social Work",
                "Sociology",
                "Spanish",
                "Special Education",
                "Sport Management",
                "Sports Media Certificate",
                "Statistics",
                "Studio Art Core & Minor",
                "Sustainability Certificate Theatre",
                "Turfgrass Management",
                "Water & Soil Resources (B.S.E.S.)",
                "Water Resources Certificate",
                "Women's Studies",
                "World Language Education"
    };
        final String[] states = {"California", "Alabama", "Arkansas", "Arizona", "Alaska", "Colorado", "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming" };
        setContentView(R.layout.activity_registration_page_1);
        firstName = (EditText) findViewById(R.id.fname);
        lastName = (EditText) findViewById(R.id.lname);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        street = (EditText) findViewById(R.id.street);
        aptNum = (EditText) findViewById(R.id.aptNum);
        city = (EditText) findViewById(R.id.city);
        state = (EditText) findViewById(R.id.state);
        zipcode = (EditText) findViewById(R.id.zipcode);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        rpassword = (EditText) findViewById(R.id.rpassword);
        major = (EditText) findViewById(R.id.major);
        studentID = (EditText) findViewById(R.id.studentID);
        dob = (EditText) findViewById(R.id.dob);
        submit= (Button) findViewById(R.id.submitButton);
        year=(EditText) findViewById(R.id.year);

        //country spinner
        state.setInputType(InputType.TYPE_NULL);
        final ArrayAdapter<String> spinner_countries = new ArrayAdapter<String>(RegistrationPage1Activity.this,android.R.layout.simple_spinner_dropdown_item, states);
        state.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                new AlertDialog.Builder(RegistrationPage1Activity.this)
                        .setTitle("Select State")
                        .setAdapter(spinner_countries, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                state.setText(states[which].toString());
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });

        //Major spinner
        major.setInputType(InputType.TYPE_NULL);
        final ArrayAdapter<String> majors = new ArrayAdapter<String>(RegistrationPage1Activity.this,android.R.layout.simple_spinner_dropdown_item, availableMajors);
        major.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                new AlertDialog.Builder(RegistrationPage1Activity.this)
                        .setTitle("Select Major")
                        .setAdapter(majors, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                major.setText(availableMajors[which].toString());
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });

        //mask the input for a number
        phoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        myCalendar = Calendar.getInstance();

       date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(RegistrationPage1Activity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //mask the input for the dob


        submit.setOnClickListener(new View.OnClickListener() {
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
                JSONObject insertStudent= new JSONObject();
                try {
                    insertStudent.put("first_name", firstName.getText().toString());
                    insertStudent.put("last_name", lastName.getText().toString());
                    insertStudent.put("phone_number", phoneNumber.getText().toString());
                    insertStudent.put("university", "UGA");
                    insertStudent.put("date_of_birth", dob.getText().toString());
                    insertStudent.put("email", email.getText().toString());
                    insertStudent.put("major", major.getText().toString());
                    insertStudent.put("year", year.getText().toString());
                    insertStudent.put("password", password.getText().toString());
                    insertStudent.put("description", studentID.getText().toString());
                    insertStudent.put("state", state.getText().toString());
                    insertStudent.put("city", city.getText().toString());
                    insertStudent.put("street", street.getText().toString());
                    insertStudent.put("apt", aptNum.getText().toString());
                    insertStudent.put("zipcode", zipcode.getText().toString());

                    HTTPClientHandlerOnLoad handleInsert= new HTTPClientHandlerOnLoad();
                    if(validate()){
                        insertStudent =new JSONObject(handleInsert.execute("postJSONRetObject",insertStudent.toString(),"insert_student").get());
                        session.setuserID(insertStudent.get("student_id").toString());
                        startActivity(new Intent(RegistrationPage1Activity.this, RegistrationPageActivity2.class));
                    }

                    Log.i("Result",insertStudent.toString()
                    );
                }catch(Exception e ){
                    Log.e("Error in RP1Oncreate", e.toString());
                }
            }
        });



    }
    private void updateLabel() {
        String myFormat = "MM-dd-yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob.setText(sdf.format(myCalendar.getTime()));
    }

    public JSONObject asyncResult(String result){
        JSONObject ret= new JSONObject();
        try {
            ret=new JSONObject(result);

        }catch(Exception e){
            Log.e("Error on asyncResult ",e.toString());

        }
        return ret;
    }

    private boolean validate() {
        boolean temp=true;
        String pass=password.getText().toString();
        String cpass=rpassword.getText().toString();
         if(!pass.equals(cpass)){
            Toast.makeText(RegistrationPage1Activity.this,"Password Not matching", Toast.LENGTH_SHORT).show();
            temp=false;
        }else if(firstName.getText().toString().equals("")
        || lastName.getText().toString().equals("")||
         phoneNumber.getText().toString().equals("")||
        dob.getText().toString().equals("")||
        email.getText().toString().equals("")
       ||major.getText().toString().equals("")
        ||year.getText().toString().equals("")
        ||password.getText().toString().equals("")
        ||studentID.getText().toString().equals("")
        || state.getText().toString().equals("")
        ||city.getText().toString().equals("")
        || street.getText().toString().equals("")
        ||zipcode.getText().toString().equals("")){
             Toast.makeText(RegistrationPage1Activity.this,"Please Fill in All of the Fields", Toast.LENGTH_SHORT).show();
             temp=false;
         }
        return temp;
    }



}
