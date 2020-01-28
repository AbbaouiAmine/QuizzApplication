package com.amineabbaoui.quizapp_o2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amineabbaoui.quizapp_o2.logic.Reponse;

public class ResultActivity extends AppCompatActivity {

    Button exitBtn;
    Button tryBtn;
    TextView txt_result;
    TextView txt_result_msg;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        exitBtn=(Button)findViewById(R.id.btn_exit);
        tryBtn=(Button)findViewById(R.id.btn_try_again);
        txt_result=(TextView)findViewById(R.id.txt_result);
        progressBar=(ProgressBar)findViewById(R.id.progress_bar_result);
        txt_result_msg=(TextView)findViewById(R.id.txt_result_msg);

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);

                finishAffinity();
                System.exit(0);
            }
        });

        tryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent=new Intent(ResultActivity.this,CameraActivity.class);
            startActivity(intent);
            }
        });

        Intent intent = getIntent();

        String score_msg=intent.getStringExtra("score_msg");
        double score_prc=Double.parseDouble(intent.getStringExtra("score_prc"));
        String score_m=intent.getStringExtra("score");
        txt_result.setText(score_msg);
        txt_result_msg.setText(score_m);

        progressBar.setSecondaryProgress(100);
        progressBar.setMax(100);
        progressBar.setProgress((int)score_prc);

    }
}
