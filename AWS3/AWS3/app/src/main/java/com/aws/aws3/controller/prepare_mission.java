package com.aws.aws3.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.aws.aws3.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class prepare_mission extends AppCompatActivity {

    public Toolbar pre_mToolbar;
    public Button pre_button;
    private static final String DataBaseName = "DataBaseIt";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "Users";

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private PagerAdapter adapter;
    private Fragment[] fragments;
    ListView listView;
    SimpleAdapter simpleAdapter;
    public Button uu_button;
    ArrayList<String> tempp = new ArrayList<>();









    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preparemission);
        tabLayout = findViewById(R.id.tabLayoutMain);
        Intent intent = this.getIntent();
        String name = intent.getStringExtra("text");








        pre_mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(pre_mToolbar);

//        pre_button =  findViewById(R.id.start_button);
//        pre_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                class completeTask extends AsyncTask<Void, Void, Boolean> {
//                    JSONArray raw = null;
//                    Intent intent = getIntent();
//                    String name = intent.getStringExtra("text");
//
//                    @Override
//                    protected Boolean doInBackground(Void... voids) {
//                        OkHttpClient client = new OkHttpClient();
//                        JSONObject jsonObject = new JSONObject();
//                        Request request = new Request.Builder()
//                                .url(api.url + "/mission/start/1")
//                                .build();
//
//                        try (Response response = client.newCall(request).execute()) {
//
//
//                            if (response.code() == 200) {
//                                raw = new JSONArray(response.body().string());
//                                return true;
//                            }
//                        } catch (IOException | JSONException e) {
//                            e.printStackTrace();
//                        }
//                        return false;
//                    }
//
//                    protected void onPostExecute(Boolean result) {
//                        if (result) {
//                            String m = "";
//                            try {
//                                m += String.format("DATE:%s\n\n", raw.getJSONObject(0).getString("date"));
//                                Intent intent = new Intent(prepare_mission.this,implementmission.class);
//                                prepare_mission.this.finish();
//                                String l = "2";
//                                intent.putExtra("id",l);
//                                startActivity(intent);
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//
//                        } else {
//
//                        }
//                    }
//                }
//                new completeTask().execute();
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//            }
//        });


        class completeTask extends AsyncTask<Void, Void, Boolean> {
            JSONArray raw = null;
            @Override
            protected Boolean doInBackground(Void... voids) {
                OkHttpClient client = new OkHttpClient();
                JSONObject jsonObject = new JSONObject();
                MediaType mediaType = MediaType.get("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(jsonObject.toString(), mediaType);
                Request request = new Request.Builder()
                        .url(api.url+"/mission/task")
                        .post(body)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if(response.code()==200) {
                        raw = new JSONArray(response.body().string());
                        return true;
                    }
                } catch (IOException|JSONException e) {
                    e.printStackTrace();
                }
                return false;
            }

            public void onPostExecute(Boolean result){
                if (result) {

                    displayListView(raw);
                }
            }
        }
        new completeTask().execute();



}

    public void displayListView(JSONArray raw) {
        Intent intent = getIntent();
        String name = intent.getStringExtra("text");

        listView = findViewById(R.id.taskview);
        final ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        for (int i = 0; i < raw.length(); i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            try {
                JSONObject temp = raw.getJSONObject(i);
                tempp.add(temp.getString("id"));
                hashMap.put("date",temp.getString("date"));
                hashMap.put("category",temp.getString("category"));
                hashMap.put("location",temp.getString("location"));
                hashMap.put("car_id",temp.getString("car_id"));
                hashMap.put("app_situation",temp.getString("app_situation"));
                hashMap.put("note",temp.getString("note"));
                arrayList.add(hashMap);



            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        System.out.println(arrayList);
        Log.d("Test", "displayListView: "+arrayList);
        String[] from = {"date", "category","app_situation","note"};
        int[] value = {R.id.textview1, R.id.textview2,R.id.textview3,R.id.textview4};
        simpleAdapter =
                new SimpleAdapter(this, arrayList, R.layout.sql_tasklist, from, value);
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(onItemClickListener);

    }

    private AdapterView.OnItemClickListener onItemClickListener
            = ((parent, view, position, id) -> {
                String ID = tempp.get(position);



            class completeTask extends AsyncTask<Void, Void, Boolean> {
                JSONArray raw = null;
                Intent intent = getIntent();
                String name = intent.getStringExtra("text");

                @Override
                protected Boolean doInBackground(Void... voids) {
                    OkHttpClient client = new OkHttpClient();
                    JSONObject jsonObject = new JSONObject();
                    Request request = new Request.Builder()
                            .url(api.url + "/mission/start/"+ID)
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
                            m += String.format("DATE:%s\n\n", raw.getJSONObject(0).getString("date"));
                            Intent intent = new Intent(prepare_mission.this, implementmission.class);
                            prepare_mission.this.finish();
                            String l = String.format(ID);
                            intent.putExtra("id",l);
                            startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } else {

                    }
                }
            }
            new completeTask().execute();



    });


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu2,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.help_item:
                new AlertDialog.Builder(prepare_mission.this)
                        .setTitle("about")
                        .setMessage("this system is develop to help ambulance \n" +
                                "reach the emergency site or hospital faster and safer.")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setNegativeButton("cancel",null).create()
                        .show();
                return true;
            case R.id.history_item:
                Intent his = new Intent(prepare_mission.this, history.class);
                startActivity(his);
                return true;
            case R.id.logout_item:
                Intent intent = new Intent(prepare_mission.this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}








