package com.example.imdou.altp1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AfterLevel extends AppCompatActivity {
    TextView txtScore,txtResult;
    int current_score, current_level;
    Button btnNext, btnStop, btnConfirm;
    Boolean check_correct;
    Boolean check50,checkassit,checkchange;
    ArrayList<Question> questionlist ;
    MediaPlayer media;
    final String API = "http://192.168.52.1:8083/api/questions?level=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_level);
        txtScore = (TextView) findViewById(R.id.txtScore);
        txtResult = (TextView) findViewById(R.id.txtResult);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        questionlist = new ArrayList<>();
        current_score = getIntent().getExtras().getInt("current_score");
        current_level = getIntent().getExtras().getInt("current_level");
        check_correct = getIntent().getExtras().getBoolean("check_correct");
        check50 = getIntent().getExtras().getBoolean("check50");
        checkassit = getIntent().getExtras().getBoolean("checkassit");
        checkchange = getIntent().getExtras().getBoolean("checkchange");
        txtScore.setText(current_score + "");
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(AfterLevel.this,ScoreBoard.class);
                inte.putExtra("current_score",current_score);
                startActivity(inte);
                finish();
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent inte = new Intent(AfterLevel.this,ScoreBoard.class);
                inte.putExtra("current_score",current_score);
                startActivity(inte);
                finish();
            }
        });
        if (check_correct) {
            media = MediaPlayer.create(this,R.raw.right);
            media.start();
           if (current_level == 15) {
               txtResult.setText("Xin chúc mừng bạn đã trở thành triệu phú");
               btnNext.setVisibility(View.GONE);
               btnStop.setVisibility(View.GONE);
           }
           else
           {txtResult.setText("Chúc mừng bạn đã trả lời đúng");
            btnConfirm.setVisibility(View.GONE);}

        }
        else {
            media = MediaPlayer.create(this,R.raw.wrong);
            media.start();
            btnNext.setVisibility(View.GONE);
            btnStop.setVisibility(View.GONE);
            txtResult.setText("Bạn đã thua cuộc! \nChúc bạn may mắn lần sau :D");
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new getJSON().execute(API+(current_level+1));
            }
        });
    }

    class getJSON extends AsyncTask<String, Integer, String> {

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
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray q = new JSONArray(s);

                for (int i = 0; i < q.length(); i++) {
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
                btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent inte = new Intent(AfterLevel.this,QuestionActivity.class);
                        inte.putExtra("questions",questionlist);
                        inte.putExtra("current_score",current_score);
                        inte.putExtra("current_level",current_level+1);
                        inte.putExtra("check50",check50);
                        inte.putExtra("checkassit",checkassit);
                        inte.putExtra("checkchange",checkchange);

                        startActivity(inte);
                        finish();
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
