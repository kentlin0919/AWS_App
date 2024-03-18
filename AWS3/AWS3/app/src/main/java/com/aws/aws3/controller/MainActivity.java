package com.aws.aws3.controller;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.aws.aws3.R;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    public Toolbar log_mToolbar2;
    public Button login_button;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        log_mToolbar2 = findViewById(R.id.main_toolbar2);
        setSupportActionBar(log_mToolbar2);

        login_button =  findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                class TestTask extends AsyncTask<Void, Void, Boolean> {
                    @Override
                    protected Boolean doInBackground(Void... voids) {
                        OkHttpClient client = new OkHttpClient();
                        JSONObject jsonObject = new JSONObject();
                        EditText username = (EditText) findViewById(R.id.editTextTextEmailAddress);
                        EditText password = (EditText) findViewById(R.id.editTextTextPassword);


                        try {
                            jsonObject.put("account",username.getText().toString());
                            jsonObject.put("password", password.getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
                        RequestBody body = RequestBody.create(jsonObject.toString(), mediaType);



                        Request request = new Request.Builder()
                                .url(api.url + "/login")
                                .post(body)
                                .build();

                        try (Response response = client.newCall(request).execute()) {
                            if(response.code()==200) {
                                return true;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }


                    protected void onPostExecute(Boolean result){
                        EditText username = (EditText) findViewById(R.id.editTextTextEmailAddress);
                        TextView msg = (TextView)  findViewById(R.id.loginMsgIncorrect);
                        if (result) {
                            Intent intent = new Intent(MainActivity.this, prepare_mission.class);
                            intent.putExtra("text",username.getText().toString());
                            System.out.println(username.getText().toString());
                            startActivity(intent);
                            Toast.makeText(MainActivity.this,"歡迎 "+username.getText().toString(),Toast.LENGTH_SHORT).show();

                        }else {
                            msg.setVisibility( View.VISIBLE );
                        }
                    }
                }
                new TestTask().execute();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.help_item1:
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("about")
                        .setMessage("this system is develop to help ambulance " +
                                "reach the emergency site or hospital faster and safer.")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setNegativeButton("cancel",null).create()
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

