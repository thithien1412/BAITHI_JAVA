package com.example.imdou.altp1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.IntegerRes;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.RunnableFuture;

public class MainActivity extends AppCompatActivity {

    ArrayList<Question> questionlist ;
    TextView txtload;
    int current_score=0;
    int current_level=1;
    Button btnplay,btnsetting,btnexit;
    MediaPlayer media;
    final String API = "http://192.168.52.1:8083/api/questions?level=1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnplay = (Button) findViewById(R.id.btnplay);
        btnsetting = (Button) findViewById(R.id.btnsetting);
        btnexit = (Button) findViewById(R.id.btnexit);
        txtload = (TextView) findViewById(R.id.txtload);
        questionlist = new ArrayList<>();
        btnplay.setVisibility(View.INVISIBLE);
        btnsetting.setVisibility(View.INVISIBLE);
        btnexit.setVisibility(View.INVISIBLE);
        media = MediaPlayer.create(MainActivity.this,R.raw.intro);
        media.start();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new getJSON().execute(API);
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        media.stop();

    }



    @Override
    protected void onRestart() {
        super.onRestart();
        media = MediaPlayer.create(MainActivity.this,R.raw.intro);
        media.start();

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

                for (int i = 0 ;i< q.length();i++)
                {
                    JSONObject obj = q.getJSONObject(i);
                    Question ques = new Question();
                    ques.q_title = obj.getString("q_title");
                    ques.answer1 = obj.getString("answer1");
                    ques.answer2 = obj.getString("answer2");
                    ques.answer3 = obj.getString("answer3");
                    ques.answer4 = obj.getString("answer4");
                    ques.level = obj.getInt("level");
                    ques.correct_answer = obj.getInt("correct_answer");
                    questionlist.add(ques);
                }
                txtload.setVisibility(View.GONE);
                btnplay.setVisibility(View.VISIBLE);
                btnsetting.setVisibility(View.VISIBLE);
                btnexit.setVisibility(View.VISIBLE);
                btnplay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent inte = new Intent(MainActivity.this,QuestionActivity.class);
                        inte.putExtra("questions",questionlist);
                        inte.putExtra("current_score",current_score);
                        inte.putExtra("current_level",current_level);
                        inte.putExtra("check50",false);
                        inte.putExtra("checkassit",false);
                        inte.putExtra("checkchange",false);
                        startActivity(inte);
                        finish();
                    }
                });


                btnsetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });


                btnexit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.exit(0);
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
