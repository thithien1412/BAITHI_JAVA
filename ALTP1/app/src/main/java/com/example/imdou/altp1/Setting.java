package com.example.imdou.altp1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class Setting extends AppCompatActivity {

    int language;
    Button btnok;
    TextView txt1;
    RadioButton rad1,rad2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        btnok = (Button) findViewById(R.id.btnok);
        txt1 = (TextView) findViewById(R.id.btnok);
        rad1 = (RadioButton) findViewById(R.id.rad1);
        rad2 = (RadioButton) findViewById(R.id.rad2);
        language = getIntent().getExtras().getInt("language");

        if (language == 1) {btnok.setText("Confirm"); txt1.setText("Select language"); rad1.setText("Vietnamese"); rad2.setText("English");}
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(Setting.this,MainActivity.class);
                if (rad1.isChecked())  inte.putExtra("language",0);
                else inte.putExtra("language",1);
                startActivity(inte);
                finish();
            }
        });
    }
}
