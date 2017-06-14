package com.example.imdou.altp1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ScoreBoard extends AppCompatActivity {

    ListView lv_score;
    ArrayList<Player_score> score_list;
    int current_score;
    CustomAdapter adapter;
    Button btnHome;
    String API = "http://192.168.52.1:8083/api/scores";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        lv_score = (ListView) findViewById(R.id.lv_score);
        score_list = new ArrayList<>();
        btnHome = (Button) findViewById(R.id.btnHome);
        current_score = getIntent().getExtras().getInt("current_score");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new getJSON().execute(API);
            }
        });
        showalert(current_score);
    }


    public void showalert(final int current_score){
        AlertDialog.Builder inputAlert = new AlertDialog.Builder(ScoreBoard.this);
        inputAlert.setTitle("Lưu điểm");
        inputAlert.setMessage("Tên người chơi: ");
        final EditText edtname = new EditText(ScoreBoard.this);
        inputAlert.setView(edtname);
        inputAlert.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = edtname.getText().toString();
                API = API + "?name=" + name + "&s=" + current_score;
                new postJSON().execute(API);

            }
        });
        inputAlert.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                btnHome.setVisibility(View.VISIBLE);
                btnHome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent inte = new Intent(ScoreBoard.this,MainActivity.class);
                        startActivity(inte);
                        finish();
                    }
                });
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = inputAlert.create();
        alertDialog.show();
    }
    class getJSON extends AsyncTask<String,Integer,String>
    {

        @Override
        protected String doInBackground(String... params) {
            String result = "";

            try {
                URL url = new URL(params[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                BufferedReader input = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                //InputStream input = httpURLConnection.getInputStream();
                String byteCha;

                while ((byteCha = input.readLine()) != null) {
                    result = result + byteCha;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return result ;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray q = new JSONArray(s);
                score_list = new ArrayList<>();
                for (int i = 0 ;i< q.length();i++)
                {
                    JSONObject obj = q.getJSONObject(i);
                    Player_score score = new Player_score();
                    score.name = obj.getString("name");
                    score.score = obj.getInt("scores");
                    score_list.add(score);
                }
                adapter = new CustomAdapter(ScoreBoard.this,R.layout.custom_listview,score_list);
                lv_score.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    class postJSON extends AsyncTask<String,Integer,String>
    {

        @Override
        protected String doInBackground(String... params) {
            String result = "";

            try {
                URL url = new URL(params[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();


                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os,"utf-8"));
                writer.write("");
                writer.flush();
                writer.close();
                os.close();




                BufferedReader input = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                //InputStream input = httpURLConnection.getInputStream();
                String byteCha;

                while ((byteCha = input.readLine()) != null) {
                    result = result + byteCha;
                }



            } catch (IOException e) {
                e.printStackTrace();
            }
            return result ;
        }

        @Override
        protected void onPostExecute(String s) {
            new getJSON().execute(API);
            AlertDialog.Builder alert = new AlertDialog.Builder(ScoreBoard.this);
            alert.setTitle("");
            alert.setMessage("Lưu điểm thành công");
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.show();
            btnHome.setVisibility(View.VISIBLE);
            btnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent inte = new Intent(ScoreBoard.this,MainActivity.class);
                    startActivity(inte);
                    finish();
                }
            });

        }
    }
}
