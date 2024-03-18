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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.aws.aws3.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class implementmission extends AppCompatActivity {
    private Toolbar mm_mtoolbar;
    public Button cancel_button;
    public Button finish_button;
    public ImageView mp_view;
    JSONArray raw;
    private Toolbar supportActionBar;
    Bundle bundle = new Bundle();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.implementmission);
        Intent intent = this.getIntent();
        String name = intent.getStringExtra("id");






        class completeTask extends AsyncTask<Void, Void, Boolean> {
            JSONArray raw = null;



            @Override
            protected Boolean doInBackground(Void... voids) {
                Intent intent = getIntent();
                String id = intent.getStringExtra("id");
                OkHttpClient client = new OkHttpClient();
                JSONObject jsonObject = new JSONObject();
                MediaType mediaType = MediaType.get("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(jsonObject.toString(), mediaType);
                Request request = new Request.Builder()
                        .url(api.url + "/mission/start/"+id)
                        .post(body)
                        .build();

                try (Response response = client.newCall(request).execute()) {


                    if (response.code() == 200) {
                        raw = new JSONArray(response.body().string());
                        return true;
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                return false;
            }

            protected void onPostExecute(Boolean result) {
                if (result) {
                    String m = "";
                    try {

                        m += String.format("Date:%s\n\n", raw.getJSONObject(0).getString("date"));
                        m += String.format("Category:%s\n\n", raw.getJSONObject(0).getString("category"));
                        m += String.format("Location:%s\n\n", raw.getJSONObject(0).getString("location"));
                        m += String.format("Car Id: %s\n\n", raw.getJSONObject(0).getString("car_id"));
                        m += String.format("Situation: %s\n\n", raw.getJSONObject(0).getString("app_situation"));
                        m += String.format("Note: %s\n", raw.getJSONObject(0).getString("note"));


                        setContentView(R.layout.implementmission);
                        TextView lblTitle = (TextView) findViewById(R.id.textViewmission);
                        lblTitle.setText(String.format(m));


                        finish_button =  findViewById(R.id.finish_button);
                        finish_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                class TestTask extends AsyncTask<Void, Void, Boolean> {
                                    Intent intent = getIntent();
                                    String id = intent.getStringExtra("id");
                                    @Override
                                    protected Boolean doInBackground(Void... voids) {
                                        OkHttpClient client = new OkHttpClient();
                                        JSONObject jsonObject = new JSONObject();
                                        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
                                        RequestBody body = RequestBody.create(jsonObject.toString(), mediaType);



                                        Request request = new Request.Builder()
                                                .url(api.url + "/mission/complete/"+id)
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
                                        if (result) {
                                            Intent intent = new Intent(implementmission.this, prepare_mission.class);
                                            startActivity(intent);
                                            Toast.makeText(implementmission.this,"complete",Toast.LENGTH_SHORT).show();

                                        }else {
                                            Toast.makeText(implementmission.this,"Can't complete",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                new TestTask().execute();
                            }
                        });



                        cancel_button =  findViewById(R.id.cancel_button);
                        cancel_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                class TestTask extends AsyncTask<Void, Void, Boolean> {
                                    Intent intent = getIntent();
                                    String id = intent.getStringExtra("id");
                                    @Override
                                    protected Boolean doInBackground(Void... voids) {
                                        OkHttpClient client = new OkHttpClient();
                                        JSONObject jsonObject = new JSONObject();
                                        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
                                        RequestBody body = RequestBody.create(jsonObject.toString(), mediaType);



                                        Request request = new Request.Builder()
                                                .url(api.url + "/mission/complete/"+id)
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
                                        if (result) {
                                            Intent intent = new Intent(implementmission.this, prepare_mission.class);
                                            startActivity(intent);
                                            Toast.makeText(implementmission.this,"cancel",Toast.LENGTH_SHORT).show();

                                        }else {
                                            Toast.makeText(implementmission.this,"Can't cancel",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                new TestTask().execute();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }
        }
        new completeTask().execute();


        mp_view = findViewById(R.id.imageView2);
        mp_view.getBackground().setAlpha(30);


        cancel_button =  findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(implementmission.this, prepare_mission.class);
                startActivity(intent);

            }
        });






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu3, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.help_item:
                new AlertDialog.Builder(implementmission.this)
                        .setTitle("about")
                        .setMessage("this system is develop to help ambulance \n" +
                                "reach the emergency site or hospital faster and safer.")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setNegativeButton("cancel", null).create()
                        .show();
                return true;
            case R.id.logout_item:
                Intent intent = new Intent(implementmission.this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }




}
