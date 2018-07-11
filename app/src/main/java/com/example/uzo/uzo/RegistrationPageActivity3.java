package com.example.uzo.uzo;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.uzo.DataHandler.HTTPClientHandlerOnLoad;
import com.example.uzo.DataHandler.MultiPartHelper;
import com.example.uzo.DataHandler.Session;


import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegistrationPageActivity3 extends AppCompatActivity {
    private String student_id;
    private Session session;
    private EditText reference1;
    private EditText reference2;
    private EditText reference3;
    private Button fileChose,back,submit;
    private File imageFile;
    private MultiPartHelper service;
    Uri chosenFile;
    RadioButton yes, mouth,media,print;
    public static final int GALLERY_REQUEST =1;
    public static final int READ_REQUEST_CODE =2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration3);
        session = new Session(RegistrationPageActivity3.this);
        reference1=(EditText) findViewById(R.id.workReference);
        reference2=(EditText) findViewById(R.id.workReference1);
        reference3=(EditText) findViewById(R.id.workReference2);
        fileChose= (Button) findViewById(R.id.chooseFile);
        back= (Button) findViewById(R.id.backReg3);
        submit= (Button) findViewById(R.id.SubmitReg3);
        yes=(RadioButton) findViewById(R.id.yes);
        media=(RadioButton) findViewById(R.id.media);
        mouth=(RadioButton) findViewById(R.id.mouth);
        print=(RadioButton) findViewById(R.id.print);
        student_id = session.getuserID();
        chosenFile=null;
        Log.i("Student Id", student_id);

        fileChose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=null;
                Intent resultIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                resultIntent.addCategory(Intent.CATEGORY_OPENABLE);

                // Filter to show only images, using the image MIME data type.
                // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
                // To search for all documents available via installed storage providers,
                // it would be "*/*".
                resultIntent.setType("*/*");

                startActivityForResult(resultIntent, READ_REQUEST_CODE);






            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chosenFile!=null) {
                    JSONObject workHistory= new JSONObject();
                    String ref1=reference1.getText().toString();
                    String ref2=reference2.getText().toString();
                    String ref3=reference3.getText().toString();
                    Boolean crime=yes.isChecked();
                    Boolean fMouth= mouth.isChecked();
                    Boolean fMedia= media.isChecked();
                    Boolean fPrint= print.isChecked();
                    HTTPClientHandlerOnLoad handleInsert= new HTTPClientHandlerOnLoad();
                    try {
                        workHistory.put("work_reference_1", ref1);
                        workHistory.put("work_reference_2", ref2);
                        workHistory.put("work_reference_3", ref3);
                        if(fMouth) {
                            workHistory.put("hear_uzo", "Word of mouth");
                        }else if (fMedia){
                            workHistory.put("hear_uzo", "Social Media");
                        }else{
                            workHistory.put("hear_uzo", "Print");
                        }
                        workHistory.put("crime", crime);
                        workHistory.put("student_id",student_id);
                        Log.e("work History",workHistory.toString());

                    }catch(Exception e){
                        Log.e("Error", e.toString());
                    }
                    try {
                        workHistory = new JSONObject(handleInsert.execute("postJSONRetObject", workHistory.toString(), "insert_student_work_history").get());
                        Log.i("Response",workHistory.toString());
                    }catch(Exception e){
                        Log.e("Error",e.toString());
                    }
                    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
                    String type = getMimeType(chosenFile);
                    File file= new File(getContextWrapper().getFilesDir().toString()+"/Resume");
                    try {
                        InputStream in = getContentResolver().openInputStream(chosenFile);
                        OutputStream out = new FileOutputStream(file);
                        byte[] buf = new byte[1024];
                        int len;
                        while ((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }
                        out.close();
                        in.close();
                    }catch(Exception e){
                        Log.e("Error",e.toString());
                    }

// Change base URL to your upload server URL.

                    RequestBody reqFile = RequestBody.create( MediaType.parse(type), file);
                    Log.i("file location", file.getAbsolutePath());
                    MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);
                    RequestBody name = RequestBody.create(MediaType.parse("text/plain"), session.getuserID()+"");


                    service = new Retrofit.Builder().baseUrl("https://uzo-web-app.herokuapp.com").client(client).build().create(MultiPartHelper.class);
                    retrofit2.Call<okhttp3.ResponseBody> req = service.postFile(body, name);
                    req.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Log.i("Respone",response.message());
                            try {
                                Log.i("Respone", response.body().string());
                                startActivity(new Intent(RegistrationPageActivity3.this, MyGigs.class));
                            }catch (Exception e){
                                Log.e("Error",e.toString());
                            }
                            Log.i("URL",call.request().url().toString());
                            Log.i("URL",call.request().body().toString());


                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            t.printStackTrace();
                        }


                    });


                }else{
                    Toast.makeText(RegistrationPageActivity3.this,"Please select file", Toast.LENGTH_SHORT).show();
                }





            }
        });

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                chosenFile = resultData.getData();
                Log.i("URI", "Uri: " + chosenFile.toString());

            }
        }

    }

    public  String getMimeType(Uri uri) {
        String mimeType = null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = this.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }

    public ContextWrapper getContextWrapper(){
        return   new ContextWrapper(this);
    }



}
