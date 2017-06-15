package com.example.imdou.altp1;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class QuestionActivity extends AppCompatActivity {
    ArrayList<Question> questions;
    Question question,question1;
    Button btn1,btn2,btn3,btn4;
    TextView title,count,txtlevel,txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt9,txt10,txt11,txt12,txt13,txt14,txt15;
    TextView txta,txtb,txtc,txtd,pa,pb,pc,pd;
    ImageButton btn50,btnassit,btnchange;
    Random random;
    int current_level,current_score;
    Boolean check50,checkassit,checkchange;
    Dialog dialog;
    CountDownTimer countdown;
    MediaPlayer media;

    @Override
    protected void onPause() {
        super.onPause();
        countdown.cancel();
        media.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        questions = new ArrayList<>();
        question = new Question();
        title = (TextView) findViewById(R.id.title);
        count = (TextView) findViewById(R.id.count);
        random = new Random();

        btn50 = (ImageButton) findViewById(R.id.btn50);
        btnassit = (ImageButton) findViewById(R.id.btnassit);
        btnchange = (ImageButton) findViewById(R.id.btnchange);

        check50 = getIntent().getExtras().getBoolean("check50");
        checkassit = getIntent().getExtras().getBoolean("checkassit");
        checkchange = getIntent().getExtras().getBoolean("checkchange");
        media = MediaPlayer.create(this,R.raw.background);
        media.start();


        if (check50) { btn50.setEnabled(false); btn50.setBackgroundResource(R.drawable.none50);}
            else btn50.setBackgroundResource(R.drawable.fiftypercent);
        if (checkassit) {btnassit.setEnabled(false); btnassit.setBackgroundResource(R.drawable.noneassit);}
        else btnassit.setBackgroundResource(R.drawable.assist);
        if (checkchange) {btnchange.setEnabled(false);btnchange.setBackgroundResource(R.drawable.nonechange);}
        else btnchange.setBackgroundResource(R.drawable.change);


        dialog = new Dialog(QuestionActivity.this);
        dialog.setContentView(R.layout.assit);
        dialog.setTitle("Ý kiến khán giả");
        Button btnOk = (Button) dialog.findViewById(R.id.btnxacnhan);
        txta = (TextView) dialog.findViewById(R.id.txta);
        txtb = (TextView) dialog.findViewById(R.id.txtb);
        txtc = (TextView) dialog.findViewById(R.id.txtc);
        txtd = (TextView) dialog.findViewById(R.id.txtd);
        pa = (TextView) dialog.findViewById(R.id.pa);
        pb = (TextView) dialog.findViewById(R.id.pb);
        pc = (TextView) dialog.findViewById(R.id.pc);
        pd = (TextView) dialog.findViewById(R.id.pd);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        btn50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a,b,c;
                a = random.nextInt(2);
                if (a == 0) { b =1; c = 1;}
                else {
                    b = random.nextInt(2);
                    if (b==0) c = 1;
                    else c = 0;
                }
                if (question.correct_answer == 1) {
                    if (a==1) {btn2.setText("");btn2.setEnabled(false);}
                    if (b==1) {btn3.setText("");btn3.setEnabled(false);}
                    if (c==1) {btn4.setText("");btn4.setEnabled(false);}
                }
                if (question.correct_answer == 2) {
                    if (a==1) {btn1.setText("");btn1.setEnabled(false);}
                    if (b==1) {btn3.setText("");btn3.setEnabled(false);}
                    if (c==1) {btn4.setText("");btn4.setEnabled(false);}
                }
                if (question.correct_answer == 3) {
                    if (a==1) {btn1.setText("");btn1.setEnabled(false);}
                    if (b==1) {btn2.setText("");btn2.setEnabled(false);}
                    if (c==1) {btn4.setText("");btn4.setEnabled(false);}
                }
                if (question.correct_answer == 4) {
                    if (a==1) {btn1.setText("");btn1.setEnabled(false);}
                    if (b==1) {btn2.setText("");btn2.setEnabled(false);}
                    if (c==1) {btn3.setText("");btn3.setEnabled(false);}
                }

                check50=true;
                btn50.setBackgroundResource(R.drawable.none50);
                btn50.setEnabled(false);
            }
        });
        btnassit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a,b,c,d;
                b = random.nextInt(51);
                c = random.nextInt(51-b);
                d = random.nextInt(51-b-c);
                a = 50+ random.nextInt(51-b-c-d);
                if (question.correct_answer==1) {pa.setText(a+"%");pb.setText(b+"%");pc.setText(c+"%");pd.setText(d+"%"); }
                if (question.correct_answer==2) {pa.setText(b+"%");pb.setText(a+"%");pc.setText(c+"%");pd.setText(d+"%"); }
                if (question.correct_answer==3) {pa.setText(b+"%");pb.setText(c+"%");pc.setText(a+"%");pd.setText(d+"%"); }
                if (question.correct_answer==4) {pa.setText(b+"%");pb.setText(c+"%");pc.setText(d+"%");pd.setText(a+"%"); }
                a=a*4;
                b=b*4;
                c=c*4;
                d=d*4;

                if (question.correct_answer==1) {txta.getLayoutParams().height = a; txtb.getLayoutParams().height=b; txtc.getLayoutParams().height=c; txtd.getLayoutParams().height=d;}
                if (question.correct_answer==2) {txta.getLayoutParams().height = b; txtb.getLayoutParams().height=a; txtc.getLayoutParams().height=c; txtd.getLayoutParams().height=d;}
                if (question.correct_answer==3) {txta.getLayoutParams().height = b; txtb.getLayoutParams().height=c; txtc.getLayoutParams().height=a; txtd.getLayoutParams().height=d;}
                if (question.correct_answer==4) {txta.getLayoutParams().height = b; txtb.getLayoutParams().height=c; txtc.getLayoutParams().height=d; txtd.getLayoutParams().height=a;}
                dialog.show();
                checkassit=true;
                btnassit.setBackgroundResource(R.drawable.noneassit);
                btnassit.setEnabled(false);
            }
        });
        btnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do {
                    question1 = questions.get(random.nextInt(questions.size()));

                }
                while (question1.q_title==question.q_title);
                question=question1;
                title.setText(question.q_title);
                btn1.setText(question.answer1);
                btn2.setText(question.answer2);
                btn3.setText(question.answer3);
                btn4.setText(question.answer4);
                btn1.setEnabled(true);
                btn2.setEnabled(true);
                btn3.setEnabled(true);
                btn4.setEnabled(true);
                checkchange = true;
                btnchange.setBackgroundResource(R.drawable.nonechange);
                btnchange.setEnabled(false);
            }
        });

        txtlevel = (TextView) findViewById(R.id.txtlevel);

        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt2);
        txt3 = (TextView) findViewById(R.id.txt3);
        txt4 = (TextView) findViewById(R.id.txt4);
        txt5 = (TextView) findViewById(R.id.txt5);
        txt6 = (TextView) findViewById(R.id.txt6);
        txt7 = (TextView) findViewById(R.id.txt7);
        txt8 = (TextView) findViewById(R.id.txt8);
        txt9 = (TextView) findViewById(R.id.txt9);
        txt10 = (TextView) findViewById(R.id.txt10);
        txt11 = (TextView) findViewById(R.id.txt11);
        txt12 = (TextView) findViewById(R.id.txt12);
        txt13 = (TextView) findViewById(R.id.txt13);
        txt14 = (TextView) findViewById(R.id.txt14);
        txt15 = (TextView) findViewById(R.id.txt15);

        txt1.setText("1:  "+ListConst_Score.constant_list_score[0]);
        txt2.setText("2:  "+ListConst_Score.constant_list_score[1]);
        txt3.setText("3:  "+ListConst_Score.constant_list_score[2]);
        txt4.setText("4:  "+ListConst_Score.constant_list_score[3]);
        txt5.setText("5:  "+ListConst_Score.constant_list_score[4]);
        txt6.setText("6:  "+ListConst_Score.constant_list_score[5]);
        txt7.setText("7:  "+ListConst_Score.constant_list_score[6]);
        txt8.setText("8:  "+ListConst_Score.constant_list_score[7]);
        txt9.setText("9:  "+ListConst_Score.constant_list_score[8]);
        txt10.setText("10:  "+ListConst_Score.constant_list_score[9]);
        txt11.setText("11:  "+ListConst_Score.constant_list_score[10]);
        txt12.setText("12:  "+ListConst_Score.constant_list_score[11]);
        txt13.setText("13:  "+ListConst_Score.constant_list_score[12]);
        txt14.setText("14:  "+ListConst_Score.constant_list_score[13]);
        txt15.setText("15:  "+ListConst_Score.constant_list_score[14]);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        questions = (ArrayList<Question>) getIntent().getSerializableExtra("questions");
        current_level = getIntent().getExtras().getInt("current_level");
        current_score = getIntent().getExtras().getInt("current_score");
        switch (current_level){
            case 1 : txt1.setTextColor(Color.parseColor("#e2f20c")); break;
            case 2 : txt2.setTextColor(Color.parseColor("#e2f20c")); break;
            case 3 : txt3.setTextColor(Color.parseColor("#e2f20c")); break;
            case 4 : txt4.setTextColor(Color.parseColor("#e2f20c")); break;
            case 5 : txt5.setTextColor(Color.parseColor("#e2f20c")); break;
            case 6 : txt6.setTextColor(Color.parseColor("#e2f20c")); break;
            case 7 : txt7.setTextColor(Color.parseColor("#e2f20c")); break;
            case 8 : txt8.setTextColor(Color.parseColor("#e2f20c")); break;
            case 9 : txt9.setTextColor(Color.parseColor("#e2f20c")); break;
            case 10 : txt10.setTextColor(Color.parseColor("#e2f20c")); break;
            case 11 : txt11.setTextColor(Color.parseColor("#e2f20c")); break;
            case 12 : txt12.setTextColor(Color.parseColor("#e2f20c")); break;
            case 13 : txt13.setTextColor(Color.parseColor("#e2f20c")); break;
            case 14 : txt14.setTextColor(Color.parseColor("#e2f20c")); break;
            case 15 : txt15.setTextColor(Color.parseColor("#e2f20c")); break;
        }


        txtlevel.setText( txtlevel.getText().toString() + " " + current_level  );


        countdown = new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                count.setText(millisUntilFinished/1000 + "");
            }

            @Override
            public void onFinish() {
                result(false);
            }
        };
        countdown.start();


        question = questions.get(random.nextInt(questions.size()));
        title.setText(question.q_title);
        btn1.setText(question.answer1);
        btn2.setText(question.answer2);
        btn3.setText(question.answer3);
        btn4.setText(question.answer4);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    showAlert(1,question.correct_answer);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert(2,question.correct_answer);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert(3,question.correct_answer);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert(4,question.correct_answer);
            }
        });

    }
    protected void result(boolean check_correct){
        Intent inte = new Intent(QuestionActivity.this,AfterLevel.class);
        if (check_correct) current_score = ListConst_Score.constant_list_score[current_level-1];
        else if (current_level>10 ) current_score = ListConst_Score.constant_list_score[9];
            else if (current_level>5) current_score =  ListConst_Score.constant_list_score[4];
             else if (current_level == 1)  current_score=0;
                else current_score = ListConst_Score.constant_list_score[current_level-2];
        inte.putExtra("current_score",current_score);
        inte.putExtra("current_level",current_level);
        inte.putExtra("check_correct",check_correct);
        inte.putExtra("check50",check50);
        inte.putExtra("checkassit",checkassit);
        inte.putExtra("checkchange",checkchange);
        countdown.cancel();
        startActivity(inte);
        finish();

    }
    protected boolean Test(int thisposition, int correct){
        if (thisposition == correct) return true;
        return false;

    }
    protected void showAlert(final int thisposition, final int correct){
        AlertDialog.Builder alert = new AlertDialog.Builder(QuestionActivity.this);

        alert.setTitle("");
        alert.setMessage("Bạn có chắc chăn chọn đáp án này ?");
        alert.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Test(thisposition,correct)) result(true);//alert1.setMessage("Correct");
                else result(false);
                dialog.dismiss();
            }
        });
        alert.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }
}
